package com.spaceprogram.db4o.sql.parser;

import com.spaceprogram.db4o.sql.query.SqlQuery;
import com.spaceprogram.db4o.sql.query.Select;

import java.util.List;

/**
 * User: treeder
 * Date: Feb 4, 2007
 * Time: 4:31:57 PM
 */
public class SelectBuilder implements Builder {

	final String keyword = "SELECT";

	public String getKeyword() {
		return keyword;
	}

	public void build(SqlQuery sq, List<String> expr, List<String> quotedStrings) {
		Select select = new Select();
		// select is just a list of fields separated by commas, not necessarily by spaces, so lets split further by commas
		List<String> values = SqlParser.separateCommas(expr, false);

		select.setFields(values);
		sq.setSelect(select);
	}

}
