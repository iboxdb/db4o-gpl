package com.spaceprogram.db4o.sql;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.ExtObjectSet;
import com.db4o.reflect.ReflectField;
import com.db4o.reflect.generic.GenericReflector;
import com.spaceprogram.db4o.sql.query.SqlQuery;
import com.spaceprogram.db4o.sql.metadata.ObjectSetMetaDataImpl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * User: treeder Date: Aug 3, 2006 Time: 3:58:32 PM
 */
public class ObjectSetWrapper implements ObjectSet<Result> {

    private ObjectSet<Result> results;
    private List<String> selectFields;
    private int index;
    private Object lastResult;
    private Object nextResult;
    private ObjectSetMetaData objectSetMetaData;
    private ObjectContainer oc;
    private SqlQuery sqlQuery;

    public ObjectSetWrapper(ObjectContainer oc, SqlQuery q, ObjectSet results) {
        this.oc = oc;
        sqlQuery = q;
        setObjectSet(results);
        if (q.getSelect() != null) {
            setSelectFields(q.getSelect().getFields());
        }
        objectSetMetaData = new ObjectSetMetaDataImpl(results, this, oc, sqlQuery);
    }

    public ObjectSetMetaData getMetaData() {
        /* if (objectSetMetaData == null) {
            objectSetMetaData = new ObjectSetMetaDataImpl(results, this, oc, sqlQuery);
        }*/
        return objectSetMetaData;
    }

    /**
     * This will check the select fields if exists, otherwise it will use the
     * fields on the object todo: could cache the fields for an object here for
     * super fast return
     *
     * @param ob
     * @param columnIndex
     * @return
     */
    public ReflectField getFieldForColumn(Object ob, int columnIndex) throws Sql4oException {
        return objectSetMetaData.getColumnReflectField(columnIndex);
    }

    public ReflectField getFieldForColumn(Object ob, String fieldName) throws Sql4oException {
        if (hasSelectFields() && !selectFields.contains(fieldName)) {
            throw new Sql4oRuntimeException("Field not found: " + fieldName);
        }
        //ReflectClass reflectClass = oc.ext().reflector().forObject(ob);
        ReflectField field = objectSetMetaData.getColumnReflectField(fieldName);
        if (field == null) {
            throw new Sql4oException("Field " + fieldName + " does not exist.");
        }
        //return getField(reflectClass, fieldName);
        return field;
    }

    public boolean hasSelectFields() {
        return (selectFields != null && selectFields.size() > 0 && !selectFields.get(0).equals("*"));
    }

    public ExtObjectSet ext() {
        return results.ext();
    }

    public boolean hasNext() {
        if (nextResult != null) {
            return true;
        }
        return results.hasNext();
    }

    public Result next() {
        Object next;
        if (nextResult != null) {
            next = nextResult;
            nextResult = null;
        } else {
            next = results.next();
        }
        lastResult = next;
        index++;
        return new ObjectWrapper(this, next);
    }

    public Result get(int index) {
        Object ret = results.get(index);
        lastResult = ret;
        // now replace with array structure based on fields chosen
        //return arrayStruct(ret);
        return new ObjectWrapper(this, ret);

    }

    public void reset() {
        results.reset();
    }

    public int size() {
        return results.size();
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public boolean contains(Object o) {
        return results.contains(o);
    }

    public Iterator<Result> iterator() {
        return results.iterator();
    }

    public Object[] toArray() {
        return results.toArray();
    }

    public Object[] toArray(Object[] a) {
        return results.toArray(a);
    }

    public boolean add(Result o) {
        return results.add(o);
    }

    public boolean remove(Object o) {
        return results.remove(o);
    }

    public boolean containsAll(Collection c) {
        return results.containsAll(c);
    }

    public boolean addAll(Collection c) {
        return results.addAll(c);
    }

    public boolean addAll(int index, Collection c) {
        return results.addAll(index, c);
    }

    public boolean removeAll(Collection c) {
        return results.removeAll(c);
    }

    public boolean retainAll(Collection c) {
        return results.retainAll(c);
    }

    public void clear() {
        results.clear();
    }

    public boolean equals(Object o) {
        return results.equals(o);
    }

    public int hashCode() {
        return results.hashCode();
    }

    public int indexOf(Object o) {
        return results.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return results.lastIndexOf(o);
    }

    public ListIterator listIterator() {
        return results.listIterator();
    }

    public ListIterator listIterator(int index) {
        return results.listIterator(index);
    }

    public List subList(int fromIndex, int toIndex) {
        return results.subList(fromIndex, toIndex);
    }

    public Result set(int index, Result element) {
        return results.set(index, element);
    }

    public void add(int index, Result element) {
        results.add(index, element);
    }

    public Result remove(int index) {
        return results.remove(index);
    }

    public void remove(Result element) {
        results.remove(element);
    }

    public void setObjectSet(ObjectSet objectSet) {
        this.results = objectSet;
    }

    public void setSelectFields(List<String> selectFields) {
        this.selectFields = selectFields;
    }

    public Object getLastResult() {
        return lastResult;
    }

    /**
     * This should only be used in very rare circumstances
     *
     * @param nextResult
     */
    public void setNextResult(Object nextResult) {
        this.nextResult = nextResult;
    }

    public List<String> getSelectFields() {
        return selectFields;
    }

    public GenericReflector getReflector() {
        return oc.ext().reflector();
    }
}
