package com.drfa.engine.expression;


import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ExpressionFactoryTest{

    @Test
    public void shouldReturnGenericExpressionIfItDoesNotMatchColumnTypeAnyRule() throws Exception{
        Expression expression = ExpressionFactory.expression("NOT EXIST", "DA");
        assertTrue(expression instanceof GenericExpression);
    }
    @Test
    public void shouldReturnGenericExpressionIfItDoesNotMatchExpressionTypeRule() throws Exception{
        Expression expression = ExpressionFactory.expression("INTEGER", "NOT EXIST");
        assertTrue(expression instanceof GenericExpression);
    }

    @Test
    public void shouldReturnGenericExpressionIfItDoesNotMatchCombinationOfCTAndETRule() throws Exception{
        Expression expression = ExpressionFactory.expression("INTEGER", "DR");
        assertTrue(expression instanceof GenericExpression);
    }
    
    @Test
    public void shouldTestDateRangeExpressionType() throws Exception{
        Expression expression = ExpressionFactory.expression("DATE", "DR");
        assertTrue(expression instanceof DateRangeExpression);
    }

    @Test
    public void shouldTestDateFormatExpressionType() throws Exception{
        Expression expression = ExpressionFactory.expression("DATE", "DF");
        assertTrue(expression instanceof DateFormatExpression);
    }

    @Test
    public void shouldTestToleranceAbsoluteExpressionType() throws Exception{
        Expression expression = ExpressionFactory.expression("Integer", "TA");
        assertTrue(expression instanceof ToleranceAbsoluteExpression);
    }

    @Test
    public void shouldTestTolerancePercentageExpressionType() throws Exception{
        Expression expression = ExpressionFactory.expression("Integer", "TP");
        assertTrue(expression instanceof TolerancePercentageExpression);
    }
    
    @Test
    public void shouldTestStringParserExpressionType() throws Exception{
        Expression expression = ExpressionFactory.expression("String", "SP");
        assertTrue(expression instanceof StringParserExpression);
    }
}