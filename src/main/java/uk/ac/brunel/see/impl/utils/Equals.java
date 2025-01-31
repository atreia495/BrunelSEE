package uk.ac.brunel.see.impl.utils;

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


public final class Equals {

   private Equals() {
   }

   public static boolean isEquals(Object left, Object right) {
      if (left == right) {
         return true;
      }
      if (left == null || right == null) {
         return false;
      }

      Class<?> leftClass = left.getClass();
      if (!leftClass.isArray()) {
         return left.equals(right);
      } else if (left.getClass() != right.getClass()) {
         return false;
      } else if (left instanceof long[]) {
         return isEquals((long[])left, (long[])right);
      } else if (left instanceof int[]) {
         return isEquals((int[])left, (int[])right);
      } else if (left instanceof short[]) {
         return isEquals((short[])left, (short[])right);
      } else if (left instanceof char[]) {
         return isEquals((char[])left, (char[])right);
      } else if (left instanceof byte[]) {
         return isEquals((byte[])left, (byte[])right);
      } else if (left instanceof double[]) {
         return isEquals((double[])left, (double[])right);
      } else if (left instanceof float[]) {
         return isEquals((float[])left, (float[])right);
      } else if (left instanceof boolean[]) {
         return isEquals((boolean[])left, (boolean[])right);
      } else {
         return isEquals((Object[])left, (Object[])right);
      }
   }

   public static boolean isEquals(long left, long right) {
      return left == right;
   }

   public static boolean isEquals(int left, int right) {
      return left == right;
   }

   public static boolean isEquals(short left, short right) {
      return left == right;
   }

   public static boolean isEquals(char left, char right) {
      return left == right;
   }

   public static boolean isEquals(byte left, byte right) {
      return left == right;
   }

   public static boolean isEquals(double left, double right) {
      return Double.doubleToLongBits(left) == Double.doubleToLongBits(right);
   }

   public static boolean isEquals(float left, float right) {
      return Float.floatToIntBits(left) == Float.floatToIntBits(right);
   }

   public static boolean isEquals(boolean left, boolean right) {
      return left == right;
   }

   public static boolean isEquals(Object[] left, Object[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(long[] left, long[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(int[] left, int[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(short[] left, short[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(char[] left, char[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(byte[] left, byte[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(double[] left, double[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(float[] left, float[] right) {
      return Arrays.equals(left, right);
   }

   public static boolean isEquals(boolean[] left, boolean[] right) {
      return Arrays.equals(left, right);
   }
}
