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
import uk.ac.brunel.see.datatypes.ToStringBuilder;
import uk.ac.brunel.see.impl.utils.AttributeValue;
import uk.ac.brunel.see.impl.utils.Equals;
import uk.ac.brunel.see.impl.utils.NameValueMap;
import uk.ac.brunel.see.impl.utils.SetAdapter;
import uk.ac.brunel.see.impl.utils.ParameterValue;

import se.pitch.ral.api.ObjectInstanceHandle;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/* @ThreadSafe */
final class HlaReferenceFrameImpl implements HlaReferenceFrame {



   public static final Set<String> HLA_ATTRIBUTE_NAMES = new SetAdapter<String>()
         .unmodifiableSet();

   public static final Set<String> HLA_INITIALIZED_ATTRIBUTE_NAMES = new SetAdapter<String>()
         .unmodifiableSet();

   private final HlaWorldImpl _hlaWorld;
   private final HlaReferenceFrameManagerImpl _objectClassManager;
   private final ObjectInstanceHandle _objectInstanceHandle;
   private final boolean _isLocal;
   private final HlaFederateId _createdByFederate;

   private final Object _attributesLock = new Object();
   private final Object _sendLock = new Object();


   private final Set<HlaReferenceFrameValueListener> _valueListeners = new CopyOnWriteArraySet<HlaReferenceFrameValueListener>();
   private final Set<HlaReferenceFrameListener> _listeners = new CopyOnWriteArraySet<HlaReferenceFrameListener>();

   private volatile boolean _initializeFired = false;
   private volatile boolean _removed = false;

   HlaReferenceFrameImpl(HlaWorldImpl hlaWorld, HlaReferenceFrameManagerImpl objectClassManager, ObjectInstanceHandle objectInstanceHandle, boolean local, HlaFederateId producingFederate) {
      _hlaWorld = hlaWorld;
      _objectClassManager = objectClassManager;
      _objectInstanceHandle = objectInstanceHandle;
      _isLocal = local;
      _createdByFederate = producingFederate;
   }

   /*
    * HlaReferenceFrame interface impl
    */

   public boolean isLocal() {
      return _isLocal;
   }

   public boolean isInitialized() {
      return true;
   }

   public boolean isWithinInterest() {
      return !_removed;
   }

   public boolean isRemoved() {
      return _removed;
   }

   public HlaFederateId getProducingFederate() {
      return _createdByFederate;
   }

   public HlaReferenceFrameAttributes getHlaReferenceFrameAttributes() {
      synchronized (_attributesLock) {
         return new HlaReferenceFrameAttributesImpl(
         );
      }
   }

   public HlaReferenceFrameUpdater getHlaReferenceFrameUpdater() {
      return new HlaReferenceFrameUpdaterImpl(this);
   }

   public String getHlaInstanceName() {
      return _objectInstanceHandle.getObjectInstanceName();
   }

   public byte[] getEncodedHlaObjectInstanceHandle() {
      return _objectInstanceHandle.getEncodedHandle();
   }

   public ObjectClassType getClassType() {
      return ObjectClassType.REFERENCE_FRAME;
   }


   public void addHlaReferenceFrameValueListener(HlaReferenceFrameValueListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _valueListeners.add(listener);
   }

   public void removeHlaReferenceFrameValueListener(HlaReferenceFrameValueListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _valueListeners.remove(listener);
   }

   public void addHlaReferenceFrameListener(HlaReferenceFrameListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.add(listener);
   }

   public void removeHlaReferenceFrameListener(HlaReferenceFrameListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.remove(listener);
   }

   /*
    * Functions used by HlaReferenceFrameManagerImpl
    */

   boolean isInitializedFired() {
      return _initializeFired;
   }

   void setInitializedFired() {
      _initializeFired = true;
   }

   ObjectInstanceHandle getObjectInstanceHandle() {
      return _objectInstanceHandle;
   }

   void setRemoved() {
      _removed = true;
      _valueListeners.clear();
      _listeners.clear();
   }


