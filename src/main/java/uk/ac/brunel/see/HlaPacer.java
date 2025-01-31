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

import java.util.concurrent.TimeUnit;

/**
 * Utility class to support pacing to wall clock time, or any other time source.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public abstract class HlaPacer {

   /**
    * Initialize or restart the pacer.
    *
    * @param initialFrame the initial frame
    *
    * @return <code>true</code> if the pacer was initialized, <code>false</code> otherwise
    */
   public abstract boolean initialize(long initialFrame);

   /**
    * Pace the pacer to the next frame.
    *
    * @return <code>false</code> on frame overrun, <code>true</code> otherwise.
    */
   public abstract boolean pace();

   /**
    * Get the time in nanoseconds [ns] since the pacer was created or initialized.
    *
    * @return the time in nanoseconds [ns] since the pacer was created or initialized.
    */
   public abstract long getNanosecondsSinceInitialize();

   /**
    * Get the number of the current frame.
    *
    * @return the number of the current frame.
    */
   public abstract long getFrame();

   /**
    * Create a pacer with the default time source.
    *
    * @param frameLength length of a frame, in <code>unit</code>
    * @param unit time unit for the <code>frameLength</code>
    *
    * @return the pacer
    */
   public static HlaPacer create(long frameLength, TimeUnit unit) {
      return create(frameLength, unit, DEFAULT_TIME_SOURCE);
   }

   /**
    * Create a pacer with a custom time source.
    *
    * @param frameLength length of a frame, in <code>unit</code>
    * @param unit time unit for the <code>frameLength</code>
    * @param source the time source
    *
    * @return the pacer
    */
   public static HlaPacer create(long frameLength, TimeUnit unit, Source source) {
      return new SourcePacer(frameLength, unit, source);
   }


   /**
    * Interface for a custom time source.
    */
   public interface Source {
      /**
       * Initialize the time source.
       *
       * @param offsetNs start offset, in nanoseconds.
       *
       * @return if the time source was initialized.
       */
      boolean initialize(long offsetNs);

      /**
       * Get number of nanoseconds [ns] since the time source was initialized.
       *
       * @return the number of nanoseconds [ns] since the time source was initialized.
       */
      long getNanosecondsSinceInitialize();
   }


   /** Time source using <code>System.nanoTime()</code>. */
   public static final Source SYSTEM_NANO_TIME_SOURCE = new Source() {
      private long _startTimeNs;

      public boolean initialize(long offsetNs) {
         _startTimeNs = System.nanoTime() - offsetNs;
         return true;
      }

      public long getNanosecondsSinceInitialize() {
         return System.nanoTime() - _startTimeNs;
      }
   };

   /** Time source using <code>System.currentTimeMillis()</code>. */
   public static final Source SYSTEM_MILLIS_TIME_SOURCE = new Source() {
      private long _startTimeMs;

      public boolean initialize(long offsetNs) {
         _startTimeMs = System.currentTimeMillis() - (offsetNs / NANOS_PER_MILLIS);
         return true;
      }

      public long getNanosecondsSinceInitialize() {
         return NANOS_PER_MILLIS * (System.currentTimeMillis() - _startTimeMs);
      }
   };

   /** The default time source, currently <code>SYSTEM_NANO_TIME_SOURCE</code>. */
   public static final Source DEFAULT_TIME_SOURCE = SYSTEM_NANO_TIME_SOURCE;

   /** Nanoseconds per milliseconds. */
   public static final long NANOS_PER_MILLIS = 1000 * 1000;

   private static final class SourcePacer extends HlaPacer {
      private final long _frameLengthNs;
      private final Source _source;
      private long _frame;

      private SourcePacer(long frameLength, TimeUnit unit, Source source) {
         _frameLengthNs = unit.toNanos(frameLength);
         _source = source;
         initialize(0);
      }

      @Override
      public boolean initialize(long initialFrame) {
         _frame = initialFrame;
         return _source.initialize(_frameLengthNs * initialFrame);
      }

      @Override
      public boolean pace() {
         _frame++;

         long nextFrameStartNs = _frame * _frameLengthNs;
         long sleepNs = nextFrameStartNs - _source.getNanosecondsSinceInitialize();
         if (sleepNs < 0) {
            return false;
         }

         try {
            Thread.sleep(sleepNs / NANOS_PER_MILLIS, (int)((sleepNs % NANOS_PER_MILLIS)));
         } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
         }
         return true;
      }

      @Override
      public long getNanosecondsSinceInitialize() {
         return _source.getNanosecondsSinceInitialize();
      }

      @Override
      public long getFrame() {
         return _frame;
      }
   }
}
