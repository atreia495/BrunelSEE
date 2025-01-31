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

import java.util.*;
import java.util.function.Function;

public abstract class CacheMap<K, V> {
   public final synchronized V get(K key) {
      return doGet(key);
   }

   public final synchronized V put(K key, V value) {
      return doPut(key, value);
   }

   public final synchronized V remove(K key) {
      return doRemove(key);
   }

   public final synchronized void clear() {
      doClear();
   }

   public static <K, V> CacheMap<K, V> create(Class<K> keyClass, Function<Integer, Boolean> removeEldestEntry) {
      if (!keyClass.isArray()) {
         return new PlainCacheMap<K, V>(removeEldestEntry);
      } else {
         return new ArrayKeyCacheMap<K, V>(keyClass, removeEldestEntry);
      }
   }

   abstract V doGet(K key);

   abstract V doPut(K key, V value);

   abstract V doRemove(K key);

   abstract void doClear();

   private static final class PlainCacheMap<K, V> extends CacheMap<K, V> {
      private final Function<Integer, Boolean> _removeEldestEntry;
      private final Map<K, V> _map;

      public PlainCacheMap(Function<Integer, Boolean> removeEldestEntry) {
         _removeEldestEntry = removeEldestEntry;
         _map = new LinkedHashMap<K, V>() {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
               return _removeEldestEntry.apply(size());
            }
         };
      }

      V doGet(K key) {
         return _map.get(key);
      }

      V doPut(K key, V value) {
         return _map.put(key, value);
      }

      V doRemove(K key) {
         return _map.remove(key);
      }

      void doClear() {
         _map.clear();
      }
   }

   private static final class ArrayKeyCacheMap<K, V> extends CacheMap<K, V> {
      private final class Key {
         private final K _key;

         Key(K key) {
            _key = key;
         }

         @Override
         @SuppressWarnings("unchecked")
         public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key that = (Key) o;

            return isEquals(_key, that._key);
         }

         @Override
         public int hashCode() {
            return getHashCode(_key);
         }
      }

      private final Function<Integer, Boolean> _removeEldestEntry;
      private final Map<Key, V> _map;
      private final Type _elementType;

      public ArrayKeyCacheMap(Class<K> keyClass, Function<Integer, Boolean> removeEldestEntry) {
         Class<?> componentType = keyClass.getComponentType();
         _elementType = Type.getType(componentType);

         _removeEldestEntry = removeEldestEntry;
         _map = new LinkedHashMap<Key, V>() {
            protected boolean removeEldestEntry(Map.Entry<Key, V> eldest) {
               return _removeEldestEntry.apply(size());
            }
         };
      }

      V doGet(K key) {
         return _map.get(wrapKey(key));
      }

      V doPut(K key, V value) {
         return _map.put(wrapKey(key), value);
      }

      V doRemove(K key) {
         return _map.remove(wrapKey(key));
      }

      void doClear() {
         _map.clear();
      }

      private Key wrapKey(K key) {
         return new Key(key);
      }

      private int getHashCode(K value) {
         switch (_elementType) {
            case LONG:
               return Arrays.hashCode((long[]) value);
            case INT:
               return Arrays.hashCode((int[]) value);
            case SHORT:
               return Arrays.hashCode((short[]) value);
            case CHAR:
               return Arrays.hashCode((char[]) value);
            case BYTE:
               return Arrays.hashCode((byte[]) value);

            case DOUBLE:
               return Arrays.hashCode((double[]) value);
            case FLOAT:
               return Arrays.hashCode((float[]) value);

            case BOOLEAN:
               return Arrays.hashCode((boolean[]) value);

            case OBJECT:
               return Arrays.deepHashCode((Object[]) value);
         }
         throw new IllegalArgumentException("Invalid element type: " + _elementType);
      }

      private boolean isEquals(K left, K right) {
         switch (_elementType) {
            case LONG:
               return Arrays.equals((long[]) left, (long[]) right);
            case INT:
               return Arrays.equals((int[]) left, (int[]) right);
            case SHORT:
               return Arrays.equals((short[]) left, (short[]) right);
            case CHAR:
               return Arrays.equals((char[]) left, (char[]) right);
            case BYTE:
               return Arrays.equals((byte[]) left, (byte[]) right);

            case DOUBLE:
               return Arrays.equals((double[]) left, (double[]) right);
            case FLOAT:
               return Arrays.equals((float[]) left, (float[]) right);

            case BOOLEAN:
               return Arrays.equals((boolean[]) left, (boolean[]) right);

            case OBJECT:
               return Arrays.deepEquals((Object[]) left, (Object[]) right);
         }
         throw new IllegalArgumentException("Invalid element type: " + _elementType);
      }
   }

   private enum Type {
      LONG(Long.TYPE),
      INT(Integer.TYPE),
      SHORT(Short.TYPE),
      CHAR(Character.TYPE),
      BYTE(Byte.TYPE),

      DOUBLE(Double.TYPE),
      FLOAT(Float.TYPE),

      BOOLEAN(Boolean.TYPE),

      OBJECT(Object.class);

      private final Class<?> _class;

      Type(Class<?> klass) {
         _class = klass;
      }

      public static Type getType(Class<?> componentType) {
         for (Type type : Type.values()) {
            if (type._class == componentType) {
               return type;
            }
         }
         // handle subclasses of Object
         if (OBJECT._class.isAssignableFrom(componentType)) {
            return OBJECT;
         }
         throw new IllegalArgumentException("Class not supported: " + componentType.getSimpleName());
      }
   }
}