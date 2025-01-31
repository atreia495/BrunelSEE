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

import se.pitch.ral.api.detective.Detective;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


/**
 * Settings for a HlaWorld.
 * <p>
 * It is recommended to use the regular configuration mechanism of the application to control the HLA configuration.
 * This should be done by overriding this class, and combine that with the usual configuration mechanism.
 * <p>
 * It is possible to override the settings for a HlaWorld by settings the <i>System Property</i>
 * <code>uk.ac.brunel.see.settings</code> to a valid <i>Properties</i> file.
 * For example
 * <pre><code>
 * java -Duk.ac.brunel.see.settings=MySettingsFile ...
 * </code></pre>
 * where <code>MySettingsFile</code> is the settings file. If this override is used, settings
 * provided during HlaWorld creation will <b>not</b> be used.
 * <p>
 * A template for the settings file:

<pre><code>
#
# HlaWorld template settings file for a Developer Studio generated federate
#

# CRC host
crcHost = localhost

# CRC port
crcPort = 8989

# Local Settings Designator for HLA 1516-2010 Evolved
localSettingsDesignator = 

# Federate name
federateName = SpaceCraft

# Federation name
federationName = SEE2025

# Plain text password for authorized connection to the RTI.
# Note that this isn't a secure way to supply a password.
# Leave empty for no credentials (HLAnoCredentials).
federationPassword =

# HLA 1516-2000 FOM URL
classicFomURL = 

# Main HLA 1516-2010 Evolved FOM Module URL
evolvedFomURL = SISO_SpaceFOM_datatypes.xml

# Additional HLA 1516-2010 Evolved FOM Module URLs
additionalFomURLs = SISO_SpaceFOM_entity.xml ; SISO_SpaceFOM_environment.xml ; SISO_SpaceFOM_management.xml ; SISO_SpaceFOM_switches.xml

# HLA 1.3 FED URL
fedURL = 

# Try to create the federation before join
createFederation = true

# Try to destroy the federation after resign
destroyFederation = true

# RTI Driver Profile to use. Can be empty or set to one of the supported profiles.
# If empty, the first available profile is used.
# Available profiles: A, B, C, D, E, F, G, H, I, J, K, N.
# Profiles A, F, H and J are deprecated and will be removed in the future.
rtiDriverProfile = B

# Enable HLA Time Management with time constrained
timeConstrained = true

# Enable HLA Time Management with time regulating
timeRegulating = true

# Step size
stepSize = 1

# HLA Time Management lookahead
lookahead = 1

# Time stamp factory to use (none, system_clock, rpr_relative or rpr_absolute)
timeStampFactory = system_clock
</code></pre>

 */
public class HlaSettings {

   /**
    * HLA version
    */
   public enum HlaVersion {
      /** HLA 1.3 */
      HLA_13,
      /** HLA 1516-2000 */
      HLA_1516,
      /** HLA 1516-2010 Evolved */
      HLA_EVOLVED,
      /** HLA 1516-202X, version 4 */
      HLA_4,
      /** No HLA version, used by the DIS profile */
      NONE
   }

   /**
    * Enumeration for different HLA call types
    */
   public enum CallType {
      /** Create Federation Execution */
      CREATE,
      /** Join Federation Execution */
      JOIN
   }

   protected String _crcHost;
   protected int _crcPort;
   protected String _localSettingsDesignator = getSetting(LOCAL_SETTINGS_DESIGNATOR, "");
   protected String _federateName = getSetting(FEDERATE_NAME_KEY, "SpaceCraft");
   protected String _federateType = getSetting(FEDERATE_TYPE_KEY, "SpaceCraft");
   protected String _federationName = getSetting(FEDERATION_NAME_KEY, "SEE2025");
   protected String _fedUrl = getSetting(FED_URL_KEY, getSetting(FOM_URL_KEY, ""));
   protected String _classicFomUrl = getSetting(CLASSIC_FOM_URL_KEY, getSetting(FOM_URL_KEY, ""));
   protected String _evolvedFomUrl = getSetting(EVOLVED_FOM_URL_KEY, getSetting(FOM_URL_KEY, "SISO_SpaceFOM_datatypes.xml"));
   protected String[] _additionalFomUrls = getSettings(ADDITIONAL_FOM_URLS_KEY, "SISO_SpaceFOM_entity.xml;SISO_SpaceFOM_environment.xml;SISO_SpaceFOM_management.xml;SISO_SpaceFOM_switches.xml");
   protected boolean _createFederation = getSetting(CREATE_FEDERATION_KEY, true);
   protected boolean _destroyFederation = getSetting(DESTROY_FEDERATION_KEY, true);
   protected String _rtiDriverProfile = getSetting(RTI_DRIVER_PROFILE_KEY, "");
   protected boolean _timeConstrained = getSetting(TIME_CONSTRAINED_KEY, true);
   protected boolean _timeRegulating = getSetting(TIME_REGULATING_KEY, true);
   protected long _stepSize = getSetting(STEP_SIZE_KEY, 1);
   protected long _lookahead = getSetting(LOOKAHEAD_KEY, 1);
   protected HlaCredentialsProvider _credentialsProvider;

