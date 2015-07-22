package com.drfa.engine.expression;

import com.drfa.engine.file.ExpressionContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class DateFormatExpression implements Expression {

    @Override
    public boolean compareValue(String baseValue, String targetValue, ExpressionContext expressionContext) {
        try {
            DateFormat baseFormat = new SimpleDateFormat(expressionContext.getBaseColumnExpressionType());
            DateFormat targetFormat = new SimpleDateFormat(expressionContext.getTargetColumnExpressionType());
            Date baseDate = baseFormat.parse(baseValue);
            Date targetDate = targetFormat.parse(targetValue);
            return baseDate.equals(targetDate);
        } catch (Exception e) {
            return false;
        }
    }
}
