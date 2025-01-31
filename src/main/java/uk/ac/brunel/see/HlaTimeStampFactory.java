package uk.ac.brunel.see;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import uk.ac.brunel.see.impl.encoders.BaseEncoder;
import uk.ac.brunel.see.impl.utils.MapAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Factory used to create timestamps.
 */
public abstract class HlaTimeStampFactory {

   /**
    * Create a timestamp for the current time.
    *
    * @return A timestamp for the current time.
    */
   public abstract HlaTimeStamp create();

   /**
    * Create a timestamp from a user supplied tag.
    * If the timestamp is not based on the user supplied tag it may be ignored.
    *
    * @param userSuppliedTag the user supplied tag that represents the current time
    *
    * @return A timestamp for the time represented by the user supplied tag.
    */
   public HlaTimeStamp create(byte[] userSuppliedTag) {
      return create();
   }

   /**
    * Create a timestamp from an existing timestamp and an offset.
    *
    * @param reference the timestamp to use as reference
    * @param offset the offset in seconds
    *
    * @return A timestamp calculated from reference and offset.
    */
   public HlaTimeStamp create(HlaTimeStamp reference, double offset) {
      return reference;
   }

   protected HlaTimeStampFactory() {
   }

   /**
    * Factory that will generate empty timestamps.
    * <p>
    * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
    */
   public static final HlaTimeStampFactory NONE = new HlaTimeStampFactory() {
      public HlaTimeStamp create() {
         return EMPTY_TIME_STAMP;
      }
   };

   /**
    * Factory that will generate system clock timestamps.
    * <p>
    * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
    */
   public static final HlaTimeStampFactory SYSTEM_CLOCK = new HlaTimeStampFactory() {
      public HlaTimeStamp create() {
         return new SystemClockTimeStamp();
      }

      @Override
      public HlaTimeStamp create(HlaTimeStamp reference, double offset) {
         return new SystemClockTimeStamp(reference.getValue() + (long) (1E+3 * offset));
      }

   };

   /**
    * Factory that will generate relative RPR FOM timestamps, using user supplied tag.
    * <p>
    * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
    */
   public static final class RprRelativeTimeStampFactory extends HlaTimeStampFactory {
      private final long _startupTime;

      /**
       * Create a factory that will generate relative RPR FOM timestamps, using the current time as startup time.
       * The startup time is in milliseconds (since the last hour).
       */
      public RprRelativeTimeStampFactory() {
         _startupTime = System.currentTimeMillis();
      }

      /**
       * Create a factory that will generate relative RPR FOM timestamps, using the supplied startup time.
       * The startup time is in milliseconds (since the last hour).
       *
       * @param startupTimeMilli the startup time in milliseconds (since the last hour)
       */
      public RprRelativeTimeStampFactory(long startupTimeMilli) {
         _startupTime = startupTimeMilli;
      }

      public HlaTimeStamp create() {
         return new RprTimeStamp(_startupTime);
      }

      @Override
      public HlaTimeStamp create(byte[] userSuppliedTag) {
         return new RprTimeStamp(userSuppliedTag);
      }

      @Override
      public HlaTimeStamp create(HlaTimeStamp reference, double offset) {
         return new RprTimeStamp(reference.getValue() + (long) (1E+6 * offset), false);
      }

      /**
       * Get the startup time for relative RPR FOM timestamps.
       * The startup time is in milliseconds (since the last hour).
       *
       * @return The startup time for relative RPR FOM timestamps.
       */
      public long getStartupTime() {
         return _startupTime;
      }
   }

   /**
    * Factory that will generate relative RPR FOM timestamps, using user supplied tag.
    * <p>
    * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
    */
   public static final RprRelativeTimeStampFactory RPR_RELATIVE = new RprRelativeTimeStampFactory();

   /**
    * Factory that will generate absolute RPR FOM timestamps, using user supplied tag.
    * <p>
    * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
    */
   public static final HlaTimeStampFactory RPR_ABSOLUTE = new HlaTimeStampFactory() {
      public HlaTimeStamp create() {
         return new RprTimeStamp();
      }

      @Override
      public HlaTimeStamp create(byte[] userSuppliedTag) {
         return new RprTimeStamp(userSuppliedTag);
      }

      @Override
      public HlaTimeStamp create(HlaTimeStamp reference, double offset) {
         return new RprTimeStamp(reference.getValue() + (long) (1E+6 * offset), true);
      }
   };

