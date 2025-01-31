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

import java.lang.reflect.Array;

/* @NotThreadSafe */
public class Value<T> {
    private T _value;
    private boolean _hasValue;


    protected Value() {
        _value = null;
        _hasValue = false;
    }

    protected Value(T value) {
        _value = value;
        _hasValue = true;
    }

    protected Value(Value<T> value) {
        _value = value.getValueForUser();
        _hasValue = value.hasValue();
    }

    protected void setValue(T value) {
        _value = value;
        _hasValue = true;
    }

    public T getValue() {
        return _value;
    }

    public boolean hasValue() {
        return _hasValue;
    }

    // if T is a mutable type (eg an Array), perform copy
    @SuppressWarnings ({ "unchecked" })
    public void setValueFromUser(T value) {
        if (value != null && value.getClass().isArray()) {
            int length = Array.getLength(value);
            _value = (T)Array.newInstance(value.getClass().getComponentType(), length);
            System.arraycopy(value, 0, _value, 0, length);
        } else {
            _value = value;
        }
        _hasValue = true;
    }

    // if T is a mutable type (eg an Array), perform copy
    @SuppressWarnings ({ "unchecked" })
    public T getValueForUser() {
        if (_value != null && _value.getClass().isArray()) {
            int length = Array.getLength(_value);
            T newArray = (T)Array.newInstance(_value.getClass().getComponentType(), length);
            System.arraycopy(_value, 0, newArray, 0, length);
            return newArray;
        }

        return _value;
    }
}
