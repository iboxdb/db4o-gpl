package com.spaceprogram.db4o.util;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import java.util.List;

/**
 * User: treeder
 * Date: Jul 24, 2006
 * Time: 12:59:51 PM
 */
public class ObjectContainerUtils {
    public static int clear(ObjectContainer oc) {
        Query q = oc.query();
        ObjectSet results = q.execute();
        int counter = 0;
        while (results.hasNext()) {
            Object ob = results.next();
            //System.out.println(ob);
            oc.delete(ob);
            counter++;
        }
        oc.commit();
        return counter;
    }

    public static int clear(ObjectContainer oc, Class aClass) {
        List results = oc.query(aClass);
        int counter = 0;
        for (Object o : results) {
            oc.delete(o);
            counter++;
        }
        return counter;
    }

    public static int dump(ObjectContainer oc) {
        System.out.println("DUMPING: " + oc.ext().identity());
        Query q = oc.query();
        List results = q.execute();
        int counter = 0;
        for (Object o : results) {
            System.out.println("object: " + o);
            counter++;
        }
        System.out.println("END DUMP: " + oc.ext().identity());
        return counter;
    }
    public static int dump(ObjectContainer oc, Class aClass) {
        System.out.println("DUMPING: " + oc.ext().identity());
        List results = oc.query(aClass);
        int counter = 0;
        for (Object o : results) {
            System.out.println("object: " + o);
            counter++;
        }
        System.out.println("END DUMP: " + oc.ext().identity());
        return counter;
    }
}
