package com.drfa.engine.meta;

/**
 * Created by Sanjiv on 7/4/2015.
 */
public class ColumnAttribute {
    
    public String columnName;

    public String columnType;

    public String columnMatching;

    public String columnRule;

    public ColumnAttribute(){
    }
    
    public ColumnAttribute(String columnName, String columnType, String columnMatching, String columnRule) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnMatching = columnMatching;
        this.columnRule = columnRule;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public String getColumnMatching() {
        return columnMatching;
    }

    public String getColumnRule() {
        return columnRule;
    }

    @Override
    public String toString() {
        return "ColumnAttribute{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnMatching='" + columnMatching + '\'' +
                ", columnRule='" + columnRule + '\'' +
                '}';
    }
}
