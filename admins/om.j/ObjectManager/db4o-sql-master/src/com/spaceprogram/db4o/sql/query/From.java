package com.spaceprogram.db4o.sql.query;

import com.spaceprogram.db4o.sql.ClassRef;

import java.util.List;
import java.util.ArrayList;

/**
 * User: treeder
 * Date: Jul 26, 2006
 * Time: 6:51:48 PM
 */
public class From {
    private List<ClassRef> classRefs = new ArrayList<ClassRef>();

    public void addClass(String className) {
        classRefs.add(new ClassRef(className));
    }

    public void addClass(String className, String alias) {
        classRefs.add(new ClassRef(className, alias));
    }

    public String toString() {
        StringBuffer ret = new StringBuffer("FROM ");
        for (int i = 0; i < classRefs.size(); i++) {
            ClassRef field = classRefs.get(i);
            if(i > 0){
                ret.append(", ");
            }
            ret.append(field);
        }
        return ret.toString();
    }

    public List<ClassRef> getClassRefs() {
        return classRefs;
    }
}
