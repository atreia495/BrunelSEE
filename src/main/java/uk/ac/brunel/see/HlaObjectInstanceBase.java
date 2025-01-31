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
 * Base interface used by all object instances.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaObjectInstanceBase {

   /**
    * An enumeration of all object class types.
    */
   public enum ObjectClassType {
      /** HLAobjectRoot, instance of <code>HlaHLAobjectRoot</code> (FOM name <code>HLAobjectRoot</code>) */
      HLAOBJECT_ROOT(HlaHLAobjectRoot.class),
      /** PhysicalEntity, instance of <code>HlaPhysicalEntity</code> (FOM name <code>HLAobjectRoot.PhysicalEntity</code>) */
      PHYSICAL_ENTITY(HlaPhysicalEntity.class),
      /** DynamicalEntity, instance of <code>HlaDynamicalEntity</code> (FOM name <code>HLAobjectRoot.PhysicalEntity.DynamicalEntity</code>) */
      DYNAMICAL_ENTITY(HlaDynamicalEntity.class),
      /** PhysicalInterface, instance of <code>HlaPhysicalInterface</code> (FOM name <code>HLAobjectRoot.PhysicalInterface</code>) */
      PHYSICAL_INTERFACE(HlaPhysicalInterface.class),
      /** ReferenceFrame, instance of <code>HlaReferenceFrame</code> (FOM name <code>HLAobjectRoot.ReferenceFrame</code>) */
      REFERENCE_FRAME(HlaReferenceFrame.class),
      /** ExecutionConfiguration, instance of <code>HlaExecutionConfiguration</code> (FOM name <code>HLAobjectRoot.ExecutionConfiguration</code>) */
      EXECUTION_CONFIGURATION(HlaExecutionConfiguration.class);

      private final Class<? extends HlaObjectInstanceBase> _instanceClass;

      private ObjectClassType(Class<? extends HlaObjectInstanceBase> instanceClass) {
         _instanceClass = instanceClass;
      }

      /**
       * Get the object instance class.
       *
       * @return the object instance class
       */
      public Class<? extends HlaObjectInstanceBase> getInstanceClass() {
         return _instanceClass;
      }
   }

   /**
    * Tells if this object instance is local or remote.
    * An local instance is an instance that you have originally created.
    *
    * @return true if local
    */
   boolean isLocal();

   /**
    * Tells if this object instance is initialized.
    * An object is initialized if all attributes marked with <i>Initialized</i> has a value.
    *
    * @return true if initialized
    */
   boolean isInitialized();

   /**
    * Tells if this object instance is within interest.
    * A removed instance is never within interest.
    *
    * @return true if within interest.
    */
   boolean isWithinInterest();

   /**
    * Tells if this object instance has been removed.
    *
    * @return true if removed
    */
   boolean isRemoved();

   /**
    * Get the <code>HLA instance name</code>.
    *
    * @return the <code>HLA instance name</code>.
    */
   String getHlaInstanceName();

   /**
    * Get the encoded <code>HLA instance handle</code>.
    *
    * @return the encoded <code>HLA instance handle</code>.
    */
   byte[] getEncodedHlaObjectInstanceHandle();

   /**
    * Get the object class type for this object instance.
    *
    * @return the object class type for this object instance
    */
    ObjectClassType getClassType();

   /**
    * Get the HlaFederateId for the federate that created this instance
    *
    * @return The federate id for the federate that created this instance
    */
    HlaFederateId getProducingFederate();
}
