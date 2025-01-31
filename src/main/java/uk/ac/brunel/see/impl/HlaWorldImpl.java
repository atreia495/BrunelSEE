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

/*
 * Generated 2025-01-31 13:17:38 (+0000) by arbit using Pitch Developer Studio 6.4.0
 */

import uk.ac.brunel.see.*;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.fed.FederateManager;
import uk.ac.brunel.see.impl.fed.FederateInteractionManager;
import uk.ac.brunel.see.impl.fed.ObjectManager;
import uk.ac.brunel.see.impl.fed.SaveRestManager;
import uk.ac.brunel.see.impl.utils.Pair;
import java.util.*;
import java.util.concurrent.*;


/* @ThreadSafe */
public final class HlaWorldImpl implements HlaWorld {

   private static final ExceptionListener DEFAULT_EXCEPTION_LISTENER = new ExceptionListener() {
      public void exceptionThrown(Throwable e, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
         synchronized (System.err) {
            System.err.print("Default exception listener " + timeStamp.toString() + ": ");
            e.printStackTrace(System.err);
         }
      }
   };

   private static class SaveState implements PersistentHelper.State {
      public final HlaLogicalTime _saveTime;
      public final HlaLogicalTime _currentLogicalTime;
      public final HlaLogicalTime _nextLogicalTime;
      public final HlaLogicalTime _defaultSendTime;
      public final long _stepSize;
      public final long _lookahead;
      public final boolean _advanceToNextEvent;

      private SaveState(HlaLogicalTime saveTime, HlaLogicalTime currentLogicalTime,
                        HlaLogicalTime nextLogicalTime, HlaLogicalTime defaultSendTime,
                        long stepSize, long lookahead, boolean advanceToNextEvent) {
         _saveTime = saveTime;
         _currentLogicalTime = currentLogicalTime;
         _nextLogicalTime = nextLogicalTime;
         _defaultSendTime = defaultSendTime;
         _stepSize = stepSize;
         _lookahead = lookahead;
         _advanceToNextEvent = advanceToNextEvent;
      }
   }

   private static final HlaTimeStampFactory DEFAULT_TIME_STAMP_FACTORY = HlaTimeStampFactory.SYSTEM_CLOCK;

   private static final Object GRANT_RECEIVED_SENTINEL = new Object();
   private static final Object GRANT_DONE_SENTINEL = new Object();


   private final FederateManager _federate;
   private final HlaInteractionManagerImpl _interactionManager;
   private final HlaSaveRestoreManagerImpl _saveRestoreManager;

   private final HlaSettings _settings;
   private final boolean _manualEvokeMode;
   private final HlaTimeStampFactory _timeStampFactory;
   private final HlaHLAobjectRootManagerImpl _hLAobjectRootManager;
   private final HlaPhysicalEntityManagerImpl _physicalEntityManager;
   private final HlaDynamicalEntityManagerImpl _dynamicalEntityManager;
   private final HlaPhysicalInterfaceManagerImpl _physicalInterfaceManager;
   private final HlaReferenceFrameManagerImpl _referenceFrameManager;
   private final HlaExecutionConfigurationManagerImpl _executionConfigurationManager;

   /* @GuardedBy("this") */
   private ExceptionListener _exceptionListener = null;

   private final Set<HlaWorldListener> _worldListeners = new CopyOnWriteArraySet<HlaWorldListener>();

   /* @GuardedBy("this") */
   private boolean _connected = false;
   private boolean _timeManagementEnabled = false;

   private HlaLogicalTime _currentLogicalTime = HlaLogicalTimeImpl.INVALID;
   private HlaLogicalTime _nextLogicalTime = HlaLogicalTimeImpl.INVALID;
   private HlaLogicalTime _defaultSendTime = HlaLogicalTimeImpl.INVALID;
   private long _stepSize;
   private long _lookahead;
   private boolean _advanceToNextEvent;
   private final PersistentHelper<SaveState> _persistentHelper = new PersistentHelper<SaveState>();

   private final Object _timeStateLock = new Object();
   /* @GuardedBy("_timeStateLock") */
   private boolean _timeStateGranted = true;
   /* @GuardedBy("_timeStateLock") */
   private final List<Runnable> _invokeWhenGranted = new ArrayList<Runnable>();

