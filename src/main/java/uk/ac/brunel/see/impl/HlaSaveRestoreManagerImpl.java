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

import uk.ac.brunel.see.*;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.fed.ObjectManager;
import uk.ac.brunel.see.impl.fed.SaveRestManager;
import uk.ac.brunel.see.impl.utils.Pair;

import java.util.concurrent.TimeUnit;


final class HlaSaveRestoreManagerImpl implements HlaSaveRestoreManager, SaveRestManager.Listener {
   private static final HlaSaveRestoreListener DEFAULT_SAVE_RESTORE_LISTENER = new HlaSaveRestoreListener.Adapter() {};

   private final HlaWorldImpl _hlaWorld;
   private final SaveRestManager _saveRestManager;
   private final ObjectManager _objectManager;
   private HlaSaveRestoreListener _saveRestoreListener = null;

   private volatile boolean _saveInProgress = false;
   private volatile boolean _restoreInProgress = false;
   private volatile HlaLogicalTime _restoredTime = null;
   private volatile boolean _wasGrantedWhenRestored = false;
   private volatile boolean _localRestoreSuccessful = false;


   public HlaSaveRestoreManagerImpl(SaveRestManager saveRestManager, ObjectManager objectManager, HlaWorldImpl hlaWorld) {
      _hlaWorld = hlaWorld;
      _saveRestManager = saveRestManager;
      _objectManager = objectManager;

      _saveRestManager.setSaveRestoreManager(this);
   }

   private HlaSaveRestoreListener getSaveRestoreListener() {
      synchronized (this) {
         return _saveRestoreListener != null ? _saveRestoreListener : DEFAULT_SAVE_RESTORE_LISTENER;
      }
   }

