package com.spaceprogram.db4o.sql;

import com.db4o.reflect.ReflectField;

/**
 * User: treeder
 * Date: Aug 20, 2006
 * Time: 3:42:32 PM
 */
public interface ObjectSetMetaData {
    int getColumnCount();
    String getColumnName(int column);

	ReflectField getColumnReflectField(int fieldIndex);

	ReflectField getColumnReflectField(String fieldName);
}
