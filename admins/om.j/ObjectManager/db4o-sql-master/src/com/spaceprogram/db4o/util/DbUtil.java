package com.spaceprogram.db4o.util;

import com.db4o.ObjectServer;
import com.db4o.ObjectContainer;
import com.db4o.Db4o;
import com.db4o.config.ConfigScope;

import java.io.File;

/**
 * User: Travis Reeder
 * Date: Jul 4, 2006
 * Time: 10:39:02 AM
 */
public class DbUtil {

    // EDIT THESE SETTINGS
    private static final String DB_FILE_SUFFIX = ".db4o.yap";
    private static final int PORT = 0;

    private static ObjectServer objectServer;

    private static final ThreadLocal dbThreadLocal = new ThreadLocal();

    public static ObjectContainer getObjectContainer() {
        ObjectContainer oc = (ObjectContainer) dbThreadLocal.get();
        if (oc == null || oc.ext().isClosed()) {
            oc = getObjectServer().openClient();
            dbThreadLocal.set(oc);
        }
        return oc;
    }

    public static void closeDb() {
        ObjectContainer oc = (ObjectContainer) dbThreadLocal.get();
        dbThreadLocal.set(null);
        if (oc != null) oc.close();
    }

    public static ObjectServer getObjectServer() {
        return getObjectServer(null);
    }

    public synchronized static ObjectServer getObjectServer(String name) {
        return getObjectServer(name, PORT);
    }

    public static ObjectServer getObjectServer(String name, int port) {
        if(name == null){
            name = "default";
        }
        if (objectServer == null) {
            objectServer = getObjectServerForFilename(name + DB_FILE_SUFFIX, port);
        }
        return objectServer;
    }


    public static void shutdown() {
        if (objectServer != null) {
            objectServer.close();
        }
    }

    public static ObjectServer getObjectServerForFilename(String yapfilename, int port) {
        File parentDir = getDbDirectory();
        File dbfile = new File(parentDir, yapfilename);

        // for replication //////////////////////////
        Db4o.configure().generateUUIDs(ConfigScope.GLOBALLY);
        Db4o.configure().generateVersionNumbers(ConfigScope.GLOBALLY);

        // other options
        Db4o.configure().exceptionsOnNotStorable(true);
        Db4o.configure().objectClass("java.math.BigDecimal").translate(new com.db4o.config.TSerializable());
        Db4o.configure().allowVersionUpdates(true);

        // now open server
        ObjectServer objectServer = Db4o.openServer(dbfile.getPath(), port);

        return objectServer;
    }

    private static File getDbDirectory() {
        // will store database in {user.home}/db4o-data directory
        String dbfile = System.getProperty("user.home") + "/db4o/data";
        File f = new File(dbfile);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

}
