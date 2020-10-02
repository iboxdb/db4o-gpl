package com.spaceprogram.db4o.sql.metadata;

import com.db4o.ObjectSet;
import com.db4o.ObjectContainer;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.ReflectField;
import com.db4o.reflect.generic.GenericReflector;
import com.spaceprogram.db4o.sql.query.SqlQuery;
import com.spaceprogram.db4o.sql.query.From;
import com.spaceprogram.db4o.sql.*;
import com.spaceprogram.db4o.sql.util.ReflectHelper;

import java.util.List;
import java.util.ArrayList;

/**
 * User: treeder
 * Date: Aug 20, 2006
 * Time: 3:43:49 PM
 */
public class ObjectSetMetaDataImpl implements ObjectSetMetaData {
	private List<String> fields = new ArrayList<String>();
	private List<ReflectField> reflectFields = new ArrayList<ReflectField>();


	public ObjectSetMetaDataImpl(ObjectSet results, ObjectSetWrapper objectSetWrapper, ObjectContainer oc, SqlQuery sqlQuery) {
		init(results, objectSetWrapper, oc, sqlQuery);
	}

	private void init(ObjectSet results, ObjectSetWrapper objectSetWrapper, ObjectContainer oc, SqlQuery sqlQuery) {

		From from = sqlQuery.getFrom();
		List<ClassRef> classRefs = from.getClassRefs();
		for (int i = 0; i < classRefs.size(); i++) {
			ClassRef classRef = classRefs.get(i);
			String cname = classRef.getClassName();
			GenericReflector reflector = oc.ext().reflector();
			ReflectClass reflectClass = reflector.forName(cname);
			//System.out.println("reflectClass for " + cname + " - " + reflectClass);
			ReflectField reflectFields[] = getDeclaredFields(reflectClass);
			for (int j = 0; j < reflectFields.length; j++) {
				ReflectField reflectField = reflectFields[j];
				if (objectSetWrapper.hasSelectFields()) {
					if (objectSetWrapper.getSelectFields().contains(reflectField.getName())) {
						//			System.out.println("added field: " + reflectField.getName());
						this.reflectFields.add(reflectField);
						fields.add(reflectField.getName());
					}
				} else {
					this.reflectFields.add(reflectField);
					fields.add(reflectField.getName());
				}
			}
		}

	}

	private ReflectField[] getDeclaredFields(ReflectClass reflectClass) {
		return ReflectHelper.getDeclaredFieldsInHeirarchy(reflectClass);
	}


	public int getColumnCount() {
		return fields.size();
	}

	public String getColumnName(int column) {
		if (column >= 0 && column < fields.size()) {
			return fields.get(column);
		}
		return null;
	}

	public ReflectField getColumnReflectField(int fieldIndex) {
		if (reflectFields.size() <= fieldIndex || fieldIndex < 0) {
			// then out of bounds, so throw
			throw new Sql4oRuntimeException("Field index out of bounds. received: " + fieldIndex + " max: " + reflectFields.size());
		} else {
			ReflectField ret = reflectFields.get(fieldIndex);
			// ret.setAccessible();
			return ret;
		}
	}

	public ReflectField getColumnReflectField(String fieldName) {
		for (int i = 0; i < reflectFields.size(); i++) {
			ReflectField reflectField = reflectFields.get(i);
			if (reflectField.getName().equals(fieldName)) {
				//reflectField.setAccessible();
				return reflectField;
			}
		}
		return null;
	}


}
