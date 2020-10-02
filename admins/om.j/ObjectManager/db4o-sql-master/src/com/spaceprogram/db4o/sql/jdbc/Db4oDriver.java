package com.spaceprogram.db4o.sql.jdbc;

import com.db4o.ObjectContainer;
import com.db4o.Db4o;

import java.sql.*;
import java.util.Properties;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * User: treeder
 * Date: Aug 1, 2006
 * Time: 5:40:08 PM
 */
public class Db4oDriver implements Driver {
    public static final int VERSION_MAJOR = 1;
    public static final int VERSION_MINOR = 0;
    private static final String DRIVER_NAME = "db4o JDBC Driver";

    static {
        try {
            // Register the JWDriver with DriverManager
            Db4oDriver driver = new Db4oDriver();
            DriverManager.registerDriver(driver);
            //System.setSecurityManager(new RMISecurityManager());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Db4oDriver() {
        System.out.println("LOADING DB4O DRIVER");
    }

    public Connection connect(String url, Properties info) throws SQLException {
        String user = info.getProperty("user");
        String password = info.getProperty("password");
        // try to make connection to db4o server
        String[] urlSplit = url.split("//");
        if (urlSplit.length < 2) {
            throwBadUrlException(url);
        }
        for (String s : urlSplit) {
            System.out.println(s);
        }
        // now take second half and get hostname/port
        String[] split2 = urlSplit[1].split(":");
        if (split2.length < 2) {
            throwBadUrlException(url);
        }
        String hostname = null;
        int port = 0;
        try {
            hostname = split2[0];
            port = Integer.parseInt(split2[1]);
        } catch (Exception e) {
            throwBadUrlException(url);
        }
        ObjectContainer oc = null;
        try {
            oc = Db4o.openClient(hostname, port, user, password);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
        return new Db4oConnection(this, oc);
    }

    private void throwBadUrlException(String url) throws SQLException {
        throw new SQLException("JDBC url is not valid [" + url + "].  Must be of the form: jdbc:db4o://HOSTNAME:PORT");
    }

    public boolean acceptsURL(String url) throws SQLException {
        return url.contains("db4o");
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return VERSION_MAJOR;
    }

    public int getMinorVersion() {
        return VERSION_MINOR;
    }

    public boolean jdbcCompliant() {
        return false;
    }

    public static String getVersion() {
        return VERSION_MAJOR + "." + VERSION_MINOR;
    }

    public static String getDriverName() {
        return DRIVER_NAME;
    }

    //@Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
