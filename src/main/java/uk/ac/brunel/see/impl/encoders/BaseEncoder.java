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

import se.pitch.encoders1516.*;
import se.pitch.encoders1516.omt.OmtEncoderFactory;
import se.pitch.encoders1516.rpr.RprEncoderFactoryImpl;

import java.math.BigInteger;

public final class BaseEncoder {
   private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
   private static final double RPR_TIMESCALE = 3600 / (Math.pow(2, 31) - 1);     // = 1.676 microseconds

   private static final EncoderFactory _encoderFactory = OmtEncoderFactory.getInstance();
   private static final RprEncoderFactory _rprEncoderFactory = RprEncoderFactoryImpl.getInstance();


   private BaseEncoder() {
   }

   /*
    * Encoder Factory methods
    */

   public static HLAASCIIchar createHLAASCIIchar() {
      return _encoderFactory.createHLAASCIIchar();
   }

   public static HLAASCIIchar createHLAASCIIchar(byte b) {
      return _encoderFactory.createHLAASCIIchar(b);
   }

   public static HLAASCIIstring createHLAASCIIstring() {
      return _encoderFactory.createHLAASCIIstring();
   }

   public static HLAASCIIstring createHLAASCIIstring(String s) {
      return _encoderFactory.createHLAASCIIstring(s);
   }

   public static HLAboolean createHLAboolean() {
      return _encoderFactory.createHLAboolean();
   }

   public static HLAboolean createHLAboolean(boolean b) {
      return _encoderFactory.createHLAboolean(b);
   }

   public static HLAbyte createHLAbyte() {
      return _encoderFactory.createHLAbyte();
   }

   public static HLAbyte createHLAbyte(byte b) {
      return _encoderFactory.createHLAbyte(b);
   }

   public static HLAvariantRecord createHLAvariantRecord() {
      return _encoderFactory.createHLAvariantRecord();
   }

   public static HLAfixedRecord createHLAfixedRecord() {
      return _encoderFactory.createHLAfixedRecord();
   }

   public static HLAfixedArray createHLAfixedArray() {
      return _encoderFactory.createHLAfixedArray();
   }

   public static HLAfloat32BE createHLAfloat32BE() {
      return _encoderFactory.createHLAfloat32BE();
   }

   public static HLAfloat32BE createHLAfloat32BE(float v) {
      return _encoderFactory.createHLAfloat32BE(v);
   }

   public static HLAfloat32LE createHLAfloat32LE() {
      return _encoderFactory.createHLAfloat32LE();
   }

   public static HLAfloat32LE createHLAfloat32LE(float v) {
      return _encoderFactory.createHLAfloat32LE(v);
   }

   public static HLAfloat64BE createHLAfloat64BE() {
      return _encoderFactory.createHLAfloat64BE();
   }

   public static HLAfloat64BE createHLAfloat64BE(double v) {
      return _encoderFactory.createHLAfloat64BE(v);
   }

   public static HLAfloat64LE createHLAfloat64LE() {
      return _encoderFactory.createHLAfloat64LE();
   }

   public static HLAfloat64LE createHLAfloat64LE(double v) {
      return _encoderFactory.createHLAfloat64LE(v);
   }

   public static HLAinteger16BE createHLAinteger16BE() {
      return _encoderFactory.createHLAinteger16BE();
   }

   public static HLAinteger16BE createHLAinteger16BE(short i) {
      return _encoderFactory.createHLAinteger16BE(i);
   }

   public static HLAinteger16LE createHLAinteger16LE() {
      return _encoderFactory.createHLAinteger16LE();
   }

   public static HLAinteger16LE createHLAinteger16LE(short i) {
      return _encoderFactory.createHLAinteger16LE(i);
   }

   public static HLAinteger32BE createHLAinteger32BE() {
      return _encoderFactory.createHLAinteger32BE();
   }

   public static HLAinteger32BE createHLAinteger32BE(int i) {
      return _encoderFactory.createHLAinteger32BE(i);
   }

   public static HLAinteger32LE createHLAinteger32LE() {
      return _encoderFactory.createHLAinteger32LE();
   }

