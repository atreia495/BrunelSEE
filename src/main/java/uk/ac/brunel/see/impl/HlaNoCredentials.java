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

/**
 * Implementation of the credentials type "HLAnoCredentials", defined in the HLA 4 standard. Using
 * credentials of this type is in practice the same as not providing credentials.
 *
 * @see HlaCredentials#createNoCredentials()
 */
public final class HlaNoCredentials implements HlaCredentials
{
    public static final HlaNoCredentials _hlaNoCredentialsInstance = new HlaNoCredentials();

    private static final byte[] _data = new byte[0];

    public HlaNoCredentials() {}

    @Override
    public String getType() {
        return NO_CREDENTIALS_TYPE;
    }

    @Override
    public byte[] getData()
    {
        return _data;
    }

    @Override
    public void dispose() {}
}