   /**
    * Creates a HlaSettings with the default settings, and then reads the settings file and updates the
    * settings that are specified in the file.
    */
   public HlaSettings() {
      _crcHost = getSetting(CRC_HOST_KEY, "localhost");
      _crcPort = getSetting(CRC_PORT_KEY, 8989);
      setDefaultCredentialsProvider();
   }

   /**
    * Creates a HlaSettings with default settings, then overrides them with settings from the
    * settings file. Finally, overrides CRC host and CRC port with the values specified here.
    *
    * @param crcHost CRC host
    * @param crcPort CRC port
    */
   public HlaSettings(String crcHost, int crcPort) {
      _crcHost = crcHost;
      _crcPort = crcPort;
      setDefaultCredentialsProvider();
   }

   /**
    * Creates a HlaSettings with default settings, then overrides them with settings from the
    * settings file. Finally, overrides Local Settings Designator with the value specified here.
    *
    * @param localSettingsDesignator Local Settings Designator (LSD)
    */
   public HlaSettings(String localSettingsDesignator) {
      _localSettingsDesignator = localSettingsDesignator;
      _crcHost = "";
      setDefaultCredentialsProvider();
   }

   private void setDefaultCredentialsProvider() {
      String password = getSetting(FEDERATION_PASSWORD_KEY);
      _credentialsProvider = hlaVersion -> {
         if (password == null || password.isEmpty()) {
            return HlaCredentials.createNoCredentials();
         } else {
            return HlaCredentials.createPasswordCredentials(password);
         }
      };
   }

   /**
    * Get the CRC host.
    *
    * @return the CRC host
    */
   public String getCrcHost() {
      return _crcHost;
   }

   /**
    * Get the CRC port.
    *
    * @return the CRC port
    */
   public int getCrcPort() {
      return _crcPort;
   }

   /**
    * Get the Local Settings Designator (LSD) for HLA Evolved.
    * The LSD can be used instead of CRC Host and Port to specify CRC to connect to.
    * <p>
    * Note that the LSD is only used if connecting to HLA Evolved and to use the default LSD (an empty string),
    * the returned CRC host also has to be an empty string.
    *
    * @return the Local Settings Designator (LSD)
    */
   public String getLocalSettingsDesignator() {
       return _localSettingsDesignator;
   }

   /**
    * Get the Federate name.
    *
    * @return the Federate name
    */
   public String getFederateName() {
      return _federateName;
   }

   /**
    * Get the Federate type.
    *
    * @return the Federate type
    */
   public String getFederateType() {
      return _federateType;
   }

   /**
    * Get the Federation name.
    *
    * @return the Federation name
    */
   public String getFederationName() {
      return _federationName;
   }

   /**
    * Gets the credentials provider for Authorization.
    *
    * @param hlaVersion HLA version used when connecting. Can be used to determine whether
    *                   credentials are supported by the HLA version or not (only HLA 4 supports
    *                   credentials).
    *
    * @return the credentials provider
    *
    * @see HlaCredentialsProvider
    */
   public HlaCredentialsProvider getCredentialsProvider(HlaVersion hlaVersion) {
      return _credentialsProvider;
   }

