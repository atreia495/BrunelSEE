package uk.ac.brunel.see.impl;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import uk.ac.brunel.see.impl.encoders.BaseEncoder;
import uk.ac.brunel.see.impl.encoders.ExecutionModeEncoder;
import se.pitch.encoders1516.HLAfloat64LE;
import se.pitch.encoders1516.HLAinteger64BE;
import se.pitch.encoders1516.HLAunicodeString;
import uk.ac.brunel.see.*;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.fed.AbstractObjectClassManager;
import uk.ac.brunel.see.impl.fed.ObjectManager;
import uk.ac.brunel.see.impl.utils.*;

import se.pitch.ral.api.ObjectInstanceHandle;
import se.pitch.ral.api.ObjectClassHandle;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;


/* @ThreadSafe */
final class HlaExecutionConfigurationManagerImpl extends AbstractObjectClassManager implements HlaExecutionConfigurationManager {

   private static final String HLA_OBJECT_CLASS_NAME = "HLAobjectRoot.ExecutionConfiguration";

   private final ObjectManager _objectManager;
   private final HlaWorldImpl _worldImpl;
   private final Object _localCreationInProgressLock = new Object();

   private volatile boolean _enabled = true;
   private volatile boolean _optional = false;
   private ObjectClassHandle _objectClassHandle = null;

   private final Map<String, HlaExecutionConfigurationImpl> _hlaInstanceNameMap = new ConcurrentHashMap<String, HlaExecutionConfigurationImpl>();
   private final Map<ByteBuffer, HlaExecutionConfigurationImpl> _encodedHlaInstanceHandleMap = new ConcurrentHashMap<ByteBuffer, HlaExecutionConfigurationImpl>();
   private final Map<ObjectInstanceHandle, HlaExecutionConfigurationImpl> _localInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaExecutionConfigurationImpl>();
   private final Map<ObjectInstanceHandle, HlaExecutionConfigurationImpl> _remoteInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaExecutionConfigurationImpl>();

   private final Set<HlaExecutionConfigurationManagerListener> _listeners =
         new CopyOnWriteArraySet<HlaExecutionConfigurationManagerListener>();

   private final Set<HlaExecutionConfigurationValueListener> _defaultInstanceValueListeners =
         new CopyOnWriteArraySet<HlaExecutionConfigurationValueListener>();
   private final Set<HlaExecutionConfigurationListener> _defaultInstanceListeners =
         new CopyOnWriteArraySet<HlaExecutionConfigurationListener>();

   /*
    * Share encoders and decoders for HlaExecutionConfigurationImpl
    */
   protected final Object _encodersLock = new Object();
   protected final Object _decodersLock = new Object();
   /* @GuardedBy("_encodersLock") */
   protected final HLAunicodeString _hLAunicodeStringEncoder = BaseEncoder.createHLAunicodeString();
   /* @GuardedBy("_decodersLock") */
   protected final HLAunicodeString _hLAunicodeStringDecoder = BaseEncoder.createHLAunicodeString();
   /* @GuardedBy("_encodersLock") */
   protected final HLAfloat64LE _hLAfloat64LEEncoder = BaseEncoder.createHLAfloat64LE();
   /* @GuardedBy("_decodersLock") */
   protected final HLAfloat64LE _hLAfloat64LEDecoder = BaseEncoder.createHLAfloat64LE();
   /* @GuardedBy("_encodersLock") */
   protected final ExecutionModeEncoder _executionModeEncoderEncoder = new ExecutionModeEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final ExecutionModeEncoder _executionModeEncoderDecoder = new ExecutionModeEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final HLAinteger64BE _hLAinteger64BEEncoder = BaseEncoder.createHLAinteger64BE();
   /* @GuardedBy("_decodersLock") */
   protected final HLAinteger64BE _hLAinteger64BEDecoder = BaseEncoder.createHLAinteger64BE();

   HlaExecutionConfigurationManagerImpl(HlaWorldImpl world, ObjectManager objectManager) {
      _objectManager = objectManager;
      _worldImpl = world;

      _objectManager.addManager(this, HLA_OBJECT_CLASS_NAME);
   }

   /*
    * HlaExecutionConfigurationManager interface impl
    */

   public List<HlaExecutionConfiguration> getHlaExecutionConfigurations() {
      return getAllHlaExecutionConfigurations();
   }

   public List<HlaExecutionConfiguration> getAllHlaExecutionConfigurations() {
      List<HlaExecutionConfiguration> list = new ArrayList<HlaExecutionConfiguration>(_localInstances.size() + _remoteInstances.size());
      list.addAll(_localInstances.values());
      list.addAll(_remoteInstances.values());
      return list;
   }

