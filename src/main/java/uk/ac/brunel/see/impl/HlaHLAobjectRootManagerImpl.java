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
final class HlaHLAobjectRootManagerImpl extends AbstractObjectClassManager implements HlaHLAobjectRootManager {

   private static final String HLA_OBJECT_CLASS_NAME = "HLAobjectRoot";

   private final ObjectManager _objectManager;
   private final HlaWorldImpl _worldImpl;
   private final Object _localCreationInProgressLock = new Object();

   private volatile boolean _enabled = true;
   private volatile boolean _optional = false;
   private ObjectClassHandle _objectClassHandle = null;

   private final Map<String, HlaHLAobjectRootImpl> _hlaInstanceNameMap = new ConcurrentHashMap<String, HlaHLAobjectRootImpl>();
   private final Map<ByteBuffer, HlaHLAobjectRootImpl> _encodedHlaInstanceHandleMap = new ConcurrentHashMap<ByteBuffer, HlaHLAobjectRootImpl>();
   private final Map<ObjectInstanceHandle, HlaHLAobjectRootImpl> _localInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaHLAobjectRootImpl>();
   private final Map<ObjectInstanceHandle, HlaHLAobjectRootImpl> _remoteInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaHLAobjectRootImpl>();

   private final Set<HlaHLAobjectRootManagerListener> _listeners =
         new CopyOnWriteArraySet<HlaHLAobjectRootManagerListener>();

   private final Set<HlaHLAobjectRootValueListener> _defaultInstanceValueListeners =
         new CopyOnWriteArraySet<HlaHLAobjectRootValueListener>();
   private final Set<HlaHLAobjectRootListener> _defaultInstanceListeners =
         new CopyOnWriteArraySet<HlaHLAobjectRootListener>();

   /*
    * Share encoders and decoders for HlaHLAobjectRootImpl
    */
   protected final Object _encodersLock = new Object();
   protected final Object _decodersLock = new Object();

   HlaHLAobjectRootManagerImpl(HlaWorldImpl world, ObjectManager objectManager) {
      _objectManager = objectManager;
      _worldImpl = world;

      _objectManager.addManager(this, HLA_OBJECT_CLASS_NAME);
   }

   /*
    * HlaHLAobjectRootManager interface impl
    */

   public List<HlaHLAobjectRoot> getHlaHLAobjectRoots() {
      return getAllHlaHLAobjectRoots();
   }

   public List<HlaHLAobjectRoot> getAllHlaHLAobjectRoots() {
      List<HlaHLAobjectRoot> list = new ArrayList<HlaHLAobjectRoot>(_localInstances.size() + _remoteInstances.size());
      list.addAll(_localInstances.values());
      list.addAll(_remoteInstances.values());
      return list;
   }

   public List<HlaHLAobjectRoot> getLocalHlaHLAobjectRoots() {
      return new ArrayList<HlaHLAobjectRoot>(_localInstances.values());
   }

   public List<HlaHLAobjectRoot> getRemoteHlaHLAobjectRoots() {
      return new ArrayList<HlaHLAobjectRoot>(_remoteInstances.values());
   }

   public HlaHLAobjectRootImpl getHLAobjectRootByHlaInstanceName(String hlaInstanceName) {
      return _hlaInstanceNameMap.get(hlaInstanceName);
   }

   public HlaHLAobjectRoot getHLAobjectRootByHlaInstanceHandle(byte[] encodedHlaInstanceHandle) {
      return _encodedHlaInstanceHandleMap.get(ByteBuffer.wrap(encodedHlaInstanceHandle));
   }

