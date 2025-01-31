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

import java.util.HashMap;
import java.util.Map;

final class PersistentHelper<T extends PersistentHelper.State> {

   /** Marker interface for all savable state. */
   public interface State {
   }

   private final Map<String, T> _persistentState = new HashMap<String, T>();

   public boolean saveState(String label, T state) {
      _persistentState.put(label, state);
      return true;
   }

   public T loadState(String label) {
      return _persistentState.get(label);
   }

   public void clear() {
      _persistentState.clear();
   }
}
