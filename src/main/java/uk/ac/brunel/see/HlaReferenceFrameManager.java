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
import java.util.List;


/**
 * Manager used to manage HlaReferenceFrames.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaReferenceFrameManager {

   /**
    * Gets a list of all HlaReferenceFrames within interest, both local and remote.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of HlaReferenceFrames
    */
    List<HlaReferenceFrame> getHlaReferenceFrames();

   /**
    * Gets a list of all HlaReferenceFrames, both local and remote.
    * HlaReferenceFrames not within interest are also included.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of all HlaReferenceFrames
    */
    List<HlaReferenceFrame> getAllHlaReferenceFrames();

   /**
    * Gets a list of local HlaReferenceFrames within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of local HlaReferenceFrames
    */
    List<HlaReferenceFrame> getLocalHlaReferenceFrames();

   /**
    * Gets a list of remote HlaReferenceFrames within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of remote HlaReferenceFrames
    */
    List<HlaReferenceFrame> getRemoteHlaReferenceFrames();

   /**
    * Find a HlaReferenceFrame with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> of the HlaReferenceFrame to find
    *
    * @return the specified HlaReferenceFrame, or <code>null</code> if not found.
    */
   HlaReferenceFrame getReferenceFrameByHlaInstanceName(String hlaInstanceName);

   /**
    * Find a HlaReferenceFrame with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param encodedHlaInstanceHandle the encoded <code>HLA instance handle</code> of the HlaReferenceFrame to find
    *
    * @return the specified HlaReferenceFrame, or <code>null</code> if not found.
    */
   HlaReferenceFrame getReferenceFrameByHlaInstanceHandle(byte[] encodedHlaInstanceHandle);

   /**
    * Creates a new HlaReferenceFrame, and sets all <i>Create</i> attributes.
    *
    *
    * @return Returns a new local HlaReferenceFrame.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaReferenceFrame createLocalHlaReferenceFrame(
    ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Creates a new HlaReferenceFrame with a specified <code>HLA instance name</code>, and sets all
    * <i>Create</i> attributes.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> to register for this instance
    *
    * @return Returns a new local HlaReferenceFrame.
    *
    * @throws HlaIllegalInstanceNameException if the name is illegal
    * @throws HlaInstanceNameInUseException if the name is already in use
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaReferenceFrame createLocalHlaReferenceFrame(
       String hlaInstanceName
    ) throws HlaIllegalInstanceNameException, HlaInstanceNameInUseException,
             HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Deletes a local HlaReferenceFrame and removes it from the federation.
    *
    * @param referenceFrame The HlaReferenceFrame to delete
    *
    * @return the HlaReferenceFrame deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame referenceFrame)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaReferenceFrame and removes it from the federation with a specified timestamp
    *
    * @param referenceFrame The HlaReferenceFrame to delete
    * @param timestamp timestamp when the instance was removed.
    *
    * @return the HlaReferenceFrame deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame referenceFrame, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaReferenceFrame and removes it from the federation at a specified logical time.
    *
    * @param referenceFrame The HlaReferenceFrame to delete
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaReferenceFrame deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame referenceFrame, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaReferenceFrame and removes it from the federation with a specified timestamp at a specified logical time.
    *
    * @param referenceFrame The HlaReferenceFrame to delete
    * @param timestamp timestamp when the instance was removed.
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaReferenceFrame deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaReferenceFrame deleteLocalHlaReferenceFrame(HlaReferenceFrame referenceFrame, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

    /**
     * Add an HlaReferenceFrame manager listener.
     *
     * @param managerListener listener to add
     */
    void addHlaReferenceFrameManagerListener(HlaReferenceFrameManagerListener managerListener);

    /**
     * Remove an HlaReferenceFrame manager listener.
     *
     * @param managerListener listener to remove
     */
    void removeHlaReferenceFrameManagerListener(HlaReferenceFrameManagerListener managerListener);

    /**
     * Add a listener that will automatically be added to all instances of HlaReferenceFrame (Both local and remote).
     * The listener is notified when any attribute of an instance of HlaReferenceFrame is updated.
     * The listener is also called when an interaction is sent to an instance of HlaReferenceFrame.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param listener The listener to add.
     */
    void addHlaReferenceFrameDefaultInstanceListener(HlaReferenceFrameListener listener);

    /**
     * Remove a listener from the set of listeners that are automatically added to new instances of HlaReferenceFrame.
     * Note: The listener will not be removed from already existing instances of HlaReferenceFrame.
     * This method is idempotent.
     *
     * @param listener The listener to remove.
     */
    void removeHlaReferenceFrameDefaultInstanceListener(HlaReferenceFrameListener listener);

    /**
     * Add a valueListener that will automatically be added to all instances of HlaReferenceFrame (Both local and remote).
     * The  Valuelistener is notified when any attribute of an instance of HlaReferenceFrame is updated.
     * Note that the attribute values that are notified are not guaranteed to
     * be the latest available values for that attribute.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to add.
     */
    void addHlaReferenceFrameDefaultInstanceValueListener(HlaReferenceFrameValueListener valueListener);

    /**
     * Remove a valueListener from the set of listeners that are automatically added to new instances of HlaReferenceFrame.
     * Note: The valueListener will not be removed from already existing instances of HlaReferenceFrame.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to remove.
     */
    void removeHlaReferenceFrameDefaultInstanceValueListener(HlaReferenceFrameValueListener valueListener);

    /**
     * Enables or disables the HlaReferenceFrame manager.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaReferenceFrame manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     *
     * @param enabled <code>true</code> to enable the HlaReferenceFrame manager, otherwise <code>false</code>
     */
    void setEnabled(boolean enabled);

    /**
     * Enables the HlaReferenceFrame manager if it is available in the FOM at runtime.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaReferenceFrame manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     */
    void setEnabledIfAvailableInFom();

    /**
     * Check if the HlaReferenceFrame manager is actually enabled when connected.
     * An HlaReferenceFrame manager may be disabled with the <code>setEnabled(false)</code> function, or it may be configured
     * as <i>if available in the FOM</i> by the <code>setEnabledIfAvailableInFom()</code> function and
     * then not available in the FOM at runtime.
     * <p>
     * This method should only be used when connected, it will always return <code>false</code>
     * when not connected.
     *
     * @return <code>true</code> if connected and the HlaReferenceFrame manager is enabled.
     */
    boolean isEnabled();
}

