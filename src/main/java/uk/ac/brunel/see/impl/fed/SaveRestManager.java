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

import uk.ac.brunel.see.HlaFederateId;
import uk.ac.brunel.see.HlaLogicalTime;
import uk.ac.brunel.see.HlaSaveRestoreListener;
import uk.ac.brunel.see.exceptions.*;

import se.pitch.ral.api.RestoreFailureReason;
import se.pitch.ral.api.SaveFailureReason;


public final class SaveRestManager {
   public static interface Listener {
      void initiateFederateSave(String label, HlaLogicalTime logicalTime);

      void federationSaved();

      void federationNotSaved(HlaSaveRestoreListener.HlaSaveFailure failure);


      void federationRestoreBegun();

      void initiateFederateRestore(String label, HlaFederateId federateHandle, String federateName);

      boolean federationRestored();

      boolean federationNotRestored(HlaSaveRestoreListener.HlaRestoreFailure failure);

      void timeAdvanceGrantAfterRestore(HlaLogicalTime logicalTime);
   }

   private final FederateManager _federate;
   private Listener _listener;
   private boolean _connected = false;

   public SaveRestManager(FederateManager federate) {
      _federate = federate;
      _federate.setSaveRestManager(this);
   }

   public void setSaveRestoreManager(Listener saveRestoreManager) {
      _listener = saveRestoreManager;
   }

   void connected() {
      _connected = true;
   }

   void disconnect() {
      _connected = false;
   }

   public void requestFederationSave(String label, HlaLogicalTime logicalTime) throws HlaNotConnectedException, HlaRtiException, HlaInvalidLogicalTimeException,
                                                                                            HlaSaveInProgressException, HlaRestoreInProgressException{
      if (!_connected) {
         throw new HlaNotConnectedException("Not connected");
      }
      _federate.requestFederationSave(label, logicalTime);
   }

   public void federateSaveBegun() throws HlaNotConnectedException, HlaRtiException, HlaRestoreInProgressException {
      if (!_connected) {
         throw new HlaNotConnectedException("Not connected");
      }
      _federate.federateSaveBegun();
   }

   public void federateSaveComplete() throws HlaNotConnectedException, HlaRtiException, HlaRestoreInProgressException {
      if (!_connected) {
         throw new HlaNotConnectedException("Not connected");
      }
      _federate.federateSaveComplete();
   }

   public void federateSaveNotComplete() throws HlaNotConnectedException, HlaRtiException, HlaRestoreInProgressException {
      if (!_connected) {
         throw new HlaNotConnectedException("Not connected");
      }
      _federate.federateSaveNotComplete();
   }

   public boolean requestFederationRestore(String label) throws HlaNotConnectedException, HlaRtiException,
                                                             HlaSaveInProgressException, HlaRestoreInProgressException {
      if (!_connected) {
         throw new HlaNotConnectedException("Not connected");
      }
      return _federate.requestFederationRestore(label);
   }

   public void federateRestoreComplete() throws HlaNotConnectedException, HlaRtiException, HlaSaveInProgressException {
      if (!_connected) {
         throw new HlaNotConnectedException("Not connected");
      }
      _federate.federateRestoreComplete();
   }

   public void federateRestoreNotComplete() throws HlaNotConnectedException, HlaRtiException, HlaSaveInProgressException {
      if (!_connected) {
         throw new HlaNotConnectedException("Not connected");
      }
      _federate.federateRestoreNotComplete();
   }

   private static HlaSaveRestoreListener.HlaSaveFailure convert(SaveFailureReason reason) {
      switch (reason) {
         case RTI_UNABLE_TO_SAVE:
            return HlaSaveRestoreListener.HlaSaveFailure.RTI_UNABLE_TO_SAVE;
         case FEDERATE_REPORTED_FAILURE:
            return HlaSaveRestoreListener.HlaSaveFailure.FEDERATE_REPORTED_FAILURE;
         case FEDERATE_RESIGNED:
            return HlaSaveRestoreListener.HlaSaveFailure.FEDERATE_RESIGNED;
         case RTI_DETECTED_FAILURE:
            return HlaSaveRestoreListener.HlaSaveFailure.RTI_DETECTED_FAILURE;
         case SAVE_TIME_CANNOT_BE_HONORED:
            return HlaSaveRestoreListener.HlaSaveFailure.SAVE_TIME_CANNOT_BE_HONORED;
         case SAVE_ABORTED:
            return HlaSaveRestoreListener.HlaSaveFailure.SAVE_ABORTED;
      }
      return HlaSaveRestoreListener.HlaSaveFailure.UNKNOWN;
   }

   private static HlaSaveRestoreListener.HlaRestoreFailure convert(RestoreFailureReason reason) {
      switch (reason) {
         case RTI_UNABLE_TO_RESTORE:
            return HlaSaveRestoreListener.HlaRestoreFailure.RTI_UNABLE_TO_RESTORE;
         case FEDERATE_REPORTED_FAILURE:
            return HlaSaveRestoreListener.HlaRestoreFailure.FEDERATE_REPORTED_FAILURE;
         case FEDERATE_RESIGNED:
            return HlaSaveRestoreListener.HlaRestoreFailure.FEDERATE_RESIGNED;
         case RTI_DETECTED_FAILURE:
            return HlaSaveRestoreListener.HlaRestoreFailure.RTI_DETECTED_FAILURE;
         case RESTORE_ABORTED:
            return HlaSaveRestoreListener.HlaRestoreFailure.RESTORE_ABORTED;
      }
      return HlaSaveRestoreListener.HlaRestoreFailure.UNKNOWN;
   }

   // Save Callbacks

   public void initiateFederateSave(String name, HlaLogicalTime logicalTime) {
      _listener.initiateFederateSave(name, logicalTime);
   }

   public void federationSaved() {
      _listener.federationSaved();
   }

   public void federationNotSaved(SaveFailureReason reason) {
      _listener.federationNotSaved(convert(reason));
   }

   // Restore Callbacks

   public void federationRestoreBegun() {
      _listener.federationRestoreBegun();
   }

   public void initiateFederateRestore(String name, HlaFederateId federateHandle, String federateName) {
      _listener.initiateFederateRestore(name, federateHandle, federateName);
   }

   public boolean federationRestored() {
      return _listener.federationRestored();
   }

   public boolean federationNotRestored(RestoreFailureReason reason) {
      return _listener.federationNotRestored(convert(reason));
   }

   public void timeAdvanceGrantAfterRestore(HlaLogicalTime logicalTime) {
      _listener.timeAdvanceGrantAfterRestore(logicalTime);
   }
}
