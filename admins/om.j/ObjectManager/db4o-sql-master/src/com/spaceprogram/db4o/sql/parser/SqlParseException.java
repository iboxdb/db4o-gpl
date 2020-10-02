package com.spaceprogram.db4o.sql.parser;

import java.text.ParseException;

/**
 * User: treeder
 * Date: Aug 1, 2006
 * Time: 3:22:08 PM
 */
public class SqlParseException extends ParseException {
    public SqlParseException(String msg) {
        super(msg, 0);
    }
/*
    public SqlParseException() {
        super();
    }*/
}
