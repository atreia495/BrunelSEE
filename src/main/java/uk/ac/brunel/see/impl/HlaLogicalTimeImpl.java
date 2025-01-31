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

import uk.ac.brunel.see.HlaLogicalTime;

/* @ThreadSafe */
public final class HlaLogicalTimeImpl implements HlaLogicalTime {
   public static final HlaLogicalTime INVALID = new HlaLogicalTimeImpl();
   public static final HlaLogicalTime INITIAL = create(0);

   private final boolean _valid;
   private final long _value;
   private final OrderType _receivedOrderType;

   
   private HlaLogicalTimeImpl(long value, OrderType receivedOrderType) {
      _valid = true;
      _value = value;
      _receivedOrderType = receivedOrderType;
   }

   private HlaLogicalTimeImpl() {
      _valid = false;
      _value = -1;
      _receivedOrderType = OrderType.NONE;
   }

   public boolean isValid() {
      return _valid;
   }

   public long getValue() {
      return _value;
   }

   public OrderType getReceivedOrderType() {
      return _receivedOrderType;
   }

   public boolean wasReceivedInTimeStampOrder() {
      return _receivedOrderType == OrderType.TIME_STAMP_ORDER;
   }

   public int compareTo(HlaLogicalTime o) {
      if (!isValid() || !o.isValid()) {
         return Boolean.compare(isValid(), o.isValid());
      }
      int compare = Long.compare(getValue(), o.getValue());
      if (compare != 0) {
         return compare;
      }
      return getReceivedOrderType().compareTo(o.getReceivedOrderType());
   }

   public HlaLogicalTime add(long addend) {
      if (!isValid()) {
         return INVALID;
      }
      return new HlaLogicalTimeImpl(getValue() + addend, OrderType.NONE);
   }

   // Factory for received logical times
   public static HlaLogicalTime createReceived(long value, boolean tso) {
      return new HlaLogicalTimeImpl(value, tso ? OrderType.TIME_STAMP_ORDER : OrderType.RECEIVE_ORDER);
   }

   // Factory for logical times
   public static HlaLogicalTime create(long value) {
      return new HlaLogicalTimeImpl(value, OrderType.NONE);
   }

   @Override
   public String toString() {
      if (!isValid()) {
         return "HlaLogicalTime<INVALID>";
      }
      if (getReceivedOrderType() == OrderType.TIME_STAMP_ORDER) {
         return "HlaLogicalTime<" + getValue() + ", TSO>";
      }
      if (getReceivedOrderType() == OrderType.RECEIVE_ORDER) {
         return "HlaLogicalTime<" + getValue() + ", RO>";
      }
      return "HlaLogicalTime<" + getValue() + ">";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof HlaLogicalTime)) {
         return false;
      }

      HlaLogicalTime that = (HlaLogicalTime)o;

      return isValid() == that.isValid() &&
             getValue() == that.getValue() &&
             getReceivedOrderType() == that.getReceivedOrderType();

   }

   @Override
   public int hashCode() {
      long value = _value;
      return 31 * ((_valid ? 31 : 0) + getReceivedOrderType().ordinal()) + (int)(value ^ (value >>> 32));
   }
}