   void provideAttributeValueUpdate(Set<String> attributes, HlaTimeStamp hlaTimeStamp) {
      try {
         synchronized (_sendLock) {
            sendAttributes(getAttributeEnum(attributes), getHlaReferenceFrameAttributes(), hlaTimeStamp, HlaLogicalTimeImpl.INVALID);
         }
      } catch (HlaBaseException e) {
         //ignore
      } catch (HlaBaseRuntimeException e) {
         //ignore
      }
   }

   Set<String> getUnsetAttributeNames() {
      Set<String> unsetAttributes = new HashSet<String>(HLA_ATTRIBUTE_NAMES.size());
      HlaReferenceFrameAttributes attributes = getHlaReferenceFrameAttributes();
      return unsetAttributes;
   }

   @SuppressWarnings ({ "UnnecessaryContinue" })
   void reflectAttributeValues(NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      Set<HlaReferenceFrameAttributes.Attribute> attributesUpdated = EnumSet.noneOf(HlaReferenceFrameAttributes.Attribute.class);

      fireAttributesUpdated(attributesUpdated, timeStamp, logicalTime);
   }



   /*
    * Functions used by HlaReferenceFrameUpdaterImpl
    */

   void applyUpdate(EnumMap<HlaReferenceFrameAttributes.Attribute, Object> attributeMap,
                    EnumSet<HlaReferenceFrameAttributes.Attribute> sendAttributes,
                    HlaTimeStamp timestamp,
                    HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (timestamp == null) {
         timestamp = _hlaWorld.getCurrentTime();
      }
      if (logicalTime == null) {
         logicalTime = _hlaWorld.getDefaultSendTime();
      }
      synchronized (_sendLock) {
         HlaReferenceFrameAttributes attributes = applyUpdate(attributeMap, timestamp, logicalTime, _hlaWorld.getFederateId());
         sendAttributes.addAll(addDefaultSendAttributes(attributeMap.keySet()));
         sendAttributes(sendAttributes, attributes, timestamp, logicalTime);
      }
   }


   /*
    * Private functions
    */

   private Set<HlaReferenceFrameAttributes.Attribute> addDefaultSendAttributes(Set<HlaReferenceFrameAttributes.Attribute> attributes) {
      return attributes;
   }

   private void fireAttributesUpdated(final Set<HlaReferenceFrameAttributes.Attribute> attributes, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaReferenceFrame instance = this;
      for (final HlaReferenceFrameListener listener : _listeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               listener.attributesUpdated(instance, attributes, timeStamp, logicalTime);
            }
         });
      }
   }

   @SuppressWarnings ({ "UnnecessaryContinue" })
   private Set<HlaReferenceFrameAttributes.Attribute> getAttributeEnum(Set<String> attributeNames) {
      Set<HlaReferenceFrameAttributes.Attribute> ret = EnumSet.noneOf(HlaReferenceFrameAttributes.Attribute.class);

      return ret;
   }

   /* @GuardedBy("_sendLock") */
   private HlaReferenceFrameAttributes applyUpdate(EnumMap<HlaReferenceFrameAttributes.Attribute, Object> attributeUpdates, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      HlaReferenceFrameAttributes ret;

      synchronized (_attributesLock) {
         for (Map.Entry<HlaReferenceFrameAttributes.Attribute, Object> attributeUpdate : attributeUpdates.entrySet()) {
            switch (attributeUpdate.getKey()) {
            }
         }
         ret = getHlaReferenceFrameAttributes();
      }

      if (!attributeUpdates.isEmpty()) {
         fireAttributesUpdated(attributeUpdates.keySet(), timeStamp, logicalTime);
      }

      return ret;
   }

   /* @GuardedBy("_sendLock") */
   private void sendAttributes(Set<HlaReferenceFrameAttributes.Attribute> attributes, HlaReferenceFrameAttributes attributeValues, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      NameValueMap attributeValueMap = new NameValueMap();

      synchronized (_objectClassManager._encodersLock) {
         for (HlaReferenceFrameAttributes.Attribute attribute : attributes) {
            switch (attribute) {
            }
         }
      }

      if (!attributeValueMap.isEmpty()) {
         _objectClassManager.updateAttributeValues(this, _objectInstanceHandle, attributeValueMap, timestamp, logicalTime);
      }
   }
}
