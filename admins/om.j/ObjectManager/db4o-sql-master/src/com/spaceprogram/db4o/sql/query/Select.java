package com.spaceprogram.db4o.sql.query;

import java.util.List;
import java.util.ArrayList;

/**
 * User: treeder
 * Date: Jul 26, 2006
 * Time: 6:49:46 PM
 */
public class Select {


	private List<String> fields = new ArrayList<String>();

	public void addField(String field) {
		fields.add(field);
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public String toString() {
        StringBuffer ret = new StringBuffer("SELECT ");
        for (int i = 0; i < fields.size(); i++) {
			String field = fields.get(i);
			if (i > 0) {
				ret.append(", ");
			}
			ret.append(field);
		}
        return ret.toString();
    }
}
