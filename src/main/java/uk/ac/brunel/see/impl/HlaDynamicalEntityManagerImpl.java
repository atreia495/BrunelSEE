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

import uk.ac.brunel.see.impl.encoders.*;
import se.pitch.encoders1516.HLAfloat64LE;
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
final class HlaDynamicalEntityManagerImpl extends AbstractObjectClassManager implements HlaDynamicalEntityManager {

   private static final String HLA_OBJECT_CLASS_NAME = "HLAobjectRoot.PhysicalEntity.DynamicalEntity";

   private final ObjectManager _objectManager;
   private final HlaWorldImpl _worldImpl;
   private final Object _localCreationInProgressLock = new Object();

   private volatile boolean _enabled = true;
   private volatile boolean _optional = false;
   private ObjectClassHandle _objectClassHandle = null;

   private final Map<String, HlaDynamicalEntityImpl> _hlaInstanceNameMap = new ConcurrentHashMap<String, HlaDynamicalEntityImpl>();
   private final Map<ByteBuffer, HlaDynamicalEntityImpl> _encodedHlaInstanceHandleMap = new ConcurrentHashMap<ByteBuffer, HlaDynamicalEntityImpl>();
   private final Map<ObjectInstanceHandle, HlaDynamicalEntityImpl> _localInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaDynamicalEntityImpl>();
   private final Map<ObjectInstanceHandle, HlaDynamicalEntityImpl> _remoteInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaDynamicalEntityImpl>();
   private final CacheMap<String, HlaDynamicalEntityImpl> _getByNameCache =
         CacheMap.create(String.class, size -> size > _localInstances.size() + _remoteInstances.size());

   private final Set<HlaDynamicalEntityManagerListener> _listeners =
         new CopyOnWriteArraySet<HlaDynamicalEntityManagerListener>();

   private final Set<HlaDynamicalEntityValueListener> _defaultInstanceValueListeners =
         new CopyOnWriteArraySet<HlaDynamicalEntityValueListener>();
   private final Set<HlaDynamicalEntityListener> _defaultInstanceListeners =
         new CopyOnWriteArraySet<HlaDynamicalEntityListener>();

   /*
    * Share encoders and decoders for HlaDynamicalEntityImpl
    */
   protected final Object _encodersLock = new Object();
   protected final Object _decodersLock = new Object();
   /* @GuardedBy("_encodersLock") */
   protected final ForceVectorEncoder _forceVectorEncoderEncoder = new ForceVectorEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final ForceVectorEncoder _forceVectorEncoderDecoder = new ForceVectorEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final TorqueVectorEncoder _torqueVectorEncoderEncoder = new TorqueVectorEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final TorqueVectorEncoder _torqueVectorEncoderDecoder = new TorqueVectorEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final HLAfloat64LE _hLAfloat64LEEncoder = BaseEncoder.createHLAfloat64LE();
   /* @GuardedBy("_decodersLock") */
   protected final HLAfloat64LE _hLAfloat64LEDecoder = BaseEncoder.createHLAfloat64LE();
   /* @GuardedBy("_encodersLock") */
   protected final InertiaMatrixEncoder _inertiaMatrixEncoderEncoder = new InertiaMatrixEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final InertiaMatrixEncoder _inertiaMatrixEncoderDecoder = new InertiaMatrixEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final InertiaRateMatrixEncoder _inertiaRateMatrixEncoderEncoder = new InertiaRateMatrixEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final InertiaRateMatrixEncoder _inertiaRateMatrixEncoderDecoder = new InertiaRateMatrixEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final HLAunicodeString _hLAunicodeStringEncoder = BaseEncoder.createHLAunicodeString();
   /* @GuardedBy("_decodersLock") */
   protected final HLAunicodeString _hLAunicodeStringDecoder = BaseEncoder.createHLAunicodeString();
   /* @GuardedBy("_encodersLock") */
   protected final SpaceTimeCoordinateStateEncoder _spaceTimeCoordinateStateEncoderEncoder = new SpaceTimeCoordinateStateEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final SpaceTimeCoordinateStateEncoder _spaceTimeCoordinateStateEncoderDecoder = new SpaceTimeCoordinateStateEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final AccelerationVectorEncoder _accelerationVectorEncoderEncoder = new AccelerationVectorEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final AccelerationVectorEncoder _accelerationVectorEncoderDecoder = new AccelerationVectorEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final AngularAccelerationVectorEncoder _angularAccelerationVectorEncoderEncoder = new AngularAccelerationVectorEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final AngularAccelerationVectorEncoder _angularAccelerationVectorEncoderDecoder = new AngularAccelerationVectorEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final PositionVectorEncoder _positionVectorEncoderEncoder = new PositionVectorEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final PositionVectorEncoder _positionVectorEncoderDecoder = new PositionVectorEncoder();
   /* @GuardedBy("_encodersLock") */
   protected final AttitudeQuaternionEncoder _attitudeQuaternionEncoderEncoder = new AttitudeQuaternionEncoder();
   /* @GuardedBy("_decodersLock") */
   protected final AttitudeQuaternionEncoder _attitudeQuaternionEncoderDecoder = new AttitudeQuaternionEncoder();

