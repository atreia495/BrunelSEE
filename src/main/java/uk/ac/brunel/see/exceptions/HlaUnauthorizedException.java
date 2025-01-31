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

import uk.ac.brunel.see.HlaCredentialsProvider;
import uk.ac.brunel.see.HlaWorld;

/**
 * Exception thrown if the credentials provided by the {@link HlaCredentialsProvider}
 * were not accepted by the RTI when connecting.
 *
 * @see HlaWorld#connect()
 */
public final class HlaUnauthorizedException extends HlaBaseException {
   public HlaUnauthorizedException(String message) {
      super(message);
   }

   public HlaUnauthorizedException(String message, Throwable cause) {
      super(message, cause);
   }
}