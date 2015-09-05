package com.drfa.engine.expression;

import com.drfa.engine.file.ExpressionContext;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class GenericExpression implements Expression {

    @Override
    public boolean compareValue(String baseValue,  String targetValue, ExpressionContext expressionContext) {
        return baseValue.equalsIgnoreCase(targetValue);
    }
}