   /* @GuardedBy("this") */
   private Timer _timer;
   /* @GuardedBy("this") */
   private ExecutorService _invokeLaterExecutor = null;

   private final NotificationQueue _notificationQueue;


   public HlaWorldImpl(HlaSettings settings, boolean manualEvokeMode, HlaTimeStampFactory timeStampFactory) {
      if (settings == null || HlaSettings.hasSettingsFile()) {
         _settings = HlaSettings.createDefault();
      } else {
         _settings = settings;
      }
      _manualEvokeMode = manualEvokeMode;
      _stepSize = _settings.getStepSize(HlaLogicalTimeImpl.INITIAL);
      _lookahead = _settings.getLookahead(HlaLogicalTimeImpl.INITIAL);

      if (HlaSettings.hasSettingsFile() || timeStampFactory == null) {
        // Order of precedence
        // 1. File-based settings override everything, even if we passed in a timeStampFactory instance, else
        // 2. If we did not pass in a timeStampFactory then use current settings
         String factoryName = HlaSettings.getTimeStampFactoryName();
         if (factoryName != null) {
            timeStampFactory = HlaTimeStampFactory.getHlaTimeStampFactory(factoryName);
         }
      }

      _timeStampFactory = timeStampFactory != null ? timeStampFactory : DEFAULT_TIME_STAMP_FACTORY;

      _federate = new FederateManager(this, _timeStampFactory);
      ObjectManager objectManager = new ObjectManager(_federate, this);
      FederateInteractionManager interactionManager = new FederateInteractionManager(_federate, this);
      _interactionManager = new HlaInteractionManagerImpl(interactionManager, this);
      _saveRestoreManager = new HlaSaveRestoreManagerImpl(new SaveRestManager(_federate), objectManager, this);

      _hLAobjectRootManager = new HlaHLAobjectRootManagerImpl(this, objectManager);
      _physicalEntityManager = new HlaPhysicalEntityManagerImpl(this, objectManager);
      _dynamicalEntityManager = new HlaDynamicalEntityManagerImpl(this, objectManager);
      _physicalInterfaceManager = new HlaPhysicalInterfaceManagerImpl(this, objectManager);
      _referenceFrameManager = new HlaReferenceFrameManagerImpl(this, objectManager);
      _executionConfigurationManager = new HlaExecutionConfigurationManagerImpl(this, objectManager);

      _notificationQueue = new NotificationQueue();
   }

