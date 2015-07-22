package com.drfa.engine.expression;

import com.drfa.engine.file.ExpressionContext;
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
        boolean comparedValue = expression.compareValue(valueToBeConverted, "95", new ExpressionContext("TA",null,null,expressionType));
        assertTrue(comparedValue);
    }

    @Test
    public void shouldTestTolerancePercentageIfItsString() throws Exception{
        String valueToBeConverted = "NA";
        String expressionType = "10";
        Expression expression = new ToleranceAbsoluteExpression();
        assertFalse(expression.compareValue(valueToBeConverted, "45", new ExpressionContext("TA",null,null,expressionType)));
    }

    @Test
    public void shouldTestTolerancePercentageIfTargetValueIsEqualToHighestBaseValue() throws Exception{
        String valueToBeConverted = "100";
        String expressionType = "13";
        Expression expression = new ToleranceAbsoluteExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, "113", new ExpressionContext("TA",null,null,expressionType));
        assertTrue(comparedValue);
    }

    @Test
    public void shouldTestTolerancePercentageHappyScenarioForOddPercentage() throws Exception{
        String valueToBeConverted = "100";
        String expressionType = "13";
        Expression expression = new ToleranceAbsoluteExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, "110", new ExpressionContext("TA",null,null,expressionType));
        assertTrue(comparedValue);
    }

    @Test
    public void shouldTestTolerancePercentageHappyScenarioForOddInput() throws Exception{
        String valueToBeConverted = "76";
        String expressionType = "13";
        Expression expression = new ToleranceAbsoluteExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, "88", new ExpressionContext("TA",null,null,expressionType));
        assertTrue(comparedValue);
    }


    @Test
    public void shouldTestTolerancePercentageIfTheTargetIsNotInRange() throws Exception{
        String valueToBeConverted = "76";
        String expressionType = "13";
        Expression expression = new TolerancePercentageExpression();
        boolean comparedValue = expression.compareValue(valueToBeConverted, "90", new ExpressionContext("TA",null,null,expressionType));
        assertFalse(comparedValue);
    }
}
