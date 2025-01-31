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
import uk.ac.brunel.see.impl.HlaNoCredentials;
import uk.ac.brunel.see.impl.HlaPasswordCredentials;

/**
 * Credentials object interface. Provided by {@link HlaCredentialsProvider} to {@link HlaWorld} when
 * connecting.
 * <p>
 * Due to the sensitive nature of credentials data, credentials should not exist in memory longer
 * than necessary. Therefore, we recommend that credentials objects are instantiated as part of
 * {@link HlaCredentialsProvider#getCredentials(HlaSettings.HlaVersion)}, and disposed of in
 * {@link HlaCredentialsProvider#clean(HlaCredentials)}.
 *
 * @see HlaCredentialsProvider
 * @see #createNoCredentials()
 * @see #createPasswordCredentials(char[])
 * @see #createPasswordCredentials(String)
 */
public interface HlaCredentials {

   /**
    * Credentials type indicating the absence of credentials. Defined by the HLA 4 standard.
    * <p>
    * HlaCredentials objects with this type should set the credentials data to an empty array
    * (zero-length). This is done automatically if you use {@link #createNoCredentials()}.
    *
    * @see #createNoCredentials()
    */
   String NO_CREDENTIALS_TYPE = "HLAnoCredentials";

   /**
    * Credentials type for plain text passwords. Defined by the HLA 4 standard.
    * <p>
    * HlaCredentials objects of this type should set the credentials data to an encoded
    * HLAunicodeString. This is done automatically if you use
    * {@link #createPasswordCredentials(char[])} or {@link #createPasswordCredentials(String)}.
    *
    * @see #createPasswordCredentials(char[])
    * @see #createPasswordCredentials(String)
    */
   String PLAIN_TEXT_PASSWORD_TYPE = "HLAplainTextPassword";

   /**
    * Creates credentials of the type "HLAnoCredentials". Using credentials of this type is in
    * practice the same as not providing credentials.
    * <p>
    * You may use this when implementing
    * {@link HlaCredentialsProvider#getCredentials(HlaSettings.HlaVersion)}.
    * <p>
    * The returned credentials object does not trigger {@link HlaCredentialsNotSupportedException}.
    *
    * @return credentials of the type "HLAnoCredentials"
    *
    * @see HlaCredentials#NO_CREDENTIALS_TYPE
    * @see HlaCredentialsProvider
    */
   static HlaCredentials createNoCredentials() {
      return HlaNoCredentials._hlaNoCredentialsInstance;
   }

   /**
    * Creates credentials of the type "HLAplainTextPassword".
    * <p>
    * You may use this when implementing
    * {@link HlaCredentialsProvider#getCredentials(HlaSettings.HlaVersion)}.
    * <p>
    * The created credentials object will save a copy of the encoded password. Delete this copy
    * after use, using {@link #dispose()}, which by default will be called from
    * {@link HlaCredentialsProvider#clean(HlaCredentials)}. Note that this makes the instance
    * "single use".
    *
    * @param password Password as a char array.
    *
    * @return credentials of the type "HLAplainTextPassword"
    *
    * @see HlaCredentials#NO_CREDENTIALS_TYPE
    * @see HlaCredentialsProvider
    */
   static HlaCredentials createPasswordCredentials(char[] password) {
      return new HlaPasswordCredentials(password);
   }

   /**
    * Creates credentials of the type "HLAplainTextPassword".
    * <p>
    * You may use this when implementing
    * {@link HlaCredentialsProvider#getCredentials(HlaSettings.HlaVersion)}.
    * <p>
    * The created credentials object will save a copy of the encoded password. Delete this copy
    * after use, using {@link #dispose()}, which by default will be called from
    * {@link HlaCredentialsProvider#clean(HlaCredentials)}. Note that this makes the instance
    * "single use".
    * <p>
    * <b>IMPORTANT:</b> While this create method is convenient, it is not recommended to use
    * {@link String} for sensitive data. This is due to the immutability of Strings and the way
    * Java manages them in memory.
    * <p>
    * It is more secure to avoid using {@link String} for your password, and instead use a char
    * array together with {@link #createPasswordCredentials(char[])}, since the array data can be
    * overwritten when no longer needed.
    *
    * @param password Password.
    *
    * @return credentials of the type "HLAplainTextPassword"
    *
    * @see HlaCredentials#NO_CREDENTIALS_TYPE
    * @see #createPasswordCredentials(char[])
    * @see HlaCredentialsProvider
    */
   static HlaCredentials createPasswordCredentials(String password) {
      return new HlaPasswordCredentials(password);
   }


   /**
    * Gets the type of credentials. Can be one of the predefined types, or any custom type.
    *
    * @return The credentials type.
    *
    * @see HlaCredentials#NO_CREDENTIALS_TYPE
    * @see HlaCredentials#PLAIN_TEXT_PASSWORD_TYPE
    */
   String getType();

   /**
    * Gets the credentials data.
    *
    * @return The credentials data as a byte array.
    */
   byte[] getData();

   /**
    * Disposes of the credentials.
    * <p>
    * If the credentials contain sensitive data, it is good practice to completely remove them from
    * memory as soon as they're no longer needed. This can be done by overwriting the data.
    */
   void dispose();
}