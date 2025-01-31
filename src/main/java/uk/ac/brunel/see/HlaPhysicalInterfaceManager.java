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
 * Manager used to manage HlaPhysicalInterfaces.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaPhysicalInterfaceManager {

   /**
    * Gets a list of all HlaPhysicalInterfaces within interest, both local and remote.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of HlaPhysicalInterfaces
    */
    List<HlaPhysicalInterface> getHlaPhysicalInterfaces();

   /**
    * Gets a list of all HlaPhysicalInterfaces, both local and remote.
    * HlaPhysicalInterfaces not within interest are also included.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of all HlaPhysicalInterfaces
    */
    List<HlaPhysicalInterface> getAllHlaPhysicalInterfaces();

   /**
    * Gets a list of local HlaPhysicalInterfaces within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of local HlaPhysicalInterfaces
    */
    List<HlaPhysicalInterface> getLocalHlaPhysicalInterfaces();

   /**
    * Gets a list of remote HlaPhysicalInterfaces within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of remote HlaPhysicalInterfaces
    */
    List<HlaPhysicalInterface> getRemoteHlaPhysicalInterfaces();

   /**
    * Find a HlaPhysicalInterface with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> of the HlaPhysicalInterface to find
    *
    * @return the specified HlaPhysicalInterface, or <code>null</code> if not found.
    */
   HlaPhysicalInterface getPhysicalInterfaceByHlaInstanceName(String hlaInstanceName);

   /**
    * Find a HlaPhysicalInterface with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param encodedHlaInstanceHandle the encoded <code>HLA instance handle</code> of the HlaPhysicalInterface to find
    *
    * @return the specified HlaPhysicalInterface, or <code>null</code> if not found.
    */
   HlaPhysicalInterface getPhysicalInterfaceByHlaInstanceHandle(byte[] encodedHlaInstanceHandle);

   /**
    * Creates a new HlaPhysicalInterface, and sets all <i>Create</i> attributes.
    *
    *
    * @return Returns a new local HlaPhysicalInterface.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalInterface createLocalHlaPhysicalInterface(
    ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Creates a new HlaPhysicalInterface with a specified <code>HLA instance name</code>, and sets all
    * <i>Create</i> attributes.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> to register for this instance
    *
    * @return Returns a new local HlaPhysicalInterface.
    *
    * @throws HlaIllegalInstanceNameException if the name is illegal
    * @throws HlaInstanceNameInUseException if the name is already in use
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalInterface createLocalHlaPhysicalInterface(
       String hlaInstanceName
    ) throws HlaIllegalInstanceNameException, HlaInstanceNameInUseException,
             HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Deletes a local HlaPhysicalInterface and removes it from the federation.
    *
    * @param physicalInterface The HlaPhysicalInterface to delete
    *
    * @return the HlaPhysicalInterface deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalInterface deleteLocalHlaPhysicalInterface(HlaPhysicalInterface physicalInterface)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaPhysicalInterface and removes it from the federation with a specified timestamp
    *
    * @param physicalInterface The HlaPhysicalInterface to delete
    * @param timestamp timestamp when the instance was removed.
    *
    * @return the HlaPhysicalInterface deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalInterface deleteLocalHlaPhysicalInterface(HlaPhysicalInterface physicalInterface, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaPhysicalInterface and removes it from the federation at a specified logical time.
    *
    * @param physicalInterface The HlaPhysicalInterface to delete
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaPhysicalInterface deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalInterface deleteLocalHlaPhysicalInterface(HlaPhysicalInterface physicalInterface, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaPhysicalInterface and removes it from the federation with a specified timestamp at a specified logical time.
    *
    * @param physicalInterface The HlaPhysicalInterface to delete
    * @param timestamp timestamp when the instance was removed.
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaPhysicalInterface deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalInterface deleteLocalHlaPhysicalInterface(HlaPhysicalInterface physicalInterface, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

    /**
     * Add an HlaPhysicalInterface manager listener.
     *
     * @param managerListener listener to add
     */
    void addHlaPhysicalInterfaceManagerListener(HlaPhysicalInterfaceManagerListener managerListener);

    /**
     * Remove an HlaPhysicalInterface manager listener.
     *
     * @param managerListener listener to remove
     */
    void removeHlaPhysicalInterfaceManagerListener(HlaPhysicalInterfaceManagerListener managerListener);

    /**
     * Add a listener that will automatically be added to all instances of HlaPhysicalInterface (Both local and remote).
     * The listener is notified when any attribute of an instance of HlaPhysicalInterface is updated.
     * The listener is also called when an interaction is sent to an instance of HlaPhysicalInterface.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param listener The listener to add.
     */
    void addHlaPhysicalInterfaceDefaultInstanceListener(HlaPhysicalInterfaceListener listener);

    /**
     * Remove a listener from the set of listeners that are automatically added to new instances of HlaPhysicalInterface.
     * Note: The listener will not be removed from already existing instances of HlaPhysicalInterface.
     * This method is idempotent.
     *
     * @param listener The listener to remove.
     */
    void removeHlaPhysicalInterfaceDefaultInstanceListener(HlaPhysicalInterfaceListener listener);

    /**
     * Add a valueListener that will automatically be added to all instances of HlaPhysicalInterface (Both local and remote).
     * The  Valuelistener is notified when any attribute of an instance of HlaPhysicalInterface is updated.
     * Note that the attribute values that are notified are not guaranteed to
     * be the latest available values for that attribute.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to add.
     */
    void addHlaPhysicalInterfaceDefaultInstanceValueListener(HlaPhysicalInterfaceValueListener valueListener);

    /**
     * Remove a valueListener from the set of listeners that are automatically added to new instances of HlaPhysicalInterface.
     * Note: The valueListener will not be removed from already existing instances of HlaPhysicalInterface.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to remove.
     */
    void removeHlaPhysicalInterfaceDefaultInstanceValueListener(HlaPhysicalInterfaceValueListener valueListener);

    /**
     * Enables or disables the HlaPhysicalInterface manager.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaPhysicalInterface manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     *
     * @param enabled <code>true</code> to enable the HlaPhysicalInterface manager, otherwise <code>false</code>
     */
    void setEnabled(boolean enabled);

    /**
     * Enables the HlaPhysicalInterface manager if it is available in the FOM at runtime.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaPhysicalInterface manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     */
    void setEnabledIfAvailableInFom();

    /**
     * Check if the HlaPhysicalInterface manager is actually enabled when connected.
     * An HlaPhysicalInterface manager may be disabled with the <code>setEnabled(false)</code> function, or it may be configured
     * as <i>if available in the FOM</i> by the <code>setEnabledIfAvailableInFom()</code> function and
     * then not available in the FOM at runtime.
     * <p>
     * This method should only be used when connected, it will always return <code>false</code>
     * when not connected.
     *
     * @return <code>true</code> if connected and the HlaPhysicalInterface manager is enabled.
     */
    boolean isEnabled();
}

