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
import uk.ac.brunel.see.exceptions.HlaValueNotSetException;

import java.util.HashMap;
import java.util.Map;


/**
 * Interface used to represent all attributes for an object instance.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaExecutionConfigurationAttributes extends HlaHLAobjectRootAttributes {

  /**
   * An enumeration of the attributes of an HlaExecutionConfiguration.
   *
   * <table>
   * <caption>All attributes</caption>
   * <tr><td><b>Enum constant</b></td><td><b>Java name</b></td><td><b>FOM name</b></td></tr>
   * <tr><td>ROOT_FRAME_NAME</td><td> rootFrameName</td><td> <code>root_frame_name</code></td></tr>
   * <tr><td>SCENARIO_TIME_EPOCH</td><td> scenarioTimeEpoch</td><td> <code>scenario_time_epoch</code></td></tr>
   * <tr><td>CURRENT_EXECUTION_MODE</td><td> currentExecutionMode</td><td> <code>current_execution_mode</code></td></tr>
   * <tr><td>NEXT_EXECUTION_MODE</td><td> nextExecutionMode</td><td> <code>next_execution_mode</code></td></tr>
   * <tr><td>NEXT_MODE_SCENARIO_TIME</td><td> nextModeScenarioTime</td><td> <code>next_mode_scenario_time</code></td></tr>
   * <tr><td>NEXT_MODE_CTE_TIME</td><td> nextModeCteTime</td><td> <code>next_mode_cte_time</code></td></tr>
   * <tr><td>LEAST_COMMON_TIME_STEP</td><td> leastCommonTimeStep</td><td> <code>least_common_time_step</code></td></tr>
   * </table>
   */
   enum Attribute {
      /**
      * rootFrameName (FOM name: <code>root_frame_name</code>).
      * <br>Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
      */
      ROOT_FRAME_NAME("root_frame_name"),
      /**
      * scenarioTimeEpoch (FOM name: <code>scenario_time_epoch</code>).
      * <br>Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
      */
      SCENARIO_TIME_EPOCH("scenario_time_epoch"),
      /**
      * currentExecutionMode (FOM name: <code>current_execution_mode</code>).
      * <br>Description from the FOM: Defines the current running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.
      */
      CURRENT_EXECUTION_MODE("current_execution_mode"),
      /**
      * nextExecutionMode (FOM name: <code>next_execution_mode</code>).
      * <br>Description from the FOM: Defines the next running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.  This is used in conjunction with the cte_mode_time, sim_mode_time and associated sync point mechanisms to coordinate federation execution mode transitions.
      */
      NEXT_EXECUTION_MODE("next_execution_mode"),
      /**
      * nextModeScenarioTime (FOM name: <code>next_mode_scenario_time</code>).
      * <br>Description from the FOM: The time for the next federation execution mode change expressed as a federation scenario time reference.  Note: this value is only meaningful for going into freeze; exiting freeze is coordinated through a sync point mechanism.
      */
      NEXT_MODE_SCENARIO_TIME("next_mode_scenario_time"),
      /**
      * nextModeCteTime (FOM name: <code>next_mode_cte_time</code>).
      * <br>Description from the FOM: The time for the next federation execution mode change expressed as a Central Timing Equipment (CTE) time reference.  The standard for this reference shall be defined in the federation agreement when CTE is used.
      */
      NEXT_MODE_CTE_TIME("next_mode_cte_time"),
      /**
      * leastCommonTimeStep (FOM name: <code>least_common_time_step</code>).
      * <br>Description from the FOM: A 64 bit integer time that represents microseconds for the least common value of all the time step values in the federation execution (LCTS).  This value is set by the Master Federate and does not change during the federation execution. This is used in the computation to find the next HLA Logical Time Boundary (HLTB) available to all federates in the federation execution.  The basic equation is HLTB = ( floor(GALT/LCTS) + 1 ) * LCTS, where GALT is the greatest available logical time.  This is used to synchronize the federates in a federation execution to be on a common logical time boundary.
      */
      LEAST_COMMON_TIME_STEP("least_common_time_step");
      
      private static final Map<String, Attribute> NAMES;

      static {
         NAMES = new HashMap<String, Attribute>(values().length);
         for (Attribute val : values()) {
            NAMES.put(val._name, val);
         }
      }

      private final String _name;

      private Attribute(String name) {
         _name = name;
      }

     /**
      * Get the name of the Attribute.
      *
      * @return the name of the attribute
      */
      public String getName() {
         return _name;
      }

     /**
      * Find the Attribute with the specified name.
      *
      * @param name name of the attribute to find
      *
      * @return the attribute with the specified <code>name</code>, or <code>null</code> if not found
      */
      public static Attribute find(String name) {
         return NAMES.get(name);
      }
   }


   /**
    * Returns true if the <code>rootFrameName</code> attribute has received a value yet.
    *
    * <br>Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
    *
    * @return true if <code>rootFrameName</code> is available.
    */
    boolean hasRootFrameName();

   /**
    * Gets the value of the <code>rootFrameName</code> attribute.
    *
    * <br>Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
    * <br>Description of the data type from the FOM: 
    *
    * @return the <code>rootFrameName</code> attribute.
    *
    * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
    */
    String getRootFrameName() throws HlaValueNotSetException;

   /**
    * Gets the value of the <code>rootFrameName</code> attribute, or <code>defaultValue</code> if value was not set.
    *
    * <br>Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
    * <br>Description of the data type from the FOM: 
    *
    * @param defaultValue default value
    *
    * @return the <code>rootFrameName</code> attribute or <code>defaultValue</code> if not set
    */
    String getRootFrameName(String defaultValue);

    /**
     * Gets the time stamped value of the <code>rootFrameName</code> attribute.
     *
     * <br>Description from the FOM: Specifies the name of the root coordinate frame in the federation execution's reference frame tree.  This frame shall remain fixed throughout the federation execution.
     * <br>Description of the data type from the FOM: 
     *
     * @return the time stamped <code>rootFrameName</code> attribute.
     *
     * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
     */
     HlaTimeStamped<String> getRootFrameNameTimeStamped() throws HlaValueNotSetException;

   /**
    * Returns true if the <code>scenarioTimeEpoch</code> attribute has received a value yet.
    *
    * <br>Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
    *
    * @return true if <code>scenarioTimeEpoch</code> is available.
    */
    boolean hasScenarioTimeEpoch();

   /**
    * Gets the value of the <code>scenarioTimeEpoch</code> attribute.
    *
    * <br>Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @return the <code>scenarioTimeEpoch</code> attribute.
    *
    * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
    */
    double getScenarioTimeEpoch() throws HlaValueNotSetException;

   /**
    * Gets the value of the <code>scenarioTimeEpoch</code> attribute, or <code>defaultValue</code> if value was not set.
    *
    * <br>Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @param defaultValue default value
    *
    * @return the <code>scenarioTimeEpoch</code> attribute or <code>defaultValue</code> if not set
    */
    double getScenarioTimeEpoch(double defaultValue);

    /**
     * Gets the time stamped value of the <code>scenarioTimeEpoch</code> attribute.
     *
     * <br>Description from the FOM: Federation execution scenario time epoch.  This is the beginning epoch expressed in Terrestrial Time (TT), using as starting epoch that of the Truncated Julian Date (TJD)- 1968-05-24 00:00:00 UTC, that corresponds to HLA logical time 0.  All joining federates shall use this time to coordinate the offset between their local simulation scenario times, their local simulation execution times and the HLA logical time.
     * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
     *
     * @return the time stamped <code>scenarioTimeEpoch</code> attribute.
     *
     * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
     */
     HlaTimeStamped<Double> getScenarioTimeEpochTimeStamped() throws HlaValueNotSetException;

   /**
    * Returns true if the <code>currentExecutionMode</code> attribute has received a value yet.
    *
    * <br>Description from the FOM: Defines the current running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.
    *
    * @return true if <code>currentExecutionMode</code> is available.
    */
    boolean hasCurrentExecutionMode();

   /**
    * Gets the value of the <code>currentExecutionMode</code> attribute.
    *
    * <br>Description from the FOM: Defines the current running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.
    * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
    *
    * @return the <code>currentExecutionMode</code> attribute.
    *
    * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
    */
    ExecutionMode getCurrentExecutionMode() throws HlaValueNotSetException;

   /**
    * Gets the value of the <code>currentExecutionMode</code> attribute, or <code>defaultValue</code> if value was not set.
    *
    * <br>Description from the FOM: Defines the current running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.
    * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
    *
    * @param defaultValue default value
    *
    * @return the <code>currentExecutionMode</code> attribute or <code>defaultValue</code> if not set
    */
    ExecutionMode getCurrentExecutionMode(ExecutionMode defaultValue);

    /**
     * Gets the time stamped value of the <code>currentExecutionMode</code> attribute.
     *
     * <br>Description from the FOM: Defines the current running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.
     * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
     *
     * @return the time stamped <code>currentExecutionMode</code> attribute.
     *
     * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
     */
     HlaTimeStamped<ExecutionMode> getCurrentExecutionModeTimeStamped() throws HlaValueNotSetException;

   /**
    * Returns true if the <code>nextExecutionMode</code> attribute has received a value yet.
    *
    * <br>Description from the FOM: Defines the next running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.  This is used in conjunction with the cte_mode_time, sim_mode_time and associated sync point mechanisms to coordinate federation execution mode transitions.
    *
    * @return true if <code>nextExecutionMode</code> is available.
    */
    boolean hasNextExecutionMode();

   /**
    * Gets the value of the <code>nextExecutionMode</code> attribute.
    *
    * <br>Description from the FOM: Defines the next running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.  This is used in conjunction with the cte_mode_time, sim_mode_time and associated sync point mechanisms to coordinate federation execution mode transitions.
    * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
    *
    * @return the <code>nextExecutionMode</code> attribute.
    *
    * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
    */
    ExecutionMode getNextExecutionMode() throws HlaValueNotSetException;

   /**
    * Gets the value of the <code>nextExecutionMode</code> attribute, or <code>defaultValue</code> if value was not set.
    *
    * <br>Description from the FOM: Defines the next running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.  This is used in conjunction with the cte_mode_time, sim_mode_time and associated sync point mechanisms to coordinate federation execution mode transitions.
    * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
    *
    * @param defaultValue default value
    *
    * @return the <code>nextExecutionMode</code> attribute or <code>defaultValue</code> if not set
    */
    ExecutionMode getNextExecutionMode(ExecutionMode defaultValue);

    /**
     * Gets the time stamped value of the <code>nextExecutionMode</code> attribute.
     *
     * <br>Description from the FOM: Defines the next running state of the federation execution in terms of a finite set of states expressed in the ExecutionMode enumeration.  This is used in conjunction with the cte_mode_time, sim_mode_time and associated sync point mechanisms to coordinate federation execution mode transitions.
     * <br>Description of the data type from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
     *
     * @return the time stamped <code>nextExecutionMode</code> attribute.
     *
     * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
     */
     HlaTimeStamped<ExecutionMode> getNextExecutionModeTimeStamped() throws HlaValueNotSetException;

   /**
    * Returns true if the <code>nextModeScenarioTime</code> attribute has received a value yet.
    *
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a federation scenario time reference.  Note: this value is only meaningful for going into freeze; exiting freeze is coordinated through a sync point mechanism.
    *
    * @return true if <code>nextModeScenarioTime</code> is available.
    */
    boolean hasNextModeScenarioTime();

   /**
    * Gets the value of the <code>nextModeScenarioTime</code> attribute.
    *
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a federation scenario time reference.  Note: this value is only meaningful for going into freeze; exiting freeze is coordinated through a sync point mechanism.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @return the <code>nextModeScenarioTime</code> attribute.
    *
    * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
    */
    double getNextModeScenarioTime() throws HlaValueNotSetException;

   /**
    * Gets the value of the <code>nextModeScenarioTime</code> attribute, or <code>defaultValue</code> if value was not set.
    *
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a federation scenario time reference.  Note: this value is only meaningful for going into freeze; exiting freeze is coordinated through a sync point mechanism.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @param defaultValue default value
    *
    * @return the <code>nextModeScenarioTime</code> attribute or <code>defaultValue</code> if not set
    */
    double getNextModeScenarioTime(double defaultValue);

    /**
     * Gets the time stamped value of the <code>nextModeScenarioTime</code> attribute.
     *
     * <br>Description from the FOM: The time for the next federation execution mode change expressed as a federation scenario time reference.  Note: this value is only meaningful for going into freeze; exiting freeze is coordinated through a sync point mechanism.
     * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
     *
     * @return the time stamped <code>nextModeScenarioTime</code> attribute.
     *
     * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
     */
     HlaTimeStamped<Double> getNextModeScenarioTimeTimeStamped() throws HlaValueNotSetException;

   /**
    * Returns true if the <code>nextModeCteTime</code> attribute has received a value yet.
    *
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a Central Timing Equipment (CTE) time reference.  The standard for this reference shall be defined in the federation agreement when CTE is used.
    *
    * @return true if <code>nextModeCteTime</code> is available.
    */
    boolean hasNextModeCteTime();

   /**
    * Gets the value of the <code>nextModeCteTime</code> attribute.
    *
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a Central Timing Equipment (CTE) time reference.  The standard for this reference shall be defined in the federation agreement when CTE is used.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @return the <code>nextModeCteTime</code> attribute.
    *
    * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
    */
    double getNextModeCteTime() throws HlaValueNotSetException;

   /**
    * Gets the value of the <code>nextModeCteTime</code> attribute, or <code>defaultValue</code> if value was not set.
    *
    * <br>Description from the FOM: The time for the next federation execution mode change expressed as a Central Timing Equipment (CTE) time reference.  The standard for this reference shall be defined in the federation agreement when CTE is used.
    * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
    *
    * @param defaultValue default value
    *
    * @return the <code>nextModeCteTime</code> attribute or <code>defaultValue</code> if not set
    */
    double getNextModeCteTime(double defaultValue);

    /**
     * Gets the time stamped value of the <code>nextModeCteTime</code> attribute.
     *
     * <br>Description from the FOM: The time for the next federation execution mode change expressed as a Central Timing Equipment (CTE) time reference.  The standard for this reference shall be defined in the federation agreement when CTE is used.
     * <br>Description of the data type from the FOM: A measurement of time. [unit: second (s), resolution: NA, accuracy: NA]
     *
     * @return the time stamped <code>nextModeCteTime</code> attribute.
     *
     * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
     */
     HlaTimeStamped<Double> getNextModeCteTimeTimeStamped() throws HlaValueNotSetException;

   /**
    * Returns true if the <code>leastCommonTimeStep</code> attribute has received a value yet.
    *
    * <br>Description from the FOM: A 64 bit integer time that represents microseconds for the least common value of all the time step values in the federation execution (LCTS).  This value is set by the Master Federate and does not change during the federation execution. This is used in the computation to find the next HLA Logical Time Boundary (HLTB) available to all federates in the federation execution.  The basic equation is HLTB = ( floor(GALT/LCTS) + 1 ) * LCTS, where GALT is the greatest available logical time.  This is used to synchronize the federates in a federation execution to be on a common logical time boundary.
    *
    * @return true if <code>leastCommonTimeStep</code> is available.
    */
    boolean hasLeastCommonTimeStep();

   /**
    * Gets the value of the <code>leastCommonTimeStep</code> attribute.
    *
    * <br>Description from the FOM: A 64 bit integer time that represents microseconds for the least common value of all the time step values in the federation execution (LCTS).  This value is set by the Master Federate and does not change during the federation execution. This is used in the computation to find the next HLA Logical Time Boundary (HLTB) available to all federates in the federation execution.  The basic equation is HLTB = ( floor(GALT/LCTS) + 1 ) * LCTS, where GALT is the greatest available logical time.  This is used to synchronize the federates in a federation execution to be on a common logical time boundary.
    * <br>Description of the data type from the FOM: Standardized 64 bit integer time [unit: NA, resolution: 1, accuracy: NA]
    *
    * @return the <code>leastCommonTimeStep</code> attribute.
    *
    * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
    */
    long getLeastCommonTimeStep() throws HlaValueNotSetException;

   /**
    * Gets the value of the <code>leastCommonTimeStep</code> attribute, or <code>defaultValue</code> if value was not set.
    *
    * <br>Description from the FOM: A 64 bit integer time that represents microseconds for the least common value of all the time step values in the federation execution (LCTS).  This value is set by the Master Federate and does not change during the federation execution. This is used in the computation to find the next HLA Logical Time Boundary (HLTB) available to all federates in the federation execution.  The basic equation is HLTB = ( floor(GALT/LCTS) + 1 ) * LCTS, where GALT is the greatest available logical time.  This is used to synchronize the federates in a federation execution to be on a common logical time boundary.
    * <br>Description of the data type from the FOM: Standardized 64 bit integer time [unit: NA, resolution: 1, accuracy: NA]
    *
    * @param defaultValue default value
    *
    * @return the <code>leastCommonTimeStep</code> attribute or <code>defaultValue</code> if not set
    */
    long getLeastCommonTimeStep(long defaultValue);

    /**
     * Gets the time stamped value of the <code>leastCommonTimeStep</code> attribute.
     *
     * <br>Description from the FOM: A 64 bit integer time that represents microseconds for the least common value of all the time step values in the federation execution (LCTS).  This value is set by the Master Federate and does not change during the federation execution. This is used in the computation to find the next HLA Logical Time Boundary (HLTB) available to all federates in the federation execution.  The basic equation is HLTB = ( floor(GALT/LCTS) + 1 ) * LCTS, where GALT is the greatest available logical time.  This is used to synchronize the federates in a federation execution to be on a common logical time boundary.
     * <br>Description of the data type from the FOM: Standardized 64 bit integer time [unit: NA, resolution: 1, accuracy: NA]
     *
     * @return the time stamped <code>leastCommonTimeStep</code> attribute.
     *
     * @throws HlaValueNotSetException unchecked exception that is thrown if no value is available.
     */
     HlaTimeStamped<Long> getLeastCommonTimeStepTimeStamped() throws HlaValueNotSetException;
}
