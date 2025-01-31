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
 * Implementation of the <code>SpaceTimeCoordinateState</code> data type from the FOM.
 * <br>Description from the FOM: A multi-dimensional representation of an observational coordinate frame and associated state.  There are three spatial dimensions, three attitude dimensions and one time dimension.  The spatial and attitude components define a right-handed orthogonal set of coordinate axes that constitute a reference frame.  The time dimension specifies the 'position' of the coordinate with respect to the physical time scale (TT).
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public final class SpaceTimeCoordinateState {

   /**
    * The value of the translationalState field.
    * <br>FOM name: <code>translational_state</code>.
    * <br>Description from the FOM: This is the reference frame's translational state with respect to its parent frame. If this frame has no parent, this attribute is meaningless.
    */
    public final ReferenceFrameTranslation translationalState;
   /**
    * The value of the rotationalState field.
    * <br>FOM name: <code>rotational_state</code>.
    * <br>Description from the FOM: This is the reference frame's rotational state with respect to its parent frame. If this frame has no parent, this attribute is meaningless.
    */
    public final ReferenceFrameRotation rotationalState;
   /**
    * The value of the time field.
    * <br>FOM name: <code>time</code>.
    * <br>Description from the FOM: This specifies the simulated physical time (TT), which represents the time dimension associated with a reference frame state.  It is the fourth component along with the three spatial dimensions that define a reference frame coordinate state.
    */
    public final double time;

    private SpaceTimeCoordinateState() {
        this.translationalState = null;
        this.rotationalState = null;
        this.time = 0;
    }

    private SpaceTimeCoordinateState(
        ReferenceFrameTranslation translationalState,
        ReferenceFrameRotation rotationalState,
        double time
    ) {
        this.translationalState = translationalState;
        this.rotationalState = rotationalState;
        this.time = time;
    }

   /**
    * Create a new SpaceTimeCoordinateState.
    *
    * @param translationalState value of the translationalState field
    * <br>Description from the FOM: This is the reference frame's translational state with respect to its parent frame. If this frame has no parent, this attribute is meaningless.
    * <br>Description of the data type from the FOM: This is the translational state of a subject reference frame with respect to a 'referent' frame. This data type does not specify the two reference frames.
    * @param rotationalState value of the rotationalState field
    * <br>Description from the FOM: This is the reference frame's rotational state with respect to its parent frame. If this frame has no parent, this attribute is meaningless.
    * <br>Description of the data type from the FOM: This is the rotational state of a reference frame with respect to a 'referent' frame.
    * @param time value of the time field
    * <br>Description from the FOM: This specifies the simulated physical time (TT), which represents the time dimension associated with a reference frame state.  It is the fourth component along with the three spatial dimensions that define a reference frame coordinate state.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @return a new SpaceTimeCoordinateState
    */
    public static SpaceTimeCoordinateState create(
        ReferenceFrameTranslation translationalState,
        ReferenceFrameRotation rotationalState,
        double time
    ) {
        if (translationalState == null) {
            throw new NullPointerException("translationalState can not be null");
        }
        if (rotationalState == null) {
            throw new NullPointerException("rotationalState can not be null");
        }

        return new SpaceTimeCoordinateState(
           translationalState,
           rotationalState,
           time
        );
    }

   /**
    * Get the value of the translationalState field.
    * <br>FOM name: <code>translational_state</code>.
    * <br>Description from the FOM: This is the reference frame's translational state with respect to its parent frame. If this frame has no parent, this attribute is meaningless.
    * <br>Description of the data type from the FOM: This is the translational state of a subject reference frame with respect to a 'referent' frame. This data type does not specify the two reference frames.
    *
    * @return the value of the translationalState field
    */
    public ReferenceFrameTranslation getTranslationalState() {
        return translationalState;
    }

   /**
    * Get the value of the rotationalState field.
    * <br>FOM name: <code>rotational_state</code>.
    * <br>Description from the FOM: This is the reference frame's rotational state with respect to its parent frame. If this frame has no parent, this attribute is meaningless.
    * <br>Description of the data type from the FOM: This is the rotational state of a reference frame with respect to a 'referent' frame.
    *
    * @return the value of the rotationalState field
    */
    public ReferenceFrameRotation getRotationalState() {
        return rotationalState;
    }

   /**
    * Get the value of the time field.
    * <br>FOM name: <code>time</code>.
    * <br>Description from the FOM: This specifies the simulated physical time (TT), which represents the time dimension associated with a reference frame state.  It is the fourth component along with the three spatial dimensions that define a reference frame coordinate state.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @return the value of the time field
    */
    public double getTime() {
        return time;
    }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || getClass() != object.getClass()) {
         return false;
      }

      SpaceTimeCoordinateState other = (SpaceTimeCoordinateState)object;

      return new EqualsBuilder()
            .append(translationalState, other.translationalState)
            .append(rotationalState, other.rotationalState)
            .append(time, other.time)
            .isEquals();
   }

   @Override
   public int hashCode() {
      return new HashCodeBuilder()
            .append(translationalState)
            .append(rotationalState)
            .append(time)
            .toHashCode();
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
            .append("translationalState", translationalState)
            .append("rotationalState", rotationalState)
            .append("time", time)
            .getToString();
   }
}