   public List<HlaExecutionConfiguration> getLocalHlaExecutionConfigurations() {
      return new ArrayList<HlaExecutionConfiguration>(_localInstances.values());
   }

   public List<HlaExecutionConfiguration> getRemoteHlaExecutionConfigurations() {
      return new ArrayList<HlaExecutionConfiguration>(_remoteInstances.values());
   }

   public HlaExecutionConfigurationImpl getExecutionConfigurationByHlaInstanceName(String hlaInstanceName) {
      return _hlaInstanceNameMap.get(hlaInstanceName);
   }

   public HlaExecutionConfiguration getExecutionConfigurationByHlaInstanceHandle(byte[] encodedHlaInstanceHandle) {
      return _encodedHlaInstanceHandleMap.get(ByteBuffer.wrap(encodedHlaInstanceHandle));
   }

   public HlaExecutionConfiguration createLocalHlaExecutionConfiguration(
      String rootFrameName,
      double scenarioTimeEpoch
   ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {
      if (_objectClassHandle == null) {
         throw new HlaNotConnectedException("Not connected");
      }

      try {
         return createLocalInstance(
            null,
            rootFrameName,
            scenarioTimeEpoch
         );
      } catch (HlaInstanceNameInUseException e) {
         //can not happen with null hlaInstanceName
         return null;
      }
   }


   public HlaExecutionConfiguration createLocalHlaExecutionConfiguration(
      String hlaInstanceName,
      String rootFrameName,
      double scenarioTimeEpoch
   ) throws HlaIllegalInstanceNameException, HlaInstanceNameInUseException,
            HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {
      if (_objectClassHandle == null) {
         throw new HlaNotConnectedException("Not connected");
      }

      //Silently ignore if we could not register object instance name, we might have registered it before
      _objectManager.registerObjectInstanceName(hlaInstanceName);

      return createLocalInstance(
         hlaInstanceName,
         rootFrameName,
         scenarioTimeEpoch
      );
   }


   private HlaExecutionConfiguration createLocalInstance(
      String hlaInstanceName,
      String rootFrameName,
      double scenarioTimeEpoch
   ) throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {
      if (rootFrameName == null) {
         throw new NullPointerException("rootFrameName can not be null");
      }

      HlaExecutionConfigurationImpl instance;
      synchronized (_localCreationInProgressLock) {
         ObjectInstanceHandle objectInstanceHandle = _objectManager.createObjectInstance(_objectClassHandle, hlaInstanceName);
         instance = createObjectInstance(objectInstanceHandle, true, _worldImpl.getFederateId());
         _localInstances.put(objectInstanceHandle, instance);
         _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
         _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      }

      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();
      fireDiscovered(instance, timeStamp);

      for (HlaExecutionConfigurationListener instanceListener : _defaultInstanceListeners) {
         instance.addHlaExecutionConfigurationListener(instanceListener);
      }
      for (HlaExecutionConfigurationValueListener instanceValueListener : _defaultInstanceValueListeners) {
         instance.addHlaExecutionConfigurationValueListener(instanceValueListener);
      }

      instance.setCreateAttributes(
         rootFrameName,
         scenarioTimeEpoch,
         timeStamp
      );
      checkInitializedFired(instance, timeStamp, HlaLogicalTimeImpl.INVALID);

      return instance;
   }

   public HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration instance)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaExecutionConfiguration(instance, _worldImpl.getCurrentTime(), _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration instance, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaExecutionConfiguration(instance, timestamp, _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration instance, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaExecutionConfiguration(instance, _worldImpl.getCurrentTime(), logicalTime, true);
   }

   public HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaExecutionConfiguration(instance, timestamp, logicalTime, true);
   }

   private HlaExecutionConfiguration deleteHlaExecutionConfiguration(HlaExecutionConfiguration instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime, boolean checkLocal)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (instance.isRemoved() || (checkLocal && !instance.isLocal())) {
         return null;
      }

      HlaExecutionConfigurationImpl instanceImpl = (HlaExecutionConfigurationImpl) instance;
      ObjectInstanceHandle instanceHandle = instanceImpl.getObjectInstanceHandle();

      _objectManager.deleteObjectInstance(instanceHandle, timestamp, logicalTime);

