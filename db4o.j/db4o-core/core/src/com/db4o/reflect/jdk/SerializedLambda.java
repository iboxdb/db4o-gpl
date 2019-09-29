/* GPL */
package com.db4o.reflect.jdk;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class SerializedLambda {

    public static SerializedLambda Instance = new SerializedLambda();

    private Class FClass;

    public Method getImplClass;
    public Method getImplMethodName;

    public Method getInstantiatedMethodType;
    public Method getCapturedArg;
    public Method getCapturedArgCount;

    SerializedLambda() {
        try {
            FClass = Class.forName("java.lang.invoke.SerializedLambda");
            getImplClass = FClass.getMethod("getImplClass");
            getImplMethodName = FClass.getMethod("getImplMethodName");
            getInstantiatedMethodType = FClass.getMethod("getInstantiatedMethodType");

            getCapturedArg = FClass.getMethod("getCapturedArg", Integer.TYPE);
            getCapturedArgCount = FClass.getMethod("getCapturedArgCount");
        } catch (Throwable ex) {

        }
    }

    public Object getMe(Object f) {
        try {
            if (f == null) {
                return null;
            }
            Method method = f.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return method.invoke(f);
        } catch (Throwable ex) {
            return null;
        }
    }

    public Class getMeClass(Object me) {
        try {
            Object o = me;
            if (o != null) {

                String tname = (String) getInstantiatedMethodType.invoke(o);
                tname = tname.substring(2, tname.length() - 3);
                tname = tname.replaceAll("/", ".");
                Class t = Class.forName(tname);
                return t;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList getMeArgs(Object me) {
        try {
            Object o = me;
            if (o != null) {

                ArrayList args = new ArrayList();
                int count = (Integer) getCapturedArgCount.invoke(o);
                for (int i = 0; i < count; i++) {
                    Object v = getCapturedArg.invoke(o, i);
                    args.add(v);
                }
                return args;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
