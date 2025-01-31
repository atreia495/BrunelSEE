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
import uk.ac.brunel.see.*;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.datatypes.ToStringBuilder;
import uk.ac.brunel.see.impl.utils.AttributeValue;
import uk.ac.brunel.see.impl.utils.Equals;
import uk.ac.brunel.see.impl.utils.NameValueMap;
import uk.ac.brunel.see.impl.utils.SetAdapter;
import uk.ac.brunel.see.impl.utils.ParameterValue;

import se.pitch.ral.api.ObjectInstanceHandle;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/* @ThreadSafe */
final class HlaExecutionConfigurationImpl implements HlaExecutionConfiguration {


   private static final String ROOT_FRAME_NAME_ATTRIBUTE_NAME = "root_frame_name";
   private static final String SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME = "scenario_time_epoch";
   private static final String CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME = "current_execution_mode";
   private static final String NEXT_EXECUTION_MODE_ATTRIBUTE_NAME = "next_execution_mode";
   private static final String NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME = "next_mode_scenario_time";
   private static final String NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME = "next_mode_cte_time";
   private static final String LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME = "least_common_time_step";

   public static final Set<String> HLA_ATTRIBUTE_NAMES = new SetAdapter<String>()
         .append(ROOT_FRAME_NAME_ATTRIBUTE_NAME)
         .append(SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME)
         .append(CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME)
         .append(NEXT_EXECUTION_MODE_ATTRIBUTE_NAME)
         .append(NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME)
         .append(NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME)
         .append(LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME)
         .unmodifiableSet();

   public static final Set<String> HLA_INITIALIZED_ATTRIBUTE_NAMES = new SetAdapter<String>()
         .append(ROOT_FRAME_NAME_ATTRIBUTE_NAME)
         .append(SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME)
         .append(CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME)
         .append(NEXT_EXECUTION_MODE_ATTRIBUTE_NAME)
         .append(NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME)
         .append(NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME)
         .append(LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME)
         .unmodifiableSet();

   private final HlaWorldImpl _hlaWorld;
   private final HlaExecutionConfigurationManagerImpl _objectClassManager;
   private final ObjectInstanceHandle _objectInstanceHandle;
   private final boolean _isLocal;
   private final HlaFederateId _createdByFederate;

   private final Object _attributesLock = new Object();
   private final Object _sendLock = new Object();

   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<String> _rootFrameNameAttribute = new AttributeValue<String>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<Double> _scenarioTimeEpochAttribute = new AttributeValue<Double>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<ExecutionMode> _currentExecutionModeAttribute = new AttributeValue<ExecutionMode>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<ExecutionMode> _nextExecutionModeAttribute = new AttributeValue<ExecutionMode>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<Double> _nextModeScenarioTimeAttribute = new AttributeValue<Double>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<Double> _nextModeCteTimeAttribute = new AttributeValue<Double>();
   /* @GuardedBy("_attributesLock") */
   private final AttributeValue<Long> _leastCommonTimeStepAttribute = new AttributeValue<Long>();

   private final Set<HlaExecutionConfigurationValueListener> _valueListeners = new CopyOnWriteArraySet<HlaExecutionConfigurationValueListener>();
   private final Set<HlaExecutionConfigurationListener> _listeners = new CopyOnWriteArraySet<HlaExecutionConfigurationListener>();

   private volatile boolean _initializeFired = false;
   private volatile boolean _removed = false;

   HlaExecutionConfigurationImpl(HlaWorldImpl hlaWorld, HlaExecutionConfigurationManagerImpl objectClassManager, ObjectInstanceHandle objectInstanceHandle, boolean local, HlaFederateId producingFederate) {
      _hlaWorld = hlaWorld;
      _objectClassManager = objectClassManager;
      _objectInstanceHandle = objectInstanceHandle;
      _isLocal = local;
      _createdByFederate = producingFederate;
   }

   /*
    * HlaExecutionConfiguration interface impl
    */

   public boolean isLocal() {
      return _isLocal;
   }

   public boolean isInitialized() {
      if (_initializeFired) {
         return true;
      }
      synchronized (_attributesLock) {
         updateRootFrameName();
         updateScenarioTimeEpoch();
         updateCurrentExecutionMode();
         updateNextExecutionMode();
         updateNextModeScenarioTime();
         updateNextModeCteTime();
         updateLeastCommonTimeStep();
         return
            _rootFrameNameAttribute.hasValue() &&
            _scenarioTimeEpochAttribute.hasValue() &&
            _currentExecutionModeAttribute.hasValue() &&
            _nextExecutionModeAttribute.hasValue() &&
            _nextModeScenarioTimeAttribute.hasValue() &&
            _nextModeCteTimeAttribute.hasValue() &&
            _leastCommonTimeStepAttribute.hasValue() 
         ;
      }
   }

   public boolean isWithinInterest() {
      return !_removed;
   }

   public boolean isRemoved() {
      return _removed;
   }

   public boolean hasRootFrameName() {
      synchronized (_attributesLock) {
         updateRootFrameName();
         return _rootFrameNameAttribute.hasValue();
      }
   }

