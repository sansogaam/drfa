package com.drfa.engine.expression;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class DateFormatExpression implements Expression {

    @Override
    public boolean compareValue(String baseValue, String baseExpressionType, String targetValue, String targetExpressionType) {
        try {
            DateFormat baseFormat = new SimpleDateFormat(baseExpressionType);
            DateFormat targetFormat = new SimpleDateFormat(targetExpressionType);
            Date baseDate = baseFormat.parse(baseValue);
            Date targetDate = targetFormat.parse(targetValue);
            return baseDate.equals(targetDate);
        } catch (Exception e) {
            return false;
        }
    }
}
