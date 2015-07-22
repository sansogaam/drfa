package com.drfa.engine.expression;

import com.drfa.engine.file.ExpressionContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class DateRangeExpression implements Expression {


    @Override
    public boolean compareValue(String baseValue, String targetValue, ExpressionContext expressionContext) {
        try {
            DateFormat baseFormat = new SimpleDateFormat(expressionContext.getBaseColumnExpressionType());
            DateFormat targetFormat = new SimpleDateFormat(expressionContext.getTargetColumnExpressionType());
            int days = Integer.parseInt(expressionContext.getRangeExpression());
            Date baseDate = baseFormat.parse(baseValue);
            Date leftHandDate = manipulateDate(baseDate,-days);
            Date rightHandDate = manipulateDate(baseDate,days);
            Date targetDate = targetFormat.parse(targetValue);
            return targetDate.before(rightHandDate) && targetDate.after(leftHandDate);
        } catch (Exception e) {
            return false;
        }
    }
    
    private Date manipulateDate(Date d, int days){
        Date modifiedDate = (Date) d.clone();
        Calendar c = Calendar.getInstance();
        c.setTime(modifiedDate);
        c.add(Calendar.DATE, days);
        modifiedDate.setTime( c.getTime().getTime() );
        return modifiedDate;
    }
}