   public static HLAinteger32LE createHLAinteger32LE(int i) {
      return _encoderFactory.createHLAinteger32LE(i);
   }

   public static HLAinteger64BE createHLAinteger64BE() {
      return _encoderFactory.createHLAinteger64BE();
   }

   public static HLAinteger64BE createHLAinteger64BE(long l) {
      return _encoderFactory.createHLAinteger64BE(l);
   }

   public static HLAinteger64LE createHLAinteger64LE() {
      return _encoderFactory.createHLAinteger64LE();
   }

   public static HLAinteger64LE createHLAinteger64LE(long l) {
      return _encoderFactory.createHLAinteger64LE(l);
   }

   public static HLAoctet createHLAoctet() {
      return _encoderFactory.createHLAoctet();
   }

   public static HLAoctet createHLAoctet(byte b) {
      return _encoderFactory.createHLAoctet(b);
   }

   public static HLAoctetPairBE createHLAoctetPairBE() {
      return _encoderFactory.createHLAoctetPairBE();
   }

   public static HLAoctetPairBE createHLAoctetPairBE(short i) {
      return _encoderFactory.createHLAoctetPairBE(i);
   }

   public static HLAoctetPairLE createHLAoctetPairLE() {
      return _encoderFactory.createHLAoctetPairLE();
   }

   public static HLAoctetPairLE createHLAoctetPairLE(short i) {
      return _encoderFactory.createHLAoctetPairLE(i);
   }

   public static HLAopaqueData createHLAopaqueData() {
      return _encoderFactory.createHLAopaqueData();
   }

   public static HLAopaqueData createHLAopaqueData(byte[] bytes) {
      return _encoderFactory.createHLAopaqueData(bytes);
   }

   public static HLAunicodeChar createHLAunicodeChar() {
      return _encoderFactory.createHLAunicodeChar();
   }

   public static HLAunicodeChar createHLAunicodeChar(short i) {
      return _encoderFactory.createHLAunicodeChar(i);
   }

   public static HLAunicodeString createHLAunicodeString() {
      return _encoderFactory.createHLAunicodeString();
   }

   public static HLAunicodeString createHLAunicodeString(String s) {
      return _encoderFactory.createHLAunicodeString(s);
   }

   public static HLAvariableArray createHLAvariableArray() {
      return _encoderFactory.createHLAvariableArray();
   }

   public static HLAvariableArray createHLAvariableArray(DataElementFactory dataElementFactory) {
      return _encoderFactory.createHLAvariableArray(dataElementFactory);
   }

   /*
    * RPR Encoder Factory methods
    */

   public static HLAlengthlessVarArray createHLAlengthlessVarArray() {
      return _rprEncoderFactory.createHLAlengthlessVarArray();
   }

   public static HLAlengthlessVarArray createHLAlengthlessVarArray(DataElementFactory dataElementFactory) {
      return _rprEncoderFactory.createHLAlengthlessVarArray(dataElementFactory);
   }

   public static HLAlengthlessVarArray createHLAlengthlessVarArray(DataElement[] dataElements) {
      return _rprEncoderFactory.createHLAlengthlessVarArray(dataElements);
   }

   public static HLApaddingTo16Array createHLApaddingTo16Array() {
      return _rprEncoderFactory.createHLApaddingTo16Array();
   }

   public static HLApaddingTo32Array createHLApaddingTo32Array() {
      return _rprEncoderFactory.createHLApaddingTo32Array();
   }

   public static HLApaddingTo64Array createHLApaddingTo64Array() {
      return _rprEncoderFactory.createHLApaddingTo64Array();
   }

   public static HLAunpaddedLengthlessVarArray createHLAunpaddedLengthlessVarArray() {
      return _rprEncoderFactory.createHLAunpaddedLengthlessVarArray();
   }

   public static HLAunpaddedLengthlessVarArray createHLAunpaddedLengthlessVarArray(DataElementFactory dataElementFactory) {
      return _rprEncoderFactory.createHLAunpaddedLengthlessVarArray(dataElementFactory);
   }

   public static HLAunpaddedLengthlessVarArray createHLAunpaddedLengthlessVarArray(DataElement[] dataElements) {
      return _rprEncoderFactory.createHLAunpaddedLengthlessVarArray(dataElements);
   }

