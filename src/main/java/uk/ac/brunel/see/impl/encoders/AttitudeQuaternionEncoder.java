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

import uk.ac.brunel.see.datatypes.AttitudeQuaternion;
import se.pitch.encoders1516.HLAfloat64LE;

public final class AttitudeQuaternionEncoder extends AbstractFixedRecordEncoder<AttitudeQuaternion> {


   public AttitudeQuaternionEncoder() {
      super(AttitudeQuaternionEncoder.class);

      _encoder.add(BaseEncoder.createHLAfloat64LE());
      _encoder.add(new VectorEncoder());
   }

   public AttitudeQuaternionEncoder(AttitudeQuaternion value) {
      this();
      setValue(value);
   }

   protected void encodeValue(AttitudeQuaternion value) {
      ((HLAfloat64LE)_encoder.get(0)).setValue(value.getScalar());
      ((VectorEncoder)_encoder.get(1)).setValue(value.getVector());
   }

   protected AttitudeQuaternion decodeValue() {
      return AttitudeQuaternion.create(
         ((HLAfloat64LE)_encoder.get(0)).getValue(),
         ((VectorEncoder)_encoder.get(1)).getValue()
      );
   }
}
