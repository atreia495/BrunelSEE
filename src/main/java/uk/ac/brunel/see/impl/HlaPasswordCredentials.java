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

import uk.ac.brunel.see.HlaCredentials;
import se.pitch.encoders1516.ByteWrapper;

import java.util.Arrays;

/**
 * Implementation of the credential type "HLAplainTextPassword", defined in the HLA 4 standard.
 * <p>
 * Instances of this class will save a copy of the credentials data. Delete this copy after use, 
 * using {@link #dispose()}. Note that this makes the instance "single use".
 *
 * @see HlaCredentials#createPasswordCredentials(char[])
 * @see HlaCredentials#createPasswordCredentials(String)
 */
public final class HlaPasswordCredentials implements HlaCredentials
{
    private final byte[] _data;


    public HlaPasswordCredentials(char[] password) {
        _data = encodeHLAunicodeString(password);
    }

    public HlaPasswordCredentials(String password) {
        this(password.toCharArray());
    }

    @Override
    public String getType() {
        return PLAIN_TEXT_PASSWORD_TYPE;
    }

    @Override
    public byte[] getData() {
        return _data;
    }

    @Override
    public void dispose() {
        Arrays.fill(_data, (byte) 0);
    }

    // Implementation matching OmtHLAunicodeString, but takes care to not deal with String and
    // uncontrolled data copies.
    private byte[] encodeHLAunicodeString(char[] password) {
        int encodedLength = 4 + password.length * 2;
        int octetBoundary = 4;
        ByteWrapper byteWrapper = new ByteWrapper(encodedLength);
        byteWrapper.align(octetBoundary);
        byteWrapper.putInt(password.length);
        int len = password.length;
        for (int i = 0; i < len; i++) {
            int v = (int) password[i];
            byteWrapper.put((v >>> 8) & 0xFF);
            byteWrapper.put((v >>> 0) & 0xFF);
        }
        return byteWrapper.array();
    }
}