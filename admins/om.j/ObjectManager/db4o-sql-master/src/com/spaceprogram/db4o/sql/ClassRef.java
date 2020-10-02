package com.spaceprogram.db4o.sql;

/**
 * User: treeder
 * Date: Jul 26, 2006
 * Time: 6:59:48 PM
 */
public class ClassRef {
    private String className;
    private String alias;

    public ClassRef(String className) {
		this(className, null);
    }

    public ClassRef(String className, String alias) {
		// if .NET query, might be surrouned by quotes, eg: FROM 'Quizlet.Question, Quizlet.Framework'
		this.className = className;
		this.alias = alias;
    }

    public String toString() {
        String ret = className;
        if (alias != null) ret += " " + alias;
        return ret;
    }

    public String getClassName() {
		return className.replaceAll("'", "");
    }

    public String getAlias() {
        return alias;
    }
}
