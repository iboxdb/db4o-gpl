package com.spaceprogram.db4o.sql.parser;


/**
 * User: treeder
 * Date: Aug 1, 2006
 * Time: 11:31:26 AM
 */
public class QueryString {
    private String input;
    private String output;

    public QueryString(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public QueryString(String inputAndOutput) {
        this.input = inputAndOutput;
        this.output = inputAndOutput;
    }


    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

}
