package com.drfa.engine;

import com.drfa.engine.meta.ColumnAttribute;

import java.util.List;
import java.util.Map;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public class ReconciliationContext {

    

    private String fileDelimiter;
    
    private List<ColumnAttribute> columnAttributes;

    private List<String> columnsToBeIgnored;

    private Map<String, String> columnTypeAttributeMapping;

    private Map<String, String> columnReconciliationRules;

    public List<String> getColumnsToBeIgnored() {
        return columnsToBeIgnored;
    }

    public void setColumnsToBeIgnored(List<String> columnsToBeIgnored) {
        this.columnsToBeIgnored = columnsToBeIgnored;
    }

    public Map<String, String> getColumnTypeAttributeMapping() {
        return columnTypeAttributeMapping;
    }

    public void setColumnTypeAttributeMapping(Map<String, String> columnTypeAttributeMapping) {
        this.columnTypeAttributeMapping = columnTypeAttributeMapping;
    }

    public Map<String, String> getColumnReconciliationRules() {
        return columnReconciliationRules;
    }


    public List<ColumnAttribute> getColumnAttributes() {
        return columnAttributes;
    }

    public void setColumnAttributes(List<ColumnAttribute> columnAttributes) {
        this.columnAttributes= columnAttributes;
    }

    public String getFileDelimiter() {
        return fileDelimiter;
    }

    public void setFileDelimiter(String fileDelimiter) {
        this.fileDelimiter = fileDelimiter;
    }
}
