package com.spaceprogram.db4o.sql.query;

import java.util.List;
import java.util.ArrayList;

/**
 * User: treeder
 * Date: Aug 1, 2006
 * Time: 11:53:01 AM
 */
public class WhereExpression implements Cloneable{

    public static final String OP_EQUALS = "=";
    public static final String OP_GREATER = ">";
    public static final String OP_GREATER_OR_EQUAL = ">=";
    public static final String OP_LESS = "<";
    public static final String OP_LESS_OR_EQUAL = "<=";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String OP_NOT_EQUAL = "<>";
    public static final String OP_NOT_EQUAL_2 = "!="; // why not

    
    private List<WhereExpression> expressions = new ArrayList<WhereExpression>();
    private String type = AND;
    private String field;
    private String operator;
    private String value;
    private boolean root;


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public WhereExpression(String type) {
        this.type = type;
        if(type == null){
            this.root = true;
        }
    }

    public WhereExpression() {

    }


    public String toString() {
        StringBuffer buff = new StringBuffer();
        //if(type != null) buff.append(type).append(" ");
        if(expressions.size() > 0){
            if(!root)buff.append(" (");
            for (int i = 0; i < expressions.size(); i++) {
                WhereExpression whereExpression = expressions.get(i);
                if(i > 0){
                    buff.append(" ").append(whereExpression.getType());
                }
                buff.append(whereExpression.toString());

            }
            if(!root)buff.append(" )");
        }
        else buff.append(" ").append(field).append(" ").append(operator).append( " ").append(value );
        return buff.toString();
    }

    public void add(WhereExpression sub) {
        expressions.add(sub);
    }


    public void setField(String field) {
        this.field = field;
    }


    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<WhereExpression> getExpressions() {
        return expressions;
    }

    public String getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public boolean isRoot() {
        return root;
    }
}
