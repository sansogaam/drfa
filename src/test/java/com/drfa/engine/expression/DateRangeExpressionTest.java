package com.drfa.engine.expression;

import com.drfa.engine.file.ExpressionContext;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 7/22/2015.
 */
public class DateRangeExpressionTest {
    @Test
    public void shouldTestTheHappyScenarioOfDifferentDateComparision(){
        String baseDate = "20-04-2015";
        String baseExpressionType ="dd-MM-yyyy";
        String targetDate = "22-Apr-2015";
        String targetExpressionType = "dd-MMM-yyyy";
        String rangeExpressionType = "5";
        Expression expression = new DateRangeExpression();
        boolean comparedValue = expression.compareValue(baseDate, targetDate, new ExpressionContext("DR",baseExpressionType, targetExpressionType,rangeExpressionType));
        assertTrue(comparedValue);
    }

    @Test
    public void shouldTestTheNegativeScenarioOfDifferentDateComparision(){
        String baseDate = "20-04-2015";
        String baseExpressionType ="dd-MM-yyyy";
        String targetDate = "28-Apr-2015";
        String targetExpressionType = "dd-MMM-yyyy";
        String rangeExpressionType = "5";
        Expression expression = new DateRangeExpression();
        boolean comparedValue = expression.compareValue(baseDate, targetDate, new ExpressionContext("DR",baseExpressionType, targetExpressionType,rangeExpressionType));
        assertFalse(comparedValue);
    }

}
