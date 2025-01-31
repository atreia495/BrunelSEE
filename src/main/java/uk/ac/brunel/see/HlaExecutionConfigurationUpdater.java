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

import uk.ac.brunel.see.datatypes.ExecutionMode;
import uk.ac.brunel.see.exceptions.*;


/**
 * Updater used to update attribute values.
 * <p>
 * This class is <code>ThreadSafe</code> as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaExecutionConfigurationUpdater extends HlaHLAobjectRootUpdater {

   /**
    * Send the rootFrameName in this update.
    * <br>Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
    * <br>Description of the data type from the FOM: 
    * <p>
    * The value can not be changed since this attribute is defined as a <i>Create</i> attribute.
    *
    * @return this updater
    */
    HlaExecutionConfigurationUpdater sendRootFrameName();

   /**
    * Send the scenarioTimeEpoch in this update.
    * <br>Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    * <p>
    * The value can not be changed since this attribute is defined as a <i>Create</i> attribute.
    *
    * @return this updater
    */
    HlaExecutionConfigurationUpdater sendScenarioTimeEpoch();

   /**
    * Set the currentExecutionMode for this update.
    * <br>Description from the FOM: Defines the current running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.
    * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
    *
    * @param currentExecutionMode the new currentExecutionMode
    *
    * @return this updater
    */
    HlaExecutionConfigurationUpdater setCurrentExecutionMode(ExecutionMode currentExecutionMode);

   /**
    * Set the nextExecutionMode for this update.
    * <br>Description from the FOM: Defines the next running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.  This is used in conjunction with the cte_mode_time, sim_mode_time and associated sync point mechanisms to coordinate federation execution mode transitions.
    * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
    *
    * @param nextExecutionMode the new nextExecutionMode
    *
    * @return this updater
    */
    HlaExecutionConfigurationUpdater setNextExecutionMode(ExecutionMode nextExecutionMode);

   /**
    * Set the nextModeScenarioTime for this update.
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a federation scenario time reference.  Note: this value is only meaningful for going into freeze; exiting freeze is coordinated through a sync point mechanism.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @param nextModeScenarioTime the new nextModeScenarioTime
    *
    * @return this updater
    */
    HlaExecutionConfigurationUpdater setNextModeScenarioTime(double nextModeScenarioTime);

   /**
    * Set the nextModeCteTime for this update.
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a Central Timing Equipment (CTE) time reference.  The standard for this reference shall be defined in the federation agreement when CTE is used.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @param nextModeCteTime the new nextModeCteTime
    *
    * @return this updater
    */
    HlaExecutionConfigurationUpdater setNextModeCteTime(double nextModeCteTime);

   /**
    * Set the leastCommonTimeStep for this update.
    * <br>Description from the FOM: A 64 bit integer time that represents microseconds for the least common value of all the time step values in the federation execution (LCTS).  This value is set by the Master Federate and does not change during the federation execution. This is used in the computation to find the next HLA Logical Time Boundary (HLTB) available to all federates in the federation execution.  The basic equation is HLTB = ( floor(GALT/LCTS) + 1 ) * LCTS, where GALT is the greatest available logical time.  This is used to synchronize the federates in a federation execution to be on a common logical time boundary.
    * <br>Description of the data type from the FOM: Standardized 64 bit integer time [unit: NA, resolution: 1, accuracy: NA]
    *
    * @param leastCommonTimeStep the new leastCommonTimeStep
    *
    * @return this updater
    */
    HlaExecutionConfigurationUpdater setLeastCommonTimeStep(long leastCommonTimeStep);

   /**
    * Send all the attributes.
    * Note that the <code>Updater</code> can not be reused after <code>sendUpdate</code> has been called.
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaAttributeNotOwnedException if a changed attribute is not owned
    * @throws HlaUpdaterReusedException if the Updater is reused
    * @throws HlaObjectInstanceIsRemovedException if the object instance has been removed
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    void sendUpdate()
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaUpdaterReusedException,
                HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Send all the attributes with a specified timestamp.
    * Note that the <code>Updater</code> can not be reused after <code>sendUpdate</code> has been called.
    *
    * @param timestamp timestamp to send for this update
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaAttributeNotOwnedException if a changed attribute is not owned
    * @throws HlaUpdaterReusedException if the Updater is reused
    * @throws HlaObjectInstanceIsRemovedException if the object instance has been removed
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    void sendUpdate(HlaTimeStamp timestamp)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaUpdaterReusedException,
                HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Send all the attributes with a specified logical time.
    * Note that the <code>Updater</code> can not be reused after <code>sendUpdate</code> has been called.
    *
    * @param logicalTime logical time to send for this update
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaAttributeNotOwnedException if a changed attribute is not owned
    * @throws HlaUpdaterReusedException if the Updater is reused
    * @throws HlaObjectInstanceIsRemovedException if the object instance has been removed
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    void sendUpdate(HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException,
                HlaInvalidLogicalTimeException, HlaUpdaterReusedException,
                HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException;

   /**
    * Send all the attributes with a specified timestamp and logical time.
    * Note that the <code>Updater</code> can not be reused after <code>sendUpdate</code> has been called.
    *
    * @param timestamp timestamp to send for this update
    * @param logicalTime logical time to send for this update
    *
    * @throws HlaNotConnectedException if the federate is not connected
    * @throws HlaInternalException on internal errors
    * @throws HlaRtiException on exception from the RTI
    * @throws HlaInvalidLogicalTimeException if the logical time is invalid
    * @throws HlaAttributeNotOwnedException if a changed attribute is not owned
    * @throws HlaUpdaterReusedException if the Updater is reused
    * @throws HlaObjectInstanceIsRemovedException if the object instance has been removed
    * @throws HlaSaveInProgressException if a federation save is in progress
    * @throws HlaRestoreInProgressException if a federation restore is in progress
    */
    void sendUpdate(HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException,
                HlaInvalidLogicalTimeException, HlaUpdaterReusedException,
                HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException;
}