   /**
    * Get the FOM URL.
    *
    * @param callType   Type of HLA call
    * @param hlaVersion HLA version of the RTI in use
    *
    * @return the FOM URL
    */
   public URL getFomUrl(CallType callType, HlaVersion hlaVersion) {
      try {
         switch (hlaVersion) {
            case HLA_13: return asURL(_fedUrl);
            case HLA_1516: return asURL(_classicFomUrl);
            case HLA_EVOLVED: return asURL(_evolvedFomUrl);
            case HLA_4: return asURL(_evolvedFomUrl);  // use Evolved FOM for now
         }
      } catch (MalformedURLException e) {
         //ignore
      }
      return null;
   }

   /**
    * Get additional FOM Module URLs.
    *
    * @param callType   Type of HLA call
    * @param hlaVersion HLA version of the RTI in use
    *
    * @return the FOM URLs
    */
   public URL[] getAdditionalFomUrls(CallType callType, HlaVersion hlaVersion) {
      List<URL> list = new ArrayList<URL>(_additionalFomUrls.length);
      for (String additionalFomUrl : _additionalFomUrls) {
         String fomUrl = additionalFomUrl.trim();
         if (fomUrl.length() > 0) {
            try {
               list.add(asURL(fomUrl));
            } catch (MalformedURLException e) {
               //ignore
            }
         }
      }
      return list.toArray(new URL[0]);
   }

   /**
    * Get if the Federation should be created before join.
    *
    * @return <code>true</code> if the Federation should be created before join
    */
   public boolean getCreateFederation() {
      return _createFederation;
   }

   /**
    * Get if the Federation should be destroyed after resign.
    *
    * @return <code>true</code> if the Federation should be destroyed after resign
    */
   public boolean getDestroyFederation() {
      return _destroyFederation;
   }

   /**
    * Get the RTI Driver Profile name.
    *
    * @param availableProfiles available RTI Driver Profiles
    *
    * @return The RTI Driver Profile name
    */
   public String getRtiDriverProfile(List<String> availableProfiles) {
      return _rtiDriverProfile;
   }

   /**
    * Get the list of RTI Driver Detectives.
    * <p>
    * <b>The ability to attach detectives must be used with caution. Misbehaving detectives can
    * cause the entire HlaWorld to misbehave or even crash.</b>        
    *
    * @return The RTI Driver Detectives, use <code>null</code> or an empty list to disable detectives.
    */
   public List<Detective> getRtiDriverDetectives() {
      return Collections.emptyList();
   }

   /**
    * Get if this federate should be time constrained using <code>HLA Time Management</code>.
    *
    * @return <code>true</code> if this federate should be time constrained
    */
   public boolean getTimeConstrained() {
      return _timeConstrained;
   }

   /**
    * Get if this federate should be time regulating using <code>HLA Time Management</code>.
    *
    * @return <code>true</code> if this federate should be time regulating
    */
   public boolean getTimeRegulating() {
      return _timeRegulating;
   }

   /**
    * Override the <code>HLA Logical Time Factory</code> class name for the specified HLA version.
    *
    * @param hlaVersion  the HLA version of the RTI in use
    * @param defaultName the default class name for the logical time factory
    *
    * @return the logical time factory class name to use.
    */
   public String getLogicalTimeFactoryName(HlaVersion hlaVersion, String defaultName) {
      return defaultName;
   }

   /**
    * Get the step size for this federate.
    *
    * @param nextLogicalTime the next logical time
    *
    * @return the step size for this federate
    */
   public long getStepSize(HlaLogicalTime nextLogicalTime) {
      return _stepSize;
   }

   /**
    * Set the step size for this federate.
    *
    * @param stepSize the new step size
    */
   public void setStepSize(long stepSize) {
      _stepSize = stepSize;
   }

   /**
    * Get the <code>HLA Time Management</code> lookahead for this federate.
    *
    * @param nextLogicalTime the next logical time
    *
    * @return the lookahead for this federate
    */
   public long getLookahead(HlaLogicalTime nextLogicalTime) {
      return _lookahead;
   }

   /**
    * Set the <code>HLA Time Management</code> lookahead for this federate.
    *
    * @param lookahead the new lookahead
    */
   public void setLookahead(long lookahead) {
      _lookahead = lookahead;
   }

   /**
    * Create default settings.
    *
    * @return a HlaSettings with the default settings
    */
   public static HlaSettings createDefault() {
      return new HlaSettings();
   }

   /**
    * Check if a settings file has be defined that should override the user supplied settings.
    *
    * @return <code>True</code> if a settings file has been defined, <code>false</code> otherwise.
    */
   public static boolean hasSettingsFile() {
      return LOADED_DEFAULT_SETTINGS;
   }

