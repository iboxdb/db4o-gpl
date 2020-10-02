package com.spaceprogram.db4o;

import com.db4o.ObjectServer;
import com.db4o.ObjectContainer;
import com.spaceprogram.db4o.util.DbUtil;
import org.junit.BeforeClass;

/**
 * User: treeder
 * Date: Sep 4, 2006
 * Time: 4:43:40 PM
 */
public class SimpleServer {
    public static String username = "myUser";
    public static String password = "myPass";
    public static int port = 11987;

    protected static ObjectServer server;
    protected ObjectContainer oc;

    public static void main(String[] args) {
        System.out.println("Starting server on port: " + port);
        server = DbUtil.getObjectServer("sqltest", port);
        server.grantAccess(username, password);
        ObjectContainer oc = server.openClient();
        TestUtils.makeContacts(oc, 100);
        TestUtils.dump(oc);
        oc.close();
    }
}
