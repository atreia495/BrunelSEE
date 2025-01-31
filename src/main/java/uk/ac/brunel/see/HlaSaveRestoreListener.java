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
 * Listeners for saving and restoring the federation.
 */
public interface HlaSaveRestoreListener {

  /**
   * Enum used to describe save failure reasons.
   */
   public enum HlaSaveFailure {
      /** The RTI was unable to save */
      RTI_UNABLE_TO_SAVE,
      /** A federate reported failure */
      FEDERATE_REPORTED_FAILURE,
      /** A federate resigned during save */
      FEDERATE_RESIGNED,
      /** The RTI detected a failure */
      RTI_DETECTED_FAILURE,
      /** The requested save time can not be honored */
      SAVE_TIME_CANNOT_BE_HONORED,
      /** The save was aborted */
      SAVE_ABORTED,
      /** Unknown */
      UNKNOWN
   }

  /**
   * Enum used to describe restore failure reasons.
   */
   public enum HlaRestoreFailure {
      /** The RTI was unable to restore */
      RTI_UNABLE_TO_RESTORE,
      /** A federate reported failure */
      FEDERATE_REPORTED_FAILURE,
      /** A federate resigned during restore */
      FEDERATE_RESIGNED,
      /** The RTI detected a failure */
      RTI_DETECTED_FAILURE,
      /** The restore was aborted */
      RESTORE_ABORTED,
      /** Unknown */
      UNKNOWN
   }

 /**
   * The federate should save its state, if <code>true</code> is returned the
   * HlaWorld will save its internal state.
   *
   * @param label label used to identify the save state
   * @param saveTime logical time when the federation should be saved
   *
   * @return <code>true</code> if the save was successful
   */
   boolean performFederateSave(String label, HlaLogicalTime saveTime);

  /**
   * All federates in the federation has successfully saved its respective state.
   */
   void federationSaved();

  /**
   * Federation was not saved, one ore more federates could not save its state.
   *
   * @param saveFailureReason reason for the failure
   */
   void federationNotSaved(HlaSaveFailure saveFailureReason);

  /**
   * The federate is about to restore its state, if <code>true</code> is returned the
   * HlaWorld will restore its internal state.
   *
   * @param label label used to identify the restore state
   *
   * @return <code>true</code> if the restore was successful
   */
   boolean performFederateRestore(String label);

  /**
   * All federates in the federation has successfully restored its respective state.
   * <p>
   * If the federate was not in the advancing time state and the save/restore was
   * time managed, the federate might need to wait until the granted time state is
   * reached.
   *
   * @param restoredTime logical time when the federation was saved
   * @param wasAdvancing <code>true</code> if the local federate was in the
   *                     advancing time state when the restore was performed
   *
   * @see HlaSaveRestoreManager#awaitGrantedStateAfterRestore
   */
   void federationRestored(HlaLogicalTime restoredTime, boolean wasAdvancing);

  /**
   * Federation was not restored, one or more federates could not restore its state.
   * The federates that failed to restore its state are now in an <b>undefined</b> state,
   * this may affect the entire federation.
   * <p>
   * If the federate was not in the advancing time state and the save/restore was
   * time managed, the federate might need to wait until the granted time state is
   * reached.
   *
   * @param restoreFailureReason reason for the failure
   * @param localRestoreWasSuccessful <code>true</code> if the local federate restore was successful
   * @param localRestoredTime logical time when the local federate was restored
   * @param wasAdvancing <code>true</code> if the local federate was in the
   *                     advancing time state when the restore was performed
   *
   * @see HlaSaveRestoreManager#awaitGrantedStateAfterRestore
   */
   void federationNotRestored(HlaRestoreFailure restoreFailureReason, boolean localRestoreWasSuccessful, HlaLogicalTime localRestoredTime, boolean wasAdvancing);

  /**
   * An abstract adapter class that implements the <code>HlaSaveRestoreListener</code> interface with empty methods.
   * Save and restore requests will be rejected by returning <code>false</code> from
   * <code>performFederateSave</code> and <code>performFederateRestore</code>.
   * <p>
   * It can be used as a base class for a listener.
   */
   public abstract static class Adapter implements HlaSaveRestoreListener {
       public boolean performFederateSave(String label, HlaLogicalTime saveTime) { return false; }

       public void federationSaved() {}

       public void federationNotSaved(HlaSaveFailure saveFailureReason) {}

       public boolean performFederateRestore(String label) { return false; }

       public void federationRestored(HlaLogicalTime restoredTime, boolean wasAdvancing) {}

       public void federationNotRestored(HlaRestoreFailure restoreFailureReason, boolean localRestoreWasSuccessful, HlaLogicalTime localRestoredTime, boolean wasAdvancing) {}
   }
}