   public String getRootFrameName() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateRootFrameName();
         if (_rootFrameNameAttribute.hasValue()) {
            return _rootFrameNameAttribute.getValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'rootFrameName' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   public String getRootFrameName(String defaultValue) {
      synchronized (_attributesLock) {
         updateRootFrameName();
         if (_rootFrameNameAttribute.hasValue()) {
            return _rootFrameNameAttribute.getValueForUser();
         }
      }
      return defaultValue;
   }

   public HlaTimeStamped<String> getRootFrameNameTimeStamped() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateRootFrameName();
         if (_rootFrameNameAttribute.hasValue()) {
            return _rootFrameNameAttribute.getTimestampedValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'rootFrameName' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   /* @GuardedBy("_attributesLock") */
   private void updateRootFrameName() {
      byte[] encodedValue = _rootFrameNameAttribute.getEncodedValue();
      if (encodedValue != null) {
         try {
            synchronized (_objectClassManager._decodersLock) {
               _objectClassManager._hLAunicodeStringDecoder.decode(encodedValue);
               _rootFrameNameAttribute.setDecodedValue(_objectClassManager._hLAunicodeStringDecoder.getValue());

               if (HlaTuning.VERIFY_RECEIVED_DATA_LENGTH && _objectClassManager._hLAunicodeStringDecoder.getEncodedLength() != encodedValue.length) {
                  _hlaWorld.postException(
                     new HlaDecodeException("Failed to decode " + ROOT_FRAME_NAME_ATTRIBUTE_NAME + ". " +
                                                  "Invalid data length " + encodedValue.length + ", expected " + _objectClassManager._hLAunicodeStringDecoder.getEncodedLength(),
                                                  encodedValue));
               }
            }
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaDecodeException("Failed to decode " + ROOT_FRAME_NAME_ATTRIBUTE_NAME, encodedValue, t));
            _rootFrameNameAttribute.clearEncodedValue();
         }
      }
   }

   public boolean hasScenarioTimeEpoch() {
      synchronized (_attributesLock) {
         updateScenarioTimeEpoch();
         return _scenarioTimeEpochAttribute.hasValue();
      }
   }

   public double getScenarioTimeEpoch() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateScenarioTimeEpoch();
         if (_scenarioTimeEpochAttribute.hasValue()) {
            return _scenarioTimeEpochAttribute.getValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'scenarioTimeEpoch' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   public double getScenarioTimeEpoch(double defaultValue) {
      synchronized (_attributesLock) {
         updateScenarioTimeEpoch();
         if (_scenarioTimeEpochAttribute.hasValue()) {
            return _scenarioTimeEpochAttribute.getValueForUser();
         }
      }
      return defaultValue;
   }

   public HlaTimeStamped<Double> getScenarioTimeEpochTimeStamped() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateScenarioTimeEpoch();
         if (_scenarioTimeEpochAttribute.hasValue()) {
            return _scenarioTimeEpochAttribute.getTimestampedValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'scenarioTimeEpoch' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   /* @GuardedBy("_attributesLock") */
   private void updateScenarioTimeEpoch() {
      byte[] encodedValue = _scenarioTimeEpochAttribute.getEncodedValue();
      if (encodedValue != null) {
         try {
            synchronized (_objectClassManager._decodersLock) {
               _objectClassManager._hLAfloat64LEDecoder.decode(encodedValue);
               _scenarioTimeEpochAttribute.setDecodedValue(_objectClassManager._hLAfloat64LEDecoder.getValue());

               if (HlaTuning.VERIFY_RECEIVED_DATA_LENGTH && _objectClassManager._hLAfloat64LEDecoder.getEncodedLength() != encodedValue.length) {
                  _hlaWorld.postException(
                     new HlaDecodeException("Failed to decode " + SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME + ". " +
                                                  "Invalid data length " + encodedValue.length + ", expected " + _objectClassManager._hLAfloat64LEDecoder.getEncodedLength(),
                                                  encodedValue));
               }
            }
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaDecodeException("Failed to decode " + SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME, encodedValue, t));
            _scenarioTimeEpochAttribute.clearEncodedValue();
         }
      }
   }

   public boolean hasCurrentExecutionMode() {
      synchronized (_attributesLock) {
         updateCurrentExecutionMode();
         return _currentExecutionModeAttribute.hasValue();
      }
   }

   public ExecutionMode getCurrentExecutionMode() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateCurrentExecutionMode();
         if (_currentExecutionModeAttribute.hasValue()) {
            return _currentExecutionModeAttribute.getValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'currentExecutionMode' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   public ExecutionMode getCurrentExecutionMode(ExecutionMode defaultValue) {
      synchronized (_attributesLock) {
         updateCurrentExecutionMode();
         if (_currentExecutionModeAttribute.hasValue()) {
            return _currentExecutionModeAttribute.getValueForUser();
         }
      }
      return defaultValue;
   }

   public HlaTimeStamped<ExecutionMode> getCurrentExecutionModeTimeStamped() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateCurrentExecutionMode();
         if (_currentExecutionModeAttribute.hasValue()) {
            return _currentExecutionModeAttribute.getTimestampedValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'currentExecutionMode' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   /* @GuardedBy("_attributesLock") */
   private void updateCurrentExecutionMode() {
      byte[] encodedValue = _currentExecutionModeAttribute.getEncodedValue();
      if (encodedValue != null) {
         try {
            synchronized (_objectClassManager._decodersLock) {
               _objectClassManager._executionModeEncoderDecoder.decode(encodedValue);
               _currentExecutionModeAttribute.setDecodedValue(_objectClassManager._executionModeEncoderDecoder.getValue());

               if (HlaTuning.VERIFY_RECEIVED_DATA_LENGTH && _objectClassManager._executionModeEncoderDecoder.getEncodedLength() != encodedValue.length) {
                  _hlaWorld.postException(
                     new HlaDecodeException("Failed to decode " + CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME + ". " +
                                                  "Invalid data length " + encodedValue.length + ", expected " + _objectClassManager._executionModeEncoderDecoder.getEncodedLength(),
                                                  encodedValue));
               }
            }
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaDecodeException("Failed to decode " + CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME, encodedValue, t));
            _currentExecutionModeAttribute.clearEncodedValue();
         }
      }
   }

   public boolean hasNextExecutionMode() {
      synchronized (_attributesLock) {
         updateNextExecutionMode();
         return _nextExecutionModeAttribute.hasValue();
      }
   }

   public ExecutionMode getNextExecutionMode() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateNextExecutionMode();
         if (_nextExecutionModeAttribute.hasValue()) {
            return _nextExecutionModeAttribute.getValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'nextExecutionMode' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   public ExecutionMode getNextExecutionMode(ExecutionMode defaultValue) {
      synchronized (_attributesLock) {
         updateNextExecutionMode();
         if (_nextExecutionModeAttribute.hasValue()) {
            return _nextExecutionModeAttribute.getValueForUser();
         }
      }
      return defaultValue;
   }

   public HlaTimeStamped<ExecutionMode> getNextExecutionModeTimeStamped() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateNextExecutionMode();
         if (_nextExecutionModeAttribute.hasValue()) {
            return _nextExecutionModeAttribute.getTimestampedValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'nextExecutionMode' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   /* @GuardedBy("_attributesLock") */
   private void updateNextExecutionMode() {
      byte[] encodedValue = _nextExecutionModeAttribute.getEncodedValue();
      if (encodedValue != null) {
         try {
            synchronized (_objectClassManager._decodersLock) {
               _objectClassManager._executionModeEncoderDecoder.decode(encodedValue);
               _nextExecutionModeAttribute.setDecodedValue(_objectClassManager._executionModeEncoderDecoder.getValue());

               if (HlaTuning.VERIFY_RECEIVED_DATA_LENGTH && _objectClassManager._executionModeEncoderDecoder.getEncodedLength() != encodedValue.length) {
                  _hlaWorld.postException(
                     new HlaDecodeException("Failed to decode " + NEXT_EXECUTION_MODE_ATTRIBUTE_NAME + ". " +
                                                  "Invalid data length " + encodedValue.length + ", expected " + _objectClassManager._executionModeEncoderDecoder.getEncodedLength(),
                                                  encodedValue));
               }
            }
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaDecodeException("Failed to decode " + NEXT_EXECUTION_MODE_ATTRIBUTE_NAME, encodedValue, t));
            _nextExecutionModeAttribute.clearEncodedValue();
         }
      }
   }

   public boolean hasNextModeScenarioTime() {
      synchronized (_attributesLock) {
         updateNextModeScenarioTime();
         return _nextModeScenarioTimeAttribute.hasValue();
      }
   }

   public double getNextModeScenarioTime() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateNextModeScenarioTime();
         if (_nextModeScenarioTimeAttribute.hasValue()) {
            return _nextModeScenarioTimeAttribute.getValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'nextModeScenarioTime' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   public double getNextModeScenarioTime(double defaultValue) {
      synchronized (_attributesLock) {
         updateNextModeScenarioTime();
         if (_nextModeScenarioTimeAttribute.hasValue()) {
            return _nextModeScenarioTimeAttribute.getValueForUser();
         }
      }
      return defaultValue;
   }

   public HlaTimeStamped<Double> getNextModeScenarioTimeTimeStamped() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateNextModeScenarioTime();
         if (_nextModeScenarioTimeAttribute.hasValue()) {
            return _nextModeScenarioTimeAttribute.getTimestampedValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'nextModeScenarioTime' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   /* @GuardedBy("_attributesLock") */
   private void updateNextModeScenarioTime() {
      byte[] encodedValue = _nextModeScenarioTimeAttribute.getEncodedValue();
      if (encodedValue != null) {
         try {
            synchronized (_objectClassManager._decodersLock) {
               _objectClassManager._hLAfloat64LEDecoder.decode(encodedValue);
               _nextModeScenarioTimeAttribute.setDecodedValue(_objectClassManager._hLAfloat64LEDecoder.getValue());

               if (HlaTuning.VERIFY_RECEIVED_DATA_LENGTH && _objectClassManager._hLAfloat64LEDecoder.getEncodedLength() != encodedValue.length) {
                  _hlaWorld.postException(
                     new HlaDecodeException("Failed to decode " + NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME + ". " +
                                                  "Invalid data length " + encodedValue.length + ", expected " + _objectClassManager._hLAfloat64LEDecoder.getEncodedLength(),
                                                  encodedValue));
               }
            }
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaDecodeException("Failed to decode " + NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME, encodedValue, t));
            _nextModeScenarioTimeAttribute.clearEncodedValue();
         }
      }
   }

   public boolean hasNextModeCteTime() {
      synchronized (_attributesLock) {
         updateNextModeCteTime();
         return _nextModeCteTimeAttribute.hasValue();
      }
   }

   public double getNextModeCteTime() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateNextModeCteTime();
         if (_nextModeCteTimeAttribute.hasValue()) {
            return _nextModeCteTimeAttribute.getValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'nextModeCteTime' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   public double getNextModeCteTime(double defaultValue) {
      synchronized (_attributesLock) {
         updateNextModeCteTime();
         if (_nextModeCteTimeAttribute.hasValue()) {
            return _nextModeCteTimeAttribute.getValueForUser();
         }
      }
      return defaultValue;
   }

   public HlaTimeStamped<Double> getNextModeCteTimeTimeStamped() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateNextModeCteTime();
         if (_nextModeCteTimeAttribute.hasValue()) {
            return _nextModeCteTimeAttribute.getTimestampedValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'nextModeCteTime' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   /* @GuardedBy("_attributesLock") */
   private void updateNextModeCteTime() {
      byte[] encodedValue = _nextModeCteTimeAttribute.getEncodedValue();
      if (encodedValue != null) {
         try {
            synchronized (_objectClassManager._decodersLock) {
               _objectClassManager._hLAfloat64LEDecoder.decode(encodedValue);
               _nextModeCteTimeAttribute.setDecodedValue(_objectClassManager._hLAfloat64LEDecoder.getValue());

               if (HlaTuning.VERIFY_RECEIVED_DATA_LENGTH && _objectClassManager._hLAfloat64LEDecoder.getEncodedLength() != encodedValue.length) {
                  _hlaWorld.postException(
                     new HlaDecodeException("Failed to decode " + NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME + ". " +
                                                  "Invalid data length " + encodedValue.length + ", expected " + _objectClassManager._hLAfloat64LEDecoder.getEncodedLength(),
                                                  encodedValue));
               }
            }
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaDecodeException("Failed to decode " + NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME, encodedValue, t));
            _nextModeCteTimeAttribute.clearEncodedValue();
         }
      }
   }

   public boolean hasLeastCommonTimeStep() {
      synchronized (_attributesLock) {
         updateLeastCommonTimeStep();
         return _leastCommonTimeStepAttribute.hasValue();
      }
   }

   public long getLeastCommonTimeStep() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateLeastCommonTimeStep();
         if (_leastCommonTimeStepAttribute.hasValue()) {
            return _leastCommonTimeStepAttribute.getValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'leastCommonTimeStep' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   public long getLeastCommonTimeStep(long defaultValue) {
      synchronized (_attributesLock) {
         updateLeastCommonTimeStep();
         if (_leastCommonTimeStepAttribute.hasValue()) {
            return _leastCommonTimeStepAttribute.getValueForUser();
         }
      }
      return defaultValue;
   }

   public HlaTimeStamped<Long> getLeastCommonTimeStepTimeStamped() throws HlaValueNotSetException {
      synchronized (_attributesLock) {
         updateLeastCommonTimeStep();
         if (_leastCommonTimeStepAttribute.hasValue()) {
            return _leastCommonTimeStepAttribute.getTimestampedValueForUser();
         }
      }
      throw new HlaValueNotSetException("Attribute 'leastCommonTimeStep' not set for instance " + _objectInstanceHandle.getObjectInstanceName());
   }

   /* @GuardedBy("_attributesLock") */
   private void updateLeastCommonTimeStep() {
      byte[] encodedValue = _leastCommonTimeStepAttribute.getEncodedValue();
      if (encodedValue != null) {
         try {
            synchronized (_objectClassManager._decodersLock) {
               _objectClassManager._hLAinteger64BEDecoder.decode(encodedValue);
               _leastCommonTimeStepAttribute.setDecodedValue(_objectClassManager._hLAinteger64BEDecoder.getValue());

               if (HlaTuning.VERIFY_RECEIVED_DATA_LENGTH && _objectClassManager._hLAinteger64BEDecoder.getEncodedLength() != encodedValue.length) {
                  _hlaWorld.postException(
                     new HlaDecodeException("Failed to decode " + LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME + ". " +
                                                  "Invalid data length " + encodedValue.length + ", expected " + _objectClassManager._hLAinteger64BEDecoder.getEncodedLength(),
                                                  encodedValue));
               }
            }
         } catch (Throwable t) {
            _hlaWorld.postException(new HlaDecodeException("Failed to decode " + LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME, encodedValue, t));
            _leastCommonTimeStepAttribute.clearEncodedValue();
         }
      }
   }

   public HlaFederateId getProducingFederate() {
      return _createdByFederate;
   }

   public HlaFederateId getLastProducingFederate(HlaExecutionConfigurationAttributes.Attribute attribute) {
      synchronized (_attributesLock) {
         switch (attribute) {
            case ROOT_FRAME_NAME:
               if (_rootFrameNameAttribute.hasLastProducingFederate()) {
                  return _rootFrameNameAttribute.getLastProducingFederate();
               }
            case SCENARIO_TIME_EPOCH:
               if (_scenarioTimeEpochAttribute.hasLastProducingFederate()) {
                  return _scenarioTimeEpochAttribute.getLastProducingFederate();
               }
            case CURRENT_EXECUTION_MODE:
               if (_currentExecutionModeAttribute.hasLastProducingFederate()) {
                  return _currentExecutionModeAttribute.getLastProducingFederate();
               }
            case NEXT_EXECUTION_MODE:
               if (_nextExecutionModeAttribute.hasLastProducingFederate()) {
                  return _nextExecutionModeAttribute.getLastProducingFederate();
               }
            case NEXT_MODE_SCENARIO_TIME:
               if (_nextModeScenarioTimeAttribute.hasLastProducingFederate()) {
                  return _nextModeScenarioTimeAttribute.getLastProducingFederate();
               }
            case NEXT_MODE_CTE_TIME:
               if (_nextModeCteTimeAttribute.hasLastProducingFederate()) {
                  return _nextModeCteTimeAttribute.getLastProducingFederate();
               }
            case LEAST_COMMON_TIME_STEP:
               if (_leastCommonTimeStepAttribute.hasLastProducingFederate()) {
                  return _leastCommonTimeStepAttribute.getLastProducingFederate();
               }
         }
      }
      return new HlaFederateIdImpl(null);
   }

   public HlaExecutionConfigurationAttributes getHlaExecutionConfigurationAttributes() {
      synchronized (_attributesLock) {
         updateRootFrameName();
         updateScenarioTimeEpoch();
         updateCurrentExecutionMode();
         updateNextExecutionMode();
         updateNextModeScenarioTime();
         updateNextModeCteTime();
         updateLeastCommonTimeStep();
         return new HlaExecutionConfigurationAttributesImpl(
            _rootFrameNameAttribute,
            _scenarioTimeEpochAttribute,
            _currentExecutionModeAttribute,
            _nextExecutionModeAttribute,
            _nextModeScenarioTimeAttribute,
            _nextModeCteTimeAttribute,
            _leastCommonTimeStepAttribute
         );
      }
   }

   public HlaExecutionConfigurationUpdater getHlaExecutionConfigurationUpdater() {
      return new HlaExecutionConfigurationUpdaterImpl(this);
   }

   public String getHlaInstanceName() {
      return _objectInstanceHandle.getObjectInstanceName();
   }

   public byte[] getEncodedHlaObjectInstanceHandle() {
      return _objectInstanceHandle.getEncodedHandle();
   }

   public ObjectClassType getClassType() {
      return ObjectClassType.EXECUTION_CONFIGURATION;
   }


   public void addHlaExecutionConfigurationValueListener(HlaExecutionConfigurationValueListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _valueListeners.add(listener);
   }

   public void removeHlaExecutionConfigurationValueListener(HlaExecutionConfigurationValueListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _valueListeners.remove(listener);
   }

   public void addHlaExecutionConfigurationListener(HlaExecutionConfigurationListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.add(listener);
   }

   public void removeHlaExecutionConfigurationListener(HlaExecutionConfigurationListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
      _listeners.remove(listener);
   }

   /*
    * Functions used by HlaExecutionConfigurationManagerImpl
    */

   boolean isInitializedFired() {
      return _initializeFired;
   }

   void setInitializedFired() {
      _initializeFired = true;
   }

   ObjectInstanceHandle getObjectInstanceHandle() {
      return _objectInstanceHandle;
   }

   void setRemoved() {
      _removed = true;
      _valueListeners.clear();
      _listeners.clear();
   }

   void setCreateAttributes(
      String rootFrameName,
      double scenarioTimeEpoch,
      HlaTimeStamp timestamp) {
      EnumMap<HlaExecutionConfigurationAttributes.Attribute, Object> attributeUpdates =
            new EnumMap<HlaExecutionConfigurationAttributes.Attribute, Object>(HlaExecutionConfigurationAttributes.Attribute.class);

      attributeUpdates.put(HlaExecutionConfigurationAttributes.Attribute.ROOT_FRAME_NAME, rootFrameName);
      attributeUpdates.put(HlaExecutionConfigurationAttributes.Attribute.SCENARIO_TIME_EPOCH, scenarioTimeEpoch);

      try {
         applyUpdate(attributeUpdates, EnumSet.noneOf(HlaExecutionConfigurationAttributes.Attribute.class), timestamp, HlaLogicalTimeImpl.INVALID);
      } catch (HlaBaseException e) {
         _hlaWorld.postException(new HlaInternalException("Failed to set create attributes " + this, e));
      } catch (HlaBaseRuntimeException e) {
         _hlaWorld.postException(new HlaInternalException("Failed to set create attributes " + this, e));
      }
   }

   void provideAttributeValueUpdate(Set<String> attributes, HlaTimeStamp hlaTimeStamp) {
      try {
         synchronized (_sendLock) {
            sendAttributes(getAttributeEnum(attributes), getHlaExecutionConfigurationAttributes(), hlaTimeStamp, HlaLogicalTimeImpl.INVALID);
         }
      } catch (HlaBaseException e) {
         //ignore
      } catch (HlaBaseRuntimeException e) {
         //ignore
      }
   }

   Set<String> getUnsetAttributeNames() {
      Set<String> unsetAttributes = new HashSet<String>(HLA_ATTRIBUTE_NAMES.size());
      HlaExecutionConfigurationAttributes attributes = getHlaExecutionConfigurationAttributes();
      if (!attributes.hasRootFrameName()) {
         unsetAttributes.add(ROOT_FRAME_NAME_ATTRIBUTE_NAME);
      }
      if (!attributes.hasScenarioTimeEpoch()) {
         unsetAttributes.add(SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME);
      }
      if (!attributes.hasCurrentExecutionMode()) {
         unsetAttributes.add(CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME);
      }
      if (!attributes.hasNextExecutionMode()) {
         unsetAttributes.add(NEXT_EXECUTION_MODE_ATTRIBUTE_NAME);
      }
      if (!attributes.hasNextModeScenarioTime()) {
         unsetAttributes.add(NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME);
      }
      if (!attributes.hasNextModeCteTime()) {
         unsetAttributes.add(NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME);
      }
      if (!attributes.hasLeastCommonTimeStep()) {
         unsetAttributes.add(LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME);
      }
      return unsetAttributes;
   }

   @SuppressWarnings ({ "UnnecessaryContinue" })
   void reflectAttributeValues(NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      Set<HlaExecutionConfigurationAttributes.Attribute> attributesUpdated = EnumSet.noneOf(HlaExecutionConfigurationAttributes.Attribute.class);

      synchronized (_attributesLock) {
         for (Map.Entry<String, byte[]> newAttribute : attributes.entrySet()) {
            String attributeName = newAttribute.getKey();
            byte[] attributeValue = newAttribute.getValue();

            HlaExecutionConfigurationAttributes.Attribute attribute = HlaExecutionConfigurationAttributes.Attribute.find(attributeName);
            if (attribute == null) {
               continue;
            }

            switch (attribute) {
               case ROOT_FRAME_NAME: {
                  attributesUpdated.add(HlaExecutionConfigurationAttributes.Attribute.ROOT_FRAME_NAME);

                  if (_valueListeners.isEmpty()) {
                     _rootFrameNameAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);
                  } else {
                     updateRootFrameName();
                     AttributeValue<String> oldValue = new AttributeValue<String>(_rootFrameNameAttribute);

                     _rootFrameNameAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);

                     updateRootFrameName();
                     if (_rootFrameNameAttribute.hasValue()) {
                        fireRootFrameNameUpdated(_rootFrameNameAttribute.getValue(), oldValue, timeStamp, logicalTime);
                     }
                     fireProducingFederateUpdatedIfChanged(Attribute.ROOT_FRAME_NAME, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  }
                  break;
               }
               case SCENARIO_TIME_EPOCH: {
                  attributesUpdated.add(HlaExecutionConfigurationAttributes.Attribute.SCENARIO_TIME_EPOCH);

                  if (_valueListeners.isEmpty()) {
                     _scenarioTimeEpochAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);
                  } else {
                     updateScenarioTimeEpoch();
                     AttributeValue<Double> oldValue = new AttributeValue<Double>(_scenarioTimeEpochAttribute);

                     _scenarioTimeEpochAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);

                     updateScenarioTimeEpoch();
                     if (_scenarioTimeEpochAttribute.hasValue()) {
                        fireScenarioTimeEpochUpdated(_scenarioTimeEpochAttribute.getValue(), oldValue, timeStamp, logicalTime);
                     }
                     fireProducingFederateUpdatedIfChanged(Attribute.SCENARIO_TIME_EPOCH, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  }
                  break;
               }
               case CURRENT_EXECUTION_MODE: {
                  attributesUpdated.add(HlaExecutionConfigurationAttributes.Attribute.CURRENT_EXECUTION_MODE);

                  if (_valueListeners.isEmpty()) {
                     _currentExecutionModeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);
                  } else {
                     updateCurrentExecutionMode();
                     AttributeValue<ExecutionMode> oldValue = new AttributeValue<ExecutionMode>(_currentExecutionModeAttribute);

                     _currentExecutionModeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);

                     updateCurrentExecutionMode();
                     if (_currentExecutionModeAttribute.hasValue()) {
                        fireCurrentExecutionModeUpdated(_currentExecutionModeAttribute.getValue(), oldValue, timeStamp, logicalTime);
                     }
                     fireProducingFederateUpdatedIfChanged(Attribute.CURRENT_EXECUTION_MODE, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  }
                  break;
               }
               case NEXT_EXECUTION_MODE: {
                  attributesUpdated.add(HlaExecutionConfigurationAttributes.Attribute.NEXT_EXECUTION_MODE);

                  if (_valueListeners.isEmpty()) {
                     _nextExecutionModeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);
                  } else {
                     updateNextExecutionMode();
                     AttributeValue<ExecutionMode> oldValue = new AttributeValue<ExecutionMode>(_nextExecutionModeAttribute);

                     _nextExecutionModeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);

                     updateNextExecutionMode();
                     if (_nextExecutionModeAttribute.hasValue()) {
                        fireNextExecutionModeUpdated(_nextExecutionModeAttribute.getValue(), oldValue, timeStamp, logicalTime);
                     }
                     fireProducingFederateUpdatedIfChanged(Attribute.NEXT_EXECUTION_MODE, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  }
                  break;
               }
               case NEXT_MODE_SCENARIO_TIME: {
                  attributesUpdated.add(HlaExecutionConfigurationAttributes.Attribute.NEXT_MODE_SCENARIO_TIME);

                  if (_valueListeners.isEmpty()) {
                     _nextModeScenarioTimeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);
                  } else {
                     updateNextModeScenarioTime();
                     AttributeValue<Double> oldValue = new AttributeValue<Double>(_nextModeScenarioTimeAttribute);

                     _nextModeScenarioTimeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);

                     updateNextModeScenarioTime();
                     if (_nextModeScenarioTimeAttribute.hasValue()) {
                        fireNextModeScenarioTimeUpdated(_nextModeScenarioTimeAttribute.getValue(), oldValue, timeStamp, logicalTime);
                     }
                     fireProducingFederateUpdatedIfChanged(Attribute.NEXT_MODE_SCENARIO_TIME, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  }
                  break;
               }
               case NEXT_MODE_CTE_TIME: {
                  attributesUpdated.add(HlaExecutionConfigurationAttributes.Attribute.NEXT_MODE_CTE_TIME);

                  if (_valueListeners.isEmpty()) {
                     _nextModeCteTimeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);
                  } else {
                     updateNextModeCteTime();
                     AttributeValue<Double> oldValue = new AttributeValue<Double>(_nextModeCteTimeAttribute);

                     _nextModeCteTimeAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);

                     updateNextModeCteTime();
                     if (_nextModeCteTimeAttribute.hasValue()) {
                        fireNextModeCteTimeUpdated(_nextModeCteTimeAttribute.getValue(), oldValue, timeStamp, logicalTime);
                     }
                     fireProducingFederateUpdatedIfChanged(Attribute.NEXT_MODE_CTE_TIME, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  }
                  break;
               }
               case LEAST_COMMON_TIME_STEP: {
                  attributesUpdated.add(HlaExecutionConfigurationAttributes.Attribute.LEAST_COMMON_TIME_STEP);

                  if (_valueListeners.isEmpty()) {
                     _leastCommonTimeStepAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);
                  } else {
                     updateLeastCommonTimeStep();
                     AttributeValue<Long> oldValue = new AttributeValue<Long>(_leastCommonTimeStepAttribute);

                     _leastCommonTimeStepAttribute.setEncodedValue(attributeValue, timeStamp, logicalTime, producingFederate);

                     updateLeastCommonTimeStep();
                     if (_leastCommonTimeStepAttribute.hasValue()) {
                        fireLeastCommonTimeStepUpdated(_leastCommonTimeStepAttribute.getValue(), oldValue, timeStamp, logicalTime);
                     }
                     fireProducingFederateUpdatedIfChanged(Attribute.LEAST_COMMON_TIME_STEP, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  }
                  break;
               }
            }
         }
      }

      fireAttributesUpdated(attributesUpdated, timeStamp, logicalTime);
   }



   /*
    * Functions used by HlaExecutionConfigurationUpdaterImpl
    */

   void applyUpdate(EnumMap<HlaExecutionConfigurationAttributes.Attribute, Object> attributeMap,
                    EnumSet<HlaExecutionConfigurationAttributes.Attribute> sendAttributes,
                    HlaTimeStamp timestamp,
                    HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInvalidLogicalTimeException, HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (timestamp == null) {
         timestamp = _hlaWorld.getCurrentTime();
      }
      if (logicalTime == null) {
         logicalTime = _hlaWorld.getDefaultSendTime();
      }
      synchronized (_sendLock) {
         HlaExecutionConfigurationAttributes attributes = applyUpdate(attributeMap, timestamp, logicalTime, _hlaWorld.getFederateId());
         sendAttributes.addAll(addDefaultSendAttributes(attributeMap.keySet()));
         sendAttributes(sendAttributes, attributes, timestamp, logicalTime);
      }
   }


   /*
    * Private functions
    */

   private Set<HlaExecutionConfigurationAttributes.Attribute> addDefaultSendAttributes(Set<HlaExecutionConfigurationAttributes.Attribute> attributes) {
      return attributes;
   }

   private void fireRootFrameNameUpdated(final String value, AttributeValue<String> previous, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      final boolean oldValid = previous.hasValue();
      final String oldValue = oldValid ? previous.getValue() : value;

      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.rootFrameNameUpdated(instance, value, oldValid, oldValue, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireScenarioTimeEpochUpdated(final double value, AttributeValue<Double> previous, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      final boolean oldValid = previous.hasValue();
      final double oldValue = oldValid ? previous.getValue() : value;

      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.scenarioTimeEpochUpdated(instance, value, oldValid, oldValue, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireCurrentExecutionModeUpdated(final ExecutionMode value, AttributeValue<ExecutionMode> previous, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      final boolean oldValid = previous.hasValue();
      final ExecutionMode oldValue = oldValid ? previous.getValue() : value;

      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.currentExecutionModeUpdated(instance, value, oldValid, oldValue, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireNextExecutionModeUpdated(final ExecutionMode value, AttributeValue<ExecutionMode> previous, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      final boolean oldValid = previous.hasValue();
      final ExecutionMode oldValue = oldValid ? previous.getValue() : value;

      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.nextExecutionModeUpdated(instance, value, oldValid, oldValue, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireNextModeScenarioTimeUpdated(final double value, AttributeValue<Double> previous, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      final boolean oldValid = previous.hasValue();
      final double oldValue = oldValid ? previous.getValue() : value;

      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.nextModeScenarioTimeUpdated(instance, value, oldValid, oldValue, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireNextModeCteTimeUpdated(final double value, AttributeValue<Double> previous, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      final boolean oldValid = previous.hasValue();
      final double oldValue = oldValid ? previous.getValue() : value;

      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.nextModeCteTimeUpdated(instance, value, oldValid, oldValue, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireLeastCommonTimeStepUpdated(final long value, AttributeValue<Long> previous, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      final boolean oldValid = previous.hasValue();
      final long oldValue = oldValid ? previous.getValue() : value;

      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.leastCommonTimeStepUpdated(instance, value, oldValid, oldValue, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireAttributesUpdated(final Set<HlaExecutionConfigurationAttributes.Attribute> attributes, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      final HlaExecutionConfiguration instance = this;
      for (final HlaExecutionConfigurationListener listener : _listeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               listener.attributesUpdated(instance, attributes, timeStamp, logicalTime);
            }
         });
      }
   }

   private void fireProducingFederateUpdatedIfChanged(final HlaExecutionConfigurationAttributes.Attribute attribute, final HlaFederateId oldProducingFederate, final HlaFederateId newProducingFederate, final HlaTimeStamp timeStamp, final HlaLogicalTime logicalTime) {
      if (Equals.isEquals(oldProducingFederate, newProducingFederate)) {
         return;
      }

      final HlaExecutionConfiguration instance = this;
      for (final HlaExecutionConfigurationValueListener valueListener : _valueListeners) {
         _hlaWorld.postNotification(new Runnable() {
            public void run() {
               valueListener.producingFederateUpdated(instance, attribute, oldProducingFederate, newProducingFederate, timeStamp, logicalTime);
            }
         });
      }
   }

   @SuppressWarnings ({ "UnnecessaryContinue" })
   private Set<HlaExecutionConfigurationAttributes.Attribute> getAttributeEnum(Set<String> attributeNames) {
      Set<HlaExecutionConfigurationAttributes.Attribute> ret = EnumSet.noneOf(HlaExecutionConfigurationAttributes.Attribute.class);

      for (String attributeName : attributeNames) {
         HlaExecutionConfigurationAttributes.Attribute attribute = HlaExecutionConfigurationAttributes.Attribute.find(attributeName);
         if (attribute != null) {
            ret.add(attribute);
         }
      }
      return ret;
   }

   /* @GuardedBy("_sendLock") */
   private HlaExecutionConfigurationAttributes applyUpdate(EnumMap<HlaExecutionConfigurationAttributes.Attribute, Object> attributeUpdates, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      HlaExecutionConfigurationAttributes ret;

      synchronized (_attributesLock) {
         for (Map.Entry<HlaExecutionConfigurationAttributes.Attribute, Object> attributeUpdate : attributeUpdates.entrySet()) {
            switch (attributeUpdate.getKey()) {
               case ROOT_FRAME_NAME: {
                  updateRootFrameName();
                  AttributeValue<String> oldValue = new AttributeValue<String>(_rootFrameNameAttribute);

                  String value = (String)attributeUpdate.getValue();
                  _rootFrameNameAttribute.setValue(value, timeStamp, logicalTime, producingFederate);

                  fireRootFrameNameUpdated(value, oldValue, timeStamp, logicalTime);
                  fireProducingFederateUpdatedIfChanged(Attribute.ROOT_FRAME_NAME, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  break;
               }
               case SCENARIO_TIME_EPOCH: {
                  updateScenarioTimeEpoch();
                  AttributeValue<Double> oldValue = new AttributeValue<Double>(_scenarioTimeEpochAttribute);

                  double value = (Double)attributeUpdate.getValue();
                  _scenarioTimeEpochAttribute.setValue(value, timeStamp, logicalTime, producingFederate);

                  fireScenarioTimeEpochUpdated(value, oldValue, timeStamp, logicalTime);
                  fireProducingFederateUpdatedIfChanged(Attribute.SCENARIO_TIME_EPOCH, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  break;
               }
               case CURRENT_EXECUTION_MODE: {
                  updateCurrentExecutionMode();
                  AttributeValue<ExecutionMode> oldValue = new AttributeValue<ExecutionMode>(_currentExecutionModeAttribute);

                  ExecutionMode value = (ExecutionMode)attributeUpdate.getValue();
                  _currentExecutionModeAttribute.setValue(value, timeStamp, logicalTime, producingFederate);

                  fireCurrentExecutionModeUpdated(value, oldValue, timeStamp, logicalTime);
                  fireProducingFederateUpdatedIfChanged(Attribute.CURRENT_EXECUTION_MODE, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  break;
               }
               case NEXT_EXECUTION_MODE: {
                  updateNextExecutionMode();
                  AttributeValue<ExecutionMode> oldValue = new AttributeValue<ExecutionMode>(_nextExecutionModeAttribute);

                  ExecutionMode value = (ExecutionMode)attributeUpdate.getValue();
                  _nextExecutionModeAttribute.setValue(value, timeStamp, logicalTime, producingFederate);

                  fireNextExecutionModeUpdated(value, oldValue, timeStamp, logicalTime);
                  fireProducingFederateUpdatedIfChanged(Attribute.NEXT_EXECUTION_MODE, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  break;
               }
               case NEXT_MODE_SCENARIO_TIME: {
                  updateNextModeScenarioTime();
                  AttributeValue<Double> oldValue = new AttributeValue<Double>(_nextModeScenarioTimeAttribute);

                  double value = (Double)attributeUpdate.getValue();
                  _nextModeScenarioTimeAttribute.setValue(value, timeStamp, logicalTime, producingFederate);

                  fireNextModeScenarioTimeUpdated(value, oldValue, timeStamp, logicalTime);
                  fireProducingFederateUpdatedIfChanged(Attribute.NEXT_MODE_SCENARIO_TIME, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  break;
               }
               case NEXT_MODE_CTE_TIME: {
                  updateNextModeCteTime();
                  AttributeValue<Double> oldValue = new AttributeValue<Double>(_nextModeCteTimeAttribute);

                  double value = (Double)attributeUpdate.getValue();
                  _nextModeCteTimeAttribute.setValue(value, timeStamp, logicalTime, producingFederate);

                  fireNextModeCteTimeUpdated(value, oldValue, timeStamp, logicalTime);
                  fireProducingFederateUpdatedIfChanged(Attribute.NEXT_MODE_CTE_TIME, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  break;
               }
               case LEAST_COMMON_TIME_STEP: {
                  updateLeastCommonTimeStep();
                  AttributeValue<Long> oldValue = new AttributeValue<Long>(_leastCommonTimeStepAttribute);

                  long value = (Long)attributeUpdate.getValue();
                  _leastCommonTimeStepAttribute.setValue(value, timeStamp, logicalTime, producingFederate);

                  fireLeastCommonTimeStepUpdated(value, oldValue, timeStamp, logicalTime);
                  fireProducingFederateUpdatedIfChanged(Attribute.LEAST_COMMON_TIME_STEP, oldValue.getLastProducingFederate(), producingFederate, timeStamp, logicalTime);
                  break;
               }
            }
         }
         ret = getHlaExecutionConfigurationAttributes();
      }

      if (!attributeUpdates.isEmpty()) {
         fireAttributesUpdated(attributeUpdates.keySet(), timeStamp, logicalTime);
      }

      return ret;
   }

   /* @GuardedBy("_sendLock") */
   private void sendAttributes(Set<HlaExecutionConfigurationAttributes.Attribute> attributes, HlaExecutionConfigurationAttributes attributeValues, HlaTimeStamp timestamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      NameValueMap attributeValueMap = new NameValueMap();

      synchronized (_objectClassManager._encodersLock) {
         for (HlaExecutionConfigurationAttributes.Attribute attribute : attributes) {
            switch (attribute) {
               case ROOT_FRAME_NAME:
                  if (attributeValues.hasRootFrameName()) {
                     try {
                        _objectClassManager._hLAunicodeStringEncoder.setValue(attributeValues.getRootFrameName());
                        attributeValueMap.put(ROOT_FRAME_NAME_ATTRIBUTE_NAME, _objectClassManager._hLAunicodeStringEncoder.toByteArray(), false);
                     } catch (Throwable t) {
                        _hlaWorld.postException(new HlaDecodeException("Failed to encode " + ROOT_FRAME_NAME_ATTRIBUTE_NAME, t));
                     }
                  }
                  break;
               case SCENARIO_TIME_EPOCH:
                  if (attributeValues.hasScenarioTimeEpoch()) {
                     try {
                        _objectClassManager._hLAfloat64LEEncoder.setValue(attributeValues.getScenarioTimeEpoch());
                        attributeValueMap.put(SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME, _objectClassManager._hLAfloat64LEEncoder.toByteArray(), false);
                     } catch (Throwable t) {
                        _hlaWorld.postException(new HlaDecodeException("Failed to encode " + SCENARIO_TIME_EPOCH_ATTRIBUTE_NAME, t));
                     }
                  }
                  break;
               case CURRENT_EXECUTION_MODE:
                  if (attributeValues.hasCurrentExecutionMode()) {
                     try {
                        _objectClassManager._executionModeEncoderEncoder.setValue(attributeValues.getCurrentExecutionMode());
                        attributeValueMap.put(CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME, _objectClassManager._executionModeEncoderEncoder.toByteArray(), false);
                     } catch (Throwable t) {
                        _hlaWorld.postException(new HlaDecodeException("Failed to encode " + CURRENT_EXECUTION_MODE_ATTRIBUTE_NAME, t));
                     }
                  }
                  break;
               case NEXT_EXECUTION_MODE:
                  if (attributeValues.hasNextExecutionMode()) {
                     try {
                        _objectClassManager._executionModeEncoderEncoder.setValue(attributeValues.getNextExecutionMode());
                        attributeValueMap.put(NEXT_EXECUTION_MODE_ATTRIBUTE_NAME, _objectClassManager._executionModeEncoderEncoder.toByteArray(), false);
                     } catch (Throwable t) {
                        _hlaWorld.postException(new HlaDecodeException("Failed to encode " + NEXT_EXECUTION_MODE_ATTRIBUTE_NAME, t));
                     }
                  }
                  break;
               case NEXT_MODE_SCENARIO_TIME:
                  if (attributeValues.hasNextModeScenarioTime()) {
                     try {
                        _objectClassManager._hLAfloat64LEEncoder.setValue(attributeValues.getNextModeScenarioTime());
                        attributeValueMap.put(NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME, _objectClassManager._hLAfloat64LEEncoder.toByteArray(), false);
                     } catch (Throwable t) {
                        _hlaWorld.postException(new HlaDecodeException("Failed to encode " + NEXT_MODE_SCENARIO_TIME_ATTRIBUTE_NAME, t));
                     }
                  }
                  break;
               case NEXT_MODE_CTE_TIME:
                  if (attributeValues.hasNextModeCteTime()) {
                     try {
                        _objectClassManager._hLAfloat64LEEncoder.setValue(attributeValues.getNextModeCteTime());
                        attributeValueMap.put(NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME, _objectClassManager._hLAfloat64LEEncoder.toByteArray(), false);
                     } catch (Throwable t) {
                        _hlaWorld.postException(new HlaDecodeException("Failed to encode " + NEXT_MODE_CTE_TIME_ATTRIBUTE_NAME, t));
                     }
                  }
                  break;
               case LEAST_COMMON_TIME_STEP:
                  if (attributeValues.hasLeastCommonTimeStep()) {
                     try {
                        _objectClassManager._hLAinteger64BEEncoder.setValue(attributeValues.getLeastCommonTimeStep());
                        attributeValueMap.put(LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME, _objectClassManager._hLAinteger64BEEncoder.toByteArray(), false);
                     } catch (Throwable t) {
                        _hlaWorld.postException(new HlaDecodeException("Failed to encode " + LEAST_COMMON_TIME_STEP_ATTRIBUTE_NAME, t));
                     }
                  }
                  break;
            }
         }
      }

      if (!attributeValueMap.isEmpty()) {
         _objectClassManager.updateAttributeValues(this, _objectInstanceHandle, attributeValueMap, timestamp, logicalTime);
      }
   }
}
