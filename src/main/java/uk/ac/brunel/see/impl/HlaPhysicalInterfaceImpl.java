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
final class HlaPhysicalInterfaceImpl implements HlaPhysicalInterface {



   public static final Set<String> HLA_ATTRIBUTE_NAMES = new SetAdapter<String>()
         .unmodifiableSet();

   public static final Set<String> HLA_INITIALIZED_ATTRIBUTE_NAMES = new SetAdapter<String>()
         .unmodifiableSet();

   private final HlaWorldImpl _hlaWorld;
   private final HlaPhysicalInterfaceManagerImpl _objectClassManager;
   private final ObjectInstanceHandle _objectInstanceHandle;
   private final boolean _isLocal;
   private final HlaFederateId _createdByFederate;

   private final Object _attributesLock = new Object();
   private final Object _sendLock = new Object();


   private final Set<HlaPhysicalInterfaceValueListener> _valueListeners = new CopyOnWriteArraySet<HlaPhysicalInterfaceValueListener>();
   private final Set<HlaPhysicalInterfaceListener> _listeners = new CopyOnWriteArraySet<HlaPhysicalInterfaceListener>();

   private volatile boolean _initializeFired = false;
   private volatile boolean _removed = false;

   HlaPhysicalInterfaceImpl(HlaWorldImpl hlaWorld, HlaPhysicalInterfaceManagerImpl objectClassManager, ObjectInstanceHandle objectInstanceHandle, boolean local, HlaFederateId producingFederate) {
      _hlaWorld = hlaWorld;
      _objectClassManager = objectClassManager;
      _objectInstanceHandle = objectInstanceHandle;
      _isLocal = local;
      _createdByFederate = producingFederate;
   }

   /*
    * HlaPhysicalInterface interface impl
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

   public HlaPhysicalInterfaceAttributes getHlaPhysicalInterfaceAttributes() {
      synchronized (_attributesLock) {
         return new HlaPhysicalInterfaceAttributesImpl(
         );
      }
   }

   public HlaPhysicalInterfaceUpdater getHlaPhysicalInterfaceUpdater() {
      return new HlaPhysicalInterfaceUpdaterImpl(this);
   }

   public String getHlaInstanceName() {
      return _objectInstanceHandle.getObjectInstanceName();
   }

   public byte[] getEncodedHlaObjectInstanceHandle() {
      return _objectInstanceHandle.getEncodedHandle();
   }

   public ObjectClassType getClassType() {
      return ObjectClassType.PHYSICAL_INTERFACE;
   }


   public void addHlaPhysicalInterfaceValueListener(HlaPhysicalInterfaceValueListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _valueListeners.add(listener);
   }

   public void removeHlaPhysicalInterfaceValueListener(HlaPhysicalInterfaceValueListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _valueListeners.remove(listener);
   }

   public void addHlaPhysicalInterfaceListener(HlaPhysicalInterfaceListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.add(listener);
   }

   public void removeHlaPhysicalInterfaceListener(HlaPhysicalInterfaceListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.remove(listener);
   }

   /*
    * Functions used by HlaPhysicalInterfaceManagerImpl
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
            sendAttributes(getAttributeEnum(attributes), getHlaPhysicalInterfaceAttributes(), hlaTimeStamp, HlaLogicalTimeImpl.INVALID);
         }
      } catch (HlaBaseException e) {
         //ignore
      } catch (HlaBaseRuntimeException e) {
         //ignore
      }
   }

   Set<String> getUnsetAttributeNames() {
      Set<String> unsetAttributes = new HashSet<String>(HLA_ATTRIBUTE_NAMES.size());
      HlaPhysicalInterfaceAttributes attributes = getHlaPhysicalInterfaceAttributes();
      return unsetAttributes;
   }

   @SuppressWarnings ({ "UnnecessaryContinue" })
   void reflectAttributeValues(NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      Set<HlaPhysicalInterfaceAttributes.Attribute> attributesUpdated = EnumSet.noneOf(HlaPhysicalInterfaceAttributes.Attribute.class);

      fireAttributesUpdated(attributesUpdated, timeStamp, logicalTime);
   }



   /*
    * Functions used by HlaPhysicalInterfaceUpdaterImpl
    */

   void applyUpdate(EnumMap<HlaPhysicalInterfaceAttributes.Attribute, Object> attributeMap,
                    EnumSet<HlaPhysicalInterfaceAttributes.Attribute> sendAttributes,
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
         HlaPhysicalInterfaceAttributes attributes = applyUpdate(attributeMap, timestamp, logicalTime, _hlaWorld.getFederateId());
         sendAttributes.addAll(addDefaultSendAttributes(attributeMap.keySet()));
         sendAttributes(sendAttributes, attributes, timestamp, logicalTime);
      }
   }


   /*
    * Private functions
    */

   private Set<HlaPhysicalInterfaceAttributes.Attribute> addDefaultSendAttributes(Set<HlaPhysicalInterfaceAttributes.Attribute> attributes) {
      return attributes;
   }

   private void fireAttributesUpdated(final Set<HlaPhysicalInterfaceAttributes.Attribute> attributes, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaPhysicalInterface instance = this;
      for (final HlaPhysicalInterfaceListener listener : _listeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               listener.attributesUpdated(instance, attributes, timeStamp, logicalTime);
            }
         });
      }
   }

   @SuppressWarnings ({ "UnnecessaryContinue" })
   private Set<HlaPhysicalInterfaceAttributes.Attribute> getAttributeEnum(Set<String> attributeNames) {
      Set<HlaPhysicalInterfaceAttributes.Attribute> ret = EnumSet.noneOf(HlaPhysicalInterfaceAttributes.Attribute.class);

      return ret;
   }

   /* @GuardedBy("_sendLock") */
   private HlaPhysicalInterfaceAttributes applyUpdate(EnumMap<HlaPhysicalInterfaceAttributes.Attribute, Object> attributeUpdates, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      HlaPhysicalInterfaceAttributes ret;

      synchronized (_attributesLock) {
         for (Map.Entry<HlaPhysicalInterfaceAttributes.Attribute, Object> attributeUpdate : attributeUpdates.entrySet()) {
            switch (attributeUpdate.getKey()) {
            }
         }
         ret = getHlaPhysicalInterfaceAttributes();
      }

      if (!attributeUpdates.isEmpty()) {
         fireAttributesUpdated(attributeUpdates.keySet(), timeStamp, logicalTime);
      }

      return ret;
   }

   /* @GuardedBy("_sendLock") */
   private void sendAttributes(Set<HlaPhysicalInterfaceAttributes.Attribute> attributes, HlaPhysicalInterfaceAttributes attributeValues, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      NameValueMap attributeValueMap = new NameValueMap();

      synchronized (_objectClassManager._encodersLock) {
         for (HlaPhysicalInterfaceAttributes.Attribute attribute : attributes) {
            switch (attribute) {
            }
         }
      }

      if (!attributeValueMap.isEmpty()) {
         _objectClassManager.updateAttributeValues(this, _objectInstanceHandle, attributeValueMap, timestamp, logicalTime);
      }
   }
}
