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

import uk.ac.brunel.see.exceptions.HlaValueNotSetException;

/**
 * A HlaFederateId is a unique identifier for a federate.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public interface HlaFederateId {

  /**
   * Check if this federate id has an encoded federate handle available
   *
   * @return <code>true</code> if this federate id has an encoded federate handle, <code>false</code> otherwise
   */
   boolean hasEncodedHlaFederateHandle();

  /**
   * Get the encoded <code>HLA federate handle</code> for this federate id.
   *
   * @return the encoded <code>HLA federate handle</code> for this federate id
   *
   * @throws HlaValueNotSetException if the federate name is unavailable
   */
   byte[] getEncodedHlaFederateHandle() throws HlaValueNotSetException;

  /**
   * True if this federate id has a federate name available
   *
   * @return true if a federate name is available
   */
   boolean hasFederateName();

  /**
   * Get the name of the federate for this federate handle
   *
   * @return name of the federate
   *
   * @throws HlaValueNotSetException if the federate name is unavailable
   */
   String getFederateName() throws HlaValueNotSetException;

  /**
   * Tests if two FederateIds are equal.
   *
   * @param obj the reference object with which to compare
   *
   * @return <code>true</code> if the objects are the same, <code>false</code> otherwise.
   */
   public boolean equals(Object obj);
}
