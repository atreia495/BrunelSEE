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
 * Manager used to manage HlaPhysicalEntitys.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaPhysicalEntityManager {

   /**
    * Gets a list of all HlaPhysicalEntitys within interest, both local and remote.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of HlaPhysicalEntitys
    */
    List<HlaPhysicalEntity> getHlaPhysicalEntitys();

   /**
    * Gets a list of all HlaPhysicalEntitys, both local and remote.
    * HlaPhysicalEntitys not within interest are also included.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of all HlaPhysicalEntitys
    */
    List<HlaPhysicalEntity> getAllHlaPhysicalEntitys();

   /**
    * Gets a list of local HlaPhysicalEntitys within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of local HlaPhysicalEntitys
    */
    List<HlaPhysicalEntity> getLocalHlaPhysicalEntitys();

   /**
    * Gets a list of remote HlaPhysicalEntitys within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of remote HlaPhysicalEntitys
    */
    List<HlaPhysicalEntity> getRemoteHlaPhysicalEntitys();

   /**
    * Find a HlaPhysicalEntity with the given <code>name</code>.
    * If more than one HlaPhysicalEntity with the given <code>name</code> is present, one of
    * them is returned, it is undefined which. If none is found, <code>null</code> is returned.
    * <p>
    * name was selected as a <i>Lookup</i> attribute.
    *
    * @param name The name that looks up which HlaPhysicalEntity to return.
    *
    * @return an HlaPhysicalEntity, or <code>null</code> if not found.
    */
    HlaPhysicalEntity getPhysicalEntityByName(String name);

   /**
    * Find a HlaPhysicalEntity with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> of the HlaPhysicalEntity to find
    *
    * @return the specified HlaPhysicalEntity, or <code>null</code> if not found.
    */
   HlaPhysicalEntity getPhysicalEntityByHlaInstanceName(String hlaInstanceName);

   /**
    * Find a HlaPhysicalEntity with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param encodedHlaInstanceHandle the encoded <code>HLA instance handle</code> of the HlaPhysicalEntity to find
    *
    * @return the specified HlaPhysicalEntity, or <code>null</code> if not found.
    */
   HlaPhysicalEntity getPhysicalEntityByHlaInstanceHandle(byte[] encodedHlaInstanceHandle);

   /**
    * Creates a new HlaPhysicalEntity, and sets all <i>Create</i> attributes.
    *
    * @param name represents the <code>name</code> from the FOM.
    *        <br> Description from the FOM: A non-empty string that identifies the entity. Each entity instance in the federation must have a unique name.
    *        <br>Description of the data type from the FOM: 
    * @param parentReferenceFrame represents the <code>parent_reference_frame</code> from the FOM.
    *        <br> Description from the FOM: The non-empty string that identifies the reference frame with respect to which the kinematic state attributes of this entity are calculated. This string must exactly match the name of some ReferenceFrame instance in the federation.
    *        <br>Description of the data type from the FOM: 
    *
    * @return Returns a new local HlaPhysicalEntity.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalEntity createLocalHlaPhysicalEntity(
       String name,
       String parentReferenceFrame
    ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Creates a new HlaPhysicalEntity with a specified <code>HLA instance name</code>, and sets all
    * <i>Create</i> attributes.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> to register for this instance
    * @param name represents the <code>name</code> from the FOM.
    *        <br>Description from the FOM: A non-empty string that identifies the entity. Each entity instance in the federation must have a unique name.
    *        <br>Description of the data type from the FOM: 
    * @param parentReferenceFrame represents the <code>parent_reference_frame</code> from the FOM.
    *        <br>Description from the FOM: The non-empty string that identifies the reference frame with respect to which the kinematic state attributes of this entity are calculated. This string must exactly match the name of some ReferenceFrame instance in the federation.
    *        <br>Description of the data type from the FOM: 
    *
    * @return Returns a new local HlaPhysicalEntity.
    *
    * @throws HlaIllegalInstanceNameException if the name is illegal
    * @throws HlaInstanceNameInUseException if the name is already in use
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalEntity createLocalHlaPhysicalEntity(
       String hlaInstanceName,
       String name,
       String parentReferenceFrame
    ) throws HlaIllegalInstanceNameException, HlaInstanceNameInUseException,
             HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Deletes a local HlaPhysicalEntity and removes it from the federation.
    *
    * @param physicalEntity The HlaPhysicalEntity to delete
    *
    * @return the HlaPhysicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalEntity deleteLocalHlaPhysicalEntity(HlaPhysicalEntity physicalEntity)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaPhysicalEntity and removes it from the federation with a specified timestamp
    *
    * @param physicalEntity The HlaPhysicalEntity to delete
    * @param timestamp timestamp when the instance was removed.
    *
    * @return the HlaPhysicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalEntity deleteLocalHlaPhysicalEntity(HlaPhysicalEntity physicalEntity, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaPhysicalEntity and removes it from the federation at a specified logical time.
    *
    * @param physicalEntity The HlaPhysicalEntity to delete
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaPhysicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalEntity deleteLocalHlaPhysicalEntity(HlaPhysicalEntity physicalEntity, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaPhysicalEntity and removes it from the federation with a specified timestamp at a specified logical time.
    *
    * @param physicalEntity The HlaPhysicalEntity to delete
    * @param timestamp timestamp when the instance was removed.
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaPhysicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaPhysicalEntity deleteLocalHlaPhysicalEntity(HlaPhysicalEntity physicalEntity, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

    /**
     * Add an HlaPhysicalEntity manager listener.
     *
     * @param managerListener listener to add
     */
    void addHlaPhysicalEntityManagerListener(HlaPhysicalEntityManagerListener managerListener);

    /**
     * Remove an HlaPhysicalEntity manager listener.
     *
     * @param managerListener listener to remove
     */
    void removeHlaPhysicalEntityManagerListener(HlaPhysicalEntityManagerListener managerListener);

    /**
     * Add a listener that will automatically be added to all instances of HlaPhysicalEntity (Both local and remote).
     * The listener is notified when any attribute of an instance of HlaPhysicalEntity is updated.
     * The listener is also called when an interaction is sent to an instance of HlaPhysicalEntity.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param listener The listener to add.
     */
    void addHlaPhysicalEntityDefaultInstanceListener(HlaPhysicalEntityListener listener);

    /**
     * Remove a listener from the set of listeners that are automatically added to new instances of HlaPhysicalEntity.
     * Note: The listener will not be removed from already existing instances of HlaPhysicalEntity.
     * This method is idempotent.
     *
     * @param listener The listener to remove.
     */
    void removeHlaPhysicalEntityDefaultInstanceListener(HlaPhysicalEntityListener listener);

    /**
     * Add a valueListener that will automatically be added to all instances of HlaPhysicalEntity (Both local and remote).
     * The  Valuelistener is notified when any attribute of an instance of HlaPhysicalEntity is updated.
     * Note that the attribute values that are notified are not guaranteed to
     * be the latest available values for that attribute.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to add.
     */
    void addHlaPhysicalEntityDefaultInstanceValueListener(HlaPhysicalEntityValueListener valueListener);

    /**
     * Remove a valueListener from the set of listeners that are automatically added to new instances of HlaPhysicalEntity.
     * Note: The valueListener will not be removed from already existing instances of HlaPhysicalEntity.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to remove.
     */
    void removeHlaPhysicalEntityDefaultInstanceValueListener(HlaPhysicalEntityValueListener valueListener);

    /**
     * Enables or disables the HlaPhysicalEntity manager.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaPhysicalEntity manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     *
     * @param enabled <code>true</code> to enable the HlaPhysicalEntity manager, otherwise <code>false</code>
     */
    void setEnabled(boolean enabled);

    /**
     * Enables the HlaPhysicalEntity manager if it is available in the FOM at runtime.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaPhysicalEntity manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     */
    void setEnabledIfAvailableInFom();

    /**
     * Check if the HlaPhysicalEntity manager is actually enabled when connected.
     * An HlaPhysicalEntity manager may be disabled with the <code>setEnabled(false)</code> function, or it may be configured
     * as <i>if available in the FOM</i> by the <code>setEnabledIfAvailableInFom()</code> function and
     * then not available in the FOM at runtime.
     * <p>
     * This method should only be used when connected, it will always return <code>false</code>
     * when not connected.
     *
     * @return <code>true</code> if connected and the HlaPhysicalEntity manager is enabled.
     */
    boolean isEnabled();
}

