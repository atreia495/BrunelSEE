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

public final class ToStringBuilder {

   private static final String NULL_STRING = "<null>";

   private final StringBuilder _str;
   private int _count = 0;


   public ToStringBuilder(Object object) {
      this(object.getClass().getSimpleName());
   }

   public ToStringBuilder(String objectName) {
      _str = new StringBuilder(objectName);
      _str.append('{');
   }

   private void appendStr(String name, String value) {
      if (_count > 0) {
         _str.append(", ");
      }
      _str.append(name).append('=').append(value);
      _count++;
   }

   public ToStringBuilder append(String name, Object value) {
      if (value == null) {
         appendStr(name, NULL_STRING);
      } else {
         if (!value.getClass().isArray()) {
            appendStr(name, value.toString());
         } else {
            if (value instanceof long[]) {
               append(name, (long[])value);
            } else if (value instanceof int[]) {
               append(name, (int[])value);
            } else if (value instanceof short[]) {
               append(name, (short[])value);
            } else if (value instanceof char[]) {
               append(name, (char[])value);
            } else if (value instanceof byte[]) {
               append(name, (byte[])value);
            } else if (value instanceof double[]) {
               append(name, (double[])value);
            } else if (value instanceof float[]) {
               append(name, (float[])value);
            } else if (value instanceof boolean[]) {
               append(name, (boolean[])value);
            } else {
               append(name, (Object[])value);
            }
         }
      }
      return this;
   }

   public ToStringBuilder append(String name, long value) {
      appendStr(name, Long.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, int value) {
      appendStr(name, Integer.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, short value) {
      appendStr(name, Short.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, char value) {
      appendStr(name, Character.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, byte value) {
      appendStr(name, Byte.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, double value) {
      appendStr(name, Double.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, float value) {
      appendStr(name, Float.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, boolean value) {
      appendStr(name, Boolean.toString(value));
      return this;
   }

   public ToStringBuilder append(String name, Object[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, long[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, int[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, short[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, char[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, byte[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, double[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, float[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public ToStringBuilder append(String name, boolean[] array) {
      appendStr(name, Arrays.toString(array));
      return this;
   }

   public String getToString() {
      _str.append('}');

      return _str.toString();
   }
}
