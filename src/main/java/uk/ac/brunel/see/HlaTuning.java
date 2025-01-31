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


/**
 * Class used to support Tuning of the generated code.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public final class HlaTuning {

   /**
    * Perform an request of attribute value update when a new instance is discovered or comes within interest.
    * <p>
    * Instances are requested after a delay [REQUEST_MIN_DELAY_MS, REQUEST_MAX_DELAY_MS].
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>request</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.request", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    *
    * @see #PROVIDE
    * @see #REQUEST_MIN_DELAY_MS
    * @see #REQUEST_MAX_DELAY_MS
    */
   public static final boolean REQUEST = getSetting("request", true);

   /**
    * Min delay for request of attribute value update for discovered instances, in milliseconds.
    * <p>
    * A random value between <i>min</i> and <i>max</i> is used.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>requestMinDelayMs</b></code>
    * to a valid <code>long</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.requestMinDelayMs", Long.toString(500));
    * </code></pre>
    *
    * Valid range is <code> [0 <= <i>min</i> <= <i>max</i> <= 3 600 000] </code> ({<code> [0 ms ... 1 hr] </code>).
    * The default value is <code>20</code> ms.
    *
    * @see #REQUEST
    * @see #REQUEST_MAX_DELAY_MS
    */
   public static final long REQUEST_MIN_DELAY_MS =
         inRange(0, getSetting("requestMinDelayMs", 20L), 60 * 60 * 1000); //in ms

   /**
    * Max delay for request of attribute value update for discovered instances, in milliseconds.
    * <p>
    * A random value between <i>min</i> and <i>max</i> is used.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>requestMaxDelayMs</b></code>
    * to a valid <code>long</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.requestMaxDelayMs", Long.toString(1000));
    * </code></pre>
    *
    * Valid range is <code> [0 <= <i>min</i> <= <i>max</i> <= 3 600 000] </code> (<code> [0 ms ... 1 hr] </code>).
    * The default value is <code>50</code> ms.
    *
    * @see #REQUEST
    * @see #REQUEST_MIN_DELAY_MS
    */
   public static final long REQUEST_MAX_DELAY_MS =
         inRange(REQUEST_MIN_DELAY_MS, getSetting("requestMaxDelayMs", 50L), 60 * 60 * 1000); //in ms

   /**
    * Rate at which repeated requests for attribute value updates will be sent for uninitialized objects, in milliseconds.
    * <p>
    * The first repeated request will occur after the initial delayed attribute value update request.
    * <p>
    * The repeated requests are useful when, for instance, best-effort attributes are set as initialized as
    * additional requests increase the chance of the object reaching the initialized state.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>requestUninitializedRateMs</b></code>
    * to a valid <code>long</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.requestUninitializedRateMs", Long.toString(1000));
    * </code></pre>
    *
    * Valid range is <code> [10 <= <i>min</i> <= <i>max</i> <= 3 600 000] </code> (<code> [10 ms ... 1 hr] </code>).
    * The default value is <code>1000</code> ms.
    *
    * @see #REQUEST
    * @see #REQUEST_UNINITIALIZED_MAX_RETRIES
    *
    */
   public static final long REQUEST_UNINITIALIZED_RATE_MS =
         inRange(10, getSetting("requestUninitializedRateMs", 1000L), 60 * 60 * 1000) ; //in ms

   /**
    * Maximum amount of repeated attribute value update requests that are sent for an uninitialized object.
    * <p>
    * The first repeated request will occur after the initial delayed attribute value update request.
    * <p>
    * The repeated requests are useful when, for instance, best-effort attributes are set as initialized as
    * additional requests increase the chance of the object reaching the initialized state.
    * <p>
    * Setting this property to 0 will turn the repeated requests off.
    * <p>
    * Repeated requests won't be performed if no initialized attributes are specified.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>requestUninitializedMaxRetries</b></code>
    * to a valid <code>long</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.requestUninitializedMaxRetries", Long.toString(3));
    * </code></pre>
    *
    * Valid range is <code> [0 <= <i>min</i> <= <i>max</i> <= Long.MAX] </code> (<code> [0 ... 2^63-1] </code>).
    * The default value is <code>3</code>.
    *
    * @see #REQUEST
    * @see #REQUEST_UNINITIALIZED_RATE_MS
    *
    */
   public static final long REQUEST_UNINITIALIZED_MAX_RETRIES =
         inRange(0, getSetting("requestUninitializedMaxRetries", 3), Long.MAX_VALUE);

   /**
    * Perform an attribute update when a provide is requested.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>provide</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.provide", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    *
    * @see #REQUEST
    */
   public static final boolean PROVIDE = getSetting("provide", true);

   /**
    * Turn on the Convey Producing Federate option in the Switches table when using HLA Evolved
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>conveyProducingFederate</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.conveyProducingFederate", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.

    */
   public static final boolean CONVEY_PRODUCING_FEDERATE = getSetting("conveyProducingFederate", true);

   /**
    * Perform automatic achieve on all announced synchronization points.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>autoAchieve</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.autoAchieve", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean AUTO_ACHIEVE = getSetting("autoAchieve", true);

   /**
    * Include a dump of the data in <code>HlaDecodeException</code>.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>includeDataInDecodeException</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.includeDataInDecodeException", Boolean.toString(true));
    * </code></pre>
    *
    * The default value is <code>false</code>.
    *
    * @see uk.ac.brunel.see.exceptions.HlaDecodeException
    */
   public static final boolean INCLUDE_DATA_IN_DECODE_EXCEPTION = getSetting("includeDataInDecodeException", false);

   /**
    * Verify the length of all received data, throws a <code>HlaDecodeException</code> if the lengths do not match.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>verifyReceivedDataLength</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.verifyReceivedDataLength", Boolean.toString(true));
    * </code></pre>
    *
    * The default value is <code>false</code>.
    *
    * @see uk.ac.brunel.see.exceptions.HlaDecodeException
    */
   public static final boolean VERIFY_RECEIVED_DATA_LENGTH = getSetting("verifyReceivedDataLength", false);

   /**
    * The resign action.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>resignAction</b></code>
    * to a valid <code>int</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.resignAction", Integer.toString(0));
    * </code></pre>
    *
    * Valid values are:
    * <ul>
    *  <li> 0: UNCONDITIONALLY_DIVEST_ATTRIBUTES - divests all owned attributes </li>
    *  <li> 1: DELETE_OBJECTS - deletes all object instances that the federate owns </li>
    *  <li> 2: CANCEL_PENDING_OWNERSHIP_ACQUISITIONS - cancels all pending ownership acquisitions </li>
    *  <li> 3: DELETE_OBJECTS_THEN_DIVEST - combines 1 and 0 </li>
    *  <li> 4: CANCEL_THEN_DELETE_THEN_DIVEST - combines 2, 1, and 0 </li>
    *  <li> 5: NO_ACTION - no handling of owned attributes or pending acquisitions </li>
    * </ul>
    * <p>
    * The default value is <code>4</code>, CANCEL_THEN_DELETE_THEN_DIVEST.
    */
   public static final int RESIGN_ACTION = getSetting("resignAction", 4);

   /**
    * Create the federation with the selected time representation for logical time,
    * even if HLA Time Management is not enabled.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>createFederationWithTimeRepresentation</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.createFederationWithTimeRepresentation", Boolean.toString(true));
    * </code></pre>
    *
    * The default value is <code>false</code>.
    */
   public static final boolean CREATE_FEDERATION_WITH_TIME_REPRESENTATION = getSetting("createFederationWithTimeRepresentation", false);

   /**
    * Always send and receive updates and interactions with a <code>LogicalTime</code>,
    * even if HLA Time Management is not enabled.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>alwaysUseLogicalTime</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.alwaysUseLogicalTime", Boolean.toString(true));
    * </code></pre>
    *
    * The default value is <code>false</code>.
    */
   public static final boolean ALWAYS_USE_LOGICAL_TIME = getSetting("alwaysUseLogicalTime", false);

   /**
    * Do an initial time advance to <code>GALT</code> when only time constrained.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>initialAdvanceWhenConstrained</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.initialAdvanceWhenConstrained", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean INITIAL_ADVANCE_WHEN_CONSTRAINED = getSetting("initialAdvanceWhenConstrained", true);

   /**
    * Do an initial time advance to a <code>LogicalTime</code> that is even divisible by the initial step size.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>initialAdvanceToEvenStepSize</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.initialAdvanceToEvenStepSize", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean INITIAL_ADVANCE_TO_EVEN_STEP_SIZE = getSetting("initialAdvanceToEvenStepSize", true);

   /**
    * Always advance time to the expected frame times when using <code>advanceToNextEvent()</code>.
    * <p>
    * Example (step size is 5 and an event is received at logical time 12):
    * <ul>
    *    <li>Granted logical times when value set to <code>true</code>: 5, 10, 12, 15, 20</li>
    *    <li>Granted logical times when value set to <code>false</code>: 5, 10, 12, 17, 22</li>
    * </ul>
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>forceFrameTimesWhenEventBased</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.forceFrameTimesWhenEventBased", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean FORCE_FRAME_TIMES_WHEN_EVENT_BASED = getSetting("forceFrameTimesWhenEventBased", true);

   /**
    * Deliver receive order messages in both time advancing state and time granted state.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>enableAsynchronousDelivery</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.enableAsynchronousDelivery", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean ENABLE_ASYNCHRONOUS_DELIVERY = getSetting("enableAsynchronousDelivery", true);

   /**
    * The capacity of the Notification Queue.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>notificationQueueCapacity</b></code>
    * to a valid <code>int</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.notificationQueueCapacity", Integer.toString(10));
    * </code></pre>
    *
    * Valid range is <code> [0 <= <i>value</i> <= Integer.MAX_VALUE] </code>.
    * Use <code>0</code> for unlimited capacity.
    * The default value is <code>200 000</code> entries.
    */
   public static final int NOTIFICATION_QUEUE_CAPACITY =
         inRange(0, getSetting("notificationQueueCapacity", 200000), Integer.MAX_VALUE);

   /**
    * The time advance methods will wait for all notifications received in the advancing state to
    * be precessed before returning from the advance method.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>advanceWaitsForNotifications</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.advanceWaitsForNotifications", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean ADVANCE_WAITS_FOR_NOTIFICATIONS = getSetting("advanceWaitsForNotifications", true);

   /**
    * If configured to use HLA Time Management, it will be enabled on connect.
    * <p>
    * If flag is false, <code>HlaWorld.enableTimeManagement</code> must be called to enable HLA Time Management.
    *
    * This flag has no effect if federate is not configured to use HLA Time Management.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>delayEnablingTimeManagement</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.enableTimeManagementOnConnect", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean ENABLE_TIME_MANAGEMENT_ON_CONNECT = getSetting("enableTimeManagementOnConnect", true);

   /**
    * Flag to control whether to throw {@link uk.ac.brunel.see.exceptions.HlaCredentialsNotSupportedException}
    * or not on the {@link HlaWorld.ExceptionListener} when passing credentials to an HLA version
    * that doesn't support credentials.
    * <p>
    * This value is changed by setting the System Property
    * <code>uk.ac.brunel.see.tuning.<b>warnOnCredentialsNotSupported</b></code>
    * to a valid <code>boolean</code> value before this class is loaded.
    *
    * <pre><code>
    * System.setProperty("uk.ac.brunel.see.tuning.warnOnCredentialsNotSupported", Boolean.toString(false));
    * </code></pre>
    *
    * The default value is <code>true</code>.
    */
   public static final boolean WARN_ON_CREDENTIALS_NOT_SUPPORTED = getSetting("warnOnCredentialsNotSupported", true);

   private static final String KEY_PREFIX = "uk.ac.brunel.see" + ".tuning.";

   private HlaTuning() {
   }

   private static int inRange(int min, int value, int max) {
      return Math.max(min, Math.min(value, max));
   }

   private static long inRange(long min, long value, long max) {
      return Math.max(min, Math.min(value, max));
   }

   private static String getSetting(String key, String defaultValue) {
      return System.getProperty(KEY_PREFIX + key, defaultValue);
   }

   private static int getSetting(String key, int defaultValue) {
      return Integer.getInteger(KEY_PREFIX + key, defaultValue);
   }

   private static long getSetting(String key, long defaultValue) {
      return Long.getLong(KEY_PREFIX + key, defaultValue);
   }

   private static boolean getSetting(String key, boolean defaultValue) {
      boolean result = defaultValue;
      try {
          result = Boolean.parseBoolean(System.getProperty(KEY_PREFIX + key, Boolean.toString(defaultValue)));
      } catch (IllegalArgumentException e) {
         //ignore
      } catch (NullPointerException e) {
         //ignore
      }
      return result;
   }
}
