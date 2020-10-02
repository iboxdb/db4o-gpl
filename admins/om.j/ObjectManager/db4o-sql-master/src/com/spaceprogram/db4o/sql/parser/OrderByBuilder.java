package com.spaceprogram.db4o.sql.parser;

import com.spaceprogram.db4o.sql.query.SqlQuery;
import com.spaceprogram.db4o.sql.query.OrderBy;

import java.util.List;

/**
 * User: treeder
 * Date: Feb 4, 2007
 * Time: 4:31:29 PM
 */
public class OrderByBuilder implements Builder {

	static final String keyword = "ORDER";


	public String getKeyword() {
		return keyword;
	}

	public void build(SqlQuery sq, List<String> expr, List<String> quotedStrings) throws SqlParseException {
		OrderBy orderBy = new OrderBy();
		// select is just a list of fields separated by commas, not necessarily by spaces, so lets split further by commas
		List<String> values = SqlParser.separateCommas(expr.subList(1, expr.size()), false);

		int j = 0;
		OrderBy.Field field = new OrderBy.Field();
		// now find ascending or descending
		for (int i = 0; i < values.size(); i++) {
			String s = values.get(i);
			if (s.equals(",")) {
				j = 0;
				orderBy.addField(field);
				field = new OrderBy.Field();
			}
			//System.out.println("orderby val: " + s);
			if (j > 1) {
				throw new SqlParseException("Order by clause invalid, too many parts: " + s);
			}
			if (j == 0) {
				field.setName(s);
			} else if (j == 1) {
				if (s.equalsIgnoreCase(OrderBy.ASC)) {
					field.orderAscending();
				} else if (s.equalsIgnoreCase(OrderBy.DESC)) {
					field.orderDescending();
				}
			}
			j++;
		}
		if(j == 0){
			throw new SqlParseException("Invalid ORDER BY clause.");
		}
		orderBy.addField(field);
		sq.setOrderBy(orderBy);
	}
}
