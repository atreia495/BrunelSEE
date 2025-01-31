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
import uk.ac.brunel.see.impl.utils.NameValueMap;

import se.pitch.ral.api.InteractionClassHandle;


public abstract class AbstractInteractionClassManager {
   public abstract void receiveInteraction(InteractionClassHandle interactionClassHandle, NameValueMap parameters,
                                           HlaTimeStamp hlaTimeStamp, HlaLogicalTime logicalTime,
                                           HlaFederateId producingFederate);

   public abstract boolean isEnabled(String interactionClassName);
   public abstract boolean isOptional(String interactionClassName);

   public abstract void connected(InteractionClassHandle interactionClassHandle, String interactionClassName)
      throws HlaNotConnectedException, HlaInternalException, HlaRtiException,
             HlaSaveInProgressException, HlaRestoreInProgressException;

   public abstract void disconnected(InteractionClassHandle interactionClassHandle);
}
