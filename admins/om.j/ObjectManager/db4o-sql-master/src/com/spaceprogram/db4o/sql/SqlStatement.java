package com.spaceprogram.db4o.sql;

/**
 * User: treeder
 * Date: Jul 26, 2006
 * Time: 6:08:19 PM
 */
public class SqlStatement {
    /**
     * <p>
     * This method is preferred over toString()
     * </p>
     * <p>
     * Subclasses can override this toString doesn't show the full statement
     * </p>
     * @return the full sql string,
     */
    public String toSQLString() {
        return toString();        
    }
}
