package com.spaceprogram.db4o.sql;

import java.util.Date;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * <p>
 * Convert fields to different data types.
 * <p/>
 * User: treeder
 * Date: Aug 21, 2006
 * Time: 2:39:19 PM
 */
public class Converter {
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	public static DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);

	public static Boolean convertToBoolean(Object o) {
		if (o instanceof Boolean) {
			// todo: this should accept Numbers too
			return (Boolean) o;
		}
		return false;
	}

	/**
	 * Converts a String value to the Class represented by to.
	 * Only converts primitives and second class types like Date and String.
	 *
	 * @param to
	 * @param from
	 * @return
	 * @throws Exception
	 */
	public static Object convertFromString(Class to, String from) throws Exception {
		Object val = null;
		//System.out.println("Class " + to);
		// could probably just instantiate most of these things using reflection
		if (to.isPrimitive()) {
			if (to.isAssignableFrom(Integer.TYPE)) {
				val = new Integer(from);
			} else if (to.isAssignableFrom(Long.TYPE)) {
				val = new Long(from);
			} else if (to.isAssignableFrom(Float.TYPE)) {
				val = new Float(from);
			} else if (to.isAssignableFrom(Double.TYPE)) {
				val = new Double(from);
			} else if (to.isAssignableFrom(Short.TYPE)) {
				val = new Short(from);
			} else if (to.isAssignableFrom(Byte.TYPE)) {
				val = new Byte(from);
			} else if (to.isAssignableFrom(Boolean.TYPE)) {
				val = new Boolean(from);
			} else if (to.isAssignableFrom(Character.TYPE)) {
				val = parseCharacter(from);
			}

		} else if (Boolean.class.isAssignableFrom(to)) {
			val = new Boolean(from);
			// todo: add the rest of the wrapper types - Boolean, Character
		} else if (Character.class.isAssignableFrom(to)) {
			val = parseCharacter(from);
		} else if (Number.class.isAssignableFrom(to)) {
			if (to == Integer.class) {
				val = new Integer(from);
			} else if (to == Long.class) {
				val = new Long(from);
			} else if (to == Float.class) {
				val = new Float(from);
			} else if (to == Double.class) {
				val = new Double(from);
			} else if (to == Short.class) {
				val = new Short(from);
			} else if (to == Byte.class) {
				val = new Byte(from);
			}
		} else if (to == String.class) {
			val = from;
		} else if (to == Date.class) {
			try {
				val = df.parse(from);
			} catch (ParseException e) {
				throw new Exception("Could not parse date: " + from + ". Format must be: " + DATE_FORMAT_PATTERN);
			}
		} else {
			throw new Exception("Value type is not recognized! " + to + " : " + from);
		}
		return val;
	}

	private static Object parseCharacter(String from) throws Exception {
		Object val;
		if (from.length() != 1) {
			throw new Exception("Could not parse character: " + from);
		}
		val = new Character(from.charAt(0));
		return val;
	}

}
