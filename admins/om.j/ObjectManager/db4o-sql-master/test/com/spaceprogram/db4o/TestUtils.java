package com.spaceprogram.db4o;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.spaceprogram.db4o.util.ObjectContainerUtils;
import com.spaceprogram.db4o.sql.Result;
import com.spaceprogram.db4o.sql.ObjectSetWrapper;
import com.spaceprogram.db4o.sql.Sql4oException;

import java.util.List;
import java.util.Calendar;

/**
 * User: treeder
 * Date: Jul 11, 2006
 * Time: 8:52:10 AM
 */
public class TestUtils {

    public static int contactNumber = 0;
	public static int idGen = 0; // added this after, that's why there is two things here

	public static void makeContacts(ObjectContainer oc, int numberOfContacts) {
        System.out.println("Making " + numberOfContacts + " contacts");
        // a some contacts too
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < numberOfContacts; i++) {
            Contact c = new Contact();
			c.setPc1("pc1 value");
			c.setPc2("pc2 value");
			c.setId(contactNumber);
            c.setName("contact " + i);
            c.setEmail("email@" + i + ".com");
            c.setCategory("friends");
            c.setAge(i * 10);
			c.setBirthDate(cal.getTime());
			cal.add(Calendar.DAY_OF_YEAR, -1);
			c.setLongField(new Long(i));

			if(i < 5) {
				c.setIncome(50000.02);
				c.setDoubleField(new Double(c.getIncome()));
			}
            else {
				c.setIncome(61000.67);
				c.setDoubleField(new Double(c.getIncome()));
			}
			Address address = new Address(nextId(), "Rodeo Drive");
			City city = new City(nextId(), "San Francisco");
			address.setCity(city);
			c.setAddress(address);
			oc.set(c);
            contactNumber++;
        }
        oc.commit();

    }

	private static Integer nextId() {
		return ++idGen;
	}


	public static int clear(ObjectContainer oc, Class aClass) {
        return ObjectContainerUtils.clear(oc, aClass);
    }

    public static int clear(ObjectContainer oc) {
        return ObjectContainerUtils.clear(oc);
    }

    public static void displaySqlResults(List<Result> results) throws Sql4oException {
        int columnCount = ((ObjectSetWrapper)results).getMetaData().getColumnCount();
        for (Result result : results) {
            System.out.print("Got: " + result.getBaseObject(0) + " : ");
            displaySqlResult(result, columnCount);
            System.out.println();
        }
    }

    public static void displaySqlResult(Result result, int columnCount) throws Sql4oException {
        for (int i = 0; i < columnCount; i++) {
            Object o = result.getObject(i);
            System.out.print("field" + i + "=" + o + " ");
        }
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
