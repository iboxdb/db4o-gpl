package com.spaceprogram.db4o.sql;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import com.spaceprogram.db4o.util.DbUtil;
import com.spaceprogram.db4o.TestUtils;
import com.spaceprogram.db4o.Contact;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;

/**
 * Abstract test class that will create a database and put some Contact objects in it.
 * Cleans itself up after tests.
 * <p/>
 * User: treeder
 * Date: Aug 20, 2006
 * Time: 3:51:42 PM
 */
public abstract class ContactTest {

    public static String username = "myUser";
    public static String password = "myPass";
    public static int port = 11987;

    protected static ObjectServer server;
    protected ObjectContainer oc;

    @BeforeClass
    public static void setupDb() {
        System.out.println("Setup");
        server = DbUtil.getObjectServer("sqltest", port);
        server.grantAccess(username, password);
        ObjectContainer oc = server.openClient();
        TestUtils.makeContacts(oc, 10);
        //TestUtils.dump(oc);
        oc.close();
    }

    @AfterClass
    public static void tearDownAfter() {
        System.out.println("tearDownAfter");
        // remove contacts
        System.out.println("Removing contacts");
        ObjectContainer oc = server.openClient();
        TestUtils.clear(oc, Contact.class);
        oc.close();

        // shutdown server
        server.close();
    }

    @Before
    public void beforeEach() {
        System.out.println("beforeEach");
        // opening and closing for each test doesn't work, not sure why
        oc = server.openClient();
    }

    @After
    public void afterEach() {
        System.out.println("afterEach");
        oc.close();
    }

}
