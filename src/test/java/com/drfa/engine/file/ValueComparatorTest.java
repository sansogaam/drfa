package com.drfa.engine.file;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ValueComparatorTest {

    @Test
    public void shouldParseColumnExpressionWithSimpleExpression() throws Exception{
        ValueComparator valueComparator = new ValueComparator("Integer", "TP-(B-NR|T-NR)-(R-5)");
        ExpressionContext expressionContext = valueComparator.parseColumnExpression();
        assertEquals("TP", expressionContext.getExpressionType());
        assertEquals("NR", expressionContext.getBaseColumnExpressionType());
        assertEquals("NR", expressionContext.getTargetColumnExpressionType());
        assertEquals("5", expressionContext.getRangeExpression());
    }

    @Test
    public void shouldParseColumnExpressionWithDateExpression() throws Exception{
        ValueComparator valueComparator = new ValueComparator("Integer", "DF-(B-dd/MMM/yyyy|T-dd-mm-yyyy)-(R-5)");
        ExpressionContext expressionContext = valueComparator.parseColumnExpression();
        assertEquals("DF", expressionContext.getExpressionType());
        assertEquals("dd/MMM/yyyy", expressionContext.getBaseColumnExpressionType());
        assertEquals("dd-mm-yyyy", expressionContext.getTargetColumnExpressionType());
        assertEquals("5", expressionContext.getRangeExpression());
    }

    @Test
    public void shouldParseColumnExpressionWithStringExpression() throws Exception{
        ValueComparator valueComparator = new ValueComparator("Integer", "DF-(B-dd/MMM/yyyy|T-dd-mm-yyyy)-(R-/d4/R652/65)");
        ExpressionContext expressionContext = valueComparator.parseColumnExpression();
        assertEquals("DF", expressionContext.getExpressionType());
        assertEquals("dd/MMM/yyyy", expressionContext.getBaseColumnExpressionType());
        assertEquals("dd-mm-yyyy", expressionContext.getTargetColumnExpressionType());
        assertEquals("/d4/R652/65", expressionContext.getRangeExpression());
    }

    @Test
    public void shouldParseColumnExpressionIfNothing() throws Exception{
        ValueComparator valueComparator = new ValueComparator("Integer", "");
        ExpressionContext expressionContext = valueComparator.parseColumnExpression();
        assertNull(expressionContext.getBaseColumnExpressionType());
        assertNull(expressionContext.getTargetColumnExpressionType());
        assertNull(expressionContext.getRangeExpression());
    }


}