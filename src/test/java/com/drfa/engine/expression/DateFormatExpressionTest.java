package com.drfa.engine.expression;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 7/20/2015.
 */
public class DateFormatExpressionTest {
    
    @Test
    public void shouldTestTheHappyScenarioOfDifferentDateComparision(){
        String baseDate = "20-04-2015";
        String baseExpressionType ="dd-MM-yyyy";
        String targetDate = "20-Apr-2015";
        String targetExpressionType = "dd-MMM-yyyy";
        Expression expression = new DateFormatExpression();
        boolean comparedValue = expression.compareValue(baseDate, baseExpressionType, targetDate,targetExpressionType);
        assertTrue(comparedValue);
    }
}
