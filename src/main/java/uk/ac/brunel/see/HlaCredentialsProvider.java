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

import uk.ac.brunel.see.exceptions.HlaCredentialsNotSupportedException;
import uk.ac.brunel.see.exceptions.HlaCredentialsProviderException;

/**
 * Credentials Provider interface. Implement to provide credentials when connecting.
 * <p>
 * A credentials provider is stored in {@link HlaSettings} and used when {@link HlaWorld#connect()}
 * is called, to provide credentials which are passed to the RTI.
 * <p>
 * Note that only HLA 4 supports Authorization.
 *
 * @see HlaSettings
 * @see HlaCredentials
 */
public interface HlaCredentialsProvider {

   /**
    * Callback to provide credentials.
    * <p>
    * If credentials are provided when connecting to a non-HLA 4 federation, an
    * {@link HlaCredentialsNotSupportedException} is posted on the
    * {@link HlaWorld.ExceptionListener}, to warn that the credentials were not used for the
    * connection. This exception can be disabled with
    * {@link HlaTuning#WARN_ON_CREDENTIALS_NOT_SUPPORTED}.
    * <p>
    * Use the <code>hlaVersion</code> parameter to ensure HLA 4 is used on connect.
    * <p>
    * This method will be called from the same thread that called {@link HlaWorld#connect()}.
    * <p>
    * It is recommended to create the credentials object when this method is called, and then
    * disposing of the credentials object when {@link #clean(HlaCredentials)} is called. This
    * makes sure that the credentials object resides in memory for the shortest time possible.
    *
    * @param hlaVersion HLA version used when connecting. Can be used to determine whether
    *                   credentials are supported by the HLA version or not (only HLA 4 supports
    *                   credentials).
    *
    * @return HLA credentials used to connect. To not provide credentials, return
    * <code>null</code> or "HLAnoCredentials" ({@link HlaCredentials#createNoCredentials()}).
    *
    * @throws HlaCredentialsProviderException if credentials cannot be provided and connection
    * attempt should be aborted.
    *
    * @see HlaCredentials
    * @see HlaCredentials#createNoCredentials()
    * @see HlaCredentials#createPasswordCredentials(char[])
    * @see HlaCredentials#createPasswordCredentials(String)
    */
   HlaCredentials getCredentials(HlaSettings.HlaVersion hlaVersion) throws HlaCredentialsProviderException;

   /**
    * Callback that notifies the provider that credentials previously provided during connect are
    * now processed and no longer needed. This is a good time to remove sensitive data from memory,
    * by for example overwriting the credentials data array.
    * <p>
    * By default, {@link HlaCredentials#dispose()} will be called. This can be changed by
    * overriding the method.
    * <p>
    * If no credentials were provided, i.e. {@link #getCredentials(HlaSettings.HlaVersion)} returned
    * <code>null</code> or an exception was thrown, this method is also called with
    * <code>null</code>.
    * <p>
    * This method will be called from the same thread that called {@link HlaWorld#connect()}.
    *
    * @param credentials Credentials previously returned by
    *                    {@link #getCredentials(HlaSettings.HlaVersion)}, which may now be disposed
    *                    of. May be <code>null</code>.
    */
   default void clean(HlaCredentials credentials) {
      if (credentials != null) {
         credentials.dispose();
      }
   }
}