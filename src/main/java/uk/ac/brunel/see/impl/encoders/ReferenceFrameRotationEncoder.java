package uk.ac.brunel.see.impl.encoders;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import uk.ac.brunel.see.datatypes.ReferenceFrameRotation;

public final class ReferenceFrameRotationEncoder extends AbstractFixedRecordEncoder<ReferenceFrameRotation> {


   public ReferenceFrameRotationEncoder() {
      super(ReferenceFrameRotationEncoder.class);

      _encoder.add(new AttitudeQuaternionEncoder());
      _encoder.add(new AngularVelocityVectorEncoder());
   }

   public ReferenceFrameRotationEncoder(ReferenceFrameRotation value) {
      this();
      setValue(value);
   }

   protected void encodeValue(ReferenceFrameRotation value) {
      ((AttitudeQuaternionEncoder)_encoder.get(0)).setValue(value.getAttitudeQuaternion());
      ((AngularVelocityVectorEncoder)_encoder.get(1)).setValue(value.getAngularVelocity());
   }

   protected ReferenceFrameRotation decodeValue() {
      return ReferenceFrameRotation.create(
         ((AttitudeQuaternionEncoder)_encoder.get(0)).getValue(),
         ((AngularVelocityVectorEncoder)_encoder.get(1)).getValue()
      );
   }
}
