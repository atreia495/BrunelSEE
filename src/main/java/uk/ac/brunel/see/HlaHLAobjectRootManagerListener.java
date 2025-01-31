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
 * Listener for changes of HlaHLAobjectRoot instances.
 */
public interface HlaHLAobjectRootManagerListener {

   /**
    * This method is called when a new HlaHLAobjectRoot instance is discovered.
    * A discovered instance is initially within interest.
    *
    * @param hLAobjectRoot the object which is discovered
    * @param timeStamp the time when the update was initiated
    */
    void hlaHLAobjectRootDiscovered(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaHLAobjectRoot instance is initialized.
    * An instance is initialized when it has been discovered and all attribute marked with
    * <i>initialized</i> has a value.
    *
    * @param hLAobjectRoot the object which is initialized
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaHLAobjectRootInitialized(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * This method is called when a HlaHLAobjectRoot instance comes within interest.
    * A discovered instance is initially within interest, so this will not
    * be called at the time of discovery.
    *
    * @param hLAobjectRoot the object which has come into interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaHLAobjectRootInInterest(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaHLAobjectRoot instance goes out of interest.
    *
    * @param hLAobjectRoot the object which has gone out of interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaHLAobjectRootOutOfInterest(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaHLAobjectRoot instance is deleted.
    *
    * @param hLAobjectRoot the object which is deleted
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaHLAobjectRootDeleted(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * An abstract adapter class that implements the HlaHLAobjectRootManagerListener interface with empty methods.
    * It might be used as a base class for a listener.
    */
    public abstract static class Adapter implements HlaHLAobjectRootManagerListener {
        public void hlaHLAobjectRootDiscovered(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp) {}
        public void hlaHLAobjectRootInitialized(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
        public void hlaHLAobjectRootInInterest(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp) {}
        public void hlaHLAobjectRootOutOfInterest(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp) {}
        public void hlaHLAobjectRootDeleted(HlaHLAobjectRoot hLAobjectRoot, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
    }
}