      _hlaInstanceNameMap.remove(instance.getHlaInstanceName());
      _encodedHlaInstanceHandleMap.remove(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()));

      if (instance.isLocal()) {
         _localInstances.remove(instanceHandle);
      } else {
         _remoteInstances.remove(instanceHandle);
      }
      instanceImpl.setRemoved();
      fireDeleted(instance, timestamp, logicalTime);

      return instance;
   }

   public void addHlaExecutionConfigurationManagerListener(HlaExecutionConfigurationManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.add(listener);
   }

   public void removeHlaExecutionConfigurationManagerListener(HlaExecutionConfigurationManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.remove(listener);
   }


   public void addHlaExecutionConfigurationDefaultInstanceListener(HlaExecutionConfigurationListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.add(listener);
   }

   public void addHlaExecutionConfigurationDefaultInstanceValueListener(HlaExecutionConfigurationValueListener valueListener) {
      if (valueListener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceValueListeners.add(valueListener);
   }

   public void removeHlaExecutionConfigurationDefaultInstanceListener(HlaExecutionConfigurationListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.remove(listener);
   }

   public void removeHlaExecutionConfigurationDefaultInstanceValueListener(HlaExecutionConfigurationValueListener valueListener) {
      if (valueListener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceValueListeners.remove(valueListener);
   }

   public void setEnabled(boolean enabled) {
      _optional = false;
      _enabled = enabled;
   }

   public void setEnabledIfAvailableInFom() {
      _optional = true;
      _enabled = true;
   }

   public boolean isEnabled() {
      return _objectClassHandle != null;
   }

   /*
    * AbstractObjectClassManager impl
    */

   @Override
   public void discoverObjectInstance(final ObjectInstanceHandle objectInstanceHandle, String instanceName, HlaTimeStamp timeStamp, HlaFederateId producingFederate) {
      final HlaExecutionConfigurationImpl instance = createObjectInstance(objectInstanceHandle, false, producingFederate);
      _remoteInstances.put(objectInstanceHandle, instance);
      _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
      _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      fireDiscovered(instance, timeStamp);

      for (HlaExecutionConfigurationListener listener : _defaultInstanceListeners) {
         instance.addHlaExecutionConfigurationListener(listener);
      }
      for (HlaExecutionConfigurationValueListener listener : _defaultInstanceValueListeners) {
         instance.addHlaExecutionConfigurationValueListener(listener);
      }

      checkInitializedFired(instance, timeStamp, HlaLogicalTimeImpl.INVALID);

      if (HlaTuning.REQUEST) {
         _worldImpl.invokeDelay(HlaTuning.REQUEST_MIN_DELAY_MS, HlaTuning.REQUEST_MAX_DELAY_MS, () -> {
               if (!instance.isRemoved()) {
                  requestValueUpdates(instance, objectInstanceHandle, instance.getUnsetAttributeNames(), 0);
               }
            });
      }
   }

   private void requestValueUpdates(final HlaExecutionConfigurationImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
      if (!requestAttributes.isEmpty()) {
         requestValueUpdateForAttributes(requestAttributes, objectInstanceHandle);
         repeatRequestIfNotInitialized(instance, objectInstanceHandle, requestAttributes, retries);
      }
   }

   private void repeatRequestIfNotInitialized(final HlaExecutionConfigurationImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
      try {
          if (retries < HlaTuning.REQUEST_UNINITIALIZED_MAX_RETRIES) {
             _worldImpl.invokeDelay(
                   HlaTuning.REQUEST_UNINITIALIZED_RATE_MS,
                   HlaTuning.REQUEST_UNINITIALIZED_RATE_MS,
                   () -> {
                      // Checking isWithinInterest() also checks that the instance is not removed, even if DDM is not used.
                      if (!instance.isInitialized() && instance.isWithinInterest()) {
                         requestValueUpdates(
                               instance,
                               objectInstanceHandle,
                               instance.getUnsetAttributeNames()
                                     .stream()
                                     .filter(HlaExecutionConfigurationImpl.HLA_INITIALIZED_ATTRIBUTE_NAMES::contains)
                                     .filter(requestAttributes::contains)
                                     .collect(Collectors.toSet()),
                               retries + 1);
                      }
                   });
          }
      } catch (IllegalStateException ignore) {
        // Can occur if we try to repeat a request when disconnecting from the federation, which we can safely ignore.
      }
   }

   private void requestValueUpdateForAttributes(final Set<String> attributes, final ObjectInstanceHandle objectInstanceHandle) {
      try {
         _objectManager.requestAttributeValueUpdate(objectInstanceHandle, attributes, _worldImpl.getCurrentTime());
      } catch (HlaBaseException | HlaBaseRuntimeException e) {
         //ignore
      }
   }

   @Override
   public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle, NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      HlaExecutionConfigurationImpl instance = _remoteInstances.get(objectInstanceHandle);
      if (instance != null) {
         instance.reflectAttributeValues(attributes, timeStamp, logicalTime, producingFederate);
         checkInitializedFired(instance, timeStamp, logicalTime);
      } else {
         _worldImpl.postException(new HlaInternalException("Get reflect for an unknown instance: " + objectInstanceHandle));
      }
   }

   @Override
   public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      HlaExecutionConfigurationImpl instance = _remoteInstances.remove(objectInstanceHandle);

      if (instance != null) {
         _hlaInstanceNameMap.remove(instance.getHlaInstanceName());
         _encodedHlaInstanceHandleMap.remove(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()));
         instance.setRemoved();
         fireDeleted(instance, timeStamp, logicalTime);
      } else {
         _worldImpl.postException(new HlaInternalException("Get remove for an unknown instance: " + objectInstanceHandle));
      }
   }

   @Override
   public boolean isEnabled(String objectClassName) {
      return _enabled;
   }

   @Override
   public boolean isOptional(String objectClassName) {
      return _optional;
   }

   @Override
   public void connected(ObjectClassHandle objectClassHandle, String objectClassName)
         throws HlaNotConnectedException, HlaFomException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _objectClassHandle = objectClassHandle;

      _objectManager.publish(_objectClassHandle, HlaExecutionConfigurationImpl.HLA_ATTRIBUTE_NAMES);
      _objectManager.subscribe(_objectClassHandle, HlaExecutionConfigurationImpl.HLA_ATTRIBUTE_NAMES);
   }

   private void clearAllInstances(boolean doFireDeleted) {
      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();

      for (Iterator<HlaExecutionConfigurationImpl> iterator = MapAdapter.concat(_localInstances.values().iterator(), _remoteInstances.values().iterator()); iterator.hasNext();) {
         HlaExecutionConfigurationImpl instance = iterator.next();
         iterator.remove();
         instance.setRemoved();
         if (doFireDeleted) {
            fireDeleted(instance, timeStamp, HlaLogicalTimeImpl.INVALID);
         }
      }
      _hlaInstanceNameMap.clear();
      _encodedHlaInstanceHandleMap.clear();
   }

   @Override
   public void disconnected(ObjectClassHandle objectClassHandle) {
      _objectClassHandle = null;
      clearAllInstances(true);
   }

   @Override
   public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, Set<String> attributes, HlaTimeStamp hlaTimeStamp) {
      HlaExecutionConfigurationImpl instance;
      synchronized (_localCreationInProgressLock) {
         instance = _localInstances.get(objectInstanceHandle);
      }

      // instance may be null since provideAttributeValueUpdate is called on a separate thread
      if (instance != null) {
         instance.provideAttributeValueUpdate(attributes, hlaTimeStamp);
      }
   }

   @Override
   public void attributesInScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributes) {
   }

   @Override
   public void attributesOutOfScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributes) {
   }

   @Override
   public void save(ObjectClassHandle objectClassHandle) throws HlaSaveFederateException {
      if (!_localInstances.isEmpty()) {
         throw new HlaSaveFederateException("Unable to save local instances");
      }
      if (!_remoteInstances.isEmpty()) {
         throw new HlaSaveFederateException("Unable to save remote instances");
      }
   }

   @Override
   public void restore(ObjectClassHandle objectClassHandle) {
      clearAllInstances(false);
   }

   /*
    * Functions called from HlaExecutionConfigurationImpl
    */

   void updateAttributeValues(HlaExecutionConfigurationImpl instance, ObjectInstanceHandle objectInstanceHandle, NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException, HlaAttributeNotOwnedException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _objectManager.updateAttributeValues(objectInstanceHandle, attributes, timeStamp, logicalTime);

      checkInitializedFired(instance, timeStamp, logicalTime);
   }


   /*
    * Private helpers...
    */

   private HlaExecutionConfigurationImpl createObjectInstance(ObjectInstanceHandle objectInstanceHandle, boolean isLocal, HlaFederateId producingFederate) {
      return new HlaExecutionConfigurationImpl(_worldImpl, this, objectInstanceHandle, isLocal, producingFederate);
   }

   private void checkInitializedFired(HlaExecutionConfigurationImpl instance, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      if (!instance.isInitializedFired() && instance.isInitialized()) {
         instance.setInitializedFired();
         fireInitialized(instance, timeStamp, logicalTime);
      }
   }

   private void fireDiscovered(final HlaExecutionConfiguration instance, final HlaTimeStamp timeStamp) {
      for (final HlaExecutionConfigurationManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaExecutionConfigurationDiscovered(instance, timeStamp);
            }
         });
      }
   }

   private void fireDeleted(final HlaExecutionConfiguration instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaExecutionConfigurationManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaExecutionConfigurationDeleted(instance, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireInitialized(final HlaExecutionConfiguration instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaExecutionConfigurationManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaExecutionConfigurationInitialized(instance, timeStamp, logicalTime);
            }
         });
      }
   }
}
