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


/**
 * A <code>HlaLogicalTime</code> is representation of the logical scenario time within the simulation.
 * Note that the logical time might not be related to wall clock time or the <code>HlaTimeStamp</code>.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 *
 * @see HlaTimeStamp
 */
public interface HlaLogicalTime extends Comparable<HlaLogicalTime>  {
   public static enum OrderType {
      TIME_STAMP_ORDER, RECEIVE_ORDER, NONE
   }

   /**
    * True if this logical time is valid and has a value.
    *
    * @return <code>true</code> if this logical time is valid and has a value, <code>false</code> otherwise.
    */
   public boolean isValid();

   /**
    * Value of the logical time.
    *
    * @return The value of the logical time.
    */
   public long getValue();

   /**
    * Return the order in which this message was received, if any.
    *
    * @return the order in which this message was received, if any.
    */
   public OrderType getReceivedOrderType();

   /**
    * True if the message associated with this logical time was received in time stamp order (TSO).
    *
    * @return <code>true</code> if the message associated with this logical time was received in time stamp order (TSO),
    *         <code>false</code> otherwise.
    */
   public boolean wasReceivedInTimeStampOrder();

   /**
    * Add <code>addend</code> to value.
    *
    * @param addend value to add
    *
    * @return the new logical time with <code>value + addend</code>
    */
   public HlaLogicalTime add(long addend);

   /**
    * Get a string representation of the logical time.
    *
    * @return A string representation of the logical time.
    */
   String toString();

   /**
    * Indicates whether some other object is "equal to" this one.
    *
    * @param other the reference object with which to compare.
    *
    * @return <code>true</code> if the objects are the same, <code>false</code> otherwise.
    */
   boolean equals(Object other);

   /**
    * Returns a hash code value for the object.
    *
    * @return a hash code value for this object.
    */
   int hashCode();
}
