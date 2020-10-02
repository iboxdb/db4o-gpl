package com.spaceprogram.db4o.sql;

import java.sql.SQLException;

/**
 * User: treeder
 * Date: Aug 3, 2006
 * Time: 4:29:59 PM
 */
public class Sql4oRuntimeException extends RuntimeException{
    public Sql4oRuntimeException(Exception e) {
        super(e);
    }

    public Sql4oRuntimeException(String msg) {
        super(msg);
    }
}
