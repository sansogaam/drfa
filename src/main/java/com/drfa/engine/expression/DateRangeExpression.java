package com.drfa.engine.expression;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class DateRangeExpression implements Expression {


    @Override
    public boolean compareValue(String baseValue, String baseExpressionType, String targetValue, String targetExpressionType) {
        return false;
    }
}
