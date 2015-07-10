package com.drfa.engine.file;

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
    
    public boolean compareValue(String baseValue, String targetValue){
        return false;
    }
        
}
