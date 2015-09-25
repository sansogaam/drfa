package com.drfa.engine.file;

import com.drfa.engine.expression.Expression;
import com.drfa.engine.expression.ExpressionFactory;

public class ValueComparator {
    private String columnType;
    private String columnExpression;


    public ValueComparator(String columnType, String columnExpression) {
        this.columnType = columnType;
        this.columnExpression = columnExpression;
    }


    public boolean compareValue(String baseValue, String targetValue) {
        ExpressionContext context = parseColumnExpression();
        Expression baseExpressionParser = ExpressionFactory.expression(columnType, context.getExpressionType());
        return baseExpressionParser.compareValue(baseValue, targetValue, context);
    }

    public ExpressionContext parseColumnExpression() {
        ExpressionContext expressionContext = new ExpressionContext();
        if (columnExpression != null && !"".equals(columnExpression)) {
            expressionContext.setExpressionType(columnExpression.substring(0, columnExpression.indexOf("-")));
            expressionContext.setBaseColumnExpressionType(columnExpression.substring(columnExpression.indexOf("(B-") + 3, columnExpression.indexOf("|")));
            expressionContext.setTargetColumnExpressionType(columnExpression.substring(columnExpression.indexOf("|T-") + 3, columnExpression.indexOf(")")));
            expressionContext.setRangeExpression(columnExpression.substring(columnExpression.indexOf("-(R-") + 4, columnExpression.length() - 1));
        }
        return expressionContext;
    }

}
