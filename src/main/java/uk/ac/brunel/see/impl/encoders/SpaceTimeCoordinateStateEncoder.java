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

import uk.ac.brunel.see.datatypes.SpaceTimeCoordinateState;
import se.pitch.encoders1516.HLAfloat64LE;

public final class SpaceTimeCoordinateStateEncoder extends AbstractFixedRecordEncoder<SpaceTimeCoordinateState> {


   public SpaceTimeCoordinateStateEncoder() {
      super(SpaceTimeCoordinateStateEncoder.class);

      _encoder.add(new ReferenceFrameTranslationEncoder());
      _encoder.add(new ReferenceFrameRotationEncoder());
      _encoder.add(BaseEncoder.createHLAfloat64LE());
   }

   public SpaceTimeCoordinateStateEncoder(SpaceTimeCoordinateState value) {
      this();
      setValue(value);
   }

   protected void encodeValue(SpaceTimeCoordinateState value) {
      ((ReferenceFrameTranslationEncoder)_encoder.get(0)).setValue(value.getTranslationalState());
      ((ReferenceFrameRotationEncoder)_encoder.get(1)).setValue(value.getRotationalState());
      ((HLAfloat64LE)_encoder.get(2)).setValue(value.getTime());
   }

   protected SpaceTimeCoordinateState decodeValue() {
      return SpaceTimeCoordinateState.create(
         ((ReferenceFrameTranslationEncoder)_encoder.get(0)).getValue(),
         ((ReferenceFrameRotationEncoder)_encoder.get(1)).getValue(),
         ((HLAfloat64LE)_encoder.get(2)).getValue()
      );
   }
}
