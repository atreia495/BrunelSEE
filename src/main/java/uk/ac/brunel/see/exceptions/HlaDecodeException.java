package uk.ac.brunel.see.exceptions;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import uk.ac.brunel.see.HlaTuning;


/**
 * Exception thrown if the attribute or parameter could not be encoded or decoded.
 */
public final class HlaDecodeException extends HlaBaseRuntimeException {

   private final byte[] _data;


   public HlaDecodeException(String message) {
      super(message);
      _data = null;
   }

   public HlaDecodeException(String message, Throwable cause) {
      super(message, cause);
      _data = null;
   }

   public HlaDecodeException(String message, byte[] data) {
      super(message);
      _data = data;
   }

   public HlaDecodeException(String message, byte[] data, Throwable cause) {
      super(message, cause);
      _data = data;
   }

   @Override
   public String getMessage() {
      if (HlaTuning.INCLUDE_DATA_IN_DECODE_EXCEPTION && _data != null) {
         return super.getMessage() + "\nData: " + byteArrayToString(_data);
      } else {
         return super.getMessage();
      }
   }

   private static final char[] HEX_STRING = new char[] {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
   };

   private static String byteArrayToString(byte[] data) {
      if (data == null) {
         return "null";
      }

      if (data.length == 0) {
         return "[]";
      }

      StringBuilder sb = new StringBuilder((int)(data.length * 2.25) + 2 + 4);
      sb.append('[');

      for (int i = 0; i < data.length; i++) {
         if (i > 0 && (i % 4) == 0) {
            sb.append(' ');
         }
         sb.append(HEX_STRING[((data[i] >>> 4) & 0x0F)]);
         sb.append(HEX_STRING[(data[i] & 0x0F)]);
      }

      sb.append(']');

      return sb.toString();
   }
}
