/* Copyright (C) 2004 - 2006  Versant Inc.  http://www.db4o.com */
package com.db4o.diagnostic;

import com.db4o.query.*;

/**
 * Diagnostic, if Native Query can not be run optimized.
 */
public class NativeQueryNotOptimized extends DiagnosticBase {

    private final Predicate _predicate;
    private final Throwable _details;

    public NativeQueryNotOptimized(Predicate predicate, Throwable details) {
        _predicate = predicate;
        _details = details;
    }

    public Object reason() {
        if (_details == null) {
            return _predicate;
        }
        return "\n" + _details.getMessage() + ".  "
                + (_predicate.ExtentInterface != null ? _predicate.ExtentInterface : _predicate);
    }

    public String problem() {
        return "Native Query Predicate could not be run optimized";
    }

    public String solution() {
        return "This Native Query was run by instantiating all objects of the candidate class. "
                //+ "Consider simplifying the expression in the Native Query method. "
                + " 'FieldName' should on the Left. except to disable Index for test";
    }

}
