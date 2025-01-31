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

final class EqualsBuilder {
   private boolean _equals = true;

   public EqualsBuilder() {
   }

   public EqualsBuilder append(Object left, Object right) {
      if (!_equals) {
         return this;
      }

      if (left == right) {
         return this;
      }
      if (left == null || right == null) {
         _equals = false;
         return this;
      }

      Class<?> leftClass = left.getClass();
      if (!leftClass.isArray()) {
         _equals = left.equals(right);
      } else if (left.getClass() != right.getClass()) {
         _equals = false;
      } else if (left instanceof long[]) {
         append((long[])left, (long[])right);
      } else if (left instanceof int[]) {
         append((int[])left, (int[])right);
      } else if (left instanceof short[]) {
         append((short[])left, (short[])right);
      } else if (left instanceof char[]) {
         append((char[])left, (char[])right);
      } else if (left instanceof byte[]) {
         append((byte[])left, (byte[])right);
      } else if (left instanceof double[]) {
         append((double[])left, (double[])right);
      } else if (left instanceof float[]) {
         append((float[])left, (float[])right);
      } else if (left instanceof boolean[]) {
         append((boolean[])left, (boolean[])right);
      } else {
         append((Object[])left, (Object[])right);
      }
      return this;
   }

   public EqualsBuilder append(long left, long right) {
      if (!_equals) {
         return this;
      }
      _equals = (left == right);
      return this;
   }

   public EqualsBuilder append(int left, int right) {
      if (!_equals) {
         return this;
      }
      _equals = (left == right);
      return this;
   }

   public EqualsBuilder append(short left, short right) {
      if (!_equals) {
         return this;
      }
      _equals = (left == right);
      return this;
   }

   public EqualsBuilder append(char left, char right) {
      if (!_equals) {
         return this;
      }
      _equals = (left == right);
      return this;
   }

   public EqualsBuilder append(byte left, byte right) {
      if (!_equals) {
         return this;
      }
      _equals = (left == right);
      return this;
   }

   public EqualsBuilder append(double left, double right) {
      if (!_equals) {
         return this;
      }
      _equals = (Double.doubleToLongBits(left) == Double.doubleToLongBits(right));
      return this;
   }

   public EqualsBuilder append(float left, float right) {
      if (!_equals) {
         return this;
      }
      _equals = (Float.floatToIntBits(left) == Float.floatToIntBits(right));
      return this;
   }

   public EqualsBuilder append(boolean left, boolean right) {
      if (!_equals) {
         return this;
      }
      _equals = (left == right);
      return this;
   }

   public EqualsBuilder append(Object[] left, Object[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(long[] left, long[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(int[] left, int[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(short[] left, short[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(char[] left, char[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(byte[] left, byte[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(double[] left, double[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(float[] left, float[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public EqualsBuilder append(boolean[] left, boolean[] right) {
      if (!_equals) {
         return this;
      }
      _equals = Arrays.equals(left, right);
      return this;
   }

   public boolean isEquals() {
      return _equals;
   }
}
