package com.drfa.engine.file;

/**
 * Created by Sanjiv on 7/22/2015.
 */
public class ExpressionContext {
    private String baseColumnExpressionType;
    private String targetColumnExpressionType;
    private String rangeExpression;
    private String expressionType;

    public ExpressionContext(){
        
        
    }
    public ExpressionContext(String expressionType, String baseColumnExpressionType, String targetColumnExpressionType, String rangeExpression) {
        this.baseColumnExpressionType = baseColumnExpressionType;
        this.targetColumnExpressionType = targetColumnExpressionType;
        this.rangeExpression = rangeExpression;
        this.expressionType = expressionType;
    }

    public String getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(String expressionType) {
        this.expressionType = expressionType;
    }

    public String getBaseColumnExpressionType() {
        return baseColumnExpressionType;
    }

    public void setBaseColumnExpressionType(String baseColumnExpressionType) {
        this.baseColumnExpressionType = baseColumnExpressionType;
    }


    public String getTargetColumnExpressionType() {
        return targetColumnExpressionType;
    }

    public void setTargetColumnExpressionType(String targetColumnExpressionType) {
        this.targetColumnExpressionType = targetColumnExpressionType;
    }


    public String getRangeExpression() {
        return rangeExpression;
    }

    public void setRangeExpression(String rangeExpression) {
        this.rangeExpression = rangeExpression;
    }
}
