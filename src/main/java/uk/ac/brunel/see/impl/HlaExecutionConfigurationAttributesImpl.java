package uk.ac.brunel.see.impl;

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
import uk.ac.brunel.see.HlaExecutionConfigurationAttributes;
import uk.ac.brunel.see.HlaTimeStamped;
import uk.ac.brunel.see.exceptions.HlaValueNotSetException;
import uk.ac.brunel.see.impl.utils.AttributeValue;

/* @Immutable */
final class HlaExecutionConfigurationAttributesImpl implements HlaExecutionConfigurationAttributes {

   private final AttributeValue<String> _rootFrameName;
   private final AttributeValue<Double> _scenarioTimeEpoch;
   private final AttributeValue<ExecutionMode> _currentExecutionMode;
   private final AttributeValue<ExecutionMode> _nextExecutionMode;
   private final AttributeValue<Double> _nextModeScenarioTime;
   private final AttributeValue<Double> _nextModeCteTime;
   private final AttributeValue<Long> _leastCommonTimeStep;

   HlaExecutionConfigurationAttributesImpl(
      AttributeValue<String> rootFrameName,
      AttributeValue<Double> scenarioTimeEpoch,
      AttributeValue<ExecutionMode> currentExecutionMode,
      AttributeValue<ExecutionMode> nextExecutionMode,
      AttributeValue<Double> nextModeScenarioTime,
      AttributeValue<Double> nextModeCteTime,
      AttributeValue<Long> leastCommonTimeStep
   ) {
      _rootFrameName = new AttributeValue<String>(rootFrameName);
      _scenarioTimeEpoch = new AttributeValue<Double>(scenarioTimeEpoch);
      _currentExecutionMode = new AttributeValue<ExecutionMode>(currentExecutionMode);
      _nextExecutionMode = new AttributeValue<ExecutionMode>(nextExecutionMode);
      _nextModeScenarioTime = new AttributeValue<Double>(nextModeScenarioTime);
      _nextModeCteTime = new AttributeValue<Double>(nextModeCteTime);
      _leastCommonTimeStep = new AttributeValue<Long>(leastCommonTimeStep);
   }

   public boolean hasRootFrameName() {
      return _rootFrameName.hasValue();
   }

   public String getRootFrameName() throws HlaValueNotSetException {
      if (_rootFrameName.hasValue()) {
         return _rootFrameName.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'rootFrameName' not set");
   }

   public String getRootFrameName(String defaultValue) {
      if (_rootFrameName.hasValue()) {
         return _rootFrameName.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<String> getRootFrameNameTimeStamped() throws HlaValueNotSetException {
      if (_rootFrameName.hasValue()) {
         return _rootFrameName.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'rootFrameName' not set");
   }

   public boolean hasScenarioTimeEpoch() {
      return _scenarioTimeEpoch.hasValue();
   }

   public double getScenarioTimeEpoch() throws HlaValueNotSetException {
      if (_scenarioTimeEpoch.hasValue()) {
         return _scenarioTimeEpoch.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'scenarioTimeEpoch' not set");
   }

   public double getScenarioTimeEpoch(double defaultValue) {
      if (_scenarioTimeEpoch.hasValue()) {
         return _scenarioTimeEpoch.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<Double> getScenarioTimeEpochTimeStamped() throws HlaValueNotSetException {
      if (_scenarioTimeEpoch.hasValue()) {
         return _scenarioTimeEpoch.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'scenarioTimeEpoch' not set");
   }

   public boolean hasCurrentExecutionMode() {
      return _currentExecutionMode.hasValue();
   }

   public ExecutionMode getCurrentExecutionMode() throws HlaValueNotSetException {
      if (_currentExecutionMode.hasValue()) {
         return _currentExecutionMode.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'currentExecutionMode' not set");
   }

   public ExecutionMode getCurrentExecutionMode(ExecutionMode defaultValue) {
      if (_currentExecutionMode.hasValue()) {
         return _currentExecutionMode.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<ExecutionMode> getCurrentExecutionModeTimeStamped() throws HlaValueNotSetException {
      if (_currentExecutionMode.hasValue()) {
         return _currentExecutionMode.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'currentExecutionMode' not set");
   }

   public boolean hasNextExecutionMode() {
      return _nextExecutionMode.hasValue();
   }

   public ExecutionMode getNextExecutionMode() throws HlaValueNotSetException {
      if (_nextExecutionMode.hasValue()) {
         return _nextExecutionMode.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'nextExecutionMode' not set");
   }

   public ExecutionMode getNextExecutionMode(ExecutionMode defaultValue) {
      if (_nextExecutionMode.hasValue()) {
         return _nextExecutionMode.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<ExecutionMode> getNextExecutionModeTimeStamped() throws HlaValueNotSetException {
      if (_nextExecutionMode.hasValue()) {
         return _nextExecutionMode.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'nextExecutionMode' not set");
   }

   public boolean hasNextModeScenarioTime() {
      return _nextModeScenarioTime.hasValue();
   }

   public double getNextModeScenarioTime() throws HlaValueNotSetException {
      if (_nextModeScenarioTime.hasValue()) {
         return _nextModeScenarioTime.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'nextModeScenarioTime' not set");
   }

   public double getNextModeScenarioTime(double defaultValue) {
      if (_nextModeScenarioTime.hasValue()) {
         return _nextModeScenarioTime.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<Double> getNextModeScenarioTimeTimeStamped() throws HlaValueNotSetException {
      if (_nextModeScenarioTime.hasValue()) {
         return _nextModeScenarioTime.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'nextModeScenarioTime' not set");
   }

   public boolean hasNextModeCteTime() {
      return _nextModeCteTime.hasValue();
   }

   public double getNextModeCteTime() throws HlaValueNotSetException {
      if (_nextModeCteTime.hasValue()) {
         return _nextModeCteTime.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'nextModeCteTime' not set");
   }

   public double getNextModeCteTime(double defaultValue) {
      if (_nextModeCteTime.hasValue()) {
         return _nextModeCteTime.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<Double> getNextModeCteTimeTimeStamped() throws HlaValueNotSetException {
      if (_nextModeCteTime.hasValue()) {
         return _nextModeCteTime.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'nextModeCteTime' not set");
   }

   public boolean hasLeastCommonTimeStep() {
      return _leastCommonTimeStep.hasValue();
   }

   public long getLeastCommonTimeStep() throws HlaValueNotSetException {
      if (_leastCommonTimeStep.hasValue()) {
         return _leastCommonTimeStep.getValue();
      }
      throw new HlaValueNotSetException("Attribute 'leastCommonTimeStep' not set");
   }

   public long getLeastCommonTimeStep(long defaultValue) {
      if (_leastCommonTimeStep.hasValue()) {
         return _leastCommonTimeStep.getValue();
      }
      return defaultValue;
   }

   public HlaTimeStamped<Long> getLeastCommonTimeStepTimeStamped() throws HlaValueNotSetException {
      if (_leastCommonTimeStep.hasValue()) {
         return _leastCommonTimeStep.getTimestampedValueForUser();
      }
      throw new HlaValueNotSetException("Attribute 'leastCommonTimeStep' not set");
   }

}
