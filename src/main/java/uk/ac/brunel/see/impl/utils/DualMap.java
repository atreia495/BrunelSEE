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

import java.util.HashMap;
import java.util.Map;


/* @ThreadSafe */
public final class DualMap<K, V> {

   private final Map<K, V> _map;
   private final Map<V, K> _reverseMap;

   public DualMap(int initialSize) {
      _map = new HashMap<K, V>(initialSize);
      _reverseMap = new HashMap<V, K>(initialSize);
   }

   public DualMap() {
      _map = new HashMap<K, V>();
      _reverseMap = new HashMap<V, K>();
   }

   public synchronized void put(K key, V value) throws IllegalArgumentException {
      if (null != _reverseMap.get(value) ||
          null != _map.get(key)) {
         throw new IllegalArgumentException("Key or value already exists!");
      }
      _map.put(key, value);
      _reverseMap.put(value, key);
   }

   public synchronized V get(K key) {
      return _map.get(key);
   }

   public synchronized K getByValue(V value) {
      return _reverseMap.get(value);
   }

   public synchronized V remove(K key) {
      V value = _map.remove(key);
      if (value != null) {
         _reverseMap.remove(value);
      }
      return value;
   }

   public synchronized K removeByValue(V value) {
      K key = _reverseMap.remove(value);
      if (key != null) {
         _map.remove(key);
      }
      return key;
   }
}