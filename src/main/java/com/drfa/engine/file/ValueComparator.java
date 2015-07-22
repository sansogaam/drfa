package com.drfa.engine.file;

import com.drfa.engine.expression.Expression;
import com.drfa.engine.expression.ExpressionFactory;

import java.util.regex.Pattern;

/**
 * Created by Sanjiv on 7/10/2015.
 */

public class ValueComparator {
    private String columnType;
    private String columnExpression;


    public ValueComparator(String columnType, String columnExpression){
        this.columnType = columnType;
        this.columnExpression = columnExpression;
    }
    
    public ExpressionContext parseColumnExpression(){
        ExpressionContext expressionContext = new ExpressionContext();
        if(columnExpression != null && !"".equals(columnExpression)) {
                expressionContext.setExpressionType(columnExpression.substring(0, columnExpression.indexOf("-")));
                expressionContext.setBaseColumnExpressionType(columnExpression.substring(columnExpression.indexOf("(B-") + 3, columnExpression.indexOf("|")));
                expressionContext.setTargetColumnExpressionType(columnExpression.substring(columnExpression.indexOf("|T-") + 3, columnExpression.indexOf(")")));
                expressionContext.setRangeExpression(columnExpression.substring(columnExpression.indexOf("-(R-") + 4, columnExpression.length()-1));
        }
        return expressionContext;
    }

    public boolean compareValue(String baseValue, String targetValue, ExpressionContext expressionContext){
        Expression baseExpressionParser = ExpressionFactory.expression(columnType,expressionContext.getExpressionType());
        return baseExpressionParser.compareValue(baseValue, targetValue,expressionContext);
    }

}