   public synchronized HlaLogicalTime connect() throws HlaConnectException, HlaInvalidLicenseException, HlaFomException,
                                                       HlaRtiException, HlaInternalException, HlaNotConnectedException, HlaInvalidLogicalTimeException,
                                                       HlaSaveInProgressException, HlaRestoreInProgressException, HlaNormalizerException,
                                                       HlaCredentialsProviderException, HlaUnauthorizedException {
      if (!_connected) {
         try {
            if (!_manualEvokeMode) {
               startAutoEvokeThread();
            }
            _timer = new Timer("HlaWorld_InvokeDelay", true);
            _invokeLaterExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
               private final ThreadFactory _threadFactory = Executors.defaultThreadFactory();
               public Thread newThread(Runnable r) {
                  Thread thread = _threadFactory.newThread(r);
                  thread.setName("HlaWorld_InvokeLater");
                  thread.setDaemon(true);
                  return thread;
               }
            });
            _currentLogicalTime = _federate.connect(_settings);
            _nextLogicalTime = _currentLogicalTime.add(_stepSize);
            _defaultSendTime = inc(_currentLogicalTime, _lookahead, true);

            //Start in Granted state
            _notificationQueue.postSentinel(GRANT_DONE_SENTINEL);

            _connected = true;
            _timeManagementEnabled = _currentLogicalTime != HlaLogicalTimeImpl.INVALID;
            fireConnected(getCurrentTime());
         } finally {
            if (!_connected) {
               _invokeLaterExecutor.shutdown();
               _invokeLaterExecutor = null;

               _notificationQueue.postStop();
               _timer.cancel();

               _currentLogicalTime = HlaLogicalTimeImpl.INVALID;
               _nextLogicalTime = HlaLogicalTimeImpl.INVALID;
               _defaultSendTime = HlaLogicalTimeImpl.INVALID;
            }
         }
      }
      return _currentLogicalTime;
   }

   public void disconnect() throws HlaFederateOwnsAttributeException, HlaRtiException, HlaInternalException {
      disconnect(null);
   }

   public synchronized void disconnect(HlaNotConnectedException postException) throws HlaFederateOwnsAttributeException, HlaRtiException, HlaInternalException {
      if (_connected) {
         _federate.disconnect(_settings.getDestroyFederation());
         _connected = false;
         _persistentHelper.clear();

         if (postException != null) {
            postException(postException);
         }

         fireDisconnected(getCurrentTime());
         _invokeLaterExecutor.shutdown();
         try {
            _invokeLaterExecutor.awaitTermination(10, TimeUnit.SECONDS);
         } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
         }
         _invokeLaterExecutor = null;

         _notificationQueue.postStop();
         _timer.cancel();
      }
   }

   public boolean enableTimeManagement() throws HlaRtiException, HlaInvalidLogicalTimeException, HlaInternalException, HlaNotConnectedException {
      if (!_connected) {
         throw new HlaNotConnectedException("Must be connected to federation to enable time management");
      }

      boolean regulating = _settings.getTimeRegulating();
      boolean constrained = _settings.getTimeConstrained();
      if (_timeManagementEnabled || (!regulating && !constrained)) {
         return false;
      }

      waitForSentinel(GRANT_DONE_SENTINEL);

      _currentLogicalTime = _federate.enableTimeManagement(regulating, constrained);
      if (_currentLogicalTime == HlaLogicalTimeImpl.INVALID) {
         return false;
      }

      _timeManagementEnabled = true;
      _nextLogicalTime = _currentLogicalTime.add(_stepSize);
      _defaultSendTime = inc(_currentLogicalTime, _lookahead, true);
      _notificationQueue.postSentinel(GRANT_DONE_SENTINEL);
      return true;
   }

   public HlaLogicalTime advanceToNextFrame()
         throws HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaInvalidLogicalTimeException, HlaInTimeAdvancingStateException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return advance(false);
   }

   public HlaLogicalTime advanceToNextEvent()
         throws HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaInvalidLogicalTimeException, HlaInTimeAdvancingStateException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return advance(true);
   }

   public HlaLogicalTime advanceToLogicalTime(HlaLogicalTime logicalTime)
         throws HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaInvalidLogicalTimeException, HlaInTimeAdvancingStateException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (!logicalTime.isValid()) {
         throw new HlaInvalidLogicalTimeException("Logical time not valid: " + logicalTime);
      }
      if (logicalTime.compareTo(_currentLogicalTime) < 0) { // logicalTime < _currentLogicalTime
         throw new HlaInvalidLogicalTimeException("Requested logical time is less the current logical time: " +
                                                   logicalTime + " < " + _currentLogicalTime);
      }
      _nextLogicalTime = logicalTime;
      return advance(false);
   }

   private HlaLogicalTime advance(boolean advanceToNextEvent)
         throws HlaRtiException, HlaInternalException, HlaNotConnectedException,
                HlaInvalidLogicalTimeException, HlaInTimeAdvancingStateException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      if (_saveRestoreManager.isSaveInProgress()) {
         throw new HlaSaveInProgressException("Can not advance during save");
      }
      if (_saveRestoreManager.isRestoreInProgress()) {
         throw new HlaRestoreInProgressException("Can not advance during restore");
      }
      if (!_connected) {
         throw new HlaNotConnectedException("Must be connected to federation to advance");
      }

      synchronized (_timeStateLock) {
         if (!_timeStateGranted) {
            throw new HlaInTimeAdvancingStateException("Can only advance in Granted state");
         }
         _timeStateGranted = false;
      }
      _advanceToNextEvent = advanceToNextEvent;

      waitForSentinel(GRANT_DONE_SENTINEL);

      _defaultSendTime = max(inc(_nextLogicalTime, _lookahead, !_advanceToNextEvent), _defaultSendTime);

      fireTimeAdvanceRequested(getCurrentTime(), _nextLogicalTime);

      // if this throws we are stuck in advancing state
      _currentLogicalTime = _advanceToNextEvent ? _federate.nextMessageRequestAvailable(_nextLogicalTime) :
                                                  _federate.timeAdvanceRequest(_nextLogicalTime);

      if (HlaTuning.ADVANCE_WAITS_FOR_NOTIFICATIONS) {
         _notificationQueue.postSentinel(GRANT_RECEIVED_SENTINEL);
      }

      returnToGrantedState();

      if (HlaTuning.ADVANCE_WAITS_FOR_NOTIFICATIONS) {
         _notificationQueue.waitForSentinel(GRANT_RECEIVED_SENTINEL);
      }

      return _currentLogicalTime;
   }

   private void returnToGrantedState()
         throws HlaInternalException, HlaRtiException,
                HlaNotConnectedException, HlaInvalidLogicalTimeException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _stepSize = _settings.getStepSize(_nextLogicalTime);
      long lookahead = _settings.getLookahead(_nextLogicalTime);

      if (lookahead != _lookahead) {
          _lookahead = lookahead;
          _federate.modifyLookahead(_lookahead);
      }

      if ((_currentLogicalTime.getValue() >= _nextLogicalTime.getValue()) || !HlaTuning.FORCE_FRAME_TIMES_WHEN_EVENT_BASED) {
         _nextLogicalTime = _currentLogicalTime.add(_stepSize);
      }
      _defaultSendTime = max(inc(_currentLogicalTime, _lookahead, !_advanceToNextEvent), _defaultSendTime);

      List<Runnable> notifications;
      synchronized (_timeStateLock) {
         _timeStateGranted = true;
         _timeStateLock.notify();
         notifications = new ArrayList<Runnable>(_invokeWhenGranted);
         _invokeWhenGranted.clear();
      }

      fireTimeAdvanceGranted(getCurrentTime(), _currentLogicalTime);

      _notificationQueue.postNotifications(notifications);
      _notificationQueue.postSentinel(GRANT_DONE_SENTINEL);
   }

   private void waitForSentinel(Object sentinel) {
      if (_manualEvokeMode) {
         while (!_notificationQueue.hasEvokedSentinel(sentinel)) {
            evoke();
         }
      } else {
         _notificationQueue.waitForSentinel(sentinel);
      }
   }

   private static HlaLogicalTime max(HlaLogicalTime time1, HlaLogicalTime time2) {
      if (time1.getValue() > time2.getValue()) {
         return time1;
      } else {
         return time2;
      }
   }

   private static HlaLogicalTime inc(HlaLogicalTime logicalTime, long addend, boolean mustIncrease) {
      if (mustIncrease && addend == 0) {
         addend = 1;
      }
      return logicalTime.add(addend);
   }

   public void invokeWhenGranted(Runnable runnable) {
      synchronized (_timeStateLock) {
         if (_timeStateGranted) {
            runnable.run();
         } else {
            _invokeWhenGranted.add(runnable);
         }
      }
   }

   public long getLookahead() {
      return _lookahead;
   }

   public long getStepSize() {
      return _stepSize;
   }

   public HlaTimeStamp getCurrentTime() {
      return _timeStampFactory.create();
   }

   public HlaTimeStampFactory getTimeStampFactory() {
      return _timeStampFactory;
   }

   public HlaLogicalTime getCurrentLogicalTime() {
      return _currentLogicalTime;
   }

   public HlaLogicalTime getNextLogicalTime() {
      return _nextLogicalTime;
   }

   public HlaLogicalTime getDefaultSendTime() {
      return _defaultSendTime;
   }

   public HlaLogicalTime createLogicalTime(long value) {
      return HlaLogicalTimeImpl.create(value);
   }

   public HlaLogicalTime createInvalidLogicalTime() {
      return HlaLogicalTimeImpl.INVALID;
   }

   public synchronized byte[] getEncodedHlaFederateHandle() {
      if (!_connected) {
         return null;
      }
      return _federate.getEncodedHlaFederateHandle();
   }

   public synchronized HlaFederateId getFederateId() {
      if (_connected) {
         return _federate.getFederateId();
      } else {
         return null;
      }
   }

   public int peek() {
      return _notificationQueue.peek();
   }

   public void evoke() {
      if (!_manualEvokeMode) {
         return;
      }

      try {
         _notificationQueue.evoke();
      } catch (Throwable t) {
         getExceptionListener().exceptionThrown(t, getCurrentTime(), getCurrentLogicalTime());
      }
   }

   public void evokeAll() {
      if (!_manualEvokeMode) {
         return;
      }

      while (_notificationQueue.peek() != 0) {
         try {
            _notificationQueue.evoke();
         } catch (Throwable t) {
            getExceptionListener().exceptionThrown(t, getCurrentTime(), getCurrentLogicalTime());
         }
      }
   }

   public void evokeAll(long millis) {
      if (!_manualEvokeMode) {
         try {
            TimeUnit.MILLISECONDS.sleep(millis);
         } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
         }
         return;
      }

      long startTime = System.currentTimeMillis();
      long now = startTime;
      while (now <= startTime + millis) {
         try {
            _notificationQueue.evoke(startTime + millis - now);
         } catch (Throwable t) {
            getExceptionListener().exceptionThrown(t, getCurrentTime(), getCurrentLogicalTime());
         }
         now = System.currentTimeMillis();
      }
   }

   public void disableNotifications() {
      if (_manualEvokeMode) {
         return;
      }
      _notificationQueue.disableNotifications();
   }

   public void enableNotifications() {
      if (_manualEvokeMode) {
         return;
      }
      _notificationQueue.enableNotifications();
   }

   public HlaHLAobjectRootManager getHlaHLAobjectRootManager() {
      return _hLAobjectRootManager;
   }

   public HlaPhysicalEntityManager getHlaPhysicalEntityManager() {
      return _physicalEntityManager;
   }

   public HlaDynamicalEntityManager getHlaDynamicalEntityManager() {
      return _dynamicalEntityManager;
   }

   public HlaPhysicalInterfaceManager getHlaPhysicalInterfaceManager() {
      return _physicalInterfaceManager;
   }

   public HlaReferenceFrameManager getHlaReferenceFrameManager() {
      return _referenceFrameManager;
   }

   public HlaExecutionConfigurationManager getHlaExecutionConfigurationManager() {
      return _executionConfigurationManager;
   }

   public HlaInteractionManager getHlaInteractionManager() {
      return _interactionManager;
   }

   public HlaSaveRestoreManager getHlaSaveRestoreManager() {
      return _saveRestoreManager;
   }

   public void setExceptionListener(ExceptionListener exceptionListener) {
      synchronized (this) {
         _exceptionListener = exceptionListener;
      }
   }

   public void addHlaWorldListener(HlaWorldListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
       _worldListeners.add(listener);
   }

   public void removeHlaWorldListener(HlaWorldListener listener) {
       if (listener == null) {
           throw new NullPointerException();
       }
       _worldListeners.remove(listener);
   }

   private void fireConnected(final HlaTimeStamp timeStamp) {
      for (final HlaWorldListener listener : _worldListeners) {
         postNotification(new Runnable() {
            public void run() {
               listener.connected(timeStamp);
            }
         });
      }
   }

   private void fireDisconnected(final HlaTimeStamp timeStamp) {
      for (final HlaWorldListener listener : _worldListeners) {
         postNotification(new Runnable() {
            public void run() {
               listener.disconnected(timeStamp);
            }
         });
      }
   }

   private void fireTimeAdvanceRequested(final HlaTimeStamp timeStamp, final HlaLogicalTime requestedTime) {
      for (final HlaWorldListener listener : _worldListeners) {
         postNotification(new Runnable() {
            public void run() {
               listener.timeAdvanceRequested(timeStamp, requestedTime);
            }
         });
      }
   }

   private void fireTimeAdvanceGranted(final HlaTimeStamp timeStamp, final HlaLogicalTime grantedTime) {
      for (final HlaWorldListener listener : _worldListeners) {
         postNotification(new Runnable() {
            public void run() {
               listener.timeAdvanceGranted(timeStamp, grantedTime);
            }
         });
      }
   }

   public HlaSettings getSettings() {
      return _settings;
   }

   void postNotification(Runnable notification) {
      _notificationQueue.postNotification(notification);
   }

   public void postException(final Throwable e) {
      final HlaTimeStamp currentTime = getCurrentTime();
      final HlaLogicalTime currentLogicalTime = getCurrentLogicalTime();
      postNotification(new Runnable() {
         public void run() {
            getExceptionListener().exceptionThrown(e, currentTime, currentLogicalTime);
         }
      });
   }

   public void invokeLater(Runnable runnable) {
      if (_invokeLaterExecutor == null) {
         //Ignore invokeLater after resign...
         return;
      }
      try {
         _invokeLaterExecutor.execute(runnable);
      } catch (RejectedExecutionException e) {
         // Already shutdown, ignore...
      }
   }

   public void invokeDelay(long minDelayMillis, long maxDelayMillis, final Runnable runnable) {
      long delay = minDelayMillis + Math.round(Math.random() * (maxDelayMillis - minDelayMillis));

      _timer.schedule(new TimerTask() {
         public void run() {
            runnable.run();
         }
      }, delay);
   }

   private void startAutoEvokeThread() {
      Thread callbackThread = new Thread("HlaWorld_AutoEvokeThread") {
         @SuppressWarnings ({ "InfiniteLoopStatement" })
         public void run() {
            while (true) {
               try {
                  if (!_notificationQueue.waitForEnable()) {
                     break;
                  }
                  if (!_notificationQueue.evoke()) {
                     break;
                  }
               } catch (Throwable e) {
                  getExceptionListener().exceptionThrown(e, getCurrentTime(), getCurrentLogicalTime());
               }
            }
         }
      };
      callbackThread.setDaemon(true);
      callbackThread.start();
   }

   private ExceptionListener getExceptionListener() {
      synchronized (this) {
         return _exceptionListener != null ? _exceptionListener : DEFAULT_EXCEPTION_LISTENER;
      }
   }

   void save(String label, HlaLogicalTime saveTime) throws HlaSaveFederateException {
      SaveState state = new SaveState(saveTime, _currentLogicalTime, _nextLogicalTime, _defaultSendTime,
                                      _stepSize, _lookahead, _advanceToNextEvent);
      if (!_persistentHelper.saveState(label, state)) {
         throw new HlaSaveFederateException("Failed to save internal state");
      }
   }

   Pair<HlaLogicalTime, Boolean> restore(String label, HlaFederateId federateHandle, String federateName) throws HlaRestoreFederateException {
      SaveState state = _persistentHelper.loadState(label);
      if (state == null) {
         throw new HlaRestoreFederateException("Unable to restore federate, unknown restore label " + label);
      }

      HlaLogicalTime saveTime = state._saveTime;

      boolean wasGranted;
      synchronized (_timeStateLock) {
         wasGranted = _timeStateGranted;

         if (_timeStateGranted && saveTime.wasReceivedInTimeStampOrder()) {
            _timeStateGranted = false;
         }
         _invokeWhenGranted.clear();
      }

      _currentLogicalTime = state._currentLogicalTime;
      _nextLogicalTime = state._nextLogicalTime;
      _defaultSendTime = state._defaultSendTime;
      _stepSize = state._stepSize;
      _lookahead = state._lookahead;
      _advanceToNextEvent = state._advanceToNextEvent;

      //federateHandle and federateName will only change if we resign from the federation and restore in a new federation

      return Pair.create(saveTime, wasGranted);
   }

   void timeAdvanceGrantAfterRestore(HlaLogicalTime logicalTime) {
      _currentLogicalTime = logicalTime;

      try {
         returnToGrantedState();
      } catch (HlaBaseException e) {
         postException(e);
      } catch (HlaBaseRuntimeException e) {
         postException(e);
      }
   }

   boolean awaitGranted(long timeout, TimeUnit unit) throws InterruptedException {
      synchronized (_timeStateLock) {
         while (!_timeStateGranted) {
            _timeStateLock.wait(unit.toMillis(timeout));
         }
         return _timeStateGranted;
      }
   }
}
