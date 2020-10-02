package com.spaceprogram.db4o.sql;

import com.db4o.reflect.ReflectField;

import java.lang.reflect.Field;

/**
 * User: treeder
 * Date: Aug 14, 2006
 * Time: 2:51:16 PM
 */
public class ObjectWrapper implements Result {
    private Object ob;
    // todo: shouldn't hold onto this ObjectSetWrapper, might create memory leaks
    private ObjectSetWrapper objectSetWrapper;

    public ObjectWrapper(ObjectSetWrapper objectSetWrapper, Object ob) {
        this.objectSetWrapper = objectSetWrapper;
        this.ob = ob;
    }

    public Object getObject(int fieldIndex) throws Sql4oException {
        ReflectField f = objectSetWrapper.getFieldForColumn(ob, fieldIndex);
        return getFieldValue(f, ob);
    }

    public Object getObject(String fieldName) throws Sql4oException {
        ReflectField f = objectSetWrapper.getFieldForColumn(ob, fieldName);
		return getFieldValue(f, ob);
    }

    private Object getFieldValue(ReflectField f, Object ob) {
        return f.get(ob);
    }


    public Object getBaseObject(int objectIndex) {
        return ob;
    }

}