   public HlaHLAobjectRoot createLocalHlaHLAobjectRoot(
   ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {
      if (_objectClassHandle == null) {
         throw new HlaNotConnectedException("Not connected");
      }

      try {
         return createLocalInstance(
            null
         );
      } catch (HlaInstanceNameInUseException e) {
         //can not happen with null hlaInstanceName
         return null;
      }
   }


   public HlaHLAobjectRoot createLocalHlaHLAobjectRoot(
      String hlaInstanceName
   ) throws HlaIllegalInstanceNameException, HlaInstanceNameInUseException,
            HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {
      if (_objectClassHandle == null) {
         throw new HlaNotConnectedException("Not connected");
      }

      //Silently ignore if we could not register object instance name, we might have registered it before
      _objectManager.registerObjectInstanceName(hlaInstanceName);

      return createLocalInstance(
         hlaInstanceName
      );
   }


   private HlaHLAobjectRoot createLocalInstance(
      String hlaInstanceName
   ) throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {

      HlaHLAobjectRootImpl instance;
      synchronized (_localCreationInProgressLock) {
         ObjectInstanceHandle objectInstanceHandle = _objectManager.createObjectInstance(_objectClassHandle, hlaInstanceName);
         instance = createObjectInstance(objectInstanceHandle, true, _worldImpl.getFederateId());
         _localInstances.put(objectInstanceHandle, instance);
         _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
         _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      }

      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();
      fireDiscovered(instance, timeStamp);

      for (HlaHLAobjectRootListener instanceListener : _defaultInstanceListeners) {
         instance.addHlaHLAobjectRootListener(instanceListener);
      }
      for (HlaHLAobjectRootValueListener instanceValueListener : _defaultInstanceValueListeners) {
         instance.addHlaHLAobjectRootValueListener(instanceValueListener);
      }

      checkInitializedFired(instance, timeStamp, HlaLogicalTimeImpl.INVALID);

      return instance;
   }

   public HlaHLAobjectRoot deleteLocalHlaHLAobjectRoot(HlaHLAobjectRoot instance)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaHLAobjectRoot(instance, _worldImpl.getCurrentTime(), _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaHLAobjectRoot deleteLocalHlaHLAobjectRoot(HlaHLAobjectRoot instance, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaHLAobjectRoot(instance, timestamp, _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaHLAobjectRoot deleteLocalHlaHLAobjectRoot(HlaHLAobjectRoot instance, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaHLAobjectRoot(instance, _worldImpl.getCurrentTime(), logicalTime, true);
   }

   public HlaHLAobjectRoot deleteLocalHlaHLAobjectRoot(HlaHLAobjectRoot instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaHLAobjectRoot(instance, timestamp, logicalTime, true);
   }

   private HlaHLAobjectRoot deleteHlaHLAobjectRoot(HlaHLAobjectRoot instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime, boolean checkLocal)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (instance.isRemoved() || (checkLocal && !instance.isLocal())) {
         return null;
      }

      HlaHLAobjectRootImpl instanceImpl = (HlaHLAobjectRootImpl) instance;
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

   public void addHlaHLAobjectRootManagerListener(HlaHLAobjectRootManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.add(listener);
   }

   public void removeHlaHLAobjectRootManagerListener(HlaHLAobjectRootManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.remove(listener);
   }


   public void addHlaHLAobjectRootDefaultInstanceListener(HlaHLAobjectRootListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.add(listener);
   }

   public void addHlaHLAobjectRootDefaultInstanceValueListener(HlaHLAobjectRootValueListener valueListener) {
      if (valueListener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceValueListeners.add(valueListener);
   }

   public void removeHlaHLAobjectRootDefaultInstanceListener(HlaHLAobjectRootListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.remove(listener);
   }

   public void removeHlaHLAobjectRootDefaultInstanceValueListener(HlaHLAobjectRootValueListener valueListener) {
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
      final HlaHLAobjectRootImpl instance = createObjectInstance(objectInstanceHandle, false, producingFederate);
      _remoteInstances.put(objectInstanceHandle, instance);
      _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
      _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      fireDiscovered(instance, timeStamp);

      for (HlaHLAobjectRootListener listener : _defaultInstanceListeners) {
         instance.addHlaHLAobjectRootListener(listener);
      }
      for (HlaHLAobjectRootValueListener listener : _defaultInstanceValueListeners) {
         instance.addHlaHLAobjectRootValueListener(listener);
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

   private void requestValueUpdates(final HlaHLAobjectRootImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
      if (!requestAttributes.isEmpty()) {
         requestValueUpdateForAttributes(requestAttributes, objectInstanceHandle);
         repeatRequestIfNotInitialized(instance, objectInstanceHandle, requestAttributes, retries);
      }
   }

   private void repeatRequestIfNotInitialized(final HlaHLAobjectRootImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
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
                                     .filter(HlaHLAobjectRootImpl.HLA_INITIALIZED_ATTRIBUTE_NAMES::contains)
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
      HlaHLAobjectRootImpl instance = _remoteInstances.get(objectInstanceHandle);
      if (instance != null) {
         instance.reflectAttributeValues(attributes, timeStamp, logicalTime, producingFederate);
         checkInitializedFired(instance, timeStamp, logicalTime);
      } else {
         _worldImpl.postException(new HlaInternalException("Get reflect for an unknown instance: " + objectInstanceHandle));
      }
   }

   @Override
   public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      HlaHLAobjectRootImpl instance = _remoteInstances.remove(objectInstanceHandle);

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

      _objectManager.publish(_objectClassHandle, HlaHLAobjectRootImpl.HLA_ATTRIBUTE_NAMES);
      _objectManager.subscribe(_objectClassHandle, HlaHLAobjectRootImpl.HLA_ATTRIBUTE_NAMES);
   }

   private void clearAllInstances(boolean doFireDeleted) {
      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();

      for (Iterator<HlaHLAobjectRootImpl> iterator = MapAdapter.concat(_localInstances.values().iterator(), _remoteInstances.values().iterator()); iterator.hasNext();) {
         HlaHLAobjectRootImpl instance = iterator.next();
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
      HlaHLAobjectRootImpl instance;
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
    * Functions called from HlaHLAobjectRootImpl
    */

   void updateAttributeValues(HlaHLAobjectRootImpl instance, ObjectInstanceHandle objectInstanceHandle, NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException, HlaAttributeNotOwnedException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _objectManager.updateAttributeValues(objectInstanceHandle, attributes, timeStamp, logicalTime);

      checkInitializedFired(instance, timeStamp, logicalTime);
   }


   /*
    * Private helpers...
    */

   private HlaHLAobjectRootImpl createObjectInstance(ObjectInstanceHandle objectInstanceHandle, boolean isLocal, HlaFederateId producingFederate) {
      return new HlaHLAobjectRootImpl(_worldImpl, this, objectInstanceHandle, isLocal, producingFederate);
   }

   private void checkInitializedFired(HlaHLAobjectRootImpl instance, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      if (!instance.isInitializedFired() && instance.isInitialized()) {
         instance.setInitializedFired();
         fireInitialized(instance, timeStamp, logicalTime);
      }
   }

   private void fireDiscovered(final HlaHLAobjectRoot instance, final HlaTimeStamp timeStamp) {
      for (final HlaHLAobjectRootManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaHLAobjectRootDiscovered(instance, timeStamp);
            }
         });
      }
   }

   private void fireDeleted(final HlaHLAobjectRoot instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaHLAobjectRootManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaHLAobjectRootDeleted(instance, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireInitialized(final HlaHLAobjectRoot instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaHLAobjectRootManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaHLAobjectRootInitialized(instance, timeStamp, logicalTime);
            }
         });
      }
   }
}
