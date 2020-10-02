package com.spaceprogram.db4o.sql;

import org.junit.Test;
import org.junit.Assert;

import java.util.List;

import com.spaceprogram.db4o.TestUtils;
import com.spaceprogram.db4o.sql.parser.SqlParseException;

/**
 * User: treeder
 * Date: Aug 20, 2006
 * Time: 3:53:16 PM
 */
public class MetaDataTest extends ContactTest {
    @Test
    public void testMetaData() throws Sql4oException, SqlParseException {
        String query = "from com.spaceprogram.db4o.Contact";

        List<Result> results = Sql4o.execute(oc, query);
        ObjectSetWrapper wrapper = (ObjectSetWrapper) results;
        ObjectSetMetaData metaData = wrapper.getMetaData();

        Assert.assertEquals(12, metaData.getColumnCount());

    }
	@Test
	public void testInheritance() throws Sql4oException, SqlParseException {
		String query = "select pc1, pc2 from com.spaceprogram.db4o.Contact";

        List<Result> results = Sql4o.execute(oc, query);
        ObjectSetWrapper wrapper = (ObjectSetWrapper) results;
        ObjectSetMetaData metaData = wrapper.getMetaData();

        Assert.assertEquals(2, metaData.getColumnCount());
		Result r = results.get(0);
		Assert.assertEquals("pc1 value", r.getObject("pc1"));
		Assert.assertEquals("pc2 value", r.getObject("pc2"));

	}
}
