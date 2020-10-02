package com.spaceprogram.db4o.sql.parser;

import org.junit.Test;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.spaceprogram.db4o.sql.SqlParser;

/**
 * User: treeder
 * Date: Aug 1, 2006
 * Time: 11:36:50 AM
 */
public class RegexTest {
    @Test
    public void testQuotedString() {
        String REGEX //"'[A-Za-z0-9._%-]*'";
                = "'[\\w\\s]*'"; // equivalent to above
        String INPUT = "where contact='someNAme LAstname'";

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(INPUT);

        System.out.println("Current REGEX is: " + REGEX);
        System.out.println("Current INPUT is: " + INPUT);

        processMatches(matcher);
    }

    @Test
    public void testOperators() {
        String REGEX
                = SqlParser.REGEX_OPERATORS; //"[(<>)(<=)(>=)]"; // equivalent to above
        Pattern pattern = Pattern.compile(REGEX);
        System.out.println("Current REGEX is: " + REGEX);

        String inputs[] = new String[]{
                "where contact='someNAme LAstname'",
                "where contact = 'somename'",
                "where contact <= 'somename'",
                "where contact<='somename'",
                "where contact <'test'",
                " where contact>= 123",
                "where contact => 123"// shouldn't work
        };
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            Matcher matcher = pattern.matcher(input);
            System.out.println("Current INPUT is: " + input);
            processMatches(matcher);
        }

    }


    private void processMatches(Matcher matcher) {
        boolean found = false;
        for (int i = 0; i < matcher.groupCount(); i++) {
            String g =  matcher.group(i);
            System.out.println("group: " + g);
        }
        while (matcher.find()) {
            System.out.println("I found the text \"" + matcher.group() +
                    "\" starting at index " + matcher.start() +
                    " and ending at index " + matcher.end() + ".");
            found = true;
        }
        if (!found) {
            System.out.println("No match found.");
        }
    }
}
