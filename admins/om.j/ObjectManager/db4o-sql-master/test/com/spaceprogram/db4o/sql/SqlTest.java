package com.spaceprogram.db4o.sql;

import com.db4o.ObjectContainer;
import com.db4o.reflect.generic.GenericObject;
import com.db4o.reflect.generic.GenericClass;
import com.db4o.reflect.generic.GenericReflector;
import com.db4o.reflect.generic.GenericField;
import com.db4o.reflect.jdk.JdkReflector;
import com.db4o.reflect.ReflectField;
import com.db4o.query.Query;
import com.spaceprogram.db4o.Contact;
import com.spaceprogram.db4o.TestUtils;
import com.spaceprogram.db4o.sql.parser.SqlParseException;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 * User: treeder
 * Date: Jul 10, 2006
 * Time: 2:12:45 PM
 */
public class SqlTest extends ContactTest {

	/**
	 * Will run a query for an object that is not in classpath.
	 * Need to make GenericObjects here with no class on hand.
	 */
	@Test
	public void testNoClass() throws Sql4oException, SqlParseException {

		initGenericObjects();

		/* Doesn't work yet, because can't make generic objects yet
				List<Result> results = Sql4o.execute(oc, "FROM com.acme.Person");
				System.out.println("Results.size: " + results.size());
				TestUtils.displaySqlResults(results);*/
	}

	private void initGenericObjects() {
		GenericClass personClass = initGenericClass();
		ReflectField surname = personClass.getDeclaredField("surname");
		ReflectField birthdate = personClass.getDeclaredField("birthdate");

		Object person = personClass.newInstance();
		surname.set(person, "John");
		birthdate.set(person, new Date());
		// todo: this doesn't work
		//oc.set(person);
	}

	private GenericClass initGenericClass() {
		GenericReflector reflector = new GenericReflector(null, new JdkReflector(Thread.currentThread().getContextClassLoader()));
		GenericClass _objectIClass = (GenericClass) reflector.forClass(Object.class);
		GenericClass result = new GenericClass(reflector, null, "com.acme.Person", _objectIClass);
		result.initFields(fields(result, reflector));
		return result;
	}

	private GenericField[] fields(GenericClass personClass, GenericReflector reflector) {
		return new GenericField[]{
				new GenericField("surname", reflector.forClass(String.class), false, false, false),
				new GenericField("birthdate", reflector.forClass(Date.class), false, false, false),
				new GenericField("bestFriend", personClass, false, false, false)
		};
	}


	/**
	 * - test query time vs normal soda query
	 * - test that correct number of results are returned
	 * - maybe correct value too
	 *
	 * @throws SQLException
	 */
	@Test
	public void testQueryResults() throws SQLException, SqlParseException, ClassNotFoundException, Sql4oException {

		// todo: assert that soda results equal sql results
		int sodaCount = 0;
		// lets time a sode query vs the jdbc
		{
			System.out.println("Soda query...");
			ObjectContainer oc = server.openClient();
			Query q = oc.query();
			q.constrain(Contact.class);
			q.descend("name").constrain("contact 2");
			q.descend("category").constrain("friends");
			long startTime = System.currentTimeMillis();
			List results = q.execute();
			for (Object o : results) {
				Contact c = (Contact) o;
				System.out.println("got: " + c);
				sodaCount++;
			}
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.println("soda duration: " + duration);
			oc.close();
		}

		int sqlCount = 0;
		{
			// now same query with sql
			System.out.println("SQL query");
			ObjectContainer oc = server.openClient();
			try {
				long startTime = System.currentTimeMillis();

				// execute query
				String query = "select * from com.spaceprogram.db4o.Contact c where " +
						" name = 'contact 2' and " + //  and email = 'email@2.com'
						" category = 'friends'";

				List<Result> results = Sql4o.execute(oc, query);
				sqlCount = results.size();
				TestUtils.displaySqlResults(results);
				long endTime = System.currentTimeMillis();
				long duration = endTime - startTime;
				System.out.println("SQL duration: " + duration);
			} finally {
				oc.close();
			}
		}
		Assert.assertEquals(sodaCount, sqlCount);
	}

