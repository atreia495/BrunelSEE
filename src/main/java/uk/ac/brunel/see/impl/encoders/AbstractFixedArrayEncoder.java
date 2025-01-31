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
import se.pitch.encoders1516.DataElement;
import se.pitch.encoders1516.HLAfixedArray;

abstract class AbstractFixedArrayEncoder<T> extends AbstractDataElement {

   private final Class<? extends AbstractFixedArrayEncoder<?>> _class;
   protected final HLAfixedArray _encoder;

   private T _value;
   private final int _length;


   AbstractFixedArrayEncoder(Class<? extends AbstractFixedArrayEncoder<?>> implementationClass, int length) {
      _class = implementationClass;
      _length = length;
      _encoder = BaseEncoder.createHLAfixedArray();
      for (int i = 0; i < _length; i++) {
         _encoder.addElement(createElement());
      }
   }

   private void assertLength(T value) {
      final int length = getLength(value);

      assert _length == length : "Invalid length for HLAfixedArray[" + _length + "]: " + length;
   }

   public T getValue() {
      return _value;
   }

   public void setValue(T value) {
      assertLength(value);

      _value = value;

      encodeValue(value);
   }

   protected abstract void encodeValue(T value);

   public int getOctetBoundary() {
      return _encoder.getOctetBoundary();
   }

   public void encode(ByteWrapper byteWrapper) {
      _encoder.encode(byteWrapper);
   }

   public int getEncodedLength() {
      return _encoder.getEncodedLength();
   }

   public void decode(ByteWrapper byteWrapper) {
      _encoder.decode(byteWrapper);

      T value = decodeValue();
      assertLength(value);
      _value = value;
   }

   protected abstract T decodeValue();

   protected abstract DataElement createElement();

   protected abstract int getLength(T value);

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!_class.isInstance(o)) {
         return false;
      }

      AbstractFixedArrayEncoder<?> that = (AbstractFixedArrayEncoder<?>)o;

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