   HlaDynamicalEntityManagerImpl(HlaWorldImpl world, ObjectManager objectManager) {
      _objectManager = objectManager;
      _worldImpl = world;

      _objectManager.addManager(this, HLA_OBJECT_CLASS_NAME);
   }

   /*
    * HlaDynamicalEntityManager interface impl
    */

   public List<HlaDynamicalEntity> getHlaDynamicalEntitys() {
      return getAllHlaDynamicalEntitys();
   }

   public List<HlaDynamicalEntity> getAllHlaDynamicalEntitys() {
      List<HlaDynamicalEntity> list = new ArrayList<HlaDynamicalEntity>(_localInstances.size() + _remoteInstances.size());
      list.addAll(_localInstances.values());
      list.addAll(_remoteInstances.values());
      return list;
   }

   public List<HlaDynamicalEntity> getLocalHlaDynamicalEntitys() {
      return new ArrayList<HlaDynamicalEntity>(_localInstances.values());
   }

   public List<HlaDynamicalEntity> getRemoteHlaDynamicalEntitys() {
      return new ArrayList<HlaDynamicalEntity>(_remoteInstances.values());
   }

   public HlaDynamicalEntityImpl getDynamicalEntityByName(String name) {
      HlaDynamicalEntityImpl cachedInstance = _getByNameCache.get(name);
      if (cachedInstance != null) {
         if (cachedInstance.isRemoved() || !Equals.isEquals(cachedInstance.getName(), name)) {
            _getByNameCache.remove(name);
         } else {
            return cachedInstance;
         }
      }

      for (HlaDynamicalEntityImpl instance : MapAdapter.concat(_localInstances.values(), _remoteInstances.values())) {
         if (instance.hasName() &&
             Equals.isEquals(instance.getName(), name)) {
            _getByNameCache.put(name, instance);
            return instance;
         }
      }
      return null;
   }

   public HlaDynamicalEntityImpl getDynamicalEntityByHlaInstanceName(String hlaInstanceName) {
      return _hlaInstanceNameMap.get(hlaInstanceName);
   }

   public HlaDynamicalEntity getDynamicalEntityByHlaInstanceHandle(byte[] encodedHlaInstanceHandle) {
      return _encodedHlaInstanceHandleMap.get(ByteBuffer.wrap(encodedHlaInstanceHandle));
   }

   public HlaDynamicalEntity createLocalHlaDynamicalEntity(
      String name,
      String parentReferenceFrame
   ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {
      if (_objectClassHandle == null) {
         throw new HlaNotConnectedException("Not connected");
      }

      try {
         return createLocalInstance(
            null,
            name,
            parentReferenceFrame
         );
      } catch (HlaInstanceNameInUseException e) {
         //can not happen with null hlaInstanceName
         return null;
      }
   }


   public HlaDynamicalEntity createLocalHlaDynamicalEntity(
      String hlaInstanceName,
      String name,
      String parentReferenceFrame
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
         name,
         parentReferenceFrame
      );
   }


