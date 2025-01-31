package uk.ac.brunel.see.impl.fed;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import se.pitch.ral.api.*;

import java.util.Map;
import java.util.Set;


abstract class FederateAmbassadorAdapter implements FederateAmbassador {
   public abstract void connectionLost(String string);

   public abstract void synchronizationPointRegistrationSucceeded(String synchPointName);

   public abstract void synchronizationPointRegistrationFailed(String synchPointName, SynchronizationPointFailureReason failureReason);

   public abstract void announceSynchronizationPoint(String synchPointName, byte[] userSuppliedTag);

   public abstract void federationSynchronized(String synchPointName);

   public abstract void initiateFederateSave(String label, LogicalTime logicalTime);

   public abstract void federationSaved();

   public abstract void federationNotSaved(SaveFailureReason saveFailureReason);

   public abstract void requestFederationRestoreSucceeded(String label);

   public abstract void requestFederationRestoreFailed(String label);

   public abstract void federationRestoreBegun();

   public abstract void initiateFederateRestore(String label, FederateHandle federateHandle, String federateName);

   public abstract void federationRestored();

   public abstract void federationNotRestored(RestoreFailureReason restoreFailureReason);

   public abstract void attributesInScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeHandleSet);

   public abstract void attributesOutOfScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeHandleSet);

   public abstract void timeAdvanceGrant(LogicalTime logicalTime);

   public abstract void timeConstrainedEnabled(LogicalTime logicalTime);

   public abstract void timeRegulationEnabled(LogicalTime logicalTime);

   public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeNames, byte[] userSuppliedTag) {
      //ownership support
   }

   public void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeNames, byte[] userSuppliedTag) {
      //ownership support
   }

   public void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeNames, byte[] userSuppliedTag) {
      //ownership support
   }

   public void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstanceHandle, Set<String> attributeNames) {
      //ownership support
   }

   public void informAttributeOwnership(ObjectInstanceHandle objectInstanceHandle, String attributeName, Owner owner, FederateHandle federateHandle) {
      //ownership support
   }

   public void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle objectInstanceHandle, Set<String> set) {
      //ownership support
   }

   public abstract void objectInstanceNameReservationSucceeded(String instanceName);

   public abstract void objectInstanceNameReservationFailed(String instanceName);

   public void multipleObjectInstanceNameReservationSucceeded(Set<String> instanceNames) {
      //not supported
   }

   public void multipleObjectInstanceNameReservationFailed(Set<String> instanceNames) {
      //not supported
   }

   public abstract void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle,
                                               String instanceName,
                                               FederateHandle producingFederate);

   public abstract void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle,
                                               Map /* <String, byte[]> */ attributeHandleValueMap,
                                               byte[] userSuppliedTag,
                                               OrderType sentOrdering,
                                               TransportationType transportationType,
                                               LogicalTime logicalTime,
                                               OrderType receivedOrdering,
                                               FederateHandle producingFederate);

   public abstract void receiveInteraction(InteractionClassHandle interactionClassHandle,
                                           Map /* <String, byte[]> */ parameterHandleValueMap,
                                           byte[] userSuppliedTag,
                                           OrderType sentOrdering,
                                           TransportationType transportationType,
                                           LogicalTime logicalTime,
                                           OrderType receivedOrdering,
                                           FederateHandle producingFederate);

   public abstract void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle,
                                             byte[] userSuppliedTag,
                                             OrderType sentOrdering,
                                             LogicalTime logicalTime,
                                             OrderType receivedOrdering,
                                             FederateHandle producingFederate);

   public abstract void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle,
                                                    Set<String> attributeHandleSet,
                                                    byte[] userSuppliedTag);
}
