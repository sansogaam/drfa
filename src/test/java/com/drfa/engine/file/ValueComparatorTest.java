package com.drfa.engine.file;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ValueComparatorTest {

    @Test
    public void shouldParseColumnExpressionWhenBaseAndTargetBothExist() throws Exception{
        ValueComparator valueComparator = new ValueComparator("Integer", "B-TP-3|T-TP-4");
        valueComparator.parseColumnExpression();
        assertEquals("TP", valueComparator.getBaseColumnExpressionType());
        assertEquals("3", valueComparator.getBaseColumnExpressionValue());
        assertEquals("TP", valueComparator.getTargetColumnExpressionType());
        assertEquals("4", valueComparator.getTargetColumnExpressionValue());
    }

    @Test
    public void shouldParserColumnExpressionOnceThereIsOnlyOneExpression() throws Exception{
        ValueComparator valueComparator = new ValueComparator("Integer", "TP-3");
        valueComparator.parseColumnExpression();
        assertEquals("TP", valueComparator.getBaseColumnExpressionType());
        assertEquals("3", valueComparator.getBaseColumnExpressionValue());
        assertEquals("TP", valueComparator.getTargetColumnExpressionType());
        assertEquals("3", valueComparator.getTargetColumnExpressionValue());
    }

    @Test
    public void shouldParserColumnExpressionOnceThereIsNoExpression() throws Exception{
        ValueComparator valueComparator = new ValueComparator("Integer", null);
        valueComparator.parseColumnExpression();
        assertNull(valueComparator.getBaseColumnExpressionType());
        assertNull(valueComparator.getTargetColumnExpressionType());
    }

}