   private HlaDynamicalEntity createLocalInstance(
      String hlaInstanceName,
      String name,
      String parentReferenceFrame
   ) throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {
      if (name == null) {
         throw new NullPointerException("name can not be null");
      }
      if (parentReferenceFrame == null) {
         throw new NullPointerException("parentReferenceFrame can not be null");
      }

      HlaDynamicalEntityImpl instance;
      synchronized (_localCreationInProgressLock) {
         ObjectInstanceHandle objectInstanceHandle = _objectManager.createObjectInstance(_objectClassHandle, hlaInstanceName);
         instance = createObjectInstance(objectInstanceHandle, true, _worldImpl.getFederateId());
         _localInstances.put(objectInstanceHandle, instance);
         _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
         _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      }

      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();
      fireDiscovered(instance, timeStamp);

      for (HlaDynamicalEntityListener instanceListener : _defaultInstanceListeners) {
         instance.addHlaDynamicalEntityListener(instanceListener);
      }
      for (HlaDynamicalEntityValueListener instanceValueListener : _defaultInstanceValueListeners) {
         instance.addHlaDynamicalEntityValueListener(instanceValueListener);
      }

      instance.setCreateAttributes(
         name,
         parentReferenceFrame,
         timeStamp
      );
      checkInitializedFired(instance, timeStamp, HlaLogicalTimeImpl.INVALID);

      return instance;
   }

