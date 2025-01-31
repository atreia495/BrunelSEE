
package uk.ac.brunel.see.impl.fed;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import uk.ac.brunel.see.HlaCredentials;
import uk.ac.brunel.see.HlaCredentialsProvider;
import uk.ac.brunel.see.HlaFederateId;
import uk.ac.brunel.see.HlaLogicalTime;
import uk.ac.brunel.see.HlaSettings;
import uk.ac.brunel.see.HlaTimeStamp;
import uk.ac.brunel.see.HlaTimeStampFactory;
import uk.ac.brunel.see.HlaTuning;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.HlaFederateIdImpl;
import uk.ac.brunel.see.impl.HlaLogicalTimeImpl;
import uk.ac.brunel.see.impl.HlaWorldImpl;
import uk.ac.brunel.see.impl.utils.MapAdapter;
import uk.ac.brunel.see.impl.utils.NameValueMap;

import se.pitch.ral.api.*;
import se.pitch.ral.api.detective.Installer;
import se.pitch.ral.api.exceptions.*;
import se.pitch.ral.api.time.LogicalTimeInteger64;
import se.pitch.ral.api.time.LogicalTimeInteger64Factory;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class FederateManager extends FederateAmbassadorAdapter {

   public interface DdmListener {
      boolean connected(RtiInfo rtiInfo) throws HlaFomException, HlaInternalException, HlaNormalizerException, HlaNotConnectedException,
                                                HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException;
      void disconnected();
   }

   private ObjectManager _objectManager;
   private FederateInteractionManager _interactionManager;
   private DdmListener _ddmListener = null;
   private final HlaWorldImpl _hlaWorld;
   private final HlaTimeStampFactory _timeStampFactory;

   private boolean _nameReservationSucceeded;
   private final Object _nameReservationSemaphore = new Object();

   private boolean _timeConstrained;
   private final BlockingQueue<se.pitch.ral.api.LogicalTime> _timeConstrainedQueue = new ArrayBlockingQueue<se.pitch.ral.api.LogicalTime>(1);
   private boolean _timeRegulating;
   private final BlockingQueue<se.pitch.ral.api.LogicalTime> _timeRegulatingQueue = new ArrayBlockingQueue<se.pitch.ral.api.LogicalTime>(1);
   private final BlockingQueue<se.pitch.ral.api.LogicalTime> _timeAdvanceQueue = new ArrayBlockingQueue<se.pitch.ral.api.LogicalTime>(1);
   private LogicalTimeInteger64Factory _logicalTimeFactory;  //@Nullable

   private boolean _restoreRequestSucceeded;
   private final Object _restoreRequestSemaphore = new Object();

   private FederateHandle _federateHandle = null;
   private RTIambassador _rtiAmbassador = null;
   private String _federationName;
   private SaveRestManager _saveRestManager;
   private boolean _expectingGrantAfterRestore = false;
   private volatile boolean _handleDisconnectDetected = false;
   private final long _informTimeoutMs = 5000;

   public FederateManager(HlaWorldImpl hlaWorld, HlaTimeStampFactory timeStampFactory) {
      _hlaWorld = hlaWorld;
      _timeStampFactory = timeStampFactory;
   }

   void setObjectManager(ObjectManager objectManager) {
      _objectManager = objectManager;
   }

   void setInteractionManager(FederateInteractionManager interactionManager) {
      _interactionManager = interactionManager;
   }

   void setSaveRestManager(SaveRestManager saveRestManager) {
      _saveRestManager = saveRestManager;
   }

   public void setDdmListener(DdmListener ddmListener) {
      _ddmListener = ddmListener;
   }

   private HlaSettings.HlaVersion getHlaVersion(HlaVersion hlaVersion) {
      switch (hlaVersion) {
         case HLA_13:
            return HlaSettings.HlaVersion.HLA_13;
         case HLA_1516:
            return HlaSettings.HlaVersion.HLA_1516;
         case HLA_EVOLVED:
            return HlaSettings.HlaVersion.HLA_EVOLVED;
         case HLA_4:
            return HlaSettings.HlaVersion.HLA_4;
         case NONE:
            return HlaSettings.HlaVersion.NONE;
      }
      return null;
   }

   private URL[] getFomUrls(HlaSettings settings, HlaSettings.CallType callType) {
      HlaSettings.HlaVersion hlaVersion = getHlaVersion(_rtiAmbassador.getHLAversion());
      URL baseUrl = settings.getFomUrl(callType, hlaVersion);
      URL[] additionalUrls = settings.getAdditionalFomUrls(callType, hlaVersion);

      int baseLength = baseUrl == null ? 0 : 1;
      URL[] urls = new URL[baseLength + additionalUrls.length];
      if (baseUrl != null) {
         urls[0] = baseUrl;
      }
      System.arraycopy(additionalUrls, 0, urls, baseLength, additionalUrls.length);

      return urls;
   }

   private List<String> getAvailableProfiles(List<RtiFactory> rtiFactories) {
      List<String> availableProfiles = new ArrayList<String>();
      for (RtiFactory factory : rtiFactories) {
         if (factory.isAvailable()) {
            availableProfiles.add(factory.getRtiInfo().getProfile());
         }
      }
      return availableProfiles;
   }

   private HlaCredentialsProvider getCredentialsProvider(HlaSettings settings, HlaSettings.HlaVersion hlaVersion) throws HlaCredentialsProviderException {
      try {
         return settings.getCredentialsProvider(hlaVersion);
      } catch (RuntimeException e) {
         throw new HlaCredentialsProviderException("Failed to get credentials provider from settings", e);
      }
   }

   private HlaCredentials getCredentials(HlaCredentialsProvider provider, HlaSettings.HlaVersion hlaVersion) throws HlaCredentialsProviderException {
      if (provider == null) {
         return null;
      }
      try {
         return provider.getCredentials(hlaVersion);
      } catch (RuntimeException e) {
         throw new HlaCredentialsProviderException("Failed to get credentials from provider", e);
      }
   }

   private Credentials wrapCredentials(HlaCredentials credentials)
   {
      // Propagate null credentials
      if (credentials == null) {
         return null;
      }

      return new Credentials() {
         @Override
         public String getType() {
            return credentials.getType();
         }

         @Override
         public byte[] getData() {
            return credentials.getData();
         }
      };
   }

   private void warnIfCredentialsNotSupported(HlaCredentials credentials, HlaSettings.HlaVersion hlaVersion) {
      if (hlaVersion.ordinal() < HlaVersion.HLA_4.ordinal()
            && HlaTuning.WARN_ON_CREDENTIALS_NOT_SUPPORTED
            && isNotEmptyCredentials(credentials)) {

         _hlaWorld.postException(new HlaCredentialsNotSupportedException(getCredentialsNotUsedMessage(hlaVersion)));
      }
   }

   private String getCredentialsNotUsedMessage(HlaSettings.HlaVersion hlaVersion) {
      return "Provided credentials not used, not supported by current HLA version: " + hlaVersion;
   }

   private String getCredentialsNotUsedWarningIfApplicable(
         Exception e,
         HlaCredentials credentials,
         HlaSettings.HlaVersion hlaVersion) {

      if (e.getMessage() != null && e.getMessage().contains("Federate not authorized")
            && hlaVersion.ordinal() < HlaVersion.HLA_4.ordinal()
            && isNotEmptyCredentials(credentials)) {

         return getCredentialsNotUsedMessage(hlaVersion);
      }

      return "";
   }

   private boolean isNotEmptyCredentials(HlaCredentials credentials) {
      return credentials != null && !HlaCredentials.NO_CREDENTIALS_TYPE.equals(credentials.getType());
   }

   private void cleanCredentials(HlaCredentialsProvider credProvider, HlaCredentials credentials) {
      try {
         if (credProvider != null) {
            credProvider.clean(credentials);
         }
      } catch (RuntimeException e) {
         _hlaWorld.postException(new HlaCredentialsProviderException("Unexpected exception thrown in credentials provider clean.", e));
      }
   }

   public HlaLogicalTime connect(HlaSettings settings)
         throws HlaConnectException, HlaInvalidLicenseException, HlaRtiException,
                HlaFomException, HlaInternalException, HlaNotConnectedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException, HlaNormalizerException,
                HlaCredentialsProviderException, HlaInvalidCredentialsException, HlaUnauthorizedException {
      boolean useTimeManagement = settings.getTimeRegulating() || settings.getTimeConstrained();
      String profile = settings.getRtiDriverProfile(getAvailableProfiles(RAL.getRtiFactories()));
      String localSettingsDesignator = settings.getLocalSettingsDesignator();
      String crcHost = settings.getCrcHost();
      int crcPort = settings.getCrcPort();
      String crcSettings = "";
      boolean destroyFederationOnDisconnect = settings.getDestroyFederation();
      RtiInfo rtiInfo;
      HlaCredentialsProvider credProvider = null;
      HlaCredentials credentials = null;
      HlaSettings.HlaVersion hlaVersion = HlaSettings.HlaVersion.NONE;
      try {
         RTIambassador rtiAmbassador;
         RtiFactory rtiFactory;
         if (profile != null && profile.length() == 1) {
            rtiFactory = RAL.getRtiFactory(profile.charAt(0));
            hlaVersion = getHlaVersion(rtiFactory.getRtiInfo().getHlaVersion());
            credProvider = getCredentialsProvider(settings, hlaVersion);
            credentials = getCredentials(credProvider, hlaVersion);
            if ((hlaVersion == HlaSettings.HlaVersion.HLA_EVOLVED ||
                 hlaVersion == HlaSettings.HlaVersion.HLA_4 ||
                 hlaVersion == HlaSettings.HlaVersion.NONE) &&
                (!localSettingsDesignator.isEmpty() || crcHost.isEmpty())) {
               crcSettings = "Profile " + profile + ", LSD '" + localSettingsDesignator + "'. ";
               rtiAmbassador = rtiFactory.getRTIambassador(localSettingsDesignator, wrapCredentials(credentials));
            } else {
               crcSettings = "Profile " + profile + ", CRC Host " + crcHost + ":" + crcPort + ". ";
               rtiAmbassador = rtiFactory.getRTIambassador(crcHost, crcPort, wrapCredentials(credentials));
            }
         } else {
            rtiFactory = RAL.getRtiFactory();
            if (rtiFactory == null) {
               throw new HlaConnectException("Unable to get RTI ambassador from RTI Driver. RTI missing from Classpath?");
            }

            hlaVersion = getHlaVersion(rtiFactory.getRtiInfo().getHlaVersion());
            credProvider = getCredentialsProvider(settings, hlaVersion);
            credentials = getCredentials(credProvider, hlaVersion);

            crcSettings = "Profile ANY, LSD '" + localSettingsDesignator + "', CRC Host " + crcHost + ":" + crcPort + ". ";
            rtiAmbassador = RAL.getRTIambassador(localSettingsDesignator, crcHost, crcPort, wrapCredentials(credentials));
         }
         rtiInfo = rtiFactory.getRtiInfo();
         _rtiAmbassador = Installer.installDetectives(settings.getRtiDriverDetectives(), rtiAmbassador);

         if (_rtiAmbassador != null && (useTimeManagement || HlaTuning.CREATE_FEDERATION_WITH_TIME_REPRESENTATION || HlaTuning.ALWAYS_USE_LOGICAL_TIME)) {
            String logicalTimeFactoryClass = settings.getLogicalTimeFactoryName(getHlaVersion(_rtiAmbassador.getHLAversion()), "HLAinteger64Time");
            _logicalTimeFactory = rtiFactory.getLogicalTimeFactory(LogicalTimeInteger64Factory.class, logicalTimeFactoryClass);
         } else {
            _logicalTimeFactory = null;
         }
      } catch (InvalidLicense e) {
          throw new HlaInvalidLicenseException("Invalid RTI Driver License", e);
      } catch (InvalidSettings | ConnectionFailed e) {
         String credentialsWarning = getCredentialsNotUsedWarningIfApplicable(e, credentials, hlaVersion);
         throw new HlaConnectException("Unable to connect to CRC. " + crcSettings + credentialsWarning, e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Unable to connect to CRC. " + crcSettings, e);
      } catch (InvalidCredentials e) {
         throw new HlaInvalidCredentialsException("Invalid Credentials. ", e);
      } catch (Unauthorized e) {
         throw new HlaUnauthorizedException("Not authorized to connect to CRC. " + crcSettings, e);
      } finally {
         cleanCredentials(credProvider, credentials);
      }

      if (_rtiAmbassador == null) {
         throw new HlaConnectException("Unable to get RTI ambassador from RTI Driver. RTI missing from Classpath?");
      }
      if (useTimeManagement && _logicalTimeFactory == null) {
         disconnectRtiAndThrow(new HlaConnectException("Unable to get Logical Time factory from RTI Driver"));
      }

      if (settings.getCreateFederation()) {
         try {
            _rtiAmbassador.createFederationExecution(settings.getFederationName(),
                                                     getFomUrls(settings, HlaSettings.CallType.CREATE),
                                                     null,
                                                     _logicalTimeFactory != null ? _logicalTimeFactory.getLogicalTimeFactories() : null);
         } catch (FederationExecutionAlreadyExists e) {
            //ignore
         } catch (CouldNotOpenFDD | ErrorReadingFDD e) {
            disconnectRtiAndThrow(new HlaFomException("Failed to create federation", e));

         } catch (InvalidLogicalTimeFactory | InvalidLogicalTimeIntervalFactory e) {
            disconnectRtiAndThrow(new HlaInternalException("Failed to create federation", e));

         } catch (RTIinternalError e) {
            String credentialsWarning = getCredentialsNotUsedWarningIfApplicable(e, credentials, hlaVersion);
            disconnectRtiAndThrow(new HlaRtiException("Failed to create federation. " + credentialsWarning, e));

         } catch (NotConnected e) {
            _rtiAmbassador = null;
            throw new HlaConnectException("Failed to create federation", e);

         } catch (Unauthorized e) {
            disconnectRtiAndThrow(new HlaUnauthorizedException("Not authorized to create federation '" + settings.getFederationName() + "'", e));
         }
      }

      try {
         _federationName = settings.getFederationName();
         _federateHandle = _rtiAmbassador.joinFederationExecution(settings.getFederateName(),
                                                                  settings.getFederateType(),
                                                                  _federationName,
                                                                  getFomUrls(settings, HlaSettings.CallType.JOIN),
                                                                  this,
                                                                  _logicalTimeFactory != null ? _logicalTimeFactory.getLogicalTimeFactories() : null);
      } catch (FederateAlreadyExecutionMember e) {
         //ignore
      } catch (NotConnected e) {
         _rtiAmbassador = null;
         throw new HlaConnectException("Failed to join federation '" + _federationName + "'", e);

      } catch (ErrorReadingFDD | CouldNotOpenFDD e) {
         disconnectRtiAndThrow(new HlaFomException("Failed to join federation '" + _federationName + "'", e));

      } catch (FederationExecutionDoesNotExist e) {
         disconnectRtiAndThrow(new HlaRtiException("Failed to join federation '" + _federationName + "'", e));
      } catch (RestoreInProgress e) {
         disconnectRtiAndThrow(new HlaRestoreInProgressException("Failed to join federation '" + _federationName + "'", e));
      } catch (SaveInProgress e) {
         disconnectRtiAndThrow(new HlaSaveInProgressException("Failed to join federation '" + _federationName + "'", e));

      } catch (InvalidLogicalTimeFactory | InvalidLogicalTimeIntervalFactory e) {
         disconnectRtiAndThrow(new HlaInternalException("Failed to join federation '" + _federationName + "'", e));
      } catch (RTIinternalError e) {
         String credentialsWarning = getCredentialsNotUsedWarningIfApplicable(e, credentials, hlaVersion);
         disconnectRtiAndThrow(new HlaInternalException("Failed to join federation '" +  _federationName + "'. " + credentialsWarning, e));
      } catch (Unauthorized e) {
         disconnectRtiAndThrow(new HlaUnauthorizedException("Not authorized to join federation '" + _federationName + "'", e));
      }

      boolean useDdm = false;
      try {
         if (_ddmListener != null) {
            useDdm = _ddmListener.connected(rtiInfo);
         }
      } catch (HlaNotConnectedException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaNormalizerException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaFomException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaRtiException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaInternalException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaSaveInProgressException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaRestoreInProgressException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      }

      if (useDdm) {
         try {
            _rtiAmbassador.setAttributeScopeAdvisorySwitch(true);
         } catch (SaveInProgress e) {
            disconnectAndThrow(destroyFederationOnDisconnect, new HlaSaveInProgressException("Failed to enable attribute scope advisory switch when joining federation " + _federationName, e));
         } catch (RestoreInProgress e) {
            disconnectAndThrow(destroyFederationOnDisconnect, new HlaRestoreInProgressException("Failed to enable attribute scope advisory switch when joining federation " + _federationName, e));
         } catch (RTIinternalError e) {
            disconnectAndThrow(destroyFederationOnDisconnect, new HlaRtiException("Failed to enable attribute scope advisory switch when joining federation " + _federationName, e));

         } catch (FederateNotExecutionMember e) {
            disconnectAndThrow(destroyFederationOnDisconnect, new HlaNotConnectedException("Failed to enable attribute scope advisory switch when joining federation " + _federationName, e));
         } catch (NotConnected e) {
            disconnectAndThrow(destroyFederationOnDisconnect, new HlaNotConnectedException("Failed to enable attribute scope advisory switch when joining federation " + _federationName, e));
         }
      }

      if (_rtiAmbassador.getHLAversion() == HlaVersion.HLA_EVOLVED && HlaTuning.CONVEY_PRODUCING_FEDERATE) {
         try {
            InteractionClassHandle handle = _rtiAmbassador.getInteractionClassHandle("HLAmanager.HLAfederate.HLAadjust.HLAsetSwitches");
            _rtiAmbassador.publishInteractionClass(handle);

            Map<String, byte[]> parameters = new HashMap<String,byte[]>(2);
            parameters.put("HLAfederate", makeHlaVariableArray(_federateHandle.getEncodedHandle()));
            parameters.put("HLAconveyProducingFederate", new byte[] { 0, 0, 0, 1 }); //HLA SWITCH TRUE

            _rtiAmbassador.sendInteraction(handle, parameters, null);
         } catch (Throwable t) {
            disconnectAndThrow(destroyFederationOnDisconnect, new HlaInternalException("Failed to send Enable Convey Producing Federate interaction", t));
         }
      }

      try {
         _objectManager.connected();
         _interactionManager.connected();
         _saveRestManager.connected();
      } catch (HlaNotConnectedException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaFomException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaRtiException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaInternalException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaSaveInProgressException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      } catch (HlaRestoreInProgressException e) {
         disconnectAndThrow(destroyFederationOnDisconnect, e);
      }

      _timeConstrained = false;
      _timeRegulating = false;
      _expectingGrantAfterRestore = false;
      se.pitch.ral.api.LogicalTime currentTime = null;
      if (useTimeManagement && HlaTuning.ENABLE_TIME_MANAGEMENT_ON_CONNECT) {
         currentTime = enableTimeManagement(settings.getTimeRegulating(), settings.getTimeConstrained(), destroyFederationOnDisconnect, true);
      }
      _handleDisconnectDetected = true;

      warnIfCredentialsNotSupported(credentials, hlaVersion);

      return decodeLogicalTime(currentTime, OrderType.NONE);
   }

   public HlaLogicalTime enableTimeManagement(boolean regulating, boolean constrained) throws HlaInternalException, HlaInvalidLogicalTimeException, HlaRtiException, HlaNotConnectedException {
      se.pitch.ral.api.LogicalTime logicalTime = enableTimeManagement(regulating, constrained, false, false);
      return decodeLogicalTime(logicalTime, OrderType.NONE);
   }

   private se.pitch.ral.api.LogicalTime enableTimeManagement(boolean regulating, boolean constrained, boolean destroyFederationOnDisconnect, boolean onJoin) throws HlaInternalException, HlaInvalidLogicalTimeException, HlaRtiException, HlaNotConnectedException {
      se.pitch.ral.api.LogicalTime currentTime = null;
      String exceptionMessage = "Failed to enable time management" + (onJoin ? (" when joining federation " + _federationName) : ".");
      try {
         if (HlaTuning.ENABLE_ASYNCHRONOUS_DELIVERY) {
            _rtiAmbassador.enableAsynchronousDelivery();
         }

         if (constrained) {
            if (!_rtiAmbassador.enableTimeConstrained()) {
               disconnectAndThrow(destroyFederationOnDisconnect, new HlaRtiException("Failed to enable time management" + (onJoin ? (" when joining federation " + _federationName) : ".") + " Not supported!"));
            }
            currentTime = _timeConstrainedQueue.take();
            _timeConstrained = true;
         }

         if (regulating) {
            if (!_rtiAmbassador.enableTimeRegulation(_logicalTimeFactory.makeLogicalTimeInterval(_hlaWorld.getLookahead()))) {
               disconnectAndThrow(destroyFederationOnDisconnect, new HlaRtiException("Failed to enable time management" + (onJoin ? (" when joining federation " + _federationName) : ".") + " Not supported!"));
            }
            currentTime = _timeRegulatingQueue.take();
            _timeRegulating = true;
         } else if (HlaTuning.INITIAL_ADVANCE_WHEN_CONSTRAINED) {
            // keep up with other regulating federates
            se.pitch.ral.api.LogicalTime galt = _rtiAmbassador.queryGALT();
            if (galt != null && !galt.equals(currentTime)) {
               HlaLogicalTime newTime = decodeLogicalTime(galt, OrderType.NONE).add(-_hlaWorld.getStepSize());
               if (newTime.getValue() > 0) {
                  _rtiAmbassador.timeAdvanceRequest(encodeLogicalTime(newTime));
                  currentTime = _timeAdvanceQueue.take();
               }
            }
         }

         HlaLogicalTime time = decodeLogicalTime(currentTime, OrderType.NONE);
         if (time.isValid() && HlaTuning.INITIAL_ADVANCE_TO_EVEN_STEP_SIZE) {
            // start at time dividable with the step size
            long diff = time.getValue() % _hlaWorld.getStepSize();
            if (diff != 0) {
               _rtiAmbassador.timeAdvanceRequest(encodeLogicalTime(time.add(diff)));
               currentTime = _timeAdvanceQueue.take();
            }
         }
      } catch (InTimeAdvancingState e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaInternalException(exceptionMessage, e));
      } catch (RequestForTimeConstrainedPending e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaInternalException(exceptionMessage, e));
      } catch (RequestForTimeRegulationPending e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaInternalException(exceptionMessage, e));
      } catch (InvalidLookahead e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaInvalidLogicalTimeException(exceptionMessage, e));
      } catch (InvalidLogicalTime e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaInvalidLogicalTimeException(exceptionMessage, e));
      } catch (LogicalTimeAlreadyPassed e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaInternalException(exceptionMessage, e));
      } catch (InterruptedException e) {
         throwException(onJoin, destroyFederationOnDisconnect, true, new HlaInternalException(exceptionMessage, e));
      } catch (SaveInProgress e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaSaveInProgressException(exceptionMessage, e));
      } catch (RestoreInProgress e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaRestoreInProgressException(exceptionMessage, e));
      } catch (RTIinternalError e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaRtiException(exceptionMessage, e));
      } catch (FederateNotExecutionMember e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaNotConnectedException(exceptionMessage, e));
      } catch (NotConnected e) {
         throwException(onJoin, destroyFederationOnDisconnect, new HlaNotConnectedException(exceptionMessage, e));
      }
      return currentTime;
   }

   private <T extends Throwable> void disconnectRtiAndThrow(T exception) throws T {
      try {
         _rtiAmbassador.disconnect();
      } catch (Throwable ignore) {
      }
      throw exception;
   }

   private <T extends Throwable> void disconnectAndThrow(boolean destroyFederationOnDisconnect, T exception) throws T {
      throwException(true, destroyFederationOnDisconnect, false, exception);
   }

   private <T extends Throwable> void throwException(boolean doDisconnect, boolean destroyFederationOnDisconnect, T exception) throws T {
      throwException(doDisconnect, destroyFederationOnDisconnect, false, exception);
   }

   private <T extends Throwable> void throwException(boolean doDisconnect, boolean destroyFederationOnDisconnect, boolean wasInterrupted, T exception) throws T {
      if (doDisconnect) {
         try {
            disconnect(destroyFederationOnDisconnect);
         } catch (Throwable ignore) {
         }
      }
      if (wasInterrupted) {
         Thread.currentThread().interrupt();
      }
      throw exception;
   }

   private static byte[] makeHlaVariableArray(byte[] data) {
      byte[] res = new byte[4 + data.length];
      res[3] = (byte)data.length;  // length is 4 so this is safe
      System.arraycopy(data, 0, res, 4, data.length);
      return res;
   }

   private static ResignAction getResignAction(int resignAction) {
      switch (resignAction) {
         case 0: return ResignAction.UNCONDITIONALLY_DIVEST_ATTRIBUTES;
         case 1: return ResignAction.DELETE_OBJECTS;
         case 2: return ResignAction.CANCEL_PENDING_OWNERSHIP_ACQUISITIONS;
         case 3: return ResignAction.DELETE_OBJECTS_THEN_DIVEST;
         case 4: return ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST;
         case 5: return ResignAction.NO_ACTION;
      }

      return ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST;
   }

   public void disconnect(boolean destroyFederationOnDisconnect) throws HlaFederateOwnsAttributeException, HlaInternalException {
      if (_timeConstrained) {
         try {
            _rtiAmbassador.disableTimeConstrained();
         } catch (RTIexception e) {
            //ignore and resign anyway
         }
      }
      if (_timeRegulating) {
         try {
            _rtiAmbassador.disableTimeRegulation();
         } catch (RTIexception e) {
            //ignore and resign anyway
         }
      }

      _handleDisconnectDetected = false;
      try {
         _rtiAmbassador.resignFederationExecution(getResignAction(HlaTuning.RESIGN_ACTION));
      } catch (FederateNotExecutionMember e) {
         //do nothing
      } catch (NotConnected e) {
         //do nothing
      } catch (FederateOwnsAttributes e) {
         _handleDisconnectDetected = true;
         throw new HlaFederateOwnsAttributeException("Failed to resign", e);
      } catch (OwnershipAcquisitionPending e) {
         _handleDisconnectDetected = true;
         throw new HlaFederateOwnsAttributeException("Failed to resign", e);
      } catch (RTIinternalError e) {
         //ignore and disconnect anyway
      }

      if (destroyFederationOnDisconnect) {
         try {
            _rtiAmbassador.destroyFederationExecution(_federationName);
         } catch (NotConnected |
                  FederationExecutionDoesNotExist |
                  FederatesCurrentlyJoined |
                  RTIinternalError |
                  Unauthorized e) {
            //ignore and disconnect anyway
         }
      }

      try {
         _rtiAmbassador.disconnect();
      } catch (FederateIsExecutionMember e) {
         //ignore and disconnect anyway
      } catch (RTIinternalError e) {
         //ignore and disconnect anyway
      }

      disconnected();
   }

   public byte[] getEncodedHlaFederateHandle() {
      if (_federateHandle != null) {
         return _federateHandle.getEncodedHandle();
      } else {
         return null;
      }
   }

   private void disconnectDetected() {
      if (_handleDisconnectDetected) {
         _handleDisconnectDetected = false;
         try {
            _hlaWorld.disconnect();
         } catch (HlaRtiException e) {
            //do nothing
         } catch (HlaInternalException e) {
            //do nothing
         }
      }
   }

   private void disconnected() {
      _objectManager.disconnect();
      _interactionManager.disconnect();
      _saveRestManager.disconnect();
      if (_ddmListener != null) {
         _ddmListener.disconnected();
      }
   }

   private HlaLogicalTime decodeLogicalTime(se.pitch.ral.api.LogicalTime logicalTime, final OrderType receivedOrdering) {
      if (!(logicalTime instanceof LogicalTimeInteger64)) {
         return HlaLogicalTimeImpl.INVALID;
      }
      long value = ((LogicalTimeInteger64)logicalTime).getValue();
      if (receivedOrdering == OrderType.NONE) {
         return HlaLogicalTimeImpl.create(value);
      } else {
         return HlaLogicalTimeImpl.createReceived(value, receivedOrdering == OrderType.TIMESTAMP);
      }
   }

   private se.pitch.ral.api.LogicalTime encodeLogicalTime(HlaLogicalTime logicalTime) {
      if (logicalTime == null || !logicalTime.isValid() || _logicalTimeFactory == null) {
         return null;
      }
      return _logicalTimeFactory.makeLogicalTime(logicalTime.getValue());
   }

   private se.pitch.ral.api.LogicalTime encodeSendLogicalTime(HlaLogicalTime logicalTime) {
      if (!_timeRegulating && !_timeConstrained && !HlaTuning.ALWAYS_USE_LOGICAL_TIME) {
         return null;
      }
      return encodeLogicalTime(logicalTime);
   }

   /*
    * Callbacks from RTI
    */

   //save

   @Override
   public void initiateFederateSave(String label, se.pitch.ral.api.LogicalTime logicalTime) {
      try {
         _saveRestManager.initiateFederateSave(label, decodeLogicalTime(logicalTime, OrderType.TIMESTAMP));
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void federationSaved() {
      try {
         _saveRestManager.federationSaved();
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void federationNotSaved(SaveFailureReason reason) {
      try {
          _saveRestManager.federationNotSaved(reason);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   //restore

   @Override
   public void requestFederationRestoreSucceeded(String label) {
      try {
         _restoreRequestSucceeded = true;
         synchronized (_restoreRequestSemaphore) {
            _restoreRequestSemaphore.notify();
         }
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void requestFederationRestoreFailed(String label) {
      try {
         _restoreRequestSucceeded = false;
         synchronized (_restoreRequestSemaphore) {
            _restoreRequestSemaphore.notify();
         }
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void federationRestoreBegun() {
      try {
         _saveRestManager.federationRestoreBegun();
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void initiateFederateRestore(String label, FederateHandle federateHandle, String federateName) {
      try {
         _saveRestManager.initiateFederateRestore(label, new HlaFederateIdImpl(federateHandle), federateName);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void federationRestored() {
      try {
         _expectingGrantAfterRestore = _saveRestManager.federationRestored();
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void federationNotRestored(RestoreFailureReason reason) {
      try {
         _expectingGrantAfterRestore = _saveRestManager.federationNotRestored(reason);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   //time management

   @Override
   public void timeAdvanceGrant(se.pitch.ral.api.LogicalTime logicalTime) {
      if (_expectingGrantAfterRestore) {
         _expectingGrantAfterRestore = false;
         try {
            _saveRestManager.timeAdvanceGrantAfterRestore(decodeLogicalTime(logicalTime, OrderType.TIMESTAMP));
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
         }
         return;
      }

      try {
         _timeAdvanceQueue.put(logicalTime);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
   }

   @Override
   public void timeConstrainedEnabled(se.pitch.ral.api.LogicalTime logicalTime) {
      try {
         _timeConstrainedQueue.put(logicalTime);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
   }

   @Override
   public void timeRegulationEnabled(se.pitch.ral.api.LogicalTime logicalTime) {
      try {
         _timeRegulatingQueue.put(logicalTime);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
   }

   //name reservation

   @Override
   public void objectInstanceNameReservationSucceeded(String instanceName) {
      _nameReservationSucceeded = true;
      synchronized (_nameReservationSemaphore) {
         _nameReservationSemaphore.notify();
      }
   }

   @Override
   public void objectInstanceNameReservationFailed(String instanceName) {
      _nameReservationSucceeded = false;
      synchronized (_nameReservationSemaphore) {
         _nameReservationSemaphore.notify();
      }
   }

   @Override
   public void connectionLost(String faultDescription) {
      try {
           _hlaWorld.disconnect(new HlaNotConnectedException(faultDescription));
       } catch (HlaRtiException e) {
          //do nothing
       } catch (HlaInternalException e) {
          //do nothing
       }
   }

   //synch points

   @Override
   public void synchronizationPointRegistrationSucceeded(String synchPointLabel) {
      //ignore
   }

   @Override
   public void synchronizationPointRegistrationFailed(String synchPointLabel, SynchronizationPointFailureReason failureReason) {
      //ignore
   }

   @Override
   public void announceSynchronizationPoint(final String synchPointLabel, byte[] userSuppliedTag) {
      if (HlaTuning.AUTO_ACHIEVE) {
         _hlaWorld.invokeLater(new Runnable() {
            public void run() {
               try {
                  _rtiAmbassador.synchronizationPointAchieved(synchPointLabel);
               } catch (RTIexception e) {
                  _hlaWorld.postException(new HlaRtiException("Failed to achieve synchronization point", e));
               }
            }
         });
      }
   }

   @Override
   public void federationSynchronized(String synchPointLabel) {
      //ignore
   }

   //objects and interactions

   @Override
   public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle,
                                      String instanceName,
                                      FederateHandle producingFederate) {
      try {
         HlaFederateIdImpl federateId = producingFederate != null ? new HlaFederateIdImpl(producingFederate) : null;
         _objectManager.discoverObjectInstance(objectInstanceHandle,
                                               instanceName,
                                               _timeStampFactory.create(),
                                               federateId);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   @SuppressWarnings ({ "unchecked" })
   public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle,
                                      Map /* <String, byte[]> */ attributeHandleValueMap,
                                      byte[] userSuppliedTag,
                                      OrderType sentOrdering,
                                      TransportationType transportationType,
                                      se.pitch.ral.api.LogicalTime logicalTime,
                                      OrderType receivedOrdering,
                                      FederateHandle producingFederate) {
      try {
         NameValueMap attributes = new NameValueMap(attributeHandleValueMap, true);
         HlaFederateIdImpl federateId = producingFederate != null ? new HlaFederateIdImpl(producingFederate) : null;
         _objectManager.reflectAttributeValues(objectInstanceHandle,
                                               attributes,
                                               _timeStampFactory.create(userSuppliedTag),
                                               decodeLogicalTime(logicalTime, receivedOrdering),
                                               federateId);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   @SuppressWarnings ({ "unchecked" })
   public void receiveInteraction(InteractionClassHandle interactionClassHandle,
                                  Map /* <String, byte[]> */ parameterHandleValueMap,
                                  byte[] userSuppliedTag,
                                  OrderType sentOrdering,
                                  TransportationType transportationType,
                                  se.pitch.ral.api.LogicalTime logicalTime,
                                  OrderType receivedOrdering,
                                  FederateHandle producingFederate) {
      try {
         NameValueMap parameters = new NameValueMap(parameterHandleValueMap, true);
         HlaFederateIdImpl federateId = producingFederate != null ? new HlaFederateIdImpl(producingFederate) : null;
         _interactionManager.receiveInteraction(interactionClassHandle,
                                                parameters,
                                                _timeStampFactory.create(userSuppliedTag),
                                                decodeLogicalTime(logicalTime, receivedOrdering),
                                                federateId);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle,
                                    byte[] userSuppliedTag,
                                    OrderType sentOrdering,
                                    se.pitch.ral.api.LogicalTime logicalTime,
                                    OrderType receivedOrdering,
                                    FederateHandle producingFederate) {
      try {
         _objectManager.removeObjectInstance(objectInstanceHandle,
                                             _timeStampFactory.create(userSuppliedTag),
                                             decodeLogicalTime(logicalTime, receivedOrdering));
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void provideAttributeValueUpdate(final ObjectInstanceHandle objectInstanceHandle,
                                           final Set<String> attributeHandleSet,
                                           final byte[] userSuppliedTag) {
      if (HlaTuning.PROVIDE) {
         _hlaWorld.invokeLater(new Runnable() {
            public void run() {
               try {
                  _objectManager.provideAttributeValueUpdate(objectInstanceHandle,
                                                             attributeHandleSet,
                                                             _timeStampFactory.create(userSuppliedTag));
               } catch (Throwable t) {
                  _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
               }
            }
         });
      }
   }

   /*
    * Time Management RTI calls
    */

   public HlaLogicalTime timeAdvanceRequest(HlaLogicalTime newLogicalTime)
         throws HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaInvalidLogicalTimeException, HlaInTimeAdvancingStateException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         se.pitch.ral.api.LogicalTime requestedTime = encodeLogicalTime(newLogicalTime);
         if (requestedTime == null) {
            return newLogicalTime;
         }
         _rtiAmbassador.timeAdvanceRequest(requestedTime);
         se.pitch.ral.api.LogicalTime currentTime = _timeAdvanceQueue.take();
         return decodeLogicalTime(currentTime, OrderType.NONE);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request time advance", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request time advance", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to request time advance", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to request time advance", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to request time advance", e);

      } catch (InTimeAdvancingState e) {
         throw new HlaInTimeAdvancingStateException("Failed to request time advance", e);

      } catch (InvalidLogicalTime e) {
         throw new HlaInvalidLogicalTimeException("Failed to request time advance", e);
      } catch (LogicalTimeAlreadyPassed e) {
         throw new HlaInvalidLogicalTimeException("Failed to request time advance", e);

      } catch (RequestForTimeRegulationPending e) {
         throw new HlaInternalException("Failed to request time advance", e);
      } catch (RequestForTimeConstrainedPending e) {
         throw new HlaInternalException("Failed to request time advance", e);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
         throw new HlaInternalException("Failed to request time advance", e);
      }
   }

   public HlaLogicalTime nextMessageRequestAvailable(HlaLogicalTime newLogicalTime)
         throws HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaInvalidLogicalTimeException, HlaInTimeAdvancingStateException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         se.pitch.ral.api.LogicalTime requestedTime = encodeLogicalTime(newLogicalTime);
         if (requestedTime == null) {
            return newLogicalTime;
         }
         _rtiAmbassador.nextMessageRequestAvailable(requestedTime);
         se.pitch.ral.api.LogicalTime currentTime = _timeAdvanceQueue.take();
         return decodeLogicalTime(currentTime, OrderType.NONE);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request time advance", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request time advance", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to request time advance", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to request time advance", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to request time advance", e);

      } catch (InTimeAdvancingState e) {
         throw new HlaInTimeAdvancingStateException("Failed to request time advance", e);

      } catch (InvalidLogicalTime e) {
         throw new HlaInvalidLogicalTimeException("Failed to request time advance", e);
      } catch (LogicalTimeAlreadyPassed e) {
         throw new HlaInvalidLogicalTimeException("Failed to request time advance", e);

      } catch (RequestForTimeRegulationPending e) {
         throw new HlaInternalException("Failed to request time advance", e);
      } catch (RequestForTimeConstrainedPending e) {
         throw new HlaInternalException("Failed to request time advance", e);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
         throw new HlaInternalException("Failed to request time advance", e);
      }
   }

   public void modifyLookahead(long lookahead)
         throws HlaInternalException, HlaRtiException,
                HlaNotConnectedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         if (_logicalTimeFactory != null) {
            _rtiAmbassador.modifyLookahead(_logicalTimeFactory.makeLogicalTimeInterval(lookahead));
         }
      } catch (InvalidLookahead e) {
         throw new HlaInvalidLogicalTimeException("Invalid lookahead value", e);

      } catch (InTimeAdvancingState e) {
         throw new HlaInternalException("Cannot modify lookahead while in advancing state", e);

      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to modify lookahead", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to modify lookahead", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to modify lookahead", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to modify lookahead", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to modify lookahead", e);
      }
   }

   /*
    * Object RTI calls
    */

   ObjectClassHandle getObjectClassHandle(String objectClassName)
         throws HlaNotConnectedException, HlaRtiException, HlaFomException {
      try {
         return _rtiAmbassador.getObjectClassHandle(objectClassName);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to get object class handle " + objectClassName, e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to get object class handle " + objectClassName, e);

      } catch (NameNotFound e) {
         throw new HlaFomException("Failed to get object class handle " + objectClassName, e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to get object class handle " + objectClassName, e);
      }
   }

   void publishObjectClassAttributes(ObjectClassHandle objectClassHandle, Set<String> attributeNames)
         throws HlaRtiException, HlaFomException, HlaInternalException, HlaNotConnectedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.publishObjectClassAttributes(objectClassHandle, attributeNames);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to publish object class", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to publish object class", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to publish object class", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to publish object class", e);
      } catch (OwnershipAcquisitionPending e) {
         throw new HlaRtiException("Failed to publish object class", e);

      } catch (NameNotFound e) {
         throw new HlaFomException("Failed to publish object class", e);

      } catch (ObjectClassNotDefined e) {
         throw new HlaInternalException("Failed to publish object class", e);
      } catch (AttributeNotDefined e) {
         throw new HlaInternalException("Failed to publish object class", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to publish object class", e);
      }
   }

   void subscribeObjectClassAttributes(ObjectClassHandle objectClassHandle, Set<String> attributeNames)
         throws HlaRtiException, HlaFomException, HlaInternalException, HlaNotConnectedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.subscribeObjectClassAttributes(objectClassHandle, attributeNames);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to object class", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to object class", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to subscribe to object class", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to subscribe to object class", e);

      } catch (NameNotFound e) {
         throw new HlaFomException("Failed to subscribe to object class", e);

      } catch (ObjectClassNotDefined e) {
         throw new HlaInternalException("Failed to subscribe to object class", e);
      } catch (AttributeNotDefined e) {
         throw new HlaInternalException("Failed to subscribe to object class", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to subscribe to object class", e);
      }
   }

   void subscribeObjectClassAttributes(ObjectClassHandle objectClassHandle, Set<String> attributeNames, Set<Region> newRegion, Set<Region> oldRegion)
         throws HlaRtiException, HlaFomException, HlaInternalException, HlaNotConnectedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         if (newRegion != null) {
            Map<String, Set<Region>> regions = MapAdapter.mapWithSameValueForAllKeys(attributeNames, newRegion);
            _rtiAmbassador.subscribeObjectClassAttributesWithRegions(objectClassHandle, regions, false);
         }
         if (oldRegion != null) {
            Map<String, Set<Region>> regions = MapAdapter.mapWithSameValueForAllKeys(attributeNames, oldRegion);
            _rtiAmbassador.unsubscribeObjectClassAttributesWithRegions(objectClassHandle, regions);
         }
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to object class", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to object class", e);


      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to subscribe to object class", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to subscribe to object class", e);

      } catch (NameNotFound e) {
         throw new HlaFomException("Failed to subscribe to object class", e);

      } catch (ObjectClassNotDefined e) {
         throw new HlaInternalException("Failed to subscribe to object class", e);
      } catch (AttributeNotDefined e) {
         throw new HlaInternalException("Failed to subscribe to object class", e);
      } catch (InvalidRegionContext e) {
         throw new HlaInternalException("Failed to subscribe to object class", e);
      } catch (InvalidRegion e) {
         throw new HlaInternalException("Failed to subscribe to object class", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to subscribe to object class", e);
      }
   }

   boolean registerObjectInstanceName(String instanceName)
         throws HlaIllegalInstanceNameException,
                HlaNotConnectedException, HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         boolean reserveNameSupported;
         synchronized (_nameReservationSemaphore) {
            _nameReservationSucceeded = false;
            reserveNameSupported = _rtiAmbassador.reserveObjectInstanceName(instanceName);
            if (reserveNameSupported) {
               // Wait for response from RTI
               try {
                  _nameReservationSemaphore.wait();
               } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
               }
            }
         }
         return !reserveNameSupported || _nameReservationSucceeded;
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to register object instance name " + instanceName, e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to register object instance name " + instanceName, e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to register object instance name " + instanceName, e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to register object instance name " + instanceName, e);

      } catch (IllegalName e) {
         throw new HlaIllegalInstanceNameException("Failed to register object instance name " + instanceName, e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to register object instance name " + instanceName, e);
      }
   }

   ObjectInstanceHandle createObjectInstance(ObjectClassHandle objectClassHandle, String instanceName)
         throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaRtiException, HlaInternalException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         if (instanceName != null) {
            ObjectInstanceHandle objectInstanceHandle = _rtiAmbassador.registerObjectInstance(objectClassHandle, instanceName);
            _rtiAmbassador.releaseObjectInstanceName(instanceName);
            return objectInstanceHandle;
         } else {
            return _rtiAmbassador.registerObjectInstance(objectClassHandle);
         }
      } catch (ObjectInstanceNameInUse e) {
         throw new HlaInstanceNameInUseException(instanceName);
      } catch (ObjectInstanceNameNotReserved e) {
         throw new HlaInstanceNameInUseException(instanceName);

      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to register object instance", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to register object instance", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to register object instance", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to register object instance", e);

      } catch (ObjectClassNotPublished e) {
         throw new HlaInternalException("Failed to register object instance", e);
      } catch (ObjectClassNotDefined e) {
         throw new HlaInternalException("Failed to register object instance", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to register object instance", e);
      }
   }

   ObjectInstanceHandle createObjectInstance(ObjectClassHandle objectClassHandle,
                                             String instanceName,
                                             Set<String> attributeNames,
                                             Region initialRegion)
         throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaRtiException, HlaInternalException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         Map<String, Set<Region>> region = MapAdapter.mapWithSameValueForAllKeys(attributeNames, Collections.singleton(initialRegion));
         ObjectInstanceHandle objectInstanceHandle = _rtiAmbassador.registerObjectInstanceWithRegions(objectClassHandle, instanceName, region);
         if (instanceName != null) {
            _rtiAmbassador.releaseObjectInstanceName(instanceName);
         }
         return objectInstanceHandle;
      } catch (ObjectInstanceNameInUse e) {
         throw new HlaInstanceNameInUseException(instanceName);
      } catch (ObjectInstanceNameNotReserved e) {
         throw new HlaInstanceNameInUseException(instanceName);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to register object instance", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to register object instance", e);
      } catch (AttributeNotDefined e) {
         throw new HlaInternalException("Failed to register object instance", e);
      } catch (AttributeNotPublished e) {
         throw new HlaInternalException("Failed to register object instance", e);
      } catch (InvalidRegionContext e) {
         throw new HlaInternalException("Failed to register object instance", e);
      } catch (InvalidRegion e) {
         throw new HlaInternalException("Failed to register object instance", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to register object instance", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to register object instance", e);
      } catch (ObjectClassNotPublished e) {
         throw new HlaInternalException("Failed to register object instance", e);
      } catch (ObjectClassNotDefined e) {
         throw new HlaInternalException("Failed to register object instance", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to register object instance", e);
      }
   }

   @SuppressWarnings ({ "unchecked" })
   void updateAttributeValues(ObjectInstanceHandle objectInstanceHandle,
                              NameValueMap attributes,
                              HlaTimeStamp timeStamp,
                              HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaRtiException, HlaInternalException,
                HlaAttributeNotOwnedException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.updateAttributeValues(objectInstanceHandle, attributes.getMap(), timeStamp.getUserSuppliedTag(), encodeSendLogicalTime(logicalTime));
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to update object instance", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to update object instance", e);
      } catch (InvalidLogicalTime e) {
          throw new HlaInvalidLogicalTimeException("Failed to update object instance", e);
      } catch (AttributeNotOwned e) {
         throw new HlaAttributeNotOwnedException("Failed to update object instance", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to update object instance", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to update object instance", e);
      } catch (AttributeNotDefined e) {
         throw new HlaInternalException("Failed to update object instance", e);
      } catch (ObjectInstanceNotKnown e) {
         throw new HlaObjectInstanceIsRemovedException("Failed to update object instance", e);
      } catch (NameNotFound e) {
         throw new HlaInternalException("Failed to update object instance", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to update object instance", e);
      }
   }

   @SuppressWarnings ({ "unchecked" })
   void updateAttributeValues(ObjectInstanceHandle objectInstanceHandle,
                              NameValueMap attributes,
                              HlaTimeStamp timeStamp,
                              HlaLogicalTime logicalTime,
                              Set<String> attributeNames,
                              Region newRegion,
                              Region oldRegion)
         throws HlaNotConnectedException, HlaRtiException, HlaInternalException,
                HlaAttributeNotOwnedException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
          _rtiAmbassador.associateRegionsForUpdates(objectInstanceHandle, MapAdapter.mapWithSameValueForAllKeys(attributeNames, Collections.singleton(newRegion)));
          _rtiAmbassador.unassociateRegionsForUpdates(objectInstanceHandle, MapAdapter.mapWithSameValueForAllKeys(attributeNames, Collections.singleton(oldRegion)));

          _rtiAmbassador.updateAttributeValues(objectInstanceHandle, attributes.getMap(), timeStamp.getUserSuppliedTag(), encodeSendLogicalTime(logicalTime));
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to update object instance", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to update object instance", e);

      } catch (InvalidLogicalTime e) {
          throw new HlaInvalidLogicalTimeException("Failed to update object instance", e);

      } catch (AttributeNotOwned e) {
         throw new HlaAttributeNotOwnedException("Failed to update object instance", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to update object instance", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to update object instance", e);

      } catch (AttributeNotDefined e) {
         throw new HlaInternalException("Failed to update object instance", e);
      } catch (ObjectInstanceNotKnown e) {
         throw new HlaObjectInstanceIsRemovedException("Failed to update object instance", e);
      } catch (NameNotFound e) {
         throw new HlaInternalException("Failed to update object instance", e);
      } catch (InvalidRegionContext e) {
         throw new HlaInternalException("Failed to update object instance", e);
      } catch (InvalidRegion e) {
         throw new HlaInternalException("Failed to update object instance", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to update object instance", e);
      }
   }

   void deleteObjectInstance(ObjectInstanceHandle objectInstanceHandle, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaInvalidLogicalTimeException, HlaObjectInstanceIsRemovedException, HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.deleteObjectInstance(objectInstanceHandle, timeStamp.getUserSuppliedTag(), encodeSendLogicalTime(logicalTime));

         String instanceName = objectInstanceHandle.getObjectInstanceName();
         if (instanceName != null && !instanceName.startsWith("HLA")) {
            _rtiAmbassador.releaseObjectInstanceName(instanceName);
         }
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to delete object instance", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to delete object instance", e);

      } catch (InvalidLogicalTime e) {
          throw new HlaInvalidLogicalTimeException("Failed to delete object instance", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to delete object instance", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to delete object instance", e);

      } catch (DeletePrivilegeNotHeld e) {
         throw new HlaInternalException("Failed to delete object instance", e);
      } catch (ObjectInstanceNotKnown e) {
         throw new HlaObjectInstanceIsRemovedException("Failed to delete object instance", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to delete object instance", e);
      }
   }

   @SuppressWarnings ({ "unchecked" })
   void requestAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, Set<String> attributes,
                                    HlaTimeStamp hlaTimeStamp)
         throws HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.requestAttributeValueUpdate(objectInstanceHandle, attributes, hlaTimeStamp.getUserSuppliedTag());
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request attribute update", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request attribute update", e);

      } catch (ObjectInstanceNotKnown e) {
         throw new HlaInternalException("Failed to request attribute update", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to request attribute update", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to request attribute update", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to request attribute update", e);

      } catch (NameNotFound e) {
         throw new HlaInternalException("Failed to request attribute update", e);
      } catch (AttributeNotDefined e) {
         throw new HlaInternalException("Failed to request attribute update", e);
      }
   }

   /*
    * Interaction RTI calls
    */

   InteractionClassHandle getInteractionClassHandle(String interactionClassName)
         throws HlaFomException, HlaNotConnectedException, HlaRtiException {
      try {
         return _rtiAmbassador.getInteractionClassHandle(interactionClassName);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to get interaction class handle " + interactionClassName, e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to get interaction class handle " + interactionClassName, e);

      } catch (NameNotFound e) {
         throw new HlaFomException("Failed to get interaction class handle " + interactionClassName, e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to get interaction class handle " + interactionClassName, e);
      }
   }

   void publishInteractionClass(InteractionClassHandle interactionClassHandle)
         throws HlaNotConnectedException, HlaRtiException, HlaInternalException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.publishInteractionClass(interactionClassHandle);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to publish interaction class", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to publish interaction class", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to publish interaction class", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to publish interaction class", e);

      } catch (InteractionClassNotDefined e) {
         throw new HlaInternalException("Failed to publish interaction class", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to publish interaction class", e);
      }
   }

   void subscribeInteractionClass(InteractionClassHandle interactionClassHandle)
         throws HlaRtiException, HlaNotConnectedException, HlaInternalException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.subscribeInteractionClass(interactionClassHandle);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to interaction class", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to interaction class", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to subscribe to interaction class", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to subscribe to interaction class", e);

      } catch (FederateServiceInvocationsAreBeingReportedViaMOM e) {
         throw new HlaInternalException("Failed to subscribe to interaction class", e);
      } catch (InteractionClassNotDefined e) {
         throw new HlaInternalException("Failed to subscribe to interaction class", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to subscribe to interaction class", e);
      }
   }

   void subscribeInteractionClass(InteractionClassHandle interactionClassHandle, Set<Region> newRegion, Set<Region> oldRegion)
         throws HlaRtiException, HlaNotConnectedException, HlaInternalException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         if (newRegion != null) {
            _rtiAmbassador.subscribeInteractionClassWithRegions(interactionClassHandle, newRegion, false);
         }
         if (oldRegion != null) {
            _rtiAmbassador.unsubscribeInteractionClassWithRegions(interactionClassHandle, oldRegion);
         }
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to interaction class", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to subscribe to interaction class", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to subscribe to interaction class", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to subscribe to interaction class", e);

      } catch (FederateServiceInvocationsAreBeingReportedViaMOM e) {
         throw new HlaInternalException("Failed to subscribe to interaction class", e);
      } catch (InteractionClassNotDefined e) {
         throw new HlaInternalException("Failed to subscribe to interaction class", e);
      } catch (InvalidRegionContext e) {
         throw new HlaInternalException("Failed to subscribe to interaction class", e);
      } catch (InvalidRegion e) {
         throw new HlaInternalException("Failed to subscribe to interaction class", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to subscribe to interaction class", e);
      }
   }

   @SuppressWarnings ({ "unchecked" })
   void sendInteraction(InteractionClassHandle interactionClassHandle,
                        NameValueMap parameters,
                        HlaTimeStamp timeStamp,
                        HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaRtiException, HlaFomException, HlaInvalidLogicalTimeException, HlaInternalException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.sendInteraction(interactionClassHandle, parameters.getMap(), timeStamp.getUserSuppliedTag(), encodeSendLogicalTime(logicalTime));
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to send interaction", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to send interaction", e);

      } catch (InvalidLogicalTime e) {
          throw new HlaInvalidLogicalTimeException("Failed to send interaction", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to send interaction", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to send interaction", e);

      } catch (NameNotFound e) {
         throw new HlaFomException("Failed to send interaction", e);

      } catch (InteractionParameterNotDefined e) {
         throw new HlaInternalException("Failed to send interaction", e);
      } catch (InteractionClassNotPublished e) {
         throw new HlaInternalException("Failed to send interaction", e);
      } catch (InteractionClassNotDefined e) {
         throw new HlaInternalException("Failed to send interaction", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to send interaction", e);
      }
   }

   @SuppressWarnings ({ "unchecked" })
   void sendInteraction(InteractionClassHandle interactionClassHandle,
                        NameValueMap parameters,
                        HlaTimeStamp timeStamp,
                        HlaLogicalTime logicalTime,
                        Region region)
         throws HlaNotConnectedException, HlaRtiException, HlaFomException, HlaInvalidLogicalTimeException, HlaInternalException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.sendInteractionWithRegions(interactionClassHandle, parameters.getMap(), timeStamp.getUserSuppliedTag(), encodeSendLogicalTime(logicalTime), Collections.singleton(region));
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to send interaction", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to send interaction", e);

      } catch (InvalidLogicalTime e) {
          throw new HlaInvalidLogicalTimeException("Failed to send interaction", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to send interaction", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to send interaction", e);

      } catch (NameNotFound e) {
         throw new HlaFomException("Failed to send interaction", e);

      } catch (InteractionParameterNotDefined e) {
         throw new HlaInternalException("Failed to send interaction", e);
      } catch (InteractionClassNotPublished e) {
         throw new HlaInternalException("Failed to send interaction", e);
      } catch (InteractionClassNotDefined e) {
         throw new HlaInternalException("Failed to send interaction", e);
      } catch (InvalidRegionContext e) {
         throw new HlaInternalException("Failed to send interaction", e);
      } catch (InvalidRegion e) {
         throw new HlaInternalException("Failed to send interaction", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to send interaction", e);
      }
   }

   /*
    * Save/Restore RTI calls
    */

   public void requestFederationSave(String label, HlaLogicalTime logicalTime) throws HlaRtiException, HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         se.pitch.ral.api.LogicalTime time = encodeSendLogicalTime(logicalTime);
         if (time != null) {
            _rtiAmbassador.requestFederationSave(label, time);
         } else {
            _rtiAmbassador.requestFederationSave(label);
         }
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to request federation save", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to request federation save", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request federation save", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request federation save", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to request federation save", e);
      } catch (FederateUnableToUseTime e) {
         throw new HlaInvalidLogicalTimeException("Failed to request federation save", e);
      } catch (LogicalTimeAlreadyPassed e) {
         throw new HlaInvalidLogicalTimeException("Failed to request federation save", e);
      } catch (InvalidLogicalTime e) {
         throw new HlaInvalidLogicalTimeException("Failed to request federation save", e);
      }
   }

   public void federateSaveBegun() throws HlaRtiException, HlaNotConnectedException,  HlaRestoreInProgressException {
      try {
         _rtiAmbassador.federateSaveBegun();
      } catch (SaveNotInitiated e) {
         throw new HlaRtiException("Failed to tell RTI save begun", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI save begun", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to tell RTI save begun", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI save begun", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to tell RTI save begun", e);
      }
   }

   public void federateSaveComplete() throws HlaRtiException, HlaNotConnectedException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.federateSaveComplete();
      } catch (FederateHasNotBegunSave e) {
         throw new HlaRtiException("Failed to tell RTI save complete", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI save complete", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to tell RTI save complete", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI save complete", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to tell RTI save complete", e);
      }
   }

   public void federateSaveNotComplete() throws HlaRtiException, HlaNotConnectedException, HlaRestoreInProgressException {
      try {
         _rtiAmbassador.federateSaveNotComplete();
      } catch (FederateHasNotBegunSave e) {
         throw new HlaRtiException("Failed to tell RTI save not complete", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI save not complete", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to tell RTI save not complete", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI save not complete", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to tell RTI save not complete", e);
      }
   }

   public boolean requestFederationRestore(String label) throws HlaRtiException, HlaNotConnectedException, HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         synchronized (_restoreRequestSemaphore) {
            _restoreRequestSucceeded = false;
            _rtiAmbassador.requestFederationRestore(label);
            // Wait for response from RTI
            try {
               _restoreRequestSemaphore.wait();
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            }
         }
         return _restoreRequestSucceeded;
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to request federation restore", e);
      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to request federation restore", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request federation restore", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to request federation restore", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to request federation restore", e);
      }
   }

   public void federateRestoreComplete() throws HlaRtiException, HlaNotConnectedException, HlaSaveInProgressException {
      try {
         _rtiAmbassador.federateRestoreComplete();
      } catch (RestoreNotRequested e) {
         throw new HlaRtiException("Failed to tell RTI federate restore complete", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI federate restore complete", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to tell RTI federate restore complete", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI federate restore complete", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to tell RTI federate restore complete", e);
      }
   }

   public void federateRestoreNotComplete() throws HlaRtiException, HlaNotConnectedException, HlaSaveInProgressException {
      try {
         _rtiAmbassador.federateRestoreNotComplete();
      } catch (RestoreNotRequested e) {
         throw new HlaRtiException("Failed to tell RTI federate restore not complete", e);
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI federate restore not complete", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to tell RTI federate restore not complete", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to tell RTI federate restore not complete", e);
      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to tell RTI federate restore not complete", e);
      }
   }

   public HlaFederateId getFederateId() {
      return new HlaFederateIdImpl(_federateHandle);
   }

   @Override
   public void attributesInScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeHandleSet) {
      try {
         _objectManager.attributesInScope(objectInstanceHandle, attributeHandleSet);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   @Override
   public void attributesOutOfScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeHandleSet) {
      try {
         _objectManager.attributesOutOfScope(objectInstanceHandle, attributeHandleSet);
      } catch (Throwable t) {
         _hlaWorld.postException(new HlaInternalException("Unhandled exception in FederateAmbassador callback: " + t.toString(), t));
      }
   }

   // area is a map from Dimension names to Ranges, all within the same hla13routingSpace
   public Region createRegion(Map<String, Region.Range> area, String hla13routingSpace)
         throws HlaNotConnectedException, HlaFomException, HlaSaveInProgressException,
                HlaRestoreInProgressException, HlaInternalException, HlaRtiException {
      try {
         Region region = _rtiAmbassador.createRegion(area, hla13routingSpace);
         _rtiAmbassador.commitRegions(Collections.singleton(region));
         return region;
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to create region", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to create region", e);

      } catch (InvalidDimension e) {
         throw new HlaFomException("Failed to create region", e);
      } catch (InvalidRegion e) {
         throw new HlaInternalException("Failed to create region", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to create region", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to create region", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to create region", e);
      }
   }

   public void deleteRegions(Collection<Region> regions)
         throws HlaNotConnectedException, HlaSaveInProgressException, HlaRestoreInProgressException,
                HlaInternalException, HlaRtiException {
      try {
         for (Region region : regions) {
            _rtiAmbassador.deleteRegion(region);
         }
      } catch (FederateNotExecutionMember e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to delete region", e);
      } catch (NotConnected e) {
         disconnectDetected();
         throw new HlaNotConnectedException("Failed to delete region", e);

      } catch (RegionInUse e) {
         throw new HlaInternalException("Failed to delete region", e);
      } catch (InvalidRegion e) {
         throw new HlaInternalException("Failed to delete region", e);

      } catch (RestoreInProgress e) {
         throw new HlaRestoreInProgressException("Failed to delete region", e);
      } catch (SaveInProgress e) {
         throw new HlaSaveInProgressException("Failed to delete region", e);

      } catch (RTIinternalError e) {
         throw new HlaRtiException("Failed to delete region", e);
      }
   }
}
