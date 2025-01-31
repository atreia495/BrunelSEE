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

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import uk.ac.brunel.see.HlaTuning;


/* @ThreadSafe */
final class NotificationQueue {
   private static class Notification {
      private final boolean _hasRunnable;
      private final Runnable _runnable;
      private final boolean _hasSentinel;
      private final Object _sentinel;

      public Notification() {
         _hasRunnable = false;
         _runnable = null;
         _hasSentinel = false;
         _sentinel = null;
      }

      public Notification(Runnable runnable) {
         _hasRunnable = true;
         _runnable = runnable;
         _hasSentinel = false;
         _sentinel = null;
      }

      public Notification(Object sentinel) {
         _hasRunnable = false;
         _runnable = null;
         _hasSentinel = true;
         _sentinel = sentinel;
      }

      public boolean hasRunnable() {
         return _hasRunnable;
      }

      public Runnable getRunnable() {
         return _runnable;
      }

      public boolean hasSentinel() {
         return _hasSentinel;
      }

      public Object getSentinel() {
         return _sentinel;
      }
   }


   private static final Notification STOP_SENTINEL = new Notification();
   private static final Notification PAUSE_SENTINEL = new Notification();

   private final BlockingQueue<Notification> _queue = HlaTuning.NOTIFICATION_QUEUE_CAPACITY == 0 ?
         new LinkedBlockingQueue<Notification>() :
         new LinkedBlockingQueue<Notification>(HlaTuning.NOTIFICATION_QUEUE_CAPACITY);
   //@GuardedBy("_sentinels")
   private final Set<Object> _sentinels = new HashSet<Object>();

   private final Object _enabledNotifier = new Object();
   private volatile boolean _notificationsEnabled = true;
   private boolean _stopped = false;


   NotificationQueue() {
   }

   void postStop() {
      postNotification(STOP_SENTINEL);
      synchronized (_enabledNotifier) {
         if (!_notificationsEnabled) {
            _stopped = true;
            _enabledNotifier.notifyAll();
         }
      }
   }

   void disableNotifications() {
      synchronized (_enabledNotifier) {
         if (_notificationsEnabled) {
            _notificationsEnabled = false;
            postNotification(PAUSE_SENTINEL);
         }
      }
   }

   void enableNotifications() {
      synchronized (_enabledNotifier) {
         if (!_notificationsEnabled) {
            _notificationsEnabled = true;
            postNotification(PAUSE_SENTINEL);
            _enabledNotifier.notifyAll();
         }
      }
   }

   /**
    * Waits for notifications to be enabled (if they are disabled).
    *
    * @return false if "Stop" is posted else true.
    */
   @SuppressWarnings ({ "PointlessBooleanExpression" })
   boolean waitForEnable() {
      synchronized (_enabledNotifier) {
         if (!_notificationsEnabled) {
            try {
               _enabledNotifier.wait();
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            }
            if (_stopped) {
               return false;
            }
         }
      }
      return true;
   }

   private void postNotification(Notification notification) {
      try {
         _queue.put(notification);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
   }

   /**
    * Posts a notification on the queue, will newer block.
    *
    * @param notification The notification to add
    */
   void postNotification(Runnable notification) {
      postNotification(new Notification(notification));
   }

   /**
    * Posts notifications on the queue, will newer block.
    *
    * @param notifications The notifications to add
    */
   void postNotifications(List<Runnable> notifications) {
      for (Runnable runnable : notifications) {
         postNotification(new Notification(runnable));
      }
   }

   /**
    * Evokes a notification on the queue if there are any. If not, block.
    *
    * @return false if "Stop" is posted else true.
    */
   boolean evoke() {
      if (!_notificationsEnabled) {
         return true;
      }
      Notification notification = null;
      try {
         notification = _queue.take();
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
      return evoke(notification);
   }

   int peek() {
      return _queue.size();
   }

   /**
    * Evokes a notification on the queue if there are any. If not, block, but at most <code>millis</code> milli seconds.
    *
    * @param millis Maximum time to block.
    *
    * @return false if "Stop" is posted else true.
    */
   boolean evoke(long millis) {
      if (!_notificationsEnabled) {
         return true;
      }
      Notification notification = null;
      try {
         notification = _queue.poll(millis, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
      return evoke(notification);
   }

   private boolean evoke(Notification notification) {
      if (notification == null) {
         return true;
      }
      if (notification == STOP_SENTINEL) {
         return false;
      }

      if (notification.hasRunnable()) {
         notification.getRunnable().run();
      } else if (notification.hasSentinel()) {
         Object sentinel = (notification).getSentinel();
         synchronized (_sentinels) {
            _sentinels.add(sentinel);
            _sentinels.notifyAll();
         }
      }
      return true;
   }

   /**
    * Post a user specified sentinel. It can later be waited for when it has arrived first in the
    * notification queue.
    *
    * @param sentinel sentinel, must follow general contract for equals and hashCode
    *
    * @see Object#equals(Object)
    * @see Object#hashCode()
    */
   void postSentinel(Object sentinel) {
      postNotification(new Notification(sentinel));
   }

   /**
    * Wait for a previously posted sentinel.
    *
    * @param sentinel sentinel to wait for.
    */
   void waitForSentinel(Object sentinel) {
      boolean interrupted = false;
      synchronized (_sentinels) {
         while (!_sentinels.remove(sentinel)) {
            try {
               _sentinels.wait();
            } catch (InterruptedException e) {
               interrupted = true;
            }
         }
      }
      if (interrupted) {
         Thread.currentThread().interrupt();
      }
   }

   /**
    * Check if a previously posted sentinel has been evoked.
    *
    * @param sentinel sentinel to check.
    */
   boolean hasEvokedSentinel(Object sentinel) {
      synchronized (_sentinels) {
         return _sentinels.remove(sentinel);
      }
   }
}
