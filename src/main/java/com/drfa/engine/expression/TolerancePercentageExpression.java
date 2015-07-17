package com.drfa.engine.expression;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class TolerancePercentageExpression implements Expression {


    @Override
    public String modifiedValue(String valueToBeConverted, String expressionType) {
        Integer intValue = Integer.parseInt(valueToBeConverted);
        double percentageValue = new Double(Double.parseDouble(expressionType)/100);
        double subtractValue = percentageValue * intValue;
        Integer calculateValue = intValue-(int)subtractValue;
        return calculateValue.toString();
    }
}
