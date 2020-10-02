package com.spaceprogram.db4o.sql.parser;

import com.spaceprogram.db4o.sql.query.SqlQuery;

import java.util.List;

/**
 * User: treeder
 * Date: Feb 4, 2007
 * Time: 4:32:27 PM
 */
public interface Builder {

	String getKeyword();

	void build(SqlQuery sq, List<String> expr, List<String> quotedStrings) throws SqlParseException;
}

