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
 * Listener for changes of HlaReferenceFrame instances.
 */
public interface HlaReferenceFrameManagerListener {

   /**
    * This method is called when a new HlaReferenceFrame instance is discovered.
    * A discovered instance is initially within interest.
    *
    * @param referenceFrame the object which is discovered
    * @param timeStamp the time when the update was initiated
    */
    void hlaReferenceFrameDiscovered(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaReferenceFrame instance is initialized.
    * An instance is initialized when it has been discovered and all attribute marked with
    * <i>initialized</i> has a value.
    *
    * @param referenceFrame the object which is initialized
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaReferenceFrameInitialized(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * This method is called when a HlaReferenceFrame instance comes within interest.
    * A discovered instance is initially within interest, so this will not
    * be called at the time of discovery.
    *
    * @param referenceFrame the object which has come into interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaReferenceFrameInInterest(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaReferenceFrame instance goes out of interest.
    *
    * @param referenceFrame the object which has gone out of interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaReferenceFrameOutOfInterest(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaReferenceFrame instance is deleted.
    *
    * @param referenceFrame the object which is deleted
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaReferenceFrameDeleted(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * An abstract adapter class that implements the HlaReferenceFrameManagerListener interface with empty methods.
    * It might be used as a base class for a listener.
    */
    public abstract static class Adapter implements HlaReferenceFrameManagerListener {
        public void hlaReferenceFrameDiscovered(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp) {}
        public void hlaReferenceFrameInitialized(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
        public void hlaReferenceFrameInInterest(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp) {}
        public void hlaReferenceFrameOutOfInterest(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp) {}
        public void hlaReferenceFrameDeleted(HlaReferenceFrame referenceFrame, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
    }
}
