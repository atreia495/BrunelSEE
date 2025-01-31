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
import uk.ac.brunel.see.HlaTimeStamp;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.HlaWorldImpl;
import uk.ac.brunel.see.impl.utils.Pair;
import uk.ac.brunel.see.impl.utils.NameValueMap;

import se.pitch.ral.api.InteractionClassHandle;
import se.pitch.ral.api.Region;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public final class FederateInteractionManager {

   private final FederateManager _federate;
   private final HlaWorldImpl _hlaWorld;
   private final Map<InteractionClassHandle, AbstractInteractionClassManager> _interactionClassManagers =
         new ConcurrentHashMap<InteractionClassHandle, AbstractInteractionClassManager>();
   private final List<Pair<AbstractInteractionClassManager, String>> _wannabeManagers =
         new ArrayList<Pair<AbstractInteractionClassManager, String>>();


   public FederateInteractionManager(FederateManager federate, HlaWorldImpl hlaWorld) {
      _federate = federate;
      _hlaWorld = hlaWorld;
      _federate.setInteractionManager(this);
   }

   public void addManager(AbstractInteractionClassManager manager, String hlaInteractionClassName) {
      _wannabeManagers.add(Pair.create(manager, hlaInteractionClassName));
   }

   public void subscribe(InteractionClassHandle interactionClassHandle)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.subscribeInteractionClass(interactionClassHandle);
   }

   public void subscribe(InteractionClassHandle interactionClassHandle, Set<Region> newRegion, Set<Region> oldRegion)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.subscribeInteractionClass(interactionClassHandle, newRegion, oldRegion);
   }

   public void publish(InteractionClassHandle interactionClassHandle)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.publishInteractionClass(interactionClassHandle);
   }

   public void sendInteraction(InteractionClassHandle interactionClassHandle, NameValueMap parameters,
                               HlaTimeStamp timeStamp, HlaLogicalTime logicalTime)
         throws HlaNotConnectedException, HlaFomException, HlaInvalidLogicalTimeException, HlaInternalException,
                HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.sendInteraction(interactionClassHandle, parameters, timeStamp, logicalTime);
   }

   public void sendInteraction(InteractionClassHandle interactionClassHandle, NameValueMap parameters,
                               HlaTimeStamp timeStamp, HlaLogicalTime logicalTime, Region region)
         throws HlaNotConnectedException, HlaFomException, HlaInvalidLogicalTimeException, HlaInternalException,
                HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      _federate.sendInteraction(interactionClassHandle, parameters, timeStamp, logicalTime, region);
   }

   // Callbacks

   void receiveInteraction(InteractionClassHandle interactionClassHandle, NameValueMap parameters,
                           HlaTimeStamp hlaTimeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      AbstractInteractionClassManager classManager = _interactionClassManagers.get(interactionClassHandle);

      if (classManager != null) {
         classManager.receiveInteraction(interactionClassHandle, parameters, hlaTimeStamp, logicalTime, producingFederate);
      } else {
         _hlaWorld.postException(new HlaInternalException("Failed to find interaction class manager: " + interactionClassHandle));
      }
   }

   void connected() throws HlaFomException, HlaInternalException, HlaNotConnectedException,
                           HlaRtiException, HlaSaveInProgressException, HlaRestoreInProgressException {
      for (Pair<AbstractInteractionClassManager, String> pair : _wannabeManagers) {
         AbstractInteractionClassManager interactionClassManager = pair.getFirst();
         String interactionClassName = pair.getSecond();
         if (interactionClassManager.isEnabled(interactionClassName)) {
            try {
               InteractionClassHandle interactionClassHandle = _federate.getInteractionClassHandle(interactionClassName);
               _interactionClassManagers.put(interactionClassHandle, interactionClassManager);
               interactionClassManager.connected(interactionClassHandle, interactionClassName);
            } catch (HlaFomException e) {
               if (!interactionClassManager.isOptional(interactionClassName)) {
                  throw e;
               }
            }
         }
      }
   }

   void disconnect() {
      Iterator<Map.Entry<InteractionClassHandle, AbstractInteractionClassManager>> iterator =
            _interactionClassManagers.entrySet().iterator();
      while (iterator.hasNext()) {
         Map.Entry<InteractionClassHandle, AbstractInteractionClassManager> entry = iterator.next();
         iterator.remove();

         AbstractInteractionClassManager interactionClassManager = entry.getValue();
         InteractionClassHandle interactionClassHandle = entry.getKey();
         interactionClassManager.disconnected(interactionClassHandle);
      }
   }
}