   /**
    * Set with all predefined HlaTimeStampFactories
    */
   private static final Map<String, HlaTimeStampFactory> TIME_STAMP_FACTORIES = new MapAdapter<String, HlaTimeStampFactory>()
      .append("none", NONE).append("system_clock", SYSTEM_CLOCK).append("rpr_relative", RPR_RELATIVE).append("rpr_absolute", RPR_ABSOLUTE).unmodifiableMap();

   /**
    * Get the predefined HlaTimeStampFactory with the supplied name.
    *
    * @param name Name of the predefined HlaTimeStampFactory
    *
    * @return The predefined HlaTimeStampFactory or <code>null</code>.
    */
   public static HlaTimeStampFactory getHlaTimeStampFactory(String name) {
      return TIME_STAMP_FACTORIES.get(name.toLowerCase(Locale.ENGLISH));
   }

   /*
    * Time stamp impls
    */

   /**
    * Abstract base class for <code>HlaTimeStamp</code>s.
    */
   protected abstract static class AbstractTimeStamp implements HlaTimeStamp {
      protected static final byte[] NULL_BYTE_ARRAY = new byte[0];

      public abstract long getValue();

      public byte[] getUserSuppliedTag() {
         return NULL_BYTE_ARRAY;
      }

      public abstract String toString();

      public int compareTo(HlaTimeStamp timestamp) {
         return Long.compare(getValue(), timestamp.getValue());
      }

      @Override
      public boolean equals(Object other) {
         if (this == other) {
            return true;
         }

         if (other == null || getClass() != other.getClass()) {
            return false;
         }

         AbstractTimeStamp that = (AbstractTimeStamp)other;
         return getValue() == that.getValue();
      }

      @Override
      public int hashCode() {
         return (int)(getValue() ^ (getValue() >>> 32));
      }
   }

   /**
    * Static timestamp that can be used for all empty timestamps.
    */
   private static final HlaTimeStamp EMPTY_TIME_STAMP = new AbstractTimeStamp() {
      public long getValue() {
         return 0;
      }

      public double secondsSince(HlaTimeStamp timestamp) {
         return 0;
      }

      public String toString() {
         return "-";
      }
   };

   /**
    * System clock timestamp implementation.
    */
   public static class SystemClockTimeStamp extends AbstractTimeStamp {
      private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

      private final long _value;

      /**
       * Create a timestamp for the current time.
       */
      public SystemClockTimeStamp() {
         _value = System.currentTimeMillis();
      }

      private SystemClockTimeStamp(long value) {
         _value = value;
      }

      /**
       * {@inheritDoc}
       * The time is in milliseconds (since midnight, January 1, 1970 UTC like {@link System#currentTimeMillis()}).
       */
      public long getValue() {
         return _value;
      }

      public double secondsSince(HlaTimeStamp timestamp) {
         return 0.001 * (double) (getValue() - timestamp.getValue());
      }

      public String toString() {
         synchronized (FORMAT) {
            return FORMAT.format(new Date(_value));
         }
      }
   }

   /**
    * RPR timestamp impl
    */
   public static class RprTimeStamp extends AbstractTimeStamp {
      private static final long MILLIS_PER_HOUR = 1000 * 60 * 60;
      private static final long MILLIS_TO_MICROS = 1000;
      private static final double MICROS_PER_SEC = 1000 * 1000;

      private final boolean _valid;
      private final long _micros;
      private final boolean _absolute;
      private final HlaTimeStamp _arrivalTime;


      /**
       * Create an absolute PPR FOM timestamp for the current time.
       */
      public RprTimeStamp() {
         final long currentTimeMilli = System.currentTimeMillis();
         _valid = true;
         _micros = (currentTimeMilli % MILLIS_PER_HOUR) * MILLIS_TO_MICROS;
         _absolute = true;
         _arrivalTime = null;
      }

      /**
       * Create a relative PPR FOM timestamp for the current time with the supplied startup time.
       * The startup time is in milliseconds (since the last hour).
       *
       * @param startupTimeMilli the startup time in milliseconds (since the last hour)
       */
      public RprTimeStamp(long startupTimeMilli) {
         final long currentTimeMilli = System.currentTimeMillis();
         _valid = true;
         _micros = ((currentTimeMilli - startupTimeMilli) % MILLIS_PER_HOUR) * MILLIS_TO_MICROS;
         _absolute = false;
         _arrivalTime = null;
      }

