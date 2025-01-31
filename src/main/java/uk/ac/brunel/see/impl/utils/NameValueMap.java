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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public final class NameValueMap {

   private final Map<String, byte[]> _map;

   public NameValueMap() {
      _map = new HashMap<String, byte[]>();
   }

   public NameValueMap(int initialCapacity) {
      _map = new HashMap<String, byte[]>(initialCapacity);
   }

   public NameValueMap(Map<String, byte[]> values, boolean copyValue) {
      _map = new HashMap<String, byte[]>(values.size());
      putAll(values, copyValue);
   }

   public int size() {
      return _map.size();
   }

   public boolean isEmpty() {
      return _map.isEmpty();
   }

   public byte[] get(String key) {
      return _map.get(key);
   }

   public byte[] put(String key, byte[] value, boolean copyValue) {
      if (copyValue) {
         int length = value.length;
         byte[] newValue = new byte[length];
         System.arraycopy(value, 0, newValue, 0, length);
         return _map.put(key, newValue);
      } else {
         return _map.put(key, value);
      }
   }

   public void putAll(Map<String, byte[]> values, boolean copyValue) {
      if (copyValue) {
         for (Map.Entry<String, byte[]> entry : values.entrySet()) {
            put(entry.getKey(), entry.getValue(), copyValue);
         }
      } else {
         _map.putAll(values);
      }
   }

   public Set<Map.Entry<String, byte[]>> entrySet() {
      return _map.entrySet();
   }

   public Set<String> keySet() {
      return _map.keySet();
   }

   public Map<String, byte[]> getMap() {
      return Collections.unmodifiableMap(_map);
   }
}
