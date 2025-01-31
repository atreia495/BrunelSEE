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

import uk.ac.brunel.see.datatypes.AttitudeQuaternion;
import uk.ac.brunel.see.datatypes.SpaceTimeCoordinateState;
import uk.ac.brunel.see.HlaDynamicalEntityUpdater;
import uk.ac.brunel.see.HlaDynamicalEntityListener;
import uk.ac.brunel.see.HlaLogicalTime;
import uk.ac.brunel.see.HlaTimeStamp;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.utils.AttributeValue;
import uk.ac.brunel.see.HlaDynamicalEntityAttributes; 

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicBoolean;


/* @ThreadSafe */
final class HlaDynamicalEntityUpdaterImpl implements HlaDynamicalEntityUpdater {
   private final AtomicBoolean _updateSent = new AtomicBoolean(false);
   private final Object _attributesLock = new Object();

   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<double[/* 3 */]> _force = new AttributeValue<double[/* 3 */]>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<double[/* 3 */]> _torque = new AttributeValue<double[/* 3 */]>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<Double> _mass = new AttributeValue<Double>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<Double> _massRate = new AttributeValue<Double>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<double[/* 9 */]> _inertia = new AttributeValue<double[/* 9 */]>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<double[/* 9 */]> _inertiaRate = new AttributeValue<double[/* 9 */]>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<String> _type = new AttributeValue<String>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<String> _status = new AttributeValue<String>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<SpaceTimeCoordinateState> _state = new AttributeValue<SpaceTimeCoordinateState>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<double[/* 3 */]> _acceleration = new AttributeValue<double[/* 3 */]>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<double[/* 3 */]> _rotationalAcceleration = new AttributeValue<double[/* 3 */]>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<double[/* 3 */]> _centerOfMass = new AttributeValue<double[/* 3 */]>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<AttitudeQuaternion> _bodyWrtStructural = new AttributeValue<AttitudeQuaternion>();
   /* @GuardedBy("_attributesLock") */
   private final EnumSet<HlaDynamicalEntityAttributes.Attribute> _sendAttributes = EnumSet.noneOf(HlaDynamicalEntityAttributes.Attribute.class);

   private final HlaDynamicalEntityImpl _instance;


   HlaDynamicalEntityUpdaterImpl(HlaDynamicalEntityImpl instance) {
      _instance = instance;
   }

   public HlaDynamicalEntityUpdaterImpl setForce(double[/* 3 */] value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _force.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setTorque(double[/* 3 */] value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _torque.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setMass(double value) {
      synchronized (_attributesLock) {
         _mass.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setMassRate(double value) {
      synchronized (_attributesLock) {
         _massRate.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setInertia(double[/* 9 */] value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _inertia.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setInertiaRate(double[/* 9 */] value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _inertiaRate.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl sendName() {
      synchronized (_attributesLock) {
         _sendAttributes.add(HlaDynamicalEntityAttributes.Attribute.NAME);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setType(String value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _type.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setStatus(String value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _status.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl sendParentReferenceFrame() {
      synchronized (_attributesLock) {
         _sendAttributes.add(HlaDynamicalEntityAttributes.Attribute.PARENT_REFERENCE_FRAME);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setState(SpaceTimeCoordinateState value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _state.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setAcceleration(double[/* 3 */] value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _acceleration.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setRotationalAcceleration(double[/* 3 */] value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _rotationalAcceleration.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setCenterOfMass(double[/* 3 */] value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _centerOfMass.setValueFromUser(value);
      }
      return this;
   }

   public HlaDynamicalEntityUpdaterImpl setBodyWrtStructural(AttitudeQuaternion value) {
      if (value == null) {
         throw new NullPointerException();
      }
      synchronized (_attributesLock) {
         _bodyWrtStructural.setValueFromUser(value);
      }
      return this;
   }


   public void sendUpdate() throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaUpdaterReusedException, HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException, HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         sendUpdate(null, null);
      } catch (HlaInvalidLogicalTimeException e) {
         //can not happen with null logicalTime
      }
   }

   public void sendUpdate(HlaTimeStamp timestamp) throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaUpdaterReusedException, HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException, HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         sendUpdate(timestamp, null);
      } catch (HlaInvalidLogicalTimeException e) {
         //can not happen with null logicalTime
      }
   }

   public void sendUpdate(HlaLogicalTime logicalTime) throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInvalidLogicalTimeException, HlaUpdaterReusedException, HlaInternalException, HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      sendUpdate(null, logicalTime);
   }

   public void sendUpdate(HlaTimeStamp timestamp, HlaLogicalTime logicalTime) throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInvalidLogicalTimeException, HlaUpdaterReusedException, HlaInternalException, HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      if (!_instance.isLocal()) {
         throw new HlaAttributeNotOwnedException("Can not change remote object");
      }
      if (_updateSent.getAndSet(true)) {
         throw new HlaUpdaterReusedException("Update already sent");
      }

      EnumMap<HlaDynamicalEntityAttributes.Attribute, Object> attributeUpdates =
            new EnumMap<HlaDynamicalEntityAttributes.Attribute, Object>(HlaDynamicalEntityAttributes.Attribute.class);
      EnumSet<HlaDynamicalEntityAttributes.Attribute> sendAttributes;

      synchronized (_attributesLock) {
         if (_force.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.FORCE, _force.getValue());
         }
         if (_torque.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.TORQUE, _torque.getValue());
         }
         if (_mass.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.MASS, _mass.getValue());
         }
         if (_massRate.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.MASS_RATE, _massRate.getValue());
         }
         if (_inertia.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.INERTIA, _inertia.getValue());
         }
         if (_inertiaRate.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.INERTIA_RATE, _inertiaRate.getValue());
         }
         if (_type.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.TYPE, _type.getValue());
         }
         if (_status.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.STATUS, _status.getValue());
         }
         if (_state.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.STATE, _state.getValue());
         }
         if (_acceleration.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.ACCELERATION, _acceleration.getValue());
         }
         if (_rotationalAcceleration.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.ROTATIONAL_ACCELERATION, _rotationalAcceleration.getValue());
         }
         if (_centerOfMass.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.CENTER_OF_MASS, _centerOfMass.getValue());
         }
         if (_bodyWrtStructural.hasValue()) {
            attributeUpdates.put(HlaDynamicalEntityAttributes.Attribute.BODY_WRT_STRUCTURAL, _bodyWrtStructural.getValue());
         }
         sendAttributes = EnumSet.copyOf(_sendAttributes);
      }

      _instance.applyUpdate(attributeUpdates, sendAttributes, timestamp, logicalTime);
   }
}
