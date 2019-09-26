//GPL
package com.db4o.nativequery.expr.cmp.operand;

import EDU.purdue.cs.bloat.tree.LocalExpr;
import com.db4o.instrumentation.api.*;

public class LocalValue extends ComparisonOperandDescendant {

    private final LocalExpr _index;

    public LocalValue(ComparisonOperandRoot root, LocalExpr index) {
        super(root);
        _index = index;
    }

    public LocalExpr index() {
        return _index;
    }

    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        LocalValue casted = (LocalValue) other;
        return _index == casted._index;
    }

    public int hashCode() {
        return super.hashCode() * 29 + Integer.hashCode(_index.index());
    }

    public String toString() {
        return super.toString() + ".[" + _index.index() + "," + _index.type().descriptor() + "]";
    }

    public void accept(ComparisonOperandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * @sharpen.property
     */
    @Override
    public TypeRef type() {
        return new TypeRef() {
            @Override
            public boolean isPrimitive() {
                return index().type().isPrimitive();
            }

            @Override
            public TypeRef elementType() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String name() {
                return index().type().className();
            }

        };
    }

}
