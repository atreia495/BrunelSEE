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

/**
 * Exception thrown if the supplied credentials have an invalid format.
 *
 * @see HlaCredentials
 */
public final class HlaInvalidCredentialsException extends HlaBaseRuntimeException {
   public HlaInvalidCredentialsException(String message) {
      super(message);
   }

   public HlaInvalidCredentialsException(String message, Throwable cause) {
      super(message, cause);
   }
}