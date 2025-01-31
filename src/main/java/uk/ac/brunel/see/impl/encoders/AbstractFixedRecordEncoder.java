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

import se.pitch.encoders1516.AbstractDataElement;
import se.pitch.encoders1516.ByteWrapper;
import se.pitch.encoders1516.HLAfixedRecord;

abstract class AbstractFixedRecordEncoder<T> extends AbstractDataElement {

   private final Class<? extends AbstractFixedRecordEncoder<?>> _class;
   protected final HLAfixedRecord _encoder;

   protected T _value;


   public AbstractFixedRecordEncoder(Class<? extends AbstractFixedRecordEncoder<?>> implementationClass) {
      _class = implementationClass;
      _encoder = BaseEncoder.createHLAfixedRecord();
   }

   public T getValue() {
      return _value;
   }

   public void setValue(T value) {
      _value = value;

      encodeValue(value);
   }

   protected abstract void encodeValue(T value);

   public void decode(ByteWrapper byteWrapper) {
      _encoder.decode(byteWrapper);

      _value = decodeValue();
   }

   protected abstract T decodeValue();

   public int getEncodedLength() {
      return _encoder.getEncodedLength();
   }

   public void encode(ByteWrapper byteWrapper) {
      _encoder.encode(byteWrapper);
   }

   public int getOctetBoundary() {
      return _encoder.getOctetBoundary();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!_class.isInstance(o)) {
         return false;
      }

      AbstractFixedRecordEncoder<?> that = (AbstractFixedRecordEncoder<?>)o;

      return _encoder.equals(that._encoder);
   }

   @Override
   public int hashCode() {
      return _encoder.hashCode();
   }

   @Override
   public String toString() {
      return _class.getSimpleName() + "<" + getValue() + ">";
   }
}