   public HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity instance)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaDynamicalEntity(instance, _worldImpl.getCurrentTime(), _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity instance, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaDynamicalEntity(instance, timestamp, _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity instance, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaDynamicalEntity(instance, _worldImpl.getCurrentTime(), logicalTime, true);
   }

   public HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaDynamicalEntity(instance, timestamp, logicalTime, true);
   }

   private HlaDynamicalEntity deleteHlaDynamicalEntity(HlaDynamicalEntity instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime, boolean checkLocal)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (instance.isRemoved() || (checkLocal && !instance.isLocal())) {
         return null;
      }

      HlaDynamicalEntityImpl instanceImpl = (HlaDynamicalEntityImpl) instance;
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
      if (instance.hasName()) {
         _getByNameCache.remove(instance.getName());
      }
      fireDeleted(instance, timestamp, logicalTime);

      return instance;
   }

   public void addHlaDynamicalEntityManagerListener(HlaDynamicalEntityManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.add(listener);
   }

   public void removeHlaDynamicalEntityManagerListener(HlaDynamicalEntityManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.remove(listener);
   }


   public void addHlaDynamicalEntityDefaultInstanceListener(HlaDynamicalEntityListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.add(listener);
   }

   public void addHlaDynamicalEntityDefaultInstanceValueListener(HlaDynamicalEntityValueListener valueListener) {
      if (valueListener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceValueListeners.add(valueListener);
   }

   public void removeHlaDynamicalEntityDefaultInstanceListener(HlaDynamicalEntityListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.remove(listener);
   }

   public void removeHlaDynamicalEntityDefaultInstanceValueListener(HlaDynamicalEntityValueListener valueListener) {
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
      final HlaDynamicalEntityImpl instance = createObjectInstance(objectInstanceHandle, false, producingFederate);
      _remoteInstances.put(objectInstanceHandle, instance);
      _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
      _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      fireDiscovered(instance, timeStamp);

      for (HlaDynamicalEntityListener listener : _defaultInstanceListeners) {
         instance.addHlaDynamicalEntityListener(listener);
      }
      for (HlaDynamicalEntityValueListener listener : _defaultInstanceValueListeners) {
         instance.addHlaDynamicalEntityValueListener(listener);
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

   private void requestValueUpdates(final HlaDynamicalEntityImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
      if (!requestAttributes.isEmpty()) {
         requestValueUpdateForAttributes(requestAttributes, objectInstanceHandle);
         repeatRequestIfNotInitialized(instance, objectInstanceHandle, requestAttributes, retries);
      }
   }

   private void repeatRequestIfNotInitialized(final HlaDynamicalEntityImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
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
                                     .filter(HlaDynamicalEntityImpl.HLA_INITIALIZED_ATTRIBUTE_NAMES::contains)
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
      HlaDynamicalEntityImpl instance = _remoteInstances.get(objectInstanceHandle);
      if (instance != null) {
         instance.reflectAttributeValues(attributes, timeStamp, logicalTime, producingFederate);
         checkInitializedFired(instance, timeStamp, logicalTime);
      } else {
         _worldImpl.postException(new HlaInternalException("Get reflect for an unknown instance: " + objectInstanceHandle));
      }
   }

   @Override
   public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      HlaDynamicalEntityImpl instance = _remoteInstances.remove(objectInstanceHandle);

      if (instance != null) {
         _hlaInstanceNameMap.remove(instance.getHlaInstanceName());
         _encodedHlaInstanceHandleMap.remove(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()));
         instance.setRemoved();
         if (instance.hasName()) {
            _getByNameCache.remove(instance.getName());
         }
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

      _objectManager.publish(_objectClassHandle, HlaDynamicalEntityImpl.HLA_ATTRIBUTE_NAMES);
      _objectManager.subscribe(_objectClassHandle, HlaDynamicalEntityImpl.HLA_ATTRIBUTE_NAMES);
   }

   private void clearAllInstances(boolean doFireDeleted) {
      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();

      for (Iterator<HlaDynamicalEntityImpl> iterator = MapAdapter.concat(_localInstances.values().iterator(), _remoteInstances.values().iterator()); iterator.hasNext();) {
         HlaDynamicalEntityImpl instance = iterator.next();
         iterator.remove();
         instance.setRemoved();
         if (doFireDeleted) {
            fireDeleted(instance, timeStamp, HlaLogicalTimeImpl.INVALID);
         }
      }
      _hlaInstanceNameMap.clear();
      _encodedHlaInstanceHandleMap.clear();
      _getByNameCache.clear();
   }

   @Override
   public void disconnected(ObjectClassHandle objectClassHandle) {
      _objectClassHandle = null;
      clearAllInstances(true);
   }

   @Override
   public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, Set<String> attributes, HlaTimeStamp hlaTimeStamp) {
      HlaDynamicalEntityImpl instance;
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
    * Functions called from HlaDynamicalEntityImpl
    */

   void updateAttributeValues(HlaDynamicalEntityImpl instance, ObjectInstanceHandle objectInstanceHandle, NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException, HlaAttributeNotOwnedException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _objectManager.updateAttributeValues(objectInstanceHandle, attributes, timeStamp, logicalTime);

      checkInitializedFired(instance, timeStamp, logicalTime);
   }


   /*
    * Private helpers...
    */

   private HlaDynamicalEntityImpl createObjectInstance(ObjectInstanceHandle objectInstanceHandle, boolean isLocal, HlaFederateId producingFederate) {
      return new HlaDynamicalEntityImpl(_worldImpl, this, objectInstanceHandle, isLocal, producingFederate);
   }

   private void checkInitializedFired(HlaDynamicalEntityImpl instance, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      if (!instance.isInitializedFired() && instance.isInitialized()) {
         _getByNameCache.put(instance.getName(), instance);
         instance.setInitializedFired();
         fireInitialized(instance, timeStamp, logicalTime);
      }
   }

   private void fireDiscovered(final HlaDynamicalEntity instance, final HlaTimeStamp timeStamp) {
      for (final HlaDynamicalEntityManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaDynamicalEntityDiscovered(instance, timeStamp);
            }
         });
      }
   }

   private void fireDeleted(final HlaDynamicalEntity instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaDynamicalEntityManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaDynamicalEntityDeleted(instance, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireInitialized(final HlaDynamicalEntity instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaDynamicalEntityManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaDynamicalEntityInitialized(instance, timeStamp, logicalTime);
            }
         });
      }
   }
}
