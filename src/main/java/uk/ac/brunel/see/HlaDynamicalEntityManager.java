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
 * Manager used to manage HlaDynamicalEntitys.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaDynamicalEntityManager {

   /**
    * Gets a list of all HlaDynamicalEntitys within interest, both local and remote.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of HlaDynamicalEntitys
    */
    List<HlaDynamicalEntity> getHlaDynamicalEntitys();

   /**
    * Gets a list of all HlaDynamicalEntitys, both local and remote.
    * HlaDynamicalEntitys not within interest are also included.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of all HlaDynamicalEntitys
    */
    List<HlaDynamicalEntity> getAllHlaDynamicalEntitys();

   /**
    * Gets a list of local HlaDynamicalEntitys within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of local HlaDynamicalEntitys
    */
    List<HlaDynamicalEntity> getLocalHlaDynamicalEntitys();

   /**
    * Gets a list of remote HlaDynamicalEntitys within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of remote HlaDynamicalEntitys
    */
    List<HlaDynamicalEntity> getRemoteHlaDynamicalEntitys();

   /**
    * Find a HlaDynamicalEntity with the given <code>name</code>.
    * If more than one HlaDynamicalEntity with the given <code>name</code> is present, one of
    * them is returned, it is undefined which. If none is found, <code>null</code> is returned.
    * <p>
    * name was selected as a <i>Lookup</i> attribute.
    *
    * @param name The name that looks up which HlaDynamicalEntity to return.
    *
    * @return an HlaDynamicalEntity, or <code>null</code> if not found.
    */
    HlaDynamicalEntity getDynamicalEntityByName(String name);

   /**
    * Find a HlaDynamicalEntity with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> of the HlaDynamicalEntity to find
    *
    * @return the specified HlaDynamicalEntity, or <code>null</code> if not found.
    */
   HlaDynamicalEntity getDynamicalEntityByHlaInstanceName(String hlaInstanceName);

   /**
    * Find a HlaDynamicalEntity with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param encodedHlaInstanceHandle the encoded <code>HLA instance handle</code> of the HlaDynamicalEntity to find
    *
    * @return the specified HlaDynamicalEntity, or <code>null</code> if not found.
    */
   HlaDynamicalEntity getDynamicalEntityByHlaInstanceHandle(byte[] encodedHlaInstanceHandle);

   /**
    * Creates a new HlaDynamicalEntity, and sets all <i>Create</i> attributes.
    *
    * @param name represents the <code>name</code> from the FOM.
    *        <br> Description from the FOM: A non-empty string that identifies the entity. Each entity instance in the federation must have a unique name.
    *        <br>Description of the data type from the FOM: 
    * @param parentReferenceFrame represents the <code>parent_reference_frame</code> from the FOM.
    *        <br> Description from the FOM: The non-empty string that identifies the reference frame with respect to which the kinematic state attributes of this entity are calculated. This string must exactly match the name of some ReferenceFrame instance in the federation.
    *        <br>Description of the data type from the FOM: 
    *
    * @return Returns a new local HlaDynamicalEntity.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaDynamicalEntity createLocalHlaDynamicalEntity(
       String name,
       String parentReferenceFrame
    ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Creates a new HlaDynamicalEntity with a specified <code>HLA instance name</code>, and sets all
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
    * @return Returns a new local HlaDynamicalEntity.
    *
    * @throws HlaIllegalInstanceNameException if the name is illegal
    * @throws HlaInstanceNameInUseException if the name is already in use
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaDynamicalEntity createLocalHlaDynamicalEntity(
       String hlaInstanceName,
       String name,
       String parentReferenceFrame
    ) throws HlaIllegalInstanceNameException, HlaInstanceNameInUseException,
             HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Deletes a local HlaDynamicalEntity and removes it from the federation.
    *
    * @param dynamicalEntity The HlaDynamicalEntity to delete
    *
    * @return the HlaDynamicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity dynamicalEntity)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaDynamicalEntity and removes it from the federation with a specified timestamp
    *
    * @param dynamicalEntity The HlaDynamicalEntity to delete
    * @param timestamp timestamp when the instance was removed.
    *
    * @return the HlaDynamicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaDynamicalEntity and removes it from the federation at a specified logical time.
    *
    * @param dynamicalEntity The HlaDynamicalEntity to delete
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaDynamicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity dynamicalEntity, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaDynamicalEntity and removes it from the federation with a specified timestamp at a specified logical time.
    *
    * @param dynamicalEntity The HlaDynamicalEntity to delete
    * @param timestamp timestamp when the instance was removed.
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaDynamicalEntity deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaDynamicalEntity deleteLocalHlaDynamicalEntity(HlaDynamicalEntity dynamicalEntity, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

    /**
     * Add an HlaDynamicalEntity manager listener.
     *
     * @param managerListener listener to add
     */
    void addHlaDynamicalEntityManagerListener(HlaDynamicalEntityManagerListener managerListener);

    /**
     * Remove an HlaDynamicalEntity manager listener.
     *
     * @param managerListener listener to remove
     */
    void removeHlaDynamicalEntityManagerListener(HlaDynamicalEntityManagerListener managerListener);

    /**
     * Add a listener that will automatically be added to all instances of HlaDynamicalEntity (Both local and remote).
     * The listener is notified when any attribute of an instance of HlaDynamicalEntity is updated.
     * The listener is also called when an interaction is sent to an instance of HlaDynamicalEntity.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param listener The listener to add.
     */
    void addHlaDynamicalEntityDefaultInstanceListener(HlaDynamicalEntityListener listener);

    /**
     * Remove a listener from the set of listeners that are automatically added to new instances of HlaDynamicalEntity.
     * Note: The listener will not be removed from already existing instances of HlaDynamicalEntity.
     * This method is idempotent.
     *
     * @param listener The listener to remove.
     */
    void removeHlaDynamicalEntityDefaultInstanceListener(HlaDynamicalEntityListener listener);

    /**
     * Add a valueListener that will automatically be added to all instances of HlaDynamicalEntity (Both local and remote).
     * The  Valuelistener is notified when any attribute of an instance of HlaDynamicalEntity is updated.
     * Note that the attribute values that are notified are not guaranteed to
     * be the latest available values for that attribute.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to add.
     */
    void addHlaDynamicalEntityDefaultInstanceValueListener(HlaDynamicalEntityValueListener valueListener);

    /**
     * Remove a valueListener from the set of listeners that are automatically added to new instances of HlaDynamicalEntity.
     * Note: The valueListener will not be removed from already existing instances of HlaDynamicalEntity.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to remove.
     */
    void removeHlaDynamicalEntityDefaultInstanceValueListener(HlaDynamicalEntityValueListener valueListener);

    /**
     * Enables or disables the HlaDynamicalEntity manager.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaDynamicalEntity manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     *
     * @param enabled <code>true</code> to enable the HlaDynamicalEntity manager, otherwise <code>false</code>
     */
    void setEnabled(boolean enabled);

    /**
     * Enables the HlaDynamicalEntity manager if it is available in the FOM at runtime.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaDynamicalEntity manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     */
    void setEnabledIfAvailableInFom();

    /**
     * Check if the HlaDynamicalEntity manager is actually enabled when connected.
     * An HlaDynamicalEntity manager may be disabled with the <code>setEnabled(false)</code> function, or it may be configured
     * as <i>if available in the FOM</i> by the <code>setEnabledIfAvailableInFom()</code> function and
     * then not available in the FOM at runtime.
     * <p>
     * This method should only be used when connected, it will always return <code>false</code>
     * when not connected.
     *
     * @return <code>true</code> if connected and the HlaDynamicalEntity manager is enabled.
     */
    boolean isEnabled();
}

