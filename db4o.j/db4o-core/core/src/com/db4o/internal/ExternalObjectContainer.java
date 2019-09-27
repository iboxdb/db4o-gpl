/* Copyright (C) 2007  Versant Inc.  http://www.db4o.com */
package com.db4o.internal;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.activation.*;
import com.db4o.internal.events.*;
import com.db4o.io.*;
import com.db4o.nativequery.optimization.Db4oOnTheFlyEnhancer;
import com.db4o.query.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @exclude
 */
public abstract class ExternalObjectContainer extends ObjectContainerBase {

    public ExternalObjectContainer(Configuration config) {
        super(config);
    }

    public final void activate(Object obj) {
        activate(null, obj);
    }

    public final void activate(Object obj, int depth) throws DatabaseClosedException {
        activate(null, obj, activationDepthProvider().activationDepth(depth, ActivationMode.ACTIVATE));
    }

    public final void deactivate(Object obj) {
        deactivate(null, obj);
    }

    public final void bind(Object obj, long id) throws ArgumentNullException, IllegalArgumentException {
        bind(null, obj, id);
    }

    public final void commit() throws DatabaseReadOnlyException, DatabaseClosedException {
        commit(null);
    }

    public final void deactivate(Object obj, int depth) throws DatabaseClosedException {
        deactivate(null, obj, depth);
    }

    public final void delete(Object a_object) {
        delete(null, a_object);
    }

    public Object descend(Object obj, String[] path) {
        return descend(null, obj, path);
    }

    public ExtObjectContainer ext() {
        return this;
    }

    public final ObjectSet queryByExample(Object template) throws DatabaseClosedException {
        return queryByExample(null, template);
    }

    public final Object getByID(long id) throws DatabaseClosedException, InvalidIDException {
        return getByID((Transaction) null, id);
    }

    public final Object getByUUID(Db4oUUID uuid) {
        return getByUUID((Transaction) null, uuid);
    }

    @Override
    public final <T> T getByID(Class<T> aclass, long ID) {
        Object o = getByID(ID);
        activate(o);
        return (T) o;
    }

    @Override
    public final <T> T getByUUID(Class<T> aclass, Db4oUUID uuid) {
        Object o = getByUUID(uuid);
        activate(o);
        return (T) o;
    }

    @Override
    public <T> T getByUUID(Class<T> aclass, String uuid) {
        return getByUUID(aclass, new Db4oUUID(uuid));
    }

    public final long getID(Object obj) {
        return getID(null, obj);
    }

    public final ObjectInfo getObjectInfo(Object obj) {
        return getObjectInfo(null, obj);
    }

    public boolean isActive(Object obj) {
        return isActive(null, obj);
    }

    public boolean isCached(long id) {
        return isCached(null, id);
    }

    public boolean isStored(Object obj) {
        return isStored(null, obj);
    }

    public final Object peekPersisted(Object obj, int depth, boolean committed) throws DatabaseClosedException {
        return peekPersisted(null, obj, activationDepthProvider().activationDepth(depth, ActivationMode.PEEK), committed);
    }

    public final void purge(Object obj) {
        purge(null, obj);
    }

    public Query query() {
        return query((Transaction) null);
    }

    public final ObjectSet query(Class clazz) {
        return queryByExample(clazz);
    }

    @Override
    public <TargetType> ObjectSet<TargetType> query(IPredicate<TargetType> predicate) {
        return query(predicate, (QueryComparator) null);
    }

    @Override
    public <TargetType> ObjectSet<TargetType> query(IPredicate<TargetType> predicate, QueryComparator<TargetType> comparator) {
        Class t = Predicate.getMatchType(predicate.getClass());
        ArrayList args = null;
        if (t == null) {
            Object o = Db4oOnTheFlyEnhancer.serializedLambda.getMe(predicate);
            if (o != null) {
                try {
                    String tname = (String) Db4oOnTheFlyEnhancer.serializedLambda.getInstantiatedMethodType.invoke(o);
                    tname = tname.substring(2, tname.length() - 3);
                    tname = tname.replaceAll("/", ".");
                    t = Class.forName(tname);

                    args = new ArrayList();
                    int count = (Integer) Db4oOnTheFlyEnhancer.serializedLambda.getCapturedArgCount.invoke(o);
                    for (int i = 0; i < count; i++) {
                        Object v = Db4oOnTheFlyEnhancer.serializedLambda.getCapturedArg.invoke(o, i);
                        args.add(v);

                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }

        final IPredicate<TargetType> f_predicate = predicate;
        Predicate<TargetType> p = new Predicate<TargetType>(t) {
            @Override
            public boolean match(TargetType candidate) {
                return f_predicate.match(candidate);
            }
        };
        if (t != null) {
            p.ExtentInterface = predicate;
            p.ExtentArgs = args;
        }

        return query(p, comparator);
    }

    public final ObjectSet query(Predicate predicate) {
        return query(predicate, (QueryComparator) null);
    }

    public final ObjectSet query(Predicate predicate, QueryComparator comparator) {
        return query(null, predicate, comparator);
    }

    public final void refresh(Object obj, int depth) {
        refresh(null, obj, depth);
    }

    public final void rollback() {
        rollback(null);
    }

    public final void store(Object obj)
            throws DatabaseClosedException, DatabaseReadOnlyException {
        store(obj, Const4.UNSPECIFIED);
    }

    public final void store(Object obj, int depth)
            throws DatabaseClosedException, DatabaseReadOnlyException {
        store(null, obj, depth == Const4.UNSPECIFIED ? (UpdateDepth) updateDepthProvider().unspecified(NullModifiedObjectQuery.INSTANCE) : (UpdateDepth) updateDepthProvider().forDepth(depth));
    }

    public final StoredClass storedClass(Object clazz) {
        return storedClass(null, clazz);
    }

    public StoredClass[] storedClasses() {
        return storedClasses(null);
    }

    public abstract void backup(Storage targetStorage, String path) throws Db4oIOException, DatabaseClosedException, NotSupportedException;

    public abstract void backupSync(Storage targetStorage, String path) throws Db4oIOException, DatabaseClosedException, NotSupportedException;

    public abstract Db4oDatabase identity();

    @Override
    public boolean inCallback() {
        return EventRegistryImpl.inCallback(this);
    }

}
