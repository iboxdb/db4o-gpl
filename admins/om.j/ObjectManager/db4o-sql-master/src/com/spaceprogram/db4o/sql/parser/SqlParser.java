package com.spaceprogram.db4o.sql.parser;

import com.spaceprogram.db4o.sql.query.*;
import com.spaceprogram.db4o.sql.SqlStatement;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * This is a core class that will do the parsing of an SQL string and return that string in an object format.
 * There are no ties to db4o in in this class.
 * <p/>
 * User: treeder
 * Date: Jul 26, 2006
 * Time: 6:05:54 PM
 */
public class SqlParser {

	private static String REGEX_QUOTED_STRING = "'[^']*'"; //"'[^'.]*'"; // "'.*'"; // "'[\\w\\s]*'"; -

	private Builder[] builders = {
			new SelectBuilder(),
			new FromBuilder(),
			new WhereBuilder(),
			new OrderByBuilder(),
	};
	private String query;
	private List<String> quotedStrings = new ArrayList<String>();

	public static SqlStatement parse(String query) throws SqlParseException {
		SqlParser parser = new SqlParser();
		parser.setQuery(query);
		return parser.doParse();

	}

	private SqlStatement doParse() throws SqlParseException {
		// what to do for quoted strings that might have spaces in them??? hmmm, how about taking those out right now and replacing them with tokens
		query = replaceQuotedStrings(query);
		//  System.out.println("query: " + query);

		String[] split = query.trim().split("\\s+"); // split by white space
		// preliminary checks
		if (split.length < 2) {
			throw new SqlParseException("Invalid query.");
		}
		// now build up query into different parts
		SqlQuery sq = new SqlQuery();
		buildQuery(split, sq);
		// ensure that the correct pieces are here
		if (sq.getFrom() == null) {
			throw new SqlParseException("No FROM part!");
		}
		return sq;
	}

	private String replaceQuotedStrings(String query) {
		StringBuffer buff = new StringBuffer(query);
		Pattern pattern = Pattern.compile(REGEX_QUOTED_STRING);
		Matcher matcher = pattern.matcher(buff);

		boolean found = false;
		int i = 0;
		while (matcher.find()) {
			/*System.out.println("found text \"" + matcher.group() +
								"\" starting at index " + matcher.start() +
								" and ending at index " + matcher.end() + ".");
						*/
			//swap the string out with a token
			quotedStrings.add(matcher.group());
			buff.replace(matcher.start(), matcher.end(), "{" + i + "}");
			matcher.reset(); // todo: could optimize this
			found = true;
			i++;
		}
		if (!found) {
			//  System.out.println("No match found.");
		}
		String ret = buff.toString();
		//System.out.println("query changed to: " + ret);
		return ret;
	}

	private void buildQuery(String[] split, SqlQuery sq) throws SqlParseException {
		// look for keywords
		Builder curBuilder = null;
		List<String> expr = new ArrayList<String>();
		for (String s : split) {
			Builder builder = getBuilder(s);
			//System.out.println("piece=" + s + ", builder=" + builder);
			if (builder != null) {
			//	System.out.println("found builder for " + s + ": " + builder);
				if (curBuilder != null) {
					curBuilder.build(sq, expr, quotedStrings);
				}
				curBuilder = builder;
				expr.clear();
			} else {
				expr.add(s);
			}
		}
		if (expr.size() > 0) {
			if (curBuilder != null)
				curBuilder.build(sq, expr, quotedStrings);
			else throw new SqlParseException("Invalid Query. No FROM part.");
		}
	}

	private Builder getBuilder(String s) {
		for (Builder builder : builders) {
			if (s.equalsIgnoreCase(builder.getKeyword())) {
				return builder;
			}
		}
		return null;
	}

	public void setQuery(String query) {
		this.query = query;
	}


	public static String replaceQuotedValue(List<String> quotedStrings, String value) {
		if (value.startsWith("{") && value.endsWith("}")) {
			int replacementIndex = Integer.parseInt(value.substring(1, value.length() - 1));
			return quotedStrings.get(replacementIndex);
		}
		return value;
	}


	public static String stripQuotes(String s) {
		return s; //s.replaceAll("'", "");
	}


	public static List<String> separateCommas(List<String> expr, boolean includeCommas) {
		List<String> values = new ArrayList<String>();
		for (String s : expr) {
			if (s.equals(",")) {
				if (includeCommas) {
					values.add(",");
				} else {
					continue; // skip it
				}
			}
			String[] s2 = s.split(",");
			int i = 0;
			for (String s1 : s2) {
				if(i > 0 && includeCommas) values.add(",");
				values.add(s1);
				i++;
			}
		}
		return values;
	}
}
