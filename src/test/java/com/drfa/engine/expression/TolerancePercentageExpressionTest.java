package com.drfa.engine.expression;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sanjiv on 7/17/2015.
 */
public class TolerancePercentageExpressionTest {
    
    @Test
    public void shouldTestTolerancePercentageHappyScenario() throws Exception{
        String valueToBeConverted = "100";
        String expressionType = "10";
        Expression expression = new TolerancePercentageExpression();
        
        assertEquals("90",expression.modifiedValue(valueToBeConverted, expressionType));
    }
}
