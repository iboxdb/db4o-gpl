package com.spaceprogram.db4o.sql.query;

import java.util.List;
import java.util.ArrayList;

/**
 * User: treeder
 * Date: Feb 4, 2007
 * Time: 4:51:24 PM
 */
public class OrderBy {

	public static final String ASC = "ASC";
	public static final String DESC = "DESC";

	List<Field> fields = new ArrayList<Field>();

	public void addField(Field field) {
		System.out.println("added field");
		fields.add(field);
	}

	public String toString() {
		StringBuffer ret = new StringBuffer("ORDER BY ");
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			if (i > 0) {
				ret.append(", ");
			}
			ret.append(field);
		}
		return ret.toString();
	}

	public List<Field> getFields() {
		return fields;
	}

	public static class Field {
		private String name;
		private boolean ascending = true;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void orderAscending() {
			ascending = true;
		}

		public void orderDescending() {
			ascending = false;
		}

		public boolean isAscending() {
			return ascending;
		}

		public String toString() {
			String ret = name;
			if(ascending){
				ret += " " + ASC;
			} else {
				ret += " " + DESC;
			}
			return ret;
		}
	}
}
