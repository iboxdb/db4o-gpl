/* Copyright (C) 2004 - 2006  Versant Inc.  http://www.db4o.com */

/**
 *   Updated GPL Version 
 */
package com.db4o.nativequery.optimization;

import java.lang.reflect.*;

import com.db4o.instrumentation.api.*;
import com.db4o.internal.Reflection4;
import com.db4o.nativequery.expr.cmp.*;
import com.db4o.nativequery.expr.cmp.operand.*;
import com.db4o.query.Predicate;

final class ComparisonQueryGeneratingVisitor implements ComparisonOperandVisitor {

    private Predicate _predicate;

    private Object _value = null;

    private final NativeClassFactory _classSource;

    private final ReferenceResolver _resolver;

    public Object value() {
        return _value;
    }

    public void visit(ConstValue operand) {
        _value = operand.value();
    }

    public void visit(FieldValue operand) {
        operand.parent().accept(this);
        _value = argLocal(operand);
    }

    private Object argLocal(FieldValue operand) {
        String name = operand.fieldName();
        for (int i = 0; i < 2; i++) {
            Class c;
            Object o;
            if (i == 0) {
                o = ((Predicate) _value).ExtentInterface;
                c = o != null ? o.getClass() : null;
            } else {
                o = _value;
                c = ((operand.parent() instanceof StaticFieldRoot) ? (Class) o : o.getClass());
            }
            if (c != null) {

                try {
                    Field field = Reflection4.getField(c, name);
                    if (field != null) {
                        field.setAccessible(true);
                        return field.get(o);
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void visit(LocalValue operand) {

        if (_predicate.ExtentArgs != null) {
            int si = operand.index().shortIndex();
            if (si == _predicate.ExtentArgs.size()) {
                _value = _predicate;
            } else {
                _value = _predicate.ExtentArgs.get(si);
            }
        } else {
            _value = _predicate;
        }
    }

    Object add(Object a, Object b) {
        if (a instanceof Double || b instanceof Double) {
            return new Double(((Double) a).doubleValue() + ((Double) b).doubleValue());
        }
        if (a instanceof Float || b instanceof Float) {
            return new Float(((Float) a).floatValue() + ((Float) b).floatValue());
        }
        if (a instanceof Long || b instanceof Long) {
            return new Long(((Long) a).longValue() + ((Long) b).longValue());
        }
        return new Integer(((Integer) a).intValue() + ((Integer) b).intValue());
    }

    Object subtract(Object a, Object b) {
        if (a instanceof Double || b instanceof Double) {
            return new Double(((Double) a).doubleValue() - ((Double) b).doubleValue());
        }
        if (a instanceof Float || b instanceof Float) {
            return new Float(((Float) a).floatValue() - ((Float) b).floatValue());
        }
        if (a instanceof Long || b instanceof Long) {
            return new Long(((Long) a).longValue() - ((Long) b).longValue());
        }
        return new Integer(((Integer) a).intValue() - ((Integer) b).intValue());
    }

    Object multiply(Object a, Object b) {
        if (a instanceof Double || b instanceof Double) {
            return new Double(((Double) a).doubleValue() * ((Double) b).doubleValue());
        }
        if (a instanceof Float || b instanceof Float) {
            return new Float(((Float) a).floatValue() * ((Float) b).floatValue());
        }
        if (a instanceof Long || b instanceof Long) {
            return new Long(((Long) a).longValue() * ((Long) b).longValue());
        }
        return new Integer(((Integer) a).intValue() * ((Integer) b).intValue());
    }

    Object divide(Object a, Object b) {
        if (a instanceof Double || b instanceof Double) {
            return new Double(((Double) a).doubleValue() / ((Double) b).doubleValue());
        }
        if (a instanceof Float || b instanceof Float) {
            return new Float(((Float) a).floatValue() / ((Float) b).floatValue());
        }
        if (a instanceof Long || b instanceof Long) {
            return new Long(((Long) a).longValue() / ((Long) b).longValue());
        }
        return new Integer(((Integer) a).intValue() / ((Integer) b).intValue());
    }

    Object modulo(Object a, Object b) {
        if (a instanceof Double || b instanceof Double) {
            return new Double(((Double) a).doubleValue() % ((Double) b).doubleValue());
        }
        if (a instanceof Float || b instanceof Float) {
            return new Float(((Float) a).floatValue() % ((Float) b).floatValue());
        }
        if (a instanceof Long || b instanceof Long) {
            return new Long(((Long) a).longValue() % ((Long) b).longValue());
        }
        return new Integer(((Integer) a).intValue() % ((Integer) b).intValue());
    }

    public void visit(ArithmeticExpression operand) {
        operand.left().accept(this);
        Object left = _value;
        operand.right().accept(this);
        Object right = _value;
        switch (operand.op().id()) {
            case ArithmeticOperator.ADD_ID:
                _value = add(left, right);
                break;
            case ArithmeticOperator.SUBTRACT_ID:
                _value = subtract(left, right);
                break;
            case ArithmeticOperator.MULTIPLY_ID:
                _value = multiply(left, right);
                break;
            case ArithmeticOperator.DIVIDE_ID:
                _value = divide(left, right);
                break;
            case ArithmeticOperator.MODULO_ID:
                _value = modulo(left, right);
                break;
        }
    }

    @Override
    public void visit(CandidateFieldRoot root) {
    }

    @Override
    public void visit(PredicateFieldRoot root) {
    }

    @Override
    public void visit(StaticFieldRoot root) {
        try {
            _value = _classSource.forName(root.type().name());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void visit(ArrayAccessValue operand) {
        operand.parent().accept(this);
        Object parent = _value;
        operand.index().accept(this);
        Integer index = (Integer) _value;
        _value = Array.get(parent, index.intValue());
    }

    public void visit(MethodCallValue operand) {
        operand.parent().accept(this);
        Object receiver = _value;
        Method method = _resolver.resolve(operand.method());
        try {
            method.setAccessible(true);
            _value = method.invoke(isStatic(method) ? null : receiver, args(operand));
        } catch (Exception exc) {
            exc.printStackTrace();
            _value = null;
        }
    }

    private Object[] args(MethodCallValue operand) {
        final ComparisonOperand[] args = operand.args();
        Object[] params = new Object[args.length];
        for (int paramIdx = 0; paramIdx < args.length; paramIdx++) {
            args[paramIdx].accept(this);
            params[paramIdx] = _value;
        }
        return params;
    }

    private boolean isStatic(Method method) {
        return NativeQueriesPlatform.isStatic(method);
    }

    public ComparisonQueryGeneratingVisitor(Predicate predicate, NativeClassFactory classSource, ReferenceResolver resolver) {
        super();
        _predicate = predicate;
        _classSource = classSource;
        _resolver = resolver;
    }

}
