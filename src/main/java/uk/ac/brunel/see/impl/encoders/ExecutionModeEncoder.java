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

import uk.ac.brunel.see.datatypes.ExecutionMode;

public final class ExecutionModeEncoder extends AbstractEnum16Encoder<ExecutionMode> {

   public ExecutionModeEncoder() {
      super(ExecutionModeEncoder.class, ExecutionMode.class, BaseEncoder.createHLAinteger16LE());
   }

   public ExecutionModeEncoder(ExecutionMode value) {
      this();
      setValue(value);
   }

   protected ExecutionMode find(long ordinal) {
      return ExecutionMode.find(ordinal);
   }
}
