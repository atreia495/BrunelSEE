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
 * Listener for changes of HlaPhysicalInterface instances.
 */
public interface HlaPhysicalInterfaceManagerListener {

   /**
    * This method is called when a new HlaPhysicalInterface instance is discovered.
    * A discovered instance is initially within interest.
    *
    * @param physicalInterface the object which is discovered
    * @param timeStamp the time when the update was initiated
    */
    void hlaPhysicalInterfaceDiscovered(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaPhysicalInterface instance is initialized.
    * An instance is initialized when it has been discovered and all attribute marked with
    * <i>initialized</i> has a value.
    *
    * @param physicalInterface the object which is initialized
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaPhysicalInterfaceInitialized(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * This method is called when a HlaPhysicalInterface instance comes within interest.
    * A discovered instance is initially within interest, so this will not
    * be called at the time of discovery.
    *
    * @param physicalInterface the object which has come into interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaPhysicalInterfaceInInterest(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaPhysicalInterface instance goes out of interest.
    *
    * @param physicalInterface the object which has gone out of interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaPhysicalInterfaceOutOfInterest(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaPhysicalInterface instance is deleted.
    *
    * @param physicalInterface the object which is deleted
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaPhysicalInterfaceDeleted(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * An abstract adapter class that implements the HlaPhysicalInterfaceManagerListener interface with empty methods.
    * It might be used as a base class for a listener.
    */
    public abstract static class Adapter implements HlaPhysicalInterfaceManagerListener {
        public void hlaPhysicalInterfaceDiscovered(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp) {}
        public void hlaPhysicalInterfaceInitialized(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
        public void hlaPhysicalInterfaceInInterest(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp) {}
        public void hlaPhysicalInterfaceOutOfInterest(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp) {}
        public void hlaPhysicalInterfaceDeleted(HlaPhysicalInterface physicalInterface, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
    }
}
