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
 * Listener for changes of HlaDynamicalEntity instances.
 */
public interface HlaDynamicalEntityManagerListener {

   /**
    * This method is called when a new HlaDynamicalEntity instance is discovered.
    * A discovered instance is initially within interest.
    *
    * @param dynamicalEntity the object which is discovered
    * @param timeStamp the time when the update was initiated
    */
    void hlaDynamicalEntityDiscovered(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaDynamicalEntity instance is initialized.
    * An instance is initialized when it has been discovered and all attribute marked with
    * <i>initialized</i> has a value.
    *
    * @param dynamicalEntity the object which is initialized
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaDynamicalEntityInitialized(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * This method is called when a HlaDynamicalEntity instance comes within interest.
    * A discovered instance is initially within interest, so this will not
    * be called at the time of discovery.
    *
    * @param dynamicalEntity the object which has come into interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaDynamicalEntityInInterest(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaDynamicalEntity instance goes out of interest.
    *
    * @param dynamicalEntity the object which has gone out of interest
    * @param timeStamp the time when the update was initiated
    */
    void hlaDynamicalEntityOutOfInterest(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp);

   /**
    * This method is called when a HlaDynamicalEntity instance is deleted.
    *
    * @param dynamicalEntity the object which is deleted
    * @param timeStamp the time when the update was initiated
    * @param logicalTime the logical time when the update was initiated
    */
    void hlaDynamicalEntityDeleted(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime);

   /**
    * An abstract adapter class that implements the HlaDynamicalEntityManagerListener interface with empty methods.
    * It might be used as a base class for a listener.
    */
    public abstract static class Adapter implements HlaDynamicalEntityManagerListener {
        public void hlaDynamicalEntityDiscovered(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp) {}
        public void hlaDynamicalEntityInitialized(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
        public void hlaDynamicalEntityInInterest(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp) {}
        public void hlaDynamicalEntityOutOfInterest(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp) {}
        public void hlaDynamicalEntityDeleted(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {}
    }
}
