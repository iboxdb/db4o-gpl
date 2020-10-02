package com.spaceprogram.db4o.sql.parser;

import com.spaceprogram.db4o.sql.query.SqlQuery;
import com.spaceprogram.db4o.sql.query.Where;
import com.spaceprogram.db4o.sql.query.WhereExpression;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;

/**
 * User: treeder
 * Date: Feb 4, 2007
 * Time: 4:33:48 PM
 */
public class WhereBuilder implements Builder {

	public static final String REGEX_OPERATORS = "<=|>=|<>|=|<|>";

	final String keyword = "WHERE";

	public String getKeyword() {
		return keyword;
	}

	public void build(SqlQuery sq, List<String> expressionSplit, List<String> quotedStrings) throws SqlParseException {
		// where clauses are tree like structures
		/*
					samples:
					where c.name = 'somename' and id = 123 or id < 300 and (name = 'scooby' OR name != 'something')

					*/
		Where where = new Where();
		buildExpr(where.getRoot(), expressionSplit, 0, quotedStrings);
		sq.setWhere(where);

	}

	private int buildExpr(WhereExpression root, List<String> expressionSplit, int index, List<String> quotedStrings) throws SqlParseException {
		//System.out.println("slit.size= " + expressionSplit.size() + " " + expressionSplit);
		// todo: support BETWEEN x AND y
		Pattern pattern = Pattern.compile(REGEX_OPERATORS);
		WhereExpression current = new WhereExpression();
		int i = index;
		for (; i < expressionSplit.size(); i++) {
			String s = expressionSplit.get(i);
			//System.out.println("WHERE expr: " + s);
			if (s.startsWith("(")) {
				// then start of a sub expression - recurse
				String token;
				if (s.equals("(")) {
					token = s;
				} else {
					// then attached to next token
					token = s.substring(1);
					expressionSplit.add(i + 1, token);
				}
				i++;
				WhereExpression sub = new WhereExpression();
				root.add(sub);
				i = buildExpr(sub, expressionSplit, i, quotedStrings);
			} else if (s.equals(")")) {
				return i;

			} else if (s.equalsIgnoreCase(WhereExpression.AND)) {
				// then new expression
				current = new WhereExpression(WhereExpression.AND);
				//root.add(current);
			} else if (s.equalsIgnoreCase(WhereExpression.OR)) {
				current = new WhereExpression(WhereExpression.OR);
				//root.add(current);
			} else {
				// otherwise, just normal expression body - ex: name = 'something' or name='something''
				// first token is field, 2nd is operator, 3rd is value
				Matcher matcher = pattern.matcher(s);
				List<MatchResult> matches = findMatches(matcher);
				int found = matches.size();
				String operator = null;
				String field = null;
				String value = null;
				int extraPiecesUsed = 0;
				if (found > 1) {
					throw new SqlParseException("Too many operators in where expression: " + s);
				} else if (found == 1) {
					// then it's on this first token
					MatchResult matchResult = matches.get(0);
					operator = matchResult.group();
					field = s.substring(0, matchResult.start());
					// check if second half is attached too
					value = checkForNextPieceAttached(s, matchResult, operator);
				} else {
					field = s;
					// check next piece
					if (expressionSplit.size() <= i + 1) {
						throw new SqlParseException("Invalid where expression.");
					}
					String s2 = expressionSplit.get(i + 1);
					//System.out.println("s2=" + s2);
					Matcher matcher2 = pattern.matcher(s2);
					List<MatchResult> matches2 = findMatches(matcher2);
					int found2 = matches2.size();
					if (found2 > 1) {
						throw new SqlParseException("Too many operators in where expression: " + s);
					} else if (found2 == 1) {
						// then all good
						MatchResult matchResult = matches2.get(0);
						operator = matchResult.group();
						//value = //checkForNextPieceAttached(s, matchResult, operator);
						if (s2.length() > matchResult.end()) {
							value = s2.substring(matchResult.end(), s2.length());
						}

					} else {
						throw new SqlParseException("Operator not found in where expression.");
					}
					extraPiecesUsed++;
				}
				if (value == null) {
					// must be next piece
					// todo: check for list size before this and the same above
					value = expressionSplit.get(i + extraPiecesUsed + 1);
					extraPiecesUsed++;
				}
				//System.out.println("full expression found: " + field + "," + operator + "," + value);
				if (value.endsWith(")")) {
					value = value.substring(0, value.length() - 1);
					expressionSplit.add(i + extraPiecesUsed + 1, ")");
				}
				// check if value was replaced
				value = SqlParser.replaceQuotedValue(quotedStrings, value);
				// System.out.println("replaced with: " + value);
				i += extraPiecesUsed;

				if (field == null || field.length() < 1
						|| operator == null || operator.length() < 1
						|| value == null || value.length() < 1) {
					throw new SqlParseException("Incomplete where expression.");
				}

				current.setField(field);
				current.setOperator(operator);
				current.setValue(value);
				//      System.out.println("current: " + current);
				root.add(current);
			}
		}
		return i;

	}


	private String checkForNextPieceAttached(String s, MatchResult matcher, String operator) {
		String value = null;
		if (s.length() > matcher.end() + operator.length()) {
			value = s.substring(matcher.end(), s.length());
		}
		return value;
	}


	private List<MatchResult> findMatches(Matcher matcher) {
		List<MatchResult> ret = new ArrayList<MatchResult>();
		while (matcher.find()) {
//                System.out.println("I found the text \"" + matcher.group() +
//                        "\" starting at index " + matcher.start() +
//                        " and ending at index " + matcher.end() + ".");
			ret.add(matcher.toMatchResult());
		}
		return ret;
	}
}