      /**
       * Create a PPR FOM timestamp from a user supplied tag.
       * The startup time is in milliseconds (since the last hour).
       *
       * @param userSuppliedTag the user supplied tag that represents the timestamp
       */
      public RprTimeStamp(byte[] userSuppliedTag) {
         _arrivalTime = new RprTimeStamp();
         if (userSuppliedTag != null && userSuppliedTag.length > 0) {
            if (userSuppliedTag.length > 8) {
               byte[] copy = new byte[8];
               System.arraycopy(userSuppliedTag, 0, copy, 0, 8);
               userSuppliedTag = copy;
            }
            BaseEncoder.RprTimestamp rprTimestamp = BaseEncoder.decodeRprTimestamp(userSuppliedTag);
            _valid = rprTimestamp.valid;
            _micros = Math.round(rprTimestamp.timestamp * MICROS_PER_SEC);
            _absolute = rprTimestamp.absolute;
         } else {
            _valid = false;
            _micros = 0;
            _absolute = false;
         }
      }

      private RprTimeStamp(long micros, boolean absolute) {
         _arrivalTime = null;
         _valid = true;
         _micros = micros;
         _absolute = absolute;
      }

      /**
       * Check if the timestamp is valid.
       *
       * @return <code>true</code> if the timestamp is valid.
       */
      public boolean isValid() {
         return _valid;
      }

      /**
       * Check if the timestamp is absolute.
       *
       * @return <code>true</code> if the timestamp is absolute.
       */
      public boolean isAbsolute() {
         return _absolute;
      }

      /**
       * Check if the timestamp has an arrival time.
       *
       * @return <code>true</code> if the timestamp has an arrival time.
       */
      public boolean hasArrivalTime() {
         return _arrivalTime != null;
      }

      /**
       * Get the arrival time.
       *
       * @return the arrival time.
       */
      public HlaTimeStamp getArrivalTime() {
         return hasArrivalTime() ? _arrivalTime : this;
      }

      /**
       * {@inheritDoc}
       * The time is in microseconds (since the last hour).
       */
      public long getValue() {
         return _micros;
      }

      public double secondsSince(HlaTimeStamp timestamp) {
         return ((double) (getValue() - timestamp.getValue())) / MICROS_PER_SEC;
      }

      @Override
      public byte[] getUserSuppliedTag() {
         return _valid ? BaseEncoder.encodeRprTimestamp(_micros / MICROS_PER_SEC, _absolute) : NULL_BYTE_ARRAY;
      }

      public String toString() {
         return (_absolute ? "": "~ ") + microToString(_micros);
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         }

         if (o == null || getClass() != o.getClass()) {
            return false;
         }

         RprTimeStamp that = (RprTimeStamp)o;

         if (!isValid()) {  // invalid RPR timestamp equals to all other invalid RPR timestamp
            return isValid() == that.isValid();
         }

         return super.equals(o) && isAbsolute() == that.isAbsolute();
      }

      @Override
      public int hashCode() {
         return super.hashCode() * 31 + (isAbsolute() ? 1 : 0);
      }


      private static final long MICRO_PER_MILLI = 1000;
      private static final long MILLI_PER_SEC = 1000;
      private static final long SEC_PER_MIN = 60;
      private static final long MIN_PER_HOUR = 60;

      private static StringBuilder append(StringBuilder sb, long value, int zeros) {
         String valueStr = Long.toString(value);
         int length = valueStr.length();
         while (length < zeros) {
            sb.append('0');
            zeros--;
         }
         return sb.append(valueStr);
      }

      private static String microToString(long micros) {
         if (micros < 0) {
            return Long.toString(micros);
         }

         long millis = micros / MICRO_PER_MILLI;
         long sec = millis / MILLI_PER_SEC;
         long min = sec / SEC_PER_MIN;
         long hour = min / MIN_PER_HOUR;

         StringBuilder sb = new StringBuilder();
         if (hour != 0) {
            sb.append(hour).append(':');
            append(sb, min % MIN_PER_HOUR, 2).append(':');
         } else {
            sb.append(min % MIN_PER_HOUR).append(':');
         }
         append(sb, sec % SEC_PER_MIN, 2).append('.');
         append(sb, millis % MILLI_PER_SEC, 3).append(',');
         append(sb, micros % MICRO_PER_MILLI, 3);

         return sb.toString();
      }
   }
}
