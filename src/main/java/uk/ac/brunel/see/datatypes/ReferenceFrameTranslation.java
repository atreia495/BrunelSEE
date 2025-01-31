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



/**
 * Implementation of the <code>ReferenceFrameTranslation</code> data type from the FOM.
 * <br>Description from the FOM: This is the translational state of a subject reference frame with respect to a 'referent' frame. This data type does not specify the two reference frames.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public final class ReferenceFrameTranslation {

   /**
    * The value of the position field.
    * <br>FOM name: <code>position</code>.
    * <br>Description from the FOM: Position of the subject frame origin with respect to the referent origin with components expressed in the referent coordinate axes.
    */
    public final double[/* 3 */] position;
   /**
    * The value of the velocity field.
    * <br>FOM name: <code>velocity</code>.
    * <br>Description from the FOM: Velocity of the subject frame origin with respect to its referent origin with components expressed in the referent coordinate axes.
    */
    public final double[/* 3 */] velocity;

    private ReferenceFrameTranslation() {
        this.position = null;
        this.velocity = null;
    }

    private ReferenceFrameTranslation(
        double[/* 3 */] position,
        double[/* 3 */] velocity
    ) {
        this.position = position;
        this.velocity = velocity;
    }

   /**
    * Create a new ReferenceFrameTranslation.
    *
    * @param position value of the position field
    * <br>Description from the FOM: Position of the subject frame origin with respect to the referent origin with components expressed in the referent coordinate axes.
    * <br>Description of the data type from the FOM: A 3-vector that specifies the translational position of one point with respect to another. This data type does not specify which points are involved, nor does it specify the coordinate axes onto which the three components of the vector are projected.
    * @param velocity value of the velocity field
    * <br>Description from the FOM: Velocity of the subject frame origin with respect to its referent origin with components expressed in the referent coordinate axes.
    * <br>Description of the data type from the FOM: A 3-vector that specifies the time derivative of the vector position of some point with respect to another as seen by an observer fixed in some reference frame. This data type does not specify which points are involved, nor does it specify the observer frame of reference, nor does it specify the coordinate axes onto which the three components of the vector are projected.
    *
    * @return a new ReferenceFrameTranslation
    */
    public static ReferenceFrameTranslation create(
        double[/* 3 */] position,
        double[/* 3 */] velocity
    ) {
        if (position == null) {
            throw new NullPointerException("position can not be null");
        }
        if (velocity == null) {
            throw new NullPointerException("velocity can not be null");
        }

        return new ReferenceFrameTranslation(
           position,
           velocity
        );
    }

   /**
    * Get the value of the position field.
    * <br>FOM name: <code>position</code>.
    * <br>Description from the FOM: Position of the subject frame origin with respect to the referent origin with components expressed in the referent coordinate axes.
    * <br>Description of the data type from the FOM: A 3-vector that specifies the translational position of one point with respect to another. This data type does not specify which points are involved, nor does it specify the coordinate axes onto which the three components of the vector are projected.
    *
    * @return the value of the position field
    */
    public double[/* 3 */] getPosition() {
        return position;
    }

   /**
    * Get the value of the velocity field.
    * <br>FOM name: <code>velocity</code>.
    * <br>Description from the FOM: Velocity of the subject frame origin with respect to its referent origin with components expressed in the referent coordinate axes.
    * <br>Description of the data type from the FOM: A 3-vector that specifies the time derivative of the vector position of some point with respect to another as seen by an observer fixed in some reference frame. This data type does not specify which points are involved, nor does it specify the observer frame of reference, nor does it specify the coordinate axes onto which the three components of the vector are projected.
    *
    * @return the value of the velocity field
    */
    public double[/* 3 */] getVelocity() {
        return velocity;
    }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || getClass() != object.getClass()) {
         return false;
      }

      ReferenceFrameTranslation other = (ReferenceFrameTranslation)object;

      return new EqualsBuilder()
            .append(position, other.position)
            .append(velocity, other.velocity)
            .isEquals();
   }

   @Override
   public int hashCode() {
      return new HashCodeBuilder()
            .append(position)
            .append(velocity)
            .toHashCode();
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
            .append("position", position)
            .append("velocity", velocity)
            .getToString();
   }
}
