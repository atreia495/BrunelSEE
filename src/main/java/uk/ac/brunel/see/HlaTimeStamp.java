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
 * A timestamp representing when the message was created.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaTimeStamp extends Comparable<HlaTimeStamp> {

   /**
    * Get a long representation of the timestamp.
    *
    * @return A long representation of the timestamp.
    */
   long getValue();

   /**
    * Get user supplied tag to send for this timestamp.
    *
    * @return The user supplied tag to send for this timestamp.
    */
   byte[] getUserSuppliedTag();

   /**
    * Get a string representation of the timestamp.
    *
    * @return A string representation of the timestamp.
    */
   String toString();

   /**
    * Indicates whether some other object is "equal to" this one.
    *
    * @param   other   the reference object with which to compare.
    *
    * @return  <code>true</code> if the objects are the same, <code>false</code> otherwise.
    */
   boolean equals(Object other);

   /**
    * Returns a hash code value for the object.
    *
    * @return a hash code value for this object.
    */
   int hashCode();

   /**
    * Calculates the time since an other timestamp.
    *
    * @param timestamp the other timestamp
    *
    * @return the time difference in seconds
    */
   double secondsSince(HlaTimeStamp timestamp);
}
