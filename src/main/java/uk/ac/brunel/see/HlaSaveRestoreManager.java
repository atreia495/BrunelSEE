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

import uk.ac.brunel.see.exceptions.*;

import java.util.concurrent.TimeUnit;

/**
 * Manager used to manage saving and restoring the federate.
 * <p>
 * Limitations in the current implementation:
 * <ul>
 * <li>Instances can not be saved. If the <code>HlaWorld</code> has any
 *     local or remote instances the save will fail.</li>
 * <li>Save and restore can only be performed within the same federation,
 *     it is not possible to save, resign, join a new federation and then restore.</li>
 * </ul>
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaSaveRestoreManager {

  /**
   * Request that the federation should save its state.
   * The actual save should be performed in the <code>performFederateSave</code> method
   * in the <code>HlaSaveRestoreListener</code>.
   *
   * @param label label used to identify a saved state
   *
   * @throws HlaNotConnectedException if the federate is not connected
   * @throws HlaRtiException on exception from the RTI
   * @throws HlaSaveInProgressException if a federation save is in progress
   * @throws HlaRestoreInProgressException if a federation restore is in progress
   */
   void requestFederationSave(String label) throws HlaNotConnectedException, HlaRtiException,
                                                   HlaSaveInProgressException, HlaRestoreInProgressException;

  /**
   * Request that the federation should save its state at the supplied logical time.
   * The actual save should be performed in the <code>performFederateSave</code> method
   * in the <code>HlaSaveRestoreListener</code>.
   *
   * @param label label used to identify a saved state
   * @param saveTime logical time when the federation save should happen
   *
   * @throws HlaNotConnectedException if the federate is not connected
   * @throws HlaRtiException on exception from the RTI
   * @throws HlaInvalidLogicalTimeException if the logical time is invalid
   * @throws HlaSaveInProgressException if a federation save is in progress
   * @throws HlaRestoreInProgressException if a federation restore is in progress
   */
   void requestFederationSave(String label, HlaLogicalTime saveTime) throws HlaNotConnectedException, HlaRtiException, HlaInvalidLogicalTimeException,
                                                                            HlaSaveInProgressException, HlaRestoreInProgressException;

  /**
   * Request that the federation should restore its state to a previously saved state.
   * The actual restore should be performed in the <code>performFederateRestore</code> method
   * in the <code>HlaSaveRestoreListener</code>.
   *
   * @param label label used to identify a saved state
   *
   * @return <code>true</code> if the request was successful
   *
   * @throws HlaNotConnectedException if the federate is not connected
   * @throws HlaRtiException on exception from the RTI
   * @throws HlaSaveInProgressException if a federation save is in progress
   * @throws HlaRestoreInProgressException if a federation restore is in progress
   */
   boolean requestFederationRestore(String label) throws HlaNotConnectedException, HlaRtiException,
                                                         HlaSaveInProgressException, HlaRestoreInProgressException;

  /**
   * Causes the current thread to wait until the granted state has been achieved
   * after the federation has been restored, unless the thread is interrupted,
   * or the specified waiting time elapses.
   * This method should only be called after the restore has been completed.
   *
   * @param timeout the maximum time to wait
   * @param unit the time unit of the <code>timeout</code> argument
   *
   * @return <code>true</code> if the granted state has been achieved
   *
   * @throws InterruptedException if the current thread is interrupted while waiting
   */
   boolean awaitGrantedStateAfterRestore(long timeout, TimeUnit unit) throws InterruptedException;

  /**
   * Sets the save and restore listener. The default listener is initially used.
   * The default listener will reject save and restore requests by returning <code>false</code> from
   * <code>performFederateSave</code> and <code>performFederateRestore</code>.
   *
   * @param listener listener to use, if <code>null</code> the default listener will be used
   */
   void setHlaSaveRestoreListener(HlaSaveRestoreListener listener);

   /**
    * Check if save is currently in progress. Note that calls may throw the
    * <code>HlaSaveInProgressException</code> even if this method return <code>false</code>
    * since a save operation can be initiated by another federate at any time.
    *
    * @return <code>true</code> if save is currently in progress
    */
   boolean isSaveInProgress();

   /**
    * Check if restore is currently in progress. Note that calls may throw the
    * <code>HlaRestoreInProgressException</code> even if this method return <code>false</code>
    * since a restore operation can be initiated by another federate at any time.
    *
    * @return <code>true</code> if restore is currently in progress
    */
   boolean isRestoreInProgress();
}
