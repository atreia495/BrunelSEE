package uk.ac.brunel.see.impl.utils;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public final class SetAdapter<V> {
   private final Set<V> _set;

   public SetAdapter(Set<V> set) {
      _set = set;
   }

   public SetAdapter() {
      _set = new HashSet<V>();
   }

   public SetAdapter<V> append(V value) {
      _set.add(value);
      return this;
   }

   public Set<V> unmodifiableSet() {
      return Collections.unmodifiableSet(_set);
   }
}