   public void requestFederationSave(String label) throws HlaNotConnectedException, HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      try {
         _saveRestManager.requestFederationSave(label, null);
      } catch (HlaInvalidLogicalTimeException e) {
         //can not happen with null logical time
      }
   }

   public void requestFederationSave(String label, HlaLogicalTime logicalTime) throws HlaNotConnectedException, HlaRtiException, HlaInvalidLogicalTimeException, HlaSaveInProgressException, HlaRestoreInProgressException {
      _saveRestManager.requestFederationSave(label, logicalTime);
   }

   public boolean requestFederationRestore(String label) throws HlaNotConnectedException, HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      return _saveRestManager.requestFederationRestore(label);
   }

   public boolean awaitGrantedStateAfterRestore(long timeout, TimeUnit unit) throws InterruptedException {
      return _hlaWorld.awaitGranted(timeout, unit);
   }

   public void setHlaSaveRestoreListener(HlaSaveRestoreListener listener) {
      synchronized (this) {
         _saveRestoreListener = listener;
      }
   }

   public boolean isSaveInProgress() {
      return _saveInProgress;
   }

   public boolean isRestoreInProgress() {
      return _restoreInProgress;
   }

   // Save Callbacks

   private void performSave(boolean federateSaved, String label, HlaLogicalTime logicalTime) {
      try {
         _saveRestManager.federateSaveBegun();

         if (!federateSaved) {
            _saveRestManager.federateSaveNotComplete();
            return;
         }

         try {
            _hlaWorld.save(label, logicalTime);
            _objectManager.save();

            _saveRestManager.federateSaveComplete();
         } catch (HlaSaveFederateException e) {
            _hlaWorld.postException(e);
            _saveRestManager.federateSaveNotComplete();
         }
      } catch (HlaRestoreInProgressException e) {
         _hlaWorld.postException(new HlaRtiException("Failed to perform save", e));
      } catch (HlaRtiException e) {
         _hlaWorld.postException(new HlaRtiException("Failed to perform save", e));
      } catch (HlaNotConnectedException e) {
         _hlaWorld.postException(new HlaRtiException("Failed to perform save", e));
      }
   }

   public void initiateFederateSave(String label, HlaLogicalTime logicalTime) {
      _saveInProgress = true;
      firePerformFederateSave(label, logicalTime);
   }

   public void federationSaved() {
      _saveInProgress = false;
      fireFederationSaved();
   }

   public void federationNotSaved(HlaSaveRestoreListener.HlaSaveFailure reason) {
      _saveInProgress = false;
      fireFederationNotSaved(reason);
   }

   // Restore Callbacks

   private void performRestore(boolean federateRestored, String label, HlaFederateId federateHandle, String federateName) {
      try {
         if (!federateRestored) {
            _saveRestManager.federateRestoreNotComplete();
            return;
         }

         try {
            Pair<HlaLogicalTime, Boolean> restoreInfo = _hlaWorld.restore(label, federateHandle, federateName);
            _restoredTime = restoreInfo.getFirst();
            _wasGrantedWhenRestored = restoreInfo.getSecond();
            _objectManager.restore();

            _localRestoreSuccessful = true;
            _saveRestManager.federateRestoreComplete();
         } catch (HlaRestoreFederateException e) {
            _hlaWorld.postException(e);
            _saveRestManager.federateRestoreNotComplete();
         }
      } catch (HlaSaveInProgressException e) {
         _hlaWorld.postException(new HlaRtiException("Failed to perform restore", e));
      } catch (HlaRtiException e) {
         _hlaWorld.postException(new HlaRtiException("Failed to perform restore", e));
      } catch (HlaNotConnectedException e) {
         _hlaWorld.postException(new HlaRtiException("Failed to perform restore", e));
      }
   }

   public void federationRestoreBegun() {
      _restoreInProgress = true;
      _localRestoreSuccessful = false;
      // wait for initiateFederateRestore
   }

   public void initiateFederateRestore(String label, HlaFederateId federateHandle, String federateName) {
      firePerformFederateRestore(label, federateHandle, federateName);
   }

   public boolean federationRestored() {
      _restoreInProgress = false;
      fireFederationRestored(_restoredTime, !_wasGrantedWhenRestored);
      return _wasGrantedWhenRestored && _restoredTime.wasReceivedInTimeStampOrder();
   }

   public boolean federationNotRestored(HlaSaveRestoreListener.HlaRestoreFailure reason) {
      _restoreInProgress = false;
      fireFederationNotRestored(reason, _localRestoreSuccessful, _localRestoreSuccessful ? _restoredTime : HlaLogicalTimeImpl.INVALID, !_wasGrantedWhenRestored);
      return _wasGrantedWhenRestored && _localRestoreSuccessful && _restoredTime.wasReceivedInTimeStampOrder();
   }

   public void timeAdvanceGrantAfterRestore(HlaLogicalTime logicalTime) {
      _hlaWorld.timeAdvanceGrantAfterRestore(logicalTime);
   }

   private void firePerformFederateSave(final String label, final HlaLogicalTime logicalTime) {
      final HlaSaveRestoreListener listener = getSaveRestoreListener();
      _hlaWorld.postNotification(new Runnable() {
         public void run() {
            final boolean success = listener.performFederateSave(label, logicalTime);

            _hlaWorld.invokeLater(new Runnable() {
               @Override
               public void run() {
                  performSave(success, label, logicalTime);
               }
            });
         }
      });
   }

   private void fireFederationSaved() {
      final HlaSaveRestoreListener listener = getSaveRestoreListener();
      _hlaWorld.postNotification(new Runnable() {
         public void run() {
            listener.federationSaved();
         }
      });
   }

   private void fireFederationNotSaved(final HlaSaveRestoreListener.HlaSaveFailure saveFailureReason) {
      final HlaSaveRestoreListener listener = getSaveRestoreListener();
      _hlaWorld.postNotification(new Runnable() {
         public void run() {
            listener.federationNotSaved(saveFailureReason);
         }
      });
   }

   private void firePerformFederateRestore(final String label, final HlaFederateId federateHandle, final String federateName) {
      final HlaSaveRestoreListener listener = getSaveRestoreListener();
      _hlaWorld.postNotification(new Runnable() {
         public void run() {
            final boolean success = listener.performFederateRestore(label);

            _hlaWorld.invokeLater(new Runnable() {
               @Override
               public void run() {
                  performRestore(success, label, federateHandle, federateName);
               }
            });
         }
      });
   }

   private void fireFederationRestored(final HlaLogicalTime logicalTime, final boolean wasAdvancing) {
      final HlaSaveRestoreListener listener = getSaveRestoreListener();
      _hlaWorld.postNotification(new Runnable() {
         public void run() {
            listener.federationRestored(logicalTime, wasAdvancing);
         }
      });
   }

   private void fireFederationNotRestored(final HlaSaveRestoreListener.HlaRestoreFailure restoreFailureReason,
                                          final boolean localRestoreWasSuccessful,
                                          final HlaLogicalTime localRestoredTime,
                                          final boolean wasAdvancing) {
      final HlaSaveRestoreListener listener = getSaveRestoreListener();
      _hlaWorld.postNotification(new Runnable() {
         public void run() {
            listener.federationNotRestored(restoreFailureReason, localRestoreWasSuccessful, localRestoredTime, wasAdvancing);
         }
      });
   }
}
