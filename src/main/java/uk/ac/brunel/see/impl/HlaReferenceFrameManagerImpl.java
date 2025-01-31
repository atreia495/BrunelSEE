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
final class HlaReferenceFrameManagerImpl extends AbstractObjectClassManager implements HlaReferenceFrameManager {

   private static final String HLA_OBJECT_CLASS_NAME = "HLAobjectRoot.ReferenceFrame";

   private final ObjectManager _objectManager;
   private final HlaWorldImpl _worldImpl;
   private final Object _localCreationInProgressLock = new Object();

   private volatile boolean _enabled = true;
   private volatile boolean _optional = false;
   private ObjectClassHandle _objectClassHandle = null;

   private final Map<String, HlaReferenceFrameImpl> _hlaInstanceNameMap = new ConcurrentHashMap<String, HlaReferenceFrameImpl>();
   private final Map<ByteBuffer, HlaReferenceFrameImpl> _encodedHlaInstanceHandleMap = new ConcurrentHashMap<ByteBuffer, HlaReferenceFrameImpl>();
   private final Map<ObjectInstanceHandle, HlaReferenceFrameImpl> _localInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaReferenceFrameImpl>();
   private final Map<ObjectInstanceHandle, HlaReferenceFrameImpl> _remoteInstances =
         new ConcurrentHashMap<ObjectInstanceHandle, HlaReferenceFrameImpl>();

   private final Set<HlaReferenceFrameManagerListener> _listeners =
         new CopyOnWriteArraySet<HlaReferenceFrameManagerListener>();

   private final Set<HlaReferenceFrameValueListener> _defaultInstanceValueListeners =
         new CopyOnWriteArraySet<HlaReferenceFrameValueListener>();
   private final Set<HlaReferenceFrameListener> _defaultInstanceListeners =
         new CopyOnWriteArraySet<HlaReferenceFrameListener>();

   /*
    * Share encoders and decoders for HlaReferenceFrameImpl
    */
   protected final Object _encodersLock = new Object();
   protected final Object _decodersLock = new Object();

   HlaReferenceFrameManagerImpl(HlaWorldImpl world, ObjectManager objectManager) {
      _objectManager = objectManager;
      _worldImpl = world;

      _objectManager.addManager(this, HLA_OBJECT_CLASS_NAME);
   }

   /*
    * HlaReferenceFrameManager interface impl
    */

   public List<HlaReferenceFrame> getHlaReferenceFrames() {
      return getAllHlaReferenceFrames();
   }

   public List<HlaReferenceFrame> getAllHlaReferenceFrames() {
      List<HlaReferenceFrame> list = new ArrayList<HlaReferenceFrame>(_localInstances.size() + _remoteInstances.size());
      list.addAll(_localInstances.values());
      list.addAll(_remoteInstances.values());
      return list;
   }

   public List<HlaReferenceFrame> getLocalHlaReferenceFrames() {
      return new ArrayList<HlaReferenceFrame>(_localInstances.values());
   }

   public List<HlaReferenceFrame> getRemoteHlaReferenceFrames() {
      return new ArrayList<HlaReferenceFrame>(_remoteInstances.values());
   }

   public HlaReferenceFrameImpl getReferenceFrameByHlaInstanceName(String hlaInstanceName) {
      return _hlaInstanceNameMap.get(hlaInstanceName);
   }

   public HlaReferenceFrame getReferenceFrameByHlaInstanceHandle(byte[] encodedHlaInstanceHandle) {
      return _encodedHlaInstanceHandleMap.get(ByteBuffer.wrap(encodedHlaInstanceHandle));
   }

   public HlaReferenceFrame createLocalHlaReferenceFrame(
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


   public HlaReferenceFrame createLocalHlaReferenceFrame(
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


   private HlaReferenceFrame createLocalInstance(
      String hlaInstanceName
   ) throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaInternalException, HlaRtiException,
            HlaSaveInProgressException, HlaRestoreInProgressException {

      HlaReferenceFrameImpl instance;
      synchronized (_localCreationInProgressLock) {
         ObjectInstanceHandle objectInstanceHandle = _objectManager.createObjectInstance(_objectClassHandle, hlaInstanceName);
         instance = createObjectInstance(objectInstanceHandle, true, _worldImpl.getFederateId());
         _localInstances.put(objectInstanceHandle, instance);
         _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
         _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      }

      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();
      fireDiscovered(instance, timeStamp);

      for (HlaReferenceFrameListener instanceListener : _defaultInstanceListeners) {
         instance.addHlaReferenceFrameListener(instanceListener);
      }
      for (HlaReferenceFrameValueListener instanceValueListener : _defaultInstanceValueListeners) {
         instance.addHlaReferenceFrameValueListener(instanceValueListener);
      }

      checkInitializedFired(instance, timeStamp, HlaLogicalTimeImpl.INVALID);

      return instance;
   }

   public HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame instance)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaReferenceFrame(instance, _worldImpl.getCurrentTime(), _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame instance, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         return deleteHlaReferenceFrame(instance, timestamp, _worldImpl.getDefaultSendTime(), true);
      } catch (HlaInvalidLogicalTimeException e) {
         throw new HlaInternalException("Failed to delete instance with default Logical Time", e);
      }
   }

