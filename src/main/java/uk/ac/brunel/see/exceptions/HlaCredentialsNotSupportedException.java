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

import uk.ac.brunel.see.HlaSettings;
import uk.ac.brunel.see.HlaCredentialsProvider;

/**
 * Exception thrown if credentials are provided when connecting to a non-HLA 4 federation, to warn
 * that the credentials were not used for the connection.
 * <p>
 * This exception can be disabled with
 * {@link uk.ac.brunel.see.HlaTuning#WARN_ON_CREDENTIALS_NOT_SUPPORTED}.
 * <p>
 * When providing credentials with
 * {@link HlaCredentialsProvider#getCredentials(HlaSettings.HlaVersion)},
 * the HlaVersion argument can be checked to determine whether credentials are supported or not.
 */
public final class HlaCredentialsNotSupportedException extends HlaBaseRuntimeException {

   public HlaCredentialsNotSupportedException(String message) {
      super(message);
   }

   public HlaCredentialsNotSupportedException(String message, Throwable cause) {
      super(message, cause);
   }
}
