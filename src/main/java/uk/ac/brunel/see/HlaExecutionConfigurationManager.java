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
 * Manager used to manage HlaExecutionConfigurations.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaExecutionConfigurationManager {

   /**
    * Gets a list of all HlaExecutionConfigurations within interest, both local and remote.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of HlaExecutionConfigurations
    */
    List<HlaExecutionConfiguration> getHlaExecutionConfigurations();

   /**
    * Gets a list of all HlaExecutionConfigurations, both local and remote.
    * HlaExecutionConfigurations not within interest are also included.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of all HlaExecutionConfigurations
    */
    List<HlaExecutionConfiguration> getAllHlaExecutionConfigurations();

   /**
    * Gets a list of local HlaExecutionConfigurations within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of local HlaExecutionConfigurations
    */
    List<HlaExecutionConfiguration> getLocalHlaExecutionConfigurations();

   /**
    * Gets a list of remote HlaExecutionConfigurations within interest.
    * The list is a copy that is allowed to be altered.
    *
    * @return a list of remote HlaExecutionConfigurations
    */
    List<HlaExecutionConfiguration> getRemoteHlaExecutionConfigurations();

   /**
    * Find a HlaExecutionConfiguration with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> of the HlaExecutionConfiguration to find
    *
    * @return the specified HlaExecutionConfiguration, or <code>null</code> if not found.
    */
   HlaExecutionConfiguration getExecutionConfigurationByHlaInstanceName(String hlaInstanceName);

   /**
    * Find a HlaExecutionConfiguration with the given <code>HLA instance name</code>.
    * If none is found, <code>null</code> is returned.
    *
    * @param encodedHlaInstanceHandle the encoded <code>HLA instance handle</code> of the HlaExecutionConfiguration to find
    *
    * @return the specified HlaExecutionConfiguration, or <code>null</code> if not found.
    */
   HlaExecutionConfiguration getExecutionConfigurationByHlaInstanceHandle(byte[] encodedHlaInstanceHandle);

   /**
    * Creates a new HlaExecutionConfiguration, and sets all <i>Create</i> attributes.
    *
    * @param rootFrameName represents the <code>root_frame_name</code> from the FOM.
    *        <br> Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
    *        <br>Description of the data type from the FOM: 
    * @param scenarioTimeEpoch represents the <code>scenario_time_epoch</code> from the FOM.
    *        <br> Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
    *        <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @return Returns a new local HlaExecutionConfiguration.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaExecutionConfiguration createLocalHlaExecutionConfiguration(
       String rootFrameName,
       double scenarioTimeEpoch
    ) throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Creates a new HlaExecutionConfiguration with a specified <code>HLA instance name</code>, and sets all
    * <i>Create</i> attributes.
    *
    * @param hlaInstanceName the <code>HLA instance name</code> to register for this instance
    * @param rootFrameName represents the <code>root_frame_name</code> from the FOM.
    *        <br>Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
    *        <br>Description of the data type from the FOM: 
    * @param scenarioTimeEpoch represents the <code>scenario_time_epoch</code> from the FOM.
    *        <br>Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
    *        <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @return Returns a new local HlaExecutionConfiguration.
    *
    * @throws HlaIllegalInstanceNameException if the name is illegal
    * @throws HlaInstanceNameInUseException if the name is already in use
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaExecutionConfiguration createLocalHlaExecutionConfiguration(
       String hlaInstanceName,
       String rootFrameName,
       double scenarioTimeEpoch
    ) throws HlaIllegalInstanceNameException, HlaInstanceNameInUseException,
             HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;


   /**
    * Deletes a local HlaExecutionConfiguration and removes it from the federation.
    *
    * @param executionConfiguration The HlaExecutionConfiguration to delete
    *
    * @return the HlaExecutionConfiguration deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration executionConfiguration)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaExecutionConfiguration and removes it from the federation with a specified timestamp
    *
    * @param executionConfiguration The HlaExecutionConfiguration to delete
    * @param timestamp timestamp when the instance was removed.
    *
    * @return the HlaExecutionConfiguration deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaExecutionConfiguration and removes it from the federation at a specified logical time.
    *
    * @param executionConfiguration The HlaExecutionConfiguration to delete
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaExecutionConfiguration deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration executionConfiguration, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Deletes a local HlaExecutionConfiguration and removes it from the federation with a specified timestamp at a specified logical time.
    *
    * @param executionConfiguration The HlaExecutionConfiguration to delete
    * @param timestamp timestamp when the instance was removed.
    * @param logicalTime The logical time when the instance was removed.
    *
    * @return the HlaExecutionConfiguration deleted, null if it did not exist.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    HlaExecutionConfiguration deleteLocalHlaExecutionConfiguration(HlaExecutionConfiguration executionConfiguration, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

    /**
     * Add an HlaExecutionConfiguration manager listener.
     *
     * @param managerListener listener to add
     */
    void addHlaExecutionConfigurationManagerListener(HlaExecutionConfigurationManagerListener managerListener);

    /**
     * Remove an HlaExecutionConfiguration manager listener.
     *
     * @param managerListener listener to remove
     */
    void removeHlaExecutionConfigurationManagerListener(HlaExecutionConfigurationManagerListener managerListener);

    /**
     * Add a listener that will automatically be added to all instances of HlaExecutionConfiguration (Both local and remote).
     * The listener is notified when any attribute of an instance of HlaExecutionConfiguration is updated.
     * The listener is also called when an interaction is sent to an instance of HlaExecutionConfiguration.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param listener The listener to add.
     */
    void addHlaExecutionConfigurationDefaultInstanceListener(HlaExecutionConfigurationListener listener);

    /**
     * Remove a listener from the set of listeners that are automatically added to new instances of HlaExecutionConfiguration.
     * Note: The listener will not be removed from already existing instances of HlaExecutionConfiguration.
     * This method is idempotent.
     *
     * @param listener The listener to remove.
     */
    void removeHlaExecutionConfigurationDefaultInstanceListener(HlaExecutionConfigurationListener listener);

    /**
     * Add a valueListener that will automatically be added to all instances of HlaExecutionConfiguration (Both local and remote).
     * The  Valuelistener is notified when any attribute of an instance of HlaExecutionConfiguration is updated.
     * Note that the attribute values that are notified are not guaranteed to
     * be the latest available values for that attribute.
     * All listeners will automatically be removed when an instance is removed.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to add.
     */
    void addHlaExecutionConfigurationDefaultInstanceValueListener(HlaExecutionConfigurationValueListener valueListener);

    /**
     * Remove a valueListener from the set of listeners that are automatically added to new instances of HlaExecutionConfiguration.
     * Note: The valueListener will not be removed from already existing instances of HlaExecutionConfiguration.
     * This method is idempotent.
     *
     * @param valueListener The valueListener to remove.
     */
    void removeHlaExecutionConfigurationDefaultInstanceValueListener(HlaExecutionConfigurationValueListener valueListener);

    /**
     * Enables or disables the HlaExecutionConfiguration manager.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaExecutionConfiguration manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     *
     * @param enabled <code>true</code> to enable the HlaExecutionConfiguration manager, otherwise <code>false</code>
     */
    void setEnabled(boolean enabled);

    /**
     * Enables the HlaExecutionConfiguration manager if it is available in the FOM at runtime.
     * The manager is enabled by default. The state can only be changed when not connected.
     * When the HlaExecutionConfiguration manager is disabled it will act as disconnected,
     * ie throws HlaNotConnectedException.
     */
    void setEnabledIfAvailableInFom();

    /**
     * Check if the HlaExecutionConfiguration manager is actually enabled when connected.
     * An HlaExecutionConfiguration manager may be disabled with the <code>setEnabled(false)</code> function, or it may be configured
     * as <i>if available in the FOM</i> by the <code>setEnabledIfAvailableInFom()</code> function and
     * then not available in the FOM at runtime.
     * <p>
     * This method should only be used when connected, it will always return <code>false</code>
     * when not connected.
     *
     * @return <code>true</code> if connected and the HlaExecutionConfiguration manager is enabled.
     */
    boolean isEnabled();
}

