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
 * Implementation of the <code>ReferenceFrameRotation</code> data type from the FOM.
 * <br>Description from the FOM: This is the rotational state of a reference frame with respect to a 'referent' frame.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public final class ReferenceFrameRotation {

   /**
    * The value of the attitudeQuaternion field.
    * <br>FOM name: <code>attitude_quaternion</code>.
    * <br>Description from the FOM: Attitude quaternion that specifies the orientation of the subject frame with respect to the referent.
    */
    public final AttitudeQuaternion attitudeQuaternion;
   /**
    * The value of the angularVelocity field.
    * <br>FOM name: <code>angular_velocity</code>.
    * <br>Description from the FOM: Angular velocity of the subject frame with respect to the referent with components resolved onto the subject coordinate axes.
    */
    public final double[/* 3 */] angularVelocity;

    private ReferenceFrameRotation() {
        this.attitudeQuaternion = null;
        this.angularVelocity = null;
    }

    private ReferenceFrameRotation(
        AttitudeQuaternion attitudeQuaternion,
        double[/* 3 */] angularVelocity
    ) {
        this.attitudeQuaternion = attitudeQuaternion;
        this.angularVelocity = angularVelocity;
    }

   /**
    * Create a new ReferenceFrameRotation.
    *
    * @param attitudeQuaternion value of the attitudeQuaternion field
    * <br>Description from the FOM: Attitude quaternion that specifies the orientation of the subject frame with respect to the referent.
    * <br>Description of the data type from the FOM: This is a quaternion quantifying the orientation of a 'subject' reference frame with respect to some other 'referent' frame. Quaternions consist of one scalar component and a 3-element vector component and can be denoted Q = (s, V), where s is a scalar and V is a vector (x, y, z). Confusion often arises regarding the meaning of the quaternion. (There are several similar but incompatible conventions.) This data type defines a so-called 'left unit transformation quaternion'. It may be used to transform the elements of a vector V_R = (xR, yR, zR) resolved in the referent frame's coordinate axes into the corresponding elements of a vector V_S = (xS, yS, zS) resolved in the subject frame's coordinate axes (i.e. determine the components of the same vector in another coordinate system). The quaternion transformation formula is (0, V_A) = Q . (0, V_B) . Q*, where '.' denotes quaternion multiplication and * denotes quaternion conjugation. The formula for multiplication of the quaternion Q1 = (s1, V1) where V1 is the vector (x1, y1, z1) by the quaternion Q2 = (s2, V2) where V2 is the vector (x2, y2, z2) is Q3 = Q1 . Q2 = (s3, V3) where s3 = s1 s2 - (x1 x2 + y1 y2 + z1 z2) and V3 = (x3, y3, z3) with x3 = s1 x2 + s2 x1 + (y1 z2 - z1 y2), y3 = s1 y2 + s2 y1 + (z1 x2 - x1 z2) and z3 = s1 z2 + s2 z1 + (x1 y2 - y1 x2). The formula for conjugation of the quaternion Q = (s, V), where V is the vector (x, y, z) is Q* = (s, -V) = (s, (-x, -y, -z)).
    * @param angularVelocity value of the angularVelocity field
    * <br>Description from the FOM: Angular velocity of the subject frame with respect to the referent with components resolved onto the subject coordinate axes.
    * <br>Description of the data type from the FOM: A 3-vector that specifies the time derivative of the orientation of one reference frame with respect to another. This data type does not specify the reference frames, nor does it specify the coordinate axes onto which the three components of the vector are projected.
    *
    * @return a new ReferenceFrameRotation
    */
    public static ReferenceFrameRotation create(
        AttitudeQuaternion attitudeQuaternion,
        double[/* 3 */] angularVelocity
    ) {
        if (attitudeQuaternion == null) {
            throw new NullPointerException("attitudeQuaternion can not be null");
        }
        if (angularVelocity == null) {
            throw new NullPointerException("angularVelocity can not be null");
        }

        return new ReferenceFrameRotation(
           attitudeQuaternion,
           angularVelocity
        );
    }

   /**
    * Get the value of the attitudeQuaternion field.
    * <br>FOM name: <code>attitude_quaternion</code>.
    * <br>Description from the FOM: Attitude quaternion that specifies the orientation of the subject frame with respect to the referent.
    * <br>Description of the data type from the FOM: This is a quaternion quantifying the orientation of a 'subject' reference frame with respect to some other 'referent' frame. Quaternions consist of one scalar component and a 3-element vector component and can be denoted Q = (s, V), where s is a scalar and V is a vector (x, y, z). Confusion often arises regarding the meaning of the quaternion. (There are several similar but incompatible conventions.) This data type defines a so-called 'left unit transformation quaternion'. It may be used to transform the elements of a vector V_R = (xR, yR, zR) resolved in the referent frame's coordinate axes into the corresponding elements of a vector V_S = (xS, yS, zS) resolved in the subject frame's coordinate axes (i.e. determine the components of the same vector in another coordinate system). The quaternion transformation formula is (0, V_A) = Q . (0, V_B) . Q*, where '.' denotes quaternion multiplication and * denotes quaternion conjugation. The formula for multiplication of the quaternion Q1 = (s1, V1) where V1 is the vector (x1, y1, z1) by the quaternion Q2 = (s2, V2) where V2 is the vector (x2, y2, z2) is Q3 = Q1 . Q2 = (s3, V3) where s3 = s1 s2 - (x1 x2 + y1 y2 + z1 z2) and V3 = (x3, y3, z3) with x3 = s1 x2 + s2 x1 + (y1 z2 - z1 y2), y3 = s1 y2 + s2 y1 + (z1 x2 - x1 z2) and z3 = s1 z2 + s2 z1 + (x1 y2 - y1 x2). The formula for conjugation of the quaternion Q = (s, V), where V is the vector (x, y, z) is Q* = (s, -V) = (s, (-x, -y, -z)).
    *
    * @return the value of the attitudeQuaternion field
    */
    public AttitudeQuaternion getAttitudeQuaternion() {
        return attitudeQuaternion;
    }

   /**
    * Get the value of the angularVelocity field.
    * <br>FOM name: <code>angular_velocity</code>.
    * <br>Description from the FOM: Angular velocity of the subject frame with respect to the referent with components resolved onto the subject coordinate axes.
    * <br>Description of the data type from the FOM: A 3-vector that specifies the time derivative of the orientation of one reference frame with respect to another. This data type does not specify the reference frames, nor does it specify the coordinate axes onto which the three components of the vector are projected.
    *
    * @return the value of the angularVelocity field
    */
    public double[/* 3 */] getAngularVelocity() {
        return angularVelocity;
    }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || getClass() != object.getClass()) {
         return false;
      }

      ReferenceFrameRotation other = (ReferenceFrameRotation)object;

      return new EqualsBuilder()
            .append(attitudeQuaternion, other.attitudeQuaternion)
            .append(angularVelocity, other.angularVelocity)
            .isEquals();
   }

   @Override
   public int hashCode() {
      return new HashCodeBuilder()
            .append(attitudeQuaternion)
            .append(angularVelocity)
            .toHashCode();
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
            .append("attitudeQuaternion", attitudeQuaternion)
            .append("angularVelocity", angularVelocity)
            .getToString();
   }
}
