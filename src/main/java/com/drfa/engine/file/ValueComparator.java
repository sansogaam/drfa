package com.drfa.engine.file;

import java.util.regex.Pattern;

/**
 * Created by Sanjiv on 7/10/2015.
 */

public class ValueComparator {
    private String columnType;
    private String columnExpression;
    private String baseColumnExpressionType;
    private String baseColumnExpressionValue;
    private String targetColumnExpressionType;
    private String targetColumnExpressionValue;

    public ValueComparator(String columnType, String columnExpression){
        this.columnType = columnType;
        this.columnExpression = columnExpression;
    }
    
    public void parseColumnExpression(){
        String baseExpression = null;
        String targetExpression = null;
        if(this.columnExpression != null) {
            if (this.columnExpression.indexOf("|") != -1) {
                String[] splitExpression = this.columnExpression.split(Pattern.quote("|"));
                baseExpression = splitExpression[0];
                targetExpression = splitExpression[1];
                this.baseColumnExpressionType = baseExpression.substring(baseExpression.indexOf("-") + 1, baseExpression.lastIndexOf("-"));
                this.baseColumnExpressionValue = baseExpression.substring(baseExpression.lastIndexOf("-")+1, baseExpression.length());
                this.targetColumnExpressionType = targetExpression.substring(targetExpression.indexOf("-") + 1, targetExpression.lastIndexOf("-"));
                this.targetColumnExpressionValue= targetExpression.substring(targetExpression.lastIndexOf("-")+1, targetExpression.length());
            } else {
                this.baseColumnExpressionType = columnExpression.substring(0, columnExpression.indexOf("-"));
                this.baseColumnExpressionValue = columnExpression.substring(columnExpression.indexOf("-")+1, columnExpression.length());
                this.targetColumnExpressionType = this.baseColumnExpressionType;
                this.targetColumnExpressionValue= this.baseColumnExpressionValue;
            }
        }
    }

    public boolean compareValue(String baseValue, String targetValue){
        return false;
    }

    public String getBaseColumnExpressionType() {
        return baseColumnExpressionType;
    }


    public String getTargetColumnExpressionType() {
        return targetColumnExpressionType;
    }

    public String getBaseColumnExpressionValue() {
        return baseColumnExpressionValue;
    }

    public String getTargetColumnExpressionValue() {
        return targetColumnExpressionValue;
    }
}
