package uk.ac.brunel.see.datatypes;

/*
 * DO NOT EDIT!
 * 
 * Automatically generated source code by Pitch Developer Studio
 * Licensed to Hridyanshu Aatreya, Pitch UK P, Project Edition
 *
 * Copyright (C) 2006-2024 Pitch Technologies AB. All rights reserved.
 * Use is subject to license terms.
 */


/**
 * Implementation of the <code>ExecutionMode</code> data type from the FOM.
 * <br>Description from the FOM: Defines the mode for the running federation execution.  This enumeration type is used for coordinating transitions between federation execution run states.
 * <p>
 * This class is <code>Immutable</code> (and therefore <code>ThreadSafe</code>)
 * as defined by <i>Java Concurrency in Practice</i>,
 * see <a href="http://jcip.net/annotations/doc/net/jcip/annotations/package-summary.html">jcip.net</a>.
 */
public enum ExecutionMode implements HlaEnumDataType {
    /** <code>EXEC_MODE_UNINITIALIZED</code> (with ordinal 0) */
    EXEC_MODE_UNINITIALIZED("EXEC_MODE_UNINITIALIZED", 0),
    /** <code>EXEC_MODE_INITIALIZING</code> (with ordinal 1) */
    EXEC_MODE_INITIALIZING("EXEC_MODE_INITIALIZING", 1),
    /** <code>EXEC_MODE_RUNNING</code> (with ordinal 2) */
    EXEC_MODE_RUNNING("EXEC_MODE_RUNNING", 2),
    /** <code>EXEC_MODE_FREEZE</code> (with ordinal 3) */
    EXEC_MODE_FREEZE("EXEC_MODE_FREEZE", 3),
    /** <code>EXEC_MODE_SHUTDOWN</code> (with ordinal 4) */
    EXEC_MODE_SHUTDOWN("EXEC_MODE_SHUTDOWN", 4);

   /**
    * The name of the enum.
    */
    public final String name;
   /**
    * The ordinal of the enum.
    */
    public final long ordinal;

    private ExecutionMode(String name, long ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public long getOrdinal() {
        return ordinal;
    }

    public String getName() {
        return name;
    }

   /**
    * Find the enum with the specified ordinal.
    *
    * @param ordinal ordinal of the enum to find
    *
    * @return the enum with the specified <code>ordinal</code>, or <code>null</code> if not found
    */
    public static ExecutionMode find(long ordinal) {
        for (ExecutionMode value : values()) {
            if (value.getOrdinal() == ordinal) {
                return value;
            }
        }
        return null;
    }

   /**
    * Find the enum with the specified name.
    *
    * @param name name of the enum to find
    *
    * @return the enum with the specified <code>name</code>, or <code>null</code> if not found
    */
    public static ExecutionMode find(String name) {
        for (ExecutionMode value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