   public HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame instance, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaReferenceFrame(instance, _worldImpl.getCurrentTime(), logicalTime, true);
   }

   public HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return deleteHlaReferenceFrame(instance, timestamp, logicalTime, true);
   }

   private HlaReferenceFrame deleteHlaReferenceFrame(HlaReferenceFrame instance, HlaTimeStamp timestamp, HlaLogicalTime logicalTime, boolean checkLocal)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (instance.isRemoved() || (checkLocal && !instance.isLocal())) {
         return null;
      }

      HlaReferenceFrameImpl instanceImpl = (HlaReferenceFrameImpl) instance;
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

   public void addHlaReferenceFrameManagerListener(HlaReferenceFrameManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.add(listener);
   }

   public void removeHlaReferenceFrameManagerListener(HlaReferenceFrameManagerListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.remove(listener);
   }


   public void addHlaReferenceFrameDefaultInstanceListener(HlaReferenceFrameListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.add(listener);
   }

   public void addHlaReferenceFrameDefaultInstanceValueListener(HlaReferenceFrameValueListener valueListener) {
      if (valueListener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceValueListeners.add(valueListener);
   }

   public void removeHlaReferenceFrameDefaultInstanceListener(HlaReferenceFrameListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      }
      _defaultInstanceListeners.remove(listener);
   }

   public void removeHlaReferenceFrameDefaultInstanceValueListener(HlaReferenceFrameValueListener valueListener) {
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
      final HlaReferenceFrameImpl instance = createObjectInstance(objectInstanceHandle, false, producingFederate);
      _remoteInstances.put(objectInstanceHandle, instance);
      _hlaInstanceNameMap.put(instance.getHlaInstanceName(), instance);
      _encodedHlaInstanceHandleMap.put(ByteBuffer.wrap(instance.getEncodedHlaObjectInstanceHandle()), instance);
      fireDiscovered(instance, timeStamp);

      for (HlaReferenceFrameListener listener : _defaultInstanceListeners) {
         instance.addHlaReferenceFrameListener(listener);
      }
      for (HlaReferenceFrameValueListener listener : _defaultInstanceValueListeners) {
         instance.addHlaReferenceFrameValueListener(listener);
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

   private void requestValueUpdates(final HlaReferenceFrameImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
      if (!requestAttributes.isEmpty()) {
         requestValueUpdateForAttributes(requestAttributes, objectInstanceHandle);
         repeatRequestIfNotInitialized(instance, objectInstanceHandle, requestAttributes, retries);
      }
   }

   private void repeatRequestIfNotInitialized(final HlaReferenceFrameImpl instance, final ObjectInstanceHandle objectInstanceHandle, final Set<String> requestAttributes, int retries) {
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
                                     .filter(HlaReferenceFrameImpl.HLA_INITIALIZED_ATTRIBUTE_NAMES::contains)
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
      HlaReferenceFrameImpl instance = _remoteInstances.get(objectInstanceHandle);
      if (instance != null) {
         instance.reflectAttributeValues(attributes, timeStamp, logicalTime, producingFederate);
         checkInitializedFired(instance, timeStamp, logicalTime);
      } else {
         _worldImpl.postException(new HlaInternalException("Get reflect for an unknown instance: " + objectInstanceHandle));
      }
   }

   @Override
   public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      HlaReferenceFrameImpl instance = _remoteInstances.remove(objectInstanceHandle);

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

      _objectManager.publish(_objectClassHandle, HlaReferenceFrameImpl.HLA_ATTRIBUTE_NAMES);
      _objectManager.subscribe(_objectClassHandle, HlaReferenceFrameImpl.HLA_ATTRIBUTE_NAMES);
   }

   private void clearAllInstances(boolean doFireDeleted) {
      HlaTimeStamp timeStamp = _worldImpl.getCurrentTime();

      for (Iterator<HlaReferenceFrameImpl> iterator = MapAdapter.concat(_localInstances.values().iterator(), _remoteInstances.values().iterator()); iterator.hasNext();) {
         HlaReferenceFrameImpl instance = iterator.next();
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
      HlaReferenceFrameImpl instance;
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
    * Functions called from HlaReferenceFrameImpl
    */

   void updateAttributeValues(HlaReferenceFrameImpl instance, ObjectInstanceHandle objectInstanceHandle, NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException, HlaAttributeNotOwnedException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _objectManager.updateAttributeValues(objectInstanceHandle, attributes, timeStamp, logicalTime);

      checkInitializedFired(instance, timeStamp, logicalTime);
   }


   /*
    * Private helpers...
    */

   private HlaReferenceFrameImpl createObjectInstance(ObjectInstanceHandle objectInstanceHandle, boolean isLocal, HlaFederateId producingFederate) {
      return new HlaReferenceFrameImpl(_worldImpl, this, objectInstanceHandle, isLocal, producingFederate);
   }

   private void checkInitializedFired(HlaReferenceFrameImpl instance, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      if (!instance.isInitializedFired() && instance.isInitialized()) {
         instance.setInitializedFired();
         fireInitialized(instance, timeStamp, logicalTime);
      }
   }

   private void fireDiscovered(final HlaReferenceFrame instance, final HlaTimeStamp timeStamp) {
      for (final HlaReferenceFrameManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaReferenceFrameDiscovered(instance, timeStamp);
            }
         });
      }
   }

   private void fireDeleted(final HlaReferenceFrame instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaReferenceFrameManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaReferenceFrameDeleted(instance, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireInitialized(final HlaReferenceFrame instance, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      for (final HlaReferenceFrameManagerListener listener : _listeners) {
         _worldImpl.postNotification(new Runnable() {
            public void run() {
               listener.hlaReferenceFrameInitialized(instance, timeStamp, logicalTime);
            }
         });
      }
   }
}
