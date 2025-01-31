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

import uk.ac.brunel.see.HlaLogicalTime;
import uk.ac.brunel.see.HlaTimeStamp;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.HlaWorldImpl;
import uk.ac.brunel.see.impl.utils.Pair;
import uk.ac.brunel.see.impl.utils.NameValueMap;

import uk.ac.brunel.see.HlaFederateId;
import se.pitch.ral.api.ObjectInstanceHandle;
import se.pitch.ral.api.ObjectClassHandle;
import se.pitch.ral.api.Region;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public final class ObjectManager {

   private static final Comparator<Pair<AbstractObjectClassManager, String>> MANAGER_COMPARATOR =
         new Comparator<Pair<AbstractObjectClassManager, String>>() {
            public int compare(Pair<AbstractObjectClassManager, String> o1, Pair<AbstractObjectClassManager, String> o2) {
               return o2.getSecond().compareToIgnoreCase(o1.getSecond());
            }
         };

   private final FederateManager _federate;
   private final HlaWorldImpl _hlaWorld;
   private final Object _registerNameInProgressLock = new Object();
   private final Map<ObjectClassHandle, AbstractObjectClassManager> _objectClassManagers =
         new ConcurrentHashMap<ObjectClassHandle, AbstractObjectClassManager>();
   private final List<Pair<AbstractObjectClassManager, String>> _wannabeManagers =
         new ArrayList<Pair<AbstractObjectClassManager, String>>();


   public ObjectManager(FederateManager federate, HlaWorldImpl hlaWorld) {
      _federate = federate;
      _hlaWorld = hlaWorld;
      _federate.setObjectManager(this);
   }

   public void addManager(AbstractObjectClassManager manager, String hlaObjectClassName) {
      _wannabeManagers.add(Pair.create(manager, hlaObjectClassName));
   }

   public void subscribe(ObjectClassHandle objectClassHandle, Set<String> attributeNames)
         throws HlaNotConnectedException, HlaFomException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.subscribeObjectClassAttributes(objectClassHandle, attributeNames);
   }

   public void subscribe(ObjectClassHandle objectClassHandle, Set<String> attributeNames, Set<Region> newRegion, Set<Region> oldRegion)
         throws HlaNotConnectedException, HlaFomException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.subscribeObjectClassAttributes(objectClassHandle, attributeNames, newRegion, oldRegion);
   }

   public void publish(ObjectClassHandle objectClassHandle, Set<String> attributeNames)
         throws HlaNotConnectedException, HlaFomException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.publishObjectClassAttributes(objectClassHandle, attributeNames);
   }

   public boolean registerObjectInstanceName(String instanceName)
         throws HlaNotConnectedException, HlaIllegalInstanceNameException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException{
      synchronized (_registerNameInProgressLock) {
         return _federate.registerObjectInstanceName(instanceName);
      }
   }

   public ObjectInstanceHandle createObjectInstance(ObjectClassHandle objectClassHandle, String instanceName)
         throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return _federate.createObjectInstance(objectClassHandle, instanceName);
   }

   public ObjectInstanceHandle createObjectInstance(ObjectClassHandle objectClassHandle, String instanceName,
                                                    Set<String> attributeNames, Region region)
         throws HlaInstanceNameInUseException, HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      return _federate.createObjectInstance(objectClassHandle, instanceName, attributeNames, region);
   }

   public void updateAttributeValues(ObjectInstanceHandle objectInstanceHandle, NameValueMap attributes,
                                     HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInvalidLogicalTimeException,
                HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.updateAttributeValues(objectInstanceHandle, attributes, timeStamp, logicalTime);
   }

   public void updateAttributeValues(ObjectInstanceHandle objectInstanceHandle, NameValueMap attributes,
                                     HlaTimeStamp timeStamp, HlaLogicalTime logicalTime,
                                     Set<String> attributeNames, Region newRegion, Region oldRegion)
         throws HlaNotConnectedException, HlaAttributeNotOwnedException, HlaInvalidLogicalTimeException,
                HlaInternalException, HlaRtiException, HlaObjectInstanceIsRemovedException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.updateAttributeValues(objectInstanceHandle, attributes, timeStamp, logicalTime, attributeNames, newRegion, oldRegion);
   }

   public void deleteObjectInstance(ObjectInstanceHandle objectInstanceHandle, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaInternalException, HlaInvalidLogicalTimeException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.deleteObjectInstance(objectInstanceHandle, timeStamp, logicalTime);
   }

   public void requestAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, Set<String> hlaAttributeNames,
                                           HlaTimeStamp currentTime)
         throws HlaInternalException, HlaRtiException, HlaNotConnectedException,
                HlaSaveInProgressException, HlaRestoreInProgressException{
      _federate.requestAttributeValueUpdate(objectInstanceHandle, hlaAttributeNames, currentTime);
   }

   // Callbacks

   void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle,
                               String instanceName, HlaTimeStamp timeStamp, HlaFederateId producingFederate) {
      AbstractObjectClassManager classManager = _objectClassManagers.get(objectInstanceHandle.getObjectClassHandle());

      if (classManager != null) {
         classManager.discoverObjectInstance(objectInstanceHandle, instanceName, timeStamp, producingFederate);
      } else {
         _hlaWorld.postException(new HlaInternalException("Failed to find object class manager: " + objectInstanceHandle.getObjectClassHandle()));
      }
   }

   void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle,
                               NameValueMap attributes, HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      AbstractObjectClassManager classManager = _objectClassManagers.get(objectInstanceHandle.getObjectClassHandle());

      if (classManager != null) {
         classManager.reflectAttributeValues(objectInstanceHandle, attributes, timeStamp, logicalTime, producingFederate);
      } else {
         _hlaWorld.postException(new HlaInternalException("Failed to find object class manager: " + objectInstanceHandle.getObjectClassHandle()));
      }
   }

   void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle,
                             HlaTimeStamp timeStamp, HlaLogicalTime logicalTime) {
      AbstractObjectClassManager classManager = _objectClassManagers.get(objectInstanceHandle.getObjectClassHandle());

      if (classManager != null) {
         classManager.removeObjectInstance(objectInstanceHandle, timeStamp, logicalTime);
      } else {
         _hlaWorld.postException(new HlaInternalException("Failed to find object class manager: " + objectInstanceHandle.getObjectClassHandle()));
      }
   }

   void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle,
                                    Set<String> attributes, HlaTimeStamp hlaTimeStamp) {
      AbstractObjectClassManager classManager = _objectClassManagers.get(objectInstanceHandle.getObjectClassHandle());

      // classManager may be null since provideAttributeValueUpdate is called on a separate thread
      if (classManager != null) {
         classManager.provideAttributeValueUpdate(objectInstanceHandle, attributes, hlaTimeStamp);
      }
   }

   void attributesInScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributes) {
       AbstractObjectClassManager classManager = _objectClassManagers.get(objectInstanceHandle.getObjectClassHandle());

       if (classManager != null) {
           classManager.attributesInScope(objectInstanceHandle, attributes);
       } else {
           _hlaWorld.postException(new HlaInternalException("Failed to find object class manager: " + objectInstanceHandle.getObjectClassHandle()));
       }
   }

   void attributesOutOfScope(ObjectInstanceHandle objectInstanceHandle, Set<String> attributes) {
       AbstractObjectClassManager classManager = _objectClassManagers.get(objectInstanceHandle.getObjectClassHandle());

       if (classManager != null) {
           classManager.attributesOutOfScope(objectInstanceHandle, attributes);
       } else {
           _hlaWorld.postException(new HlaInternalException("Failed to find object class manager: " + objectInstanceHandle.getObjectClassHandle()));
       }
   }

   void connected() throws HlaNotConnectedException, HlaFomException,
                           HlaRtiException, HlaInternalException,
                           HlaSaveInProgressException, HlaRestoreInProgressException {
      List<Pair<AbstractObjectClassManager, String>> sortedWannabeManagers =
            new ArrayList<Pair<AbstractObjectClassManager, String>>(_wannabeManagers);
      Collections.sort(sortedWannabeManagers, MANAGER_COMPARATOR);

      for (Pair<AbstractObjectClassManager, String> pair : sortedWannabeManagers) {
         AbstractObjectClassManager objectClassManager = pair.getFirst();
         String objectClassName = pair.getSecond();
         if (objectClassManager.isEnabled(objectClassName)) {
            try {
               ObjectClassHandle objectClassHandle = _federate.getObjectClassHandle(objectClassName);
               _objectClassManagers.put(objectClassHandle, objectClassManager);
               objectClassManager.connected(objectClassHandle, objectClassName);
            } catch (HlaFomException e) {
               if (!objectClassManager.isOptional(objectClassName)) {
                  throw e;
               }
            }
         }
      }
   }

   void disconnect() {
      Iterator<Map.Entry<ObjectClassHandle, AbstractObjectClassManager>> iterator =
            _objectClassManagers.entrySet().iterator();
      while (iterator.hasNext()) {
         Map.Entry<ObjectClassHandle, AbstractObjectClassManager> entry = iterator.next();
         iterator.remove();

         AbstractObjectClassManager objectClassManager = entry.getValue();
         ObjectClassHandle objectClassHandle = entry.getKey();
         objectClassManager.disconnected(objectClassHandle);
      }
   }

   public void save() throws HlaSaveFederateException {
      for (Map.Entry<ObjectClassHandle, AbstractObjectClassManager> entry : _objectClassManagers.entrySet()) {
         AbstractObjectClassManager objectClassManager = entry.getValue();
         ObjectClassHandle objectClassHandle = entry.getKey();
         objectClassManager.save(objectClassHandle);
      }
   }

   public void restore() throws HlaRestoreFederateException {
      for (Map.Entry<ObjectClassHandle, AbstractObjectClassManager> entry : _objectClassManagers.entrySet()) {
         AbstractObjectClassManager objectClassManager = entry.getValue();
         ObjectClassHandle objectClassHandle = entry.getKey();
         objectClassManager.restore(objectClassHandle);
      }
   }
}
