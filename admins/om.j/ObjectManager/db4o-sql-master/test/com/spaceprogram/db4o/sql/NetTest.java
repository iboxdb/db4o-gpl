package com.spaceprogram.db4o.sql;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.reflect.ReflectClass;
import com.spaceprogram.db4o.sql.parser.SqlParseException;
import com.spaceprogram.db4o.sql.util.ReflectHelper;
import org.junit.*;

import java.util.List;

/**
 * Test a .NET formatted database.
 * <p/>
 * User: treeder
 * Date: Oct 8, 2006
 * Time: 12:41:58 PM
 */
public class NetTest {
    public static String username = "myUser";
    public static String password = "myPass";
    public static int port = 11987;

    protected static ObjectServer server;
    protected ObjectContainer oc;

    @BeforeClass
    public static void setupDb() {
        System.out.println("Setup");
        Db4o.configure().exceptionsOnNotStorable(true);
        Db4o.configure().objectClass("java.math.BigDecimal").translate(new com.db4o.config.TSerializable());
        Db4o.configure().allowVersionUpdates(true);
        server = Db4o.openServer("resources/jap.net.yap", port); //DbUtil.getObjectServer("resources/sqltest", port);
        server.grantAccess(username, password);
//        ObjectContainer oc = server.openClient();
//        //TestUtils.makeContacts(oc, 10);
//        TestUtils.dump(oc);
//        oc.close();
    }

    @AfterClass
    public static void tearDownAfter() {
        System.out.println("tearDownAfter");
        // remove contacts
        /*ObjectContainer oc = server.openClient();
        TestUtils.clear(oc, Contact.class);
        oc.close();
*/
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

    @Test
    public void checkStoredClasses(){
        List storedClasses = ReflectHelper.getUserStoredClasses(oc);
        for (int i = 0; i < storedClasses.size(); i++) {
            ReflectClass storedClass = (ReflectClass) storedClasses.get(i);
            System.out.println("storedClass: " + storedClass.getName());
        }
    }
    @Test
    public void testClassLookup(){
        ReflectClass reflectClass = oc.ext().reflector().forName("Quizlet.Question, Quizlet.Framework");
        System.out.println("class: " + reflectClass);
        Assert.assertNotNull(reflectClass);

    }


    @Test
    public void testDotNetQuery() throws Sql4oException, SqlParseException {
        String query = "FROM 'Quizlet.Question, Quizlet.Framework'";
        ObjectSetWrapper results = (ObjectSetWrapper) Sql4o.execute(oc, query);
        Assert.assertTrue(results.size() > 0);
    }

}
