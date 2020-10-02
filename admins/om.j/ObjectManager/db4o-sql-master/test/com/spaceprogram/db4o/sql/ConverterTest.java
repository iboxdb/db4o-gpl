package com.spaceprogram.db4o.sql;

import org.junit.Test;
import org.junit.Assert;

/**
 * User: treeder
 * Date: Mar 9, 2007
 * Time: 10:49:47 AM
 */
public class ConverterTest {

	@Test
	public void testConvertFromStringBoolean() throws Exception {
		Class toGet = Boolean.class;
		Object expected = Boolean.FALSE;
		assertConvert(toGet, "false", expected);

		assertConvert(Boolean.TYPE, "false", expected);
	}

	private void assertConvert(Class toGet, String input, Object expected) throws Exception {
		Object ob = Converter.convertFromString(toGet, input);
		Assert.assertEquals(expected, ob);
	}
}
