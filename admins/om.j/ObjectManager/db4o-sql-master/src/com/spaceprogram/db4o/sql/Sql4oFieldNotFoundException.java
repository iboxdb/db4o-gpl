package com.spaceprogram.db4o.sql;

/**
 * User: treeder
 * Date: Aug 15, 2006
 * Time: 11:05:44 PM
 */
public class Sql4oFieldNotFoundException extends Sql4oRuntimeException{
    public Sql4oFieldNotFoundException(Exception e) {
        super(e);
    }

    public Sql4oFieldNotFoundException(String msg) {
        super(msg);
    }
}