   /**
    * Get the time stamp factory name.
    *
    * @return The time stamp factory name.
    */
   public static String getTimeStampFactoryName() {
      return getSetting(TIME_STAMP_FACTORY_KEY, null);
   }


   private static final boolean LOADED_DEFAULT_SETTINGS = loadDefaultSettings();

   private static final String KEY_PREFIX = "uk.ac.brunel.see" + ".";

   private static final String SETTINGS_FILE_KEY = "settings";

   private static final String CRC_HOST_KEY = "crcHost";
   private static final String CRC_PORT_KEY = "crcPort";
   private static final String LOCAL_SETTINGS_DESIGNATOR = "localSettingsDesignator";
   private static final String FEDERATE_NAME_KEY = "federateName";
   private static final String FEDERATE_TYPE_KEY = "federateType";
   private static final String FEDERATION_NAME_KEY = "federationName";
   private static final String FEDERATION_PASSWORD_KEY = "federationPassword";
   private static final String FOM_URL_KEY = "fomURL";  //For backwards compatibility
   private static final String CLASSIC_FOM_URL_KEY = "classicFomURL";
   private static final String EVOLVED_FOM_URL_KEY = "evolvedFomURL";
   private static final String ADDITIONAL_FOM_URLS_KEY = "additionalFomURLs";
   private static final String FED_URL_KEY = "fedURL";
   private static final String CREATE_FEDERATION_KEY = "createFederation";
   private static final String DESTROY_FEDERATION_KEY = "destroyFederation";
   private static final String RTI_DRIVER_PROFILE_KEY = "rtiDriverProfile";
   private static final String TIME_STAMP_FACTORY_KEY = "timeStampFactory";
   private static final String TIME_CONSTRAINED_KEY = "timeConstrained";
   private static final String TIME_REGULATING_KEY = "timeRegulating";
   private static final String STEP_SIZE_KEY = "stepSize";
   private static final String LOOKAHEAD_KEY = "lookahead";

   private static boolean loadDefaultSettings() {
      String fileName = System.getProperty(KEY_PREFIX + SETTINGS_FILE_KEY);

      if (fileName == null) {
         return false;
      }

      try {
         Properties properties = new Properties();
         properties.load(new BufferedInputStream(new FileInputStream(fileName)));

         for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();

            if (System.getProperty(KEY_PREFIX + key) == null) {
               System.setProperty(KEY_PREFIX + key, value);
            }
         }
         return true;

      } catch (FileNotFoundException e) {
         System.err.println("Failed to find settings file: " + fileName);
      } catch (IOException e) {
         System.err.println("Failed to load settings file " + fileName + ": " + e.toString());
      } catch (Throwable e) {
         System.err.println("Failed to use settings file " + fileName + ": " + e.toString());
      }
      return false;
   }

   private static String getSetting(String key) {
      return System.getProperty(KEY_PREFIX + key);
   }

   private static String getSetting(String key, String defaultValue) {
      return System.getProperty(KEY_PREFIX + key, defaultValue);
   }

   private static String[] getSettings(String key, String defaultValue) {
      return System.getProperty(KEY_PREFIX + key, defaultValue).split(";");
   }

   private static int getSetting(String key, int defaultValue) {
      return Integer.getInteger(KEY_PREFIX + key, defaultValue);
   }

   private static double getSetting(String key, double defaultValue) {
      String str = System.getProperty(KEY_PREFIX + key);
      if (str != null) {
         try {
            return Double.valueOf(str);
         } catch (NumberFormatException e) {
            // ignore
         }
      }
      return defaultValue;
   }

   private static boolean getSetting(String key, boolean defaultValue) {
      try {
          return Boolean.parseBoolean(System.getProperty(KEY_PREFIX + key, Boolean.toString(defaultValue)));
      } catch (IllegalArgumentException e) {
         //ignore
      } catch (NullPointerException e) {
         //ignore
      }
      return defaultValue;
   }

   protected static URL asURL(String fileNameOrURL) throws MalformedURLException {
      try {
         return new URL(fileNameOrURL);
      } catch (MalformedURLException e) {
         //ignore
      }
      return new File(fileNameOrURL).toURI().toURL();
   }
}
