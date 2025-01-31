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
import uk.ac.brunel.see.HlaPhysicalEntityAttributes;
import uk.ac.brunel.see.HlaTimeStamped;
import uk.ac.brunel.see.exceptions.HlaValueNotSetException;
import uk.ac.brunel.see.impl.utils.AttributeValue;

/* @Immutable */
final class HlaPhysicalEntityAttributesImpl implements HlaPhysicalEntityAttributes {

   private final AttributeValue<String> _name;
   private final AttributeValue<String> _type;
   private final AttributeValue<String> _status;
   private final AttributeValue<String> _parentReferenceFrame;
   private final AttributeValue<SpaceTimeCoordinateState> _state;
   private final AttributeValue<double[/* 3 */]> _acceleration;
   private final AttributeValue<double[/* 3 */]> _rotationalAcceleration;
   private final AttributeValue<double[/* 3 */]> _centerOfMass;
   private final AttributeValue<AttitudeQuaternion> _bodyWrtStructural;

   HlaPhysicalEntityAttributesImpl(
      AttributeValue<String> name,
      AttributeValue<String> type,
      AttributeValue<String> status,
      AttributeValue<String> parentReferenceFrame,
      AttributeValue<SpaceTimeCoordinateState> state,
      AttributeValue<double[/* 3 */]> acceleration,
      AttributeValue<double[/* 3 */]> rotationalAcceleration,
      AttributeValue<double[/* 3 */]> centerOfMass,
      AttributeValue<AttitudeQuaternion> bodyWrtStructural
   ) {
      _name = new AttributeValue<String>(name);
      _type = new AttributeValue<String>(type);
      _status = new AttributeValue<String>(status);
      _parentReferenceFrame = new AttributeValue<String>(parentReferenceFrame);
      _state = new AttributeValue<SpaceTimeCoordinateState>(state);
      _acceleration = new AttributeValue<double[/* 3 */]>(acceleration);
      _rotationalAcceleration = new AttributeValue<double[/* 3 */]>(rotationalAcceleration);
      _centerOfMass = new AttributeValue<double[/* 3 */]>(centerOfMass);
      _bodyWrtStructural = new AttributeValue<AttitudeQuaternion>(bodyWrtStructural);
   }

   public boolean hasName() {
      return _name.hasValue();
   }

   public String getName() throws HlaValueNotSetException {
      if (_name.hasValue()) {
         return _name.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'name' not set");
   }

   public String getName(String defaultValue) {
      if (_name.hasValue()) {
         return _name.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<String> getNameTimeStamped() throws HlaValueNotSetException {
      if (_name.hasValue()) {
         return _name.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'name' not set");
   }

   public boolean hasType() {
      return _type.hasValue();
   }

   public String getType() throws HlaValueNotSetException {
      if (_type.hasValue()) {
         return _type.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'type' not set");
   }

   public String getType(String defaultValue) {
      if (_type.hasValue()) {
         return _type.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<String> getTypeTimeStamped() throws HlaValueNotSetException {
      if (_type.hasValue()) {
         return _type.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'type' not set");
   }

   public boolean hasStatus() {
      return _status.hasValue();
   }

   public String getStatus() throws HlaValueNotSetException {
      if (_status.hasValue()) {
         return _status.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'status' not set");
   }

   public String getStatus(String defaultValue) {
      if (_status.hasValue()) {
         return _status.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<String> getStatusTimeStamped() throws HlaValueNotSetException {
      if (_status.hasValue()) {
         return _status.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'status' not set");
   }

   public boolean hasParentReferenceFrame() {
      return _parentReferenceFrame.hasValue();
   }

   public String getParentReferenceFrame() throws HlaValueNotSetException {
      if (_parentReferenceFrame.hasValue()) {
         return _parentReferenceFrame.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'parentReferenceFrame' not set");
   }

   public String getParentReferenceFrame(String defaultValue) {
      if (_parentReferenceFrame.hasValue()) {
         return _parentReferenceFrame.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<String> getParentReferenceFrameTimeStamped() throws HlaValueNotSetException {
      if (_parentReferenceFrame.hasValue()) {
         return _parentReferenceFrame.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'parentReferenceFrame' not set");
   }

   public boolean hasState() {
      return _state.hasValue();
   }

   public SpaceTimeCoordinateState getState() throws HlaValueNotSetException {
      if (_state.hasValue()) {
         return _state.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'state' not set");
   }

   public SpaceTimeCoordinateState getState(SpaceTimeCoordinateState defaultValue) {
      if (_state.hasValue()) {
         return _state.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<SpaceTimeCoordinateState> getStateTimeStamped() throws HlaValueNotSetException {
      if (_state.hasValue()) {
         return _state.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'state' not set");
   }

   public boolean hasAcceleration() {
      return _acceleration.hasValue();
   }

   public double[/* 3 */] getAcceleration() throws HlaValueNotSetException {
      if (_acceleration.hasValue()) {
         return _acceleration.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'acceleration' not set");
   }

   public double[/* 3 */] getAcceleration(double[/* 3 */] defaultValue) {
      if (_acceleration.hasValue()) {
         return _acceleration.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<double[/* 3 */]> getAccelerationTimeStamped() throws HlaValueNotSetException {
      if (_acceleration.hasValue()) {
         return _acceleration.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'acceleration' not set");
   }

   public boolean hasRotationalAcceleration() {
      return _rotationalAcceleration.hasValue();
   }

   public double[/* 3 */] getRotationalAcceleration() throws HlaValueNotSetException {
      if (_rotationalAcceleration.hasValue()) {
         return _rotationalAcceleration.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'rotationalAcceleration' not set");
   }

   public double[/* 3 */] getRotationalAcceleration(double[/* 3 */] defaultValue) {
      if (_rotationalAcceleration.hasValue()) {
         return _rotationalAcceleration.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<double[/* 3 */]> getRotationalAccelerationTimeStamped() throws HlaValueNotSetException {
      if (_rotationalAcceleration.hasValue()) {
         return _rotationalAcceleration.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'rotationalAcceleration' not set");
   }

   public boolean hasCenterOfMass() {
      return _centerOfMass.hasValue();
   }

   public double[/* 3 */] getCenterOfMass() throws HlaValueNotSetException {
      if (_centerOfMass.hasValue()) {
         return _centerOfMass.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'centerOfMass' not set");
   }

   public double[/* 3 */] getCenterOfMass(double[/* 3 */] defaultValue) {
      if (_centerOfMass.hasValue()) {
         return _centerOfMass.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<double[/* 3 */]> getCenterOfMassTimeStamped() throws HlaValueNotSetException {
      if (_centerOfMass.hasValue()) {
         return _centerOfMass.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'centerOfMass' not set");
   }

   public boolean hasBodyWrtStructural() {
      return _bodyWrtStructural.hasValue();
   }

   public AttitudeQuaternion getBodyWrtStructural() throws HlaValueNotSetException {
      if (_bodyWrtStructural.hasValue()) {
         return _bodyWrtStructural.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'bodyWrtStructural' not set");
   }

   public AttitudeQuaternion getBodyWrtStructural(AttitudeQuaternion defaultValue) {
      if (_bodyWrtStructural.hasValue()) {
         return _bodyWrtStructural.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<AttitudeQuaternion> getBodyWrtStructuralTimeStamped() throws HlaValueNotSetException {
      if (_bodyWrtStructural.hasValue()) {
         return _bodyWrtStructural.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'bodyWrtStructural' not set");
   }

}
