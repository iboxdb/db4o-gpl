package com.spaceprogram.db4o.sql;

/**
 * User: treeder
 * Date: Aug 14, 2006
 * Time: 2:53:24 PM
 */
public interface Result {
    
    /**
     * The getObject(String) method is recommended over this one in case the field order changes.
     *
     * @param fieldIndex the field index within the object or the select expression
     * @return the field value for the field in the object at this index.
     */
    Object getObject(int fieldIndex) throws Sql4oException;

    /**
     *
     * @param fieldName the name of the field in the object
     * @return the field value for the object with specified field name
     */
    Object getObject(String fieldName) throws Sql4oException;

    /**
     *
     * @param objectIndex this is here to support joins, you will generally just use zero
     * @return the underlying object the the field values are obtained from
     */
    Object getBaseObject(int objectIndex);
}
