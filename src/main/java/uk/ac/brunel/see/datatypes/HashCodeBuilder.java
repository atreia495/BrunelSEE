package uk.ac.brunel.see.datatypes;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import java.util.Arrays;

final class HashCodeBuilder {

   private final int _constant;
   private int _total;

   public HashCodeBuilder() {
      _constant = 37;
      _total = 17;
   }

   public HashCodeBuilder append(Object value) {
      if (value == null) {
         _total = _total * _constant;
      } else {
         if (!value.getClass().isArray()) {
            _total = _total * _constant + value.hashCode();
         } else {
            if (value instanceof long[]) {
               append((long[])value);
            } else if (value instanceof int[]) {
               append((int[])value);
            } else if (value instanceof short[]) {
               append((short[])value);
            } else if (value instanceof char[]) {
               append((char[])value);
            } else if (value instanceof byte[]) {
               append((byte[])value);
            } else if (value instanceof double[]) {
               append((double[])value);
            } else if (value instanceof float[]) {
               append((float[])value);
            } else if (value instanceof boolean[]) {
               append((boolean[])value);
            } else {
               append((Object[])value);
            }
         }
      }
      return this;
   }

   public HashCodeBuilder append(long value) {
      _total = _total * _constant + ((int)(value ^ (value >> 32)));
      return this;
   }

   public HashCodeBuilder append(int value) {
      _total = _total * _constant + value;
      return this;
   }

   public HashCodeBuilder append(short value) {
      _total = _total * _constant + value;
      return this;
   }

   public HashCodeBuilder append(char value) {
      _total = _total * _constant + value;
      return this;
   }

   public HashCodeBuilder append(byte value) {
      _total = _total * _constant + value;
      return this;
   }

   public HashCodeBuilder append(double value) {
      return append(Double.doubleToLongBits(value));
   }

   public HashCodeBuilder append(float value) {
      _total = _total * _constant + Float.floatToIntBits(value);
      return this;
   }

   public HashCodeBuilder append(boolean value) {
      _total = _total * _constant + (value ? 0 : 1);
      return this;
   }

   public HashCodeBuilder append(Object[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(long[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(int[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(short[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(char[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(byte[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(double[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(float[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public HashCodeBuilder append(boolean[] array) {
      _total = _total * _constant + Arrays.hashCode(array);
      return this;
   }

   public int toHashCode() {
      return _total;
   }
}
