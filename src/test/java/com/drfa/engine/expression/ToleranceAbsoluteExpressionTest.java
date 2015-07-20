package com.drfa.engine.expression;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 7/19/2015.
 */
public class ToleranceAbsoluteExpressionTest {

    @Test
    public void shouldTestTolerancePercentageHappyScenario() throws Exception{
        String valueToBeConverted = "100";
        String expressionType = "10";
        Expression expression = new ToleranceAbsoluteExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, expressionType, "95",null);
        assertTrue(comparedValue);
    }

    @Test
    public void shouldTestTolerancePercentageIfItsString() throws Exception{
        String valueToBeConverted = "NA";
        String expressionType = "10";
        Expression expression = new ToleranceAbsoluteExpression();
        assertFalse(expression.compareValue(valueToBeConverted, expressionType,"45", null));
    }

    @Test
    public void shouldTestTolerancePercentageIfTargetValueIsEqualToHighestBaseValue() throws Exception{
        String valueToBeConverted = "100";
        String expressionType = "13";
        Expression expression = new ToleranceAbsoluteExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, expressionType, "113",null);
        assertTrue(comparedValue);
    }

    @Test
    public void shouldTestTolerancePercentageHappyScenarioForOddPercentage() throws Exception{
        String valueToBeConverted = "100";
        String expressionType = "13";
        Expression expression = new ToleranceAbsoluteExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, expressionType, "110",null);
        assertTrue(comparedValue);
    }

    @Test
    public void shouldTestTolerancePercentageHappyScenarioForOddInput() throws Exception{
        String valueToBeConverted = "76";
        String expressionType = "13";
        Expression expression = new ToleranceAbsoluteExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, expressionType, "88",null);
        assertTrue(comparedValue);
    }


    @Test
    public void shouldTestTolerancePercentageIfTheTargetIsNotInRange() throws Exception{
        String valueToBeConverted = "76";
        String expressionType = "13";
        Expression expression = new TolerancePercentageExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, expressionType, "90",null);
        assertFalse(comparedValue);
    }
}
