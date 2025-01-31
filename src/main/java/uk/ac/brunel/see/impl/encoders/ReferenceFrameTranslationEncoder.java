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

import uk.ac.brunel.see.datatypes.ReferenceFrameTranslation;

public final class ReferenceFrameTranslationEncoder extends AbstractFixedRecordEncoder<ReferenceFrameTranslation> {


   public ReferenceFrameTranslationEncoder() {
      super(ReferenceFrameTranslationEncoder.class);

      _encoder.add(new PositionVectorEncoder());
      _encoder.add(new VelocityVectorEncoder());
   }

   public ReferenceFrameTranslationEncoder(ReferenceFrameTranslation value) {
      this();
      setValue(value);
   }

   protected void encodeValue(ReferenceFrameTranslation value) {
      ((PositionVectorEncoder)_encoder.get(0)).setValue(value.getPosition());
      ((VelocityVectorEncoder)_encoder.get(1)).setValue(value.getVelocity());
   }

   protected ReferenceFrameTranslation decodeValue() {
      return ReferenceFrameTranslation.create(
         ((PositionVectorEncoder)_encoder.get(0)).getValue(),
         ((VelocityVectorEncoder)_encoder.get(1)).getValue()
      );
   }
}
