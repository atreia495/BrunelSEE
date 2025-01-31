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
import uk.ac.brunel.see.datatypes.ToStringBuilder;
import uk.ac.brunel.see.exceptions.*;
import uk.ac.brunel.see.impl.fed.AbstractInteractionClassManager;
import uk.ac.brunel.see.impl.fed.FederateInteractionManager;
import uk.ac.brunel.see.impl.utils.*;

import se.pitch.ral.api.InteractionClassHandle;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/* @ThreadSafe */
final class HlaInteractionManagerImpl extends AbstractInteractionClassManager implements HlaInteractionManager {


   private static class InteractionInfo {
      private final boolean _local;
      private final boolean _remote;

      public InteractionInfo(boolean local, boolean remote) {
         _local = local;
         _remote = remote;
      }

      public boolean isLocal() {
         return _local;
      }

      public boolean isRemote() {
         return _remote;
      }
   }


   private static final Map<String, InteractionInfo> INTERACTIONS = new MapAdapter<String, InteractionInfo>()
         .unmodifiableMap();


   private final FederateInteractionManager _interactionManager;
   private final HlaWorldImpl _hlaWorld;

   private final DualMap<String, InteractionClassHandle> _interactions =
      new DualMap<String, InteractionClassHandle>(0);



   HlaInteractionManagerImpl(FederateInteractionManager interactionManager, HlaWorldImpl hlaWorld) {
      _interactionManager = interactionManager;
      _hlaWorld = hlaWorld;

      for (String name : INTERACTIONS.keySet()) {
         _interactionManager.addManager(this, name);
      }
   }

   /*
    * HlaInteractionManager interface impl
    */


   /*
    * AbstractInteractionClassManager impl
    */

   @Override
   public void receiveInteraction(InteractionClassHandle interactionClassHandle, NameValueMap parameters, HlaTimeStamp hlaTimeStamp, HlaLogicalTime logicalTime, HlaFederateId producingFederate) {
      String interactionName = _interactions.getByValue(interactionClassHandle);
      if (interactionName == null) {
         _hlaWorld.postException(new HlaInternalException("Received unknown interaction: " + interactionClassHandle));
         return;
      }

      _hlaWorld.postException(new HlaInternalException("Received unknown interaction: " + interactionName));
   }

   @Override
   public boolean isEnabled(String interactionClassName) {
      return true;
   }

   @Override
   public boolean isOptional(String interactionClassName) {
      return false;
   }

   @Override
   public void connected(InteractionClassHandle interactionClassHandle, String interactionClassName)
         throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
                HlaSaveInProgressException, HlaRestoreInProgressException {
      _interactions.put(interactionClassName, interactionClassHandle);

      InteractionInfo interactionInfo = INTERACTIONS.get(interactionClassName);
      if (interactionInfo == null) {
         return;
      }

      if (interactionInfo.isLocal()) {
         _interactionManager.publish(interactionClassHandle);
      }

      if (interactionInfo.isRemote()) {
         _interactionManager.subscribe(interactionClassHandle);
      }
   }

   @Override
   public void disconnected(InteractionClassHandle interactionClassHandle) {
      _interactions.removeByValue(interactionClassHandle);
   }


   /*
    * Private functions
    */


}
