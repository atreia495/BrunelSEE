package uk.ac.brunel.see.exceptions;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import uk.ac.brunel.see.HlaCredentials;
import uk.ac.brunel.see.HlaCredentialsProvider;
import uk.ac.brunel.see.HlaSettings;
import uk.ac.brunel.see.HlaWorld;

/**
 * Exception thrown if a {@link HlaCredentialsProvider} fails to provide or clean
 * credentials during connect.
 * <p>
 * Can be thrown by you inside
 * {@link HlaCredentialsProvider#getCredentials(HlaSettings.HlaVersion)}. Any RuntimeException
 * thrown by the provider will be wrapped in this exception. The exception is propagated and thrown
 * by the {@link HlaWorld#connect()} method.
  * <p>
 * Additionally, any RuntimeException thrown in {@link HlaCredentialsProvider#clean(HlaCredentials)} will be wrapped in this
 * exception and then posted on the {@link HlaWorld.ExceptionListener}.
 *
 * @see HlaCredentialsProvider#getCredentials(HlaSettings.HlaVersion)
 * @see HlaCredentialsProvider#clean(HlaCredentials)
 * @see HlaWorld#connect()
 */
public final class HlaCredentialsProviderException extends HlaBaseException
{
   public HlaCredentialsProviderException(String message) {
      super(message);
   }

   public HlaCredentialsProviderException(String message, Throwable cause) {
      super(message, cause);
   }
}