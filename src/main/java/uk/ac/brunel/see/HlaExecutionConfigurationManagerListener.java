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
 * Listener for changes of HlaExecutionConfiguration instances.
 */
public interface HlaExecutionConfigurationManagerListener {

   /**
    * This method is called when a new HlaExecutionConfiguration instance is discovered.
    * A discovered instance is initially within interest.
    *
    * @param executionConfiguration the object which is discovered
    * @param timeStamp the time when the update was initiated
    */
    void hlaExecutionConfigurationDiscovered(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaExecutionConfiguration instance is initialized.
    * An instance is initialized when it has been discovered and all attribute marked with
    * <i>initialized</i> has a value.
    *
    * @param executionConfiguration the object which is initialized
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaExecutionConfigurationInitialized(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * This method is called when a HlaExecutionConfiguration instance comes within interest.
    * A discovered instance is initially within interest, so this will not
    * be called at the time of discovery.
    *
    * @param executionConfiguration the object which has come into interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaExecutionConfigurationInInterest(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaExecutionConfiguration instance goes out of interest.
    *
    * @param executionConfiguration the object which has gone out of interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaExecutionConfigurationOutOfInterest(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaExecutionConfiguration instance is deleted.
    *
    * @param executionConfiguration the object which is deleted
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaExecutionConfigurationDeleted(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * An abstract adapter class that implements the HlaExecutionConfigurationManagerListener interface with empty methods.
    * It might be used as a base class for a listener.
    */
    public abstract static class Adapter implements HlaExecutionConfigurationManagerListener {
        public void hlaExecutionConfigurationDiscovered(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp) {}
        public void hlaExecutionConfigurationInitialized(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
        public void hlaExecutionConfigurationInInterest(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp) {}
        public void hlaExecutionConfigurationOutOfInterest(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp) {}
        public void hlaExecutionConfigurationDeleted(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
    }
}
