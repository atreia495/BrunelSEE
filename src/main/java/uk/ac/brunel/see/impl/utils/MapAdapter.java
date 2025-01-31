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


public final class MapAdapter<K, V> {
   private final Map<K, V> _map;

   public MapAdapter(Map<K, V> map) {
      _map = map;
   }

   public MapAdapter() {
      _map = new HashMap<K, V>();
   }

   public MapAdapter<K, V> append(K key, V value) {
      _map.put(key, value);
      return this;
   }

   public Map<K, V> unmodifiableMap() {
      return Collections.unmodifiableMap(_map);
   }

   /*
    * Static utility methods for Iterables and Iterators
    */

   public static <T> Iterator<T> concat(final Iterator<T> first, final Iterator<T> second) {
      return new Iterator<T>() {
         private Iterator<T> _current = first;

         @Override
         public boolean hasNext() {
            if (_current.hasNext()) {
               return true;
            } else {
               if (_current == first) {
                  _current = second;
                  return hasNext();
               } else {
                  return false;
               }
            }
         }

         @Override
         public T next() {
            if (hasNext()) {
               return _current.next();
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            _current.remove();
         }
      };
   }

   public static <T> Iterable<T> concat(final Iterable<T> first, final Iterable<T> second) {
      return new Iterable<T>() {
         @Override
         public Iterator<T> iterator() {
            return concat(first.iterator(), second.iterator());
         }
      };
   }

   /*
    * Static utility method for Maps with the same values for all keys.
    */
   private static final class SameValueForAllKeysMap<K, V> extends AbstractMap<K, V> {
      private final Set<K> _keys;
      private final V _value;
      private Set<Map.Entry<K,V>> _entrySet;

      public SameValueForAllKeysMap(Set<K> keys, V value) {
         _keys = keys;
         _value = value;
      }

      @Override
      public int size() {
         return _keys.size();
      }

      @Override
      public boolean containsValue(Object value) {
         return _value.equals(value);
      }

      @Override
      public boolean containsKey(Object key) {
         return _keys.contains(key);
      }

      @Override
      public V get(Object key) {
         if (_keys.contains(key)) {
            return _value;
         } else {
            return null;
         }
      }

      @Override
      public V put(K key, V value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public V remove(Object key) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void clear() {
         throw new UnsupportedOperationException();
      }

      @Override
      public Set<K> keySet() {
         return _keys;
      }

      @Override
      public Set<Entry<K, V>> entrySet() {
         if (_entrySet == null) {
            _entrySet = new EntrySet();
         }
         return _entrySet;
      }

      private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
         @Override
         public int size() {
            return SameValueForAllKeysMap.this.size();
         }

         @Override
         public void clear() {
            SameValueForAllKeysMap.this.clear();
         }

         @Override
         public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
         }

         @Override
         public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
               return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;

            return SameValueForAllKeysMap.this.containsKey(entry.getKey()) &&
                   SameValueForAllKeysMap.this.containsValue(entry.getValue());
         }
      }

      private final class EntryIterator implements Iterator<Entry<K,V>> {
         private final Iterator<K> _iter;

         public EntryIterator() {
            _iter = SameValueForAllKeysMap.this._keys.iterator();
         }

         @Override
         public boolean hasNext() {
            return _iter.hasNext();
         }

         @Override
         public Entry<K, V> next() {
            return new SimpleImmutableEntry<K, V>(_iter.next(), _value);
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      }
   }

   public static <K, V> Map<K, V> mapWithSameValueForAllKeys(Set<K> keys, V value) {
      return new SameValueForAllKeysMap<K, V>(keys, value);
   }
}