	@Test
	public void testSelectFieldsQuery() throws SqlParseException, Sql4oException {
		String query = "select name, age from com.spaceprogram.db4o.Contact c where " +
				"name = 'contact 2' and " + //  and email = 'email@2.com'
				" category = 'friends'";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(1, results.size());
		// get by index
		Result result = results.get(0);
		Assert.assertEquals("contact 2", result.getObject(0));

		// get by name
		Assert.assertEquals(20, result.getObject("age"));
	}

	@Test(expected = Sql4oRuntimeException.class)
	public void testFieldExceptions() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "select name, age from com.spaceprogram.db4o.Contact c where " +
				"name = 'contact 2' and " + //  and email = 'email@2.com'
				" category = 'friends'";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Result result = results.get(0);
		Object somefield = result.getObject("somefield"); // this should throw, but it's expected
	}

	@Test
	public void testAsteriskQuery() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "select * from com.spaceprogram.db4o.Contact c where " +
				"name = 'contact 2' and " + //  and email = 'email@2.com'
				" category = 'friends'";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(1, results.size());
		// get by index
		Result result = results.get(0);
		Assert.assertEquals("contact 2", result.getObject("name"));

		// get by name
		Assert.assertEquals(20, result.getObject("age"));
	}

	@Test
	public void testNoSelectQuery() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"name = 'contact 2' and " + //  and email = 'email@2.com'
				" category = 'friends'";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(1, results.size());

		Result result = results.get(0);
		Assert.assertEquals("contact 2", result.getObject("name"));

		Assert.assertEquals(20, result.getObject("age"));
	}

	@Test
	public void testIntCondition() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				" age = 10 or age = 20 ";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(2, results.size());

		Result result = results.get(0);
		Assert.assertTrue(result.getObject("age").equals(10) || result.getObject("age").equals(20));
	}

	@Test
	public void testIntegerCondition() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				" id = 1 ";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(1, results.size());

		Result result = results.get(0);
		Assert.assertEquals("contact 1", result.getObject("name"));

		Assert.assertEquals(new Integer(1), result.getObject("id"));
	}

	@Test
	public void testLongCondition() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				" longField = 1 ";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(1, results.size());

		Result result = results.get(0);

		Assert.assertEquals(new Long(1), result.getObject("longField"));
	}

	@Test
	public void testDoubleCondition() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"age = 20 and " +
				"income = 50000.02";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(1, results.size());

		Result result = results.get(0);
		Assert.assertEquals("contact 2", result.getObject("name"));
		Assert.assertEquals(50000.02, result.getObject("income"));
	}

	@Test
	public void testDateCondition() throws SqlParseException, ClassNotFoundException, Sql4oException {
		int numDays = 6;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -numDays);

		String query = "from com.spaceprogram.db4o.Contact c where " +
				"birthDate > '" + Converter.df.format(cal.getTime()) + "' ";
		System.out.println("query: " + query);

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(numDays + 1, results.size());

	}

	@Test
	public void testDoubleCondition2() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"age = 20 and " +
				"doubleField = 50000.02";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(1, results.size());

		Result result = results.get(0);
		Assert.assertEquals("contact 2", result.getObject("name"));
		Assert.assertEquals(50000.02, result.getObject("income"));
	}

	@Test
	public void testComplexWhere1() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"(age = 10 or age = 20) and " +
				"income = 50000.02";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(2, results.size());

		Result result = results.get(0);
		Assert.assertEquals(50000.02, result.getObject("income"));
	}

	@Test
	public void testLessThan() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"income < 50000.03";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(5, results.size());
	}

	@Test
	public void testLessThanOrEqual() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"income <= 50000.02";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(5, results.size());
	}

	@Test
	public void testLessThanOrEqual2() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"income <= 50000.01";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(0, results.size());
	}

	@Test
	public void testGreaterThan() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"income > 50000.03";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(5, results.size());
	}

	@Test
	public void testGreaterThanOrEqual() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"income >= 50000.02";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(10, results.size());
	}

	@Test
	public void testGreaterThanOrEqual2() throws SqlParseException, ClassNotFoundException, Sql4oException {
		String query = "from com.spaceprogram.db4o.Contact c where " +
				"income >= 50000.03";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(5, results.size());
	}

	@Test(expected = Sql4oException.class)
	public void testInvalidField() throws SqlParseException, ClassNotFoundException, Sql4oException {
		// age2 is not valid
		String query = "select age, age2 from com.spaceprogram.db4o.Contact c where " +
				"income >= 50000.03";

		List<Result> results = Sql4o.execute(oc, query);
		TestUtils.displaySqlResults(results);

		Assert.assertEquals(5, results.size());
	}

	@Test
	public void testGetColumnNames() throws SqlParseException, ClassNotFoundException, Sql4oException {
		// age2 is not valid
		String query = "select id, age, income from com.spaceprogram.db4o.Contact c where " +
				"income >= 50000.03";

		ObjectSetWrapper results = (ObjectSetWrapper) Sql4o.execute(oc, query);
		Assert.assertEquals(3, results.getMetaData().getColumnCount());
		Assert.assertEquals("id", results.getMetaData().getColumnName(0));
		Assert.assertEquals("age", results.getMetaData().getColumnName(1));
		Assert.assertEquals("income", results.getMetaData().getColumnName(2));
	}

	@Test
	public void testOneDeep() throws SqlParseException, ClassNotFoundException, Sql4oException {
		// age2 is not valid
		String query = "from com.spaceprogram.db4o.Contact c where c.address.id = 3";

		ObjectSetWrapper results = (ObjectSetWrapper) Sql4o.execute(oc, query);
		Assert.assertEquals(1, results.size());
		Result result = (Result) results.get(0);
		Contact c = (Contact) result.getBaseObject(0);
		Assert.assertEquals(3, c.getAddress().getId());
	}
	@Test
	public void testTwoDeep() throws SqlParseException, ClassNotFoundException, Sql4oException {
		// age2 is not valid
		String query = "from com.spaceprogram.db4o.Contact c where c.address.city.id = 2";

		ObjectSetWrapper results = (ObjectSetWrapper) Sql4o.execute(oc, query);
		Assert.assertEquals(1, results.size());
		Result result = (Result) results.get(0);
		Contact c = (Contact) result.getBaseObject(0);
		Assert.assertEquals(2, c.getAddress().getCity().getId());
	}

	@Test
	public void testOrderingDescending() throws SqlParseException, ClassNotFoundException, Sql4oException {
		// age2 is not valid
		String query = "from com.spaceprogram.db4o.Contact c order by name desc";

		ObjectSetWrapper results = (ObjectSetWrapper) Sql4o.execute(oc, query);
		int i = results.size();
		for (Object resultOb : results) {
			Result result = (Result) resultOb;
			Contact c = (Contact) result.getBaseObject(0);
			Assert.assertEquals("contact " + (i-1), c.getName());
			i--;
		}
	}
	@Test
	public void testOrderingAscending() throws SqlParseException, ClassNotFoundException, Sql4oException {
		// age2 is not valid
		String query = "from com.spaceprogram.db4o.Contact c order by name asc";

		ObjectSetWrapper results = (ObjectSetWrapper) Sql4o.execute(oc, query);
		int i = 0;
		for (Object resultOb : results) {
			Result result = (Result) resultOb;
			Contact c = (Contact) result.getBaseObject(0);
			Assert.assertEquals("contact " + (i), c.getName());
			i++;
		}
	}
}
