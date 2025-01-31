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

import uk.ac.brunel.see.datatypes.HlaEnumDataType;
import uk.ac.brunel.see.exceptions.HlaDecodeException;
import se.pitch.encoders1516.AbstractDataElement;
import se.pitch.encoders1516.ByteWrapper;
import se.pitch.encoders1516.DataElementInteger16;

abstract class AbstractEnum16Encoder<T extends HlaEnumDataType> extends AbstractDataElement {

   private final Class<? extends AbstractEnum16Encoder<?>> _class;
   private final Class<T> _dataTypeClass;
   private final DataElementInteger16 _encoder;
   private T _value;

   protected AbstractEnum16Encoder(Class<? extends AbstractEnum16Encoder<?>> implementationClass,
                                   Class<T> dataTypeClass,
                                   DataElementInteger16 encoder) {
      _class = implementationClass;
      _dataTypeClass = dataTypeClass;
      _encoder = encoder;
   }

   public T getValue() {
      return _value;
   }

   public void setValue(T value) {
      _value = value;
      _encoder.setValue((short)_value.getOrdinal());
   }

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
      short ordinal = _encoder.getValue();
      T newValue = find(ordinal);
      if (newValue != null) {
         _value = newValue;
      } else {
         throw new HlaDecodeException("Failed to decode " + _dataTypeClass.getSimpleName() + " with ordinal " + ordinal);
      }
   }

   protected abstract T find(long ordinal);

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!_class.isInstance(o)) {
         return false;
      }

      AbstractEnum16Encoder<?> that = (AbstractEnum16Encoder<?>)o;

      return _encoder.equals(that._encoder);
   }

   @Override
   public int hashCode() {
      return _encoder.hashCode();
   }

   @Override
   public String toString() {
      return _class.getSimpleName() + "<" + getValue().getName() + ">";
   }
}