   public static OMT13boolean createOMT13boolean() {
      return _rprEncoderFactory.createOMT13boolean();
   }

   public static OMT13boolean createOMT13boolean(boolean b) {
      return _rprEncoderFactory.createOMT13boolean(b);
   }

   public static OMT13boolean getOMT13boolean(boolean b) {
      return _rprEncoderFactory.getOMT13boolean(b);
   }

   public static OMT13string createOMT13string() {
      return _rprEncoderFactory.createOMT13string();
   }

   public static OMT13string createOMT13string(String s) {
      return _rprEncoderFactory.createOMT13string(s);
   }

   public static UnsignedShort createUnsignedShort() {
      return _rprEncoderFactory.createUnsignedShort();
   }

   public static UnsignedShort createUnsignedShort(int i) {
      return _rprEncoderFactory.createUnsignedShort(i);
   }

   public static UnsignedInteger16BE createUnsignedInteger16BE() {
      return _rprEncoderFactory.createUnsignedInteger16BE();
   }

   public static UnsignedInteger16BE createUnsignedInteger16BE(int i) {
      return _rprEncoderFactory.createUnsignedInteger16BE(i);
   }

   public static UnsignedInteger32BE createUnsignedInteger32BE() {
      return _rprEncoderFactory.createUnsignedInteger32BE();
   }

   public static UnsignedInteger32BE createUnsignedInteger32BE(long l) {
      return _rprEncoderFactory.createUnsignedInteger32BE(l);
   }

   public static UnsignedInteger64BE createUnsignedInteger64BE() {
      return _rprEncoderFactory.createUnsignedInteger64BE();
   }

   public static UnsignedInteger64BE createUnsignedInteger64BE(BigInteger bigInteger) {
      return _rprEncoderFactory.createUnsignedInteger64BE(bigInteger);
   }

   /*
    *  RPR timestamp
    */

   private static int characterDigit(char ch) throws NumberFormatException {
      int val = (int)ch;
      if ('0' <= val && val <= '9') {
         return val - '0';
      }
      if ('A' <= val && val <= 'F') {
         return 10 + val - 'A';
      }
      if ('a' <= val && val <= 'f') {
         return 10 + val - 'a';
      }
      throw new NumberFormatException("Incorrect character: " + ch + " - not [0-F]");
   }

   private static long decodeHexString(byte[] data) throws NumberFormatException {
      long value = 0;
      for (int i = 0, shift = (data.length - 1) * 4; i < data.length; i++, shift -= 4) {
         value += ((long)characterDigit((char)data[i])) << shift;
      }
      return value;
   }

   private static byte[] encodeHexString(int value) {
      byte[] bytes = new byte[8];
      for (int i = 0, shift = 28; i < 8; i++, shift -= 4) {
         bytes[i] = (byte)HEX_DIGITS[(value >> shift) & 0x0F];
      }

      return bytes;
   }

   public static byte[] encodeRprTimestamp(double sec, boolean absolute) {
      int timeSteps = (int)(sec / RPR_TIMESCALE);

      // The most 31 significant bits are used
      int rprTime = timeSteps << 1;

      if (absolute) {
         // Add 1 in the least significant bit to indicate absolute time
         rprTime++;
      }

      return encodeHexString(rprTime);
   }

   public static class RprTimestamp {
      public final boolean valid;
      public final double timestamp; //in s
      public final boolean absolute;

      RprTimestamp(double timestampS, boolean isAbsolute) {
         valid = true;
         timestamp = timestampS;
         absolute = isAbsolute;
      }

      RprTimestamp() {
         valid = false;
         timestamp = 0;
         absolute = false;
      }
   }

   public static RprTimestamp decodeRprTimestamp(byte[] byteArray) {
      try {
         long rprTicks = decodeHexString(byteArray);

         // Lowest bit indicates absolute time)
         boolean absolute = (rprTicks & 0x01) == 0x01;
         long timeSteps = rprTicks >>> 1;

         return new RprTimestamp(timeSteps * RPR_TIMESCALE, absolute);
      } catch (NumberFormatException e) {
         return new RprTimestamp();
      }
   }
}
