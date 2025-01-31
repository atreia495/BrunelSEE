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
 * Listener for changes of HlaPhysicalEntity instances.
 */
public interface HlaPhysicalEntityManagerListener {

   /**
    * This method is called when a new HlaPhysicalEntity instance is discovered.
    * A discovered instance is initially within interest.
    *
    * @param physicalEntity the object which is discovered
    * @param timeStamp the time when the update was initiated
    */
    void hlaPhysicalEntityDiscovered(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaPhysicalEntity instance is initialized.
    * An instance is initialized when it has been discovered and all attribute marked with
    * <i>initialized</i> has a value.
    *
    * @param physicalEntity the object which is initialized
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaPhysicalEntityInitialized(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * This method is called when a HlaPhysicalEntity instance comes within interest.
    * A discovered instance is initially within interest, so this will not
    * be called at the time of discovery.
    *
    * @param physicalEntity the object which has come into interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaPhysicalEntityInInterest(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaPhysicalEntity instance goes out of interest.
    *
    * @param physicalEntity the object which has gone out of interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaPhysicalEntityOutOfInterest(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaPhysicalEntity instance is deleted.
    *
    * @param physicalEntity the object which is deleted
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaPhysicalEntityDeleted(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * An abstract adapter class that implements the HlaPhysicalEntityManagerListener interface with empty methods.
    * It might be used as a base class for a listener.
    */
    public abstract static class Adapter implements HlaPhysicalEntityManagerListener {
        public void hlaPhysicalEntityDiscovered(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp) {}
        public void hlaPhysicalEntityInitialized(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
        public void hlaPhysicalEntityInInterest(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp) {}
        public void hlaPhysicalEntityOutOfInterest(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp) {}
        public void hlaPhysicalEntityDeleted(HlaPhysicalEntity physicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
    }
}
