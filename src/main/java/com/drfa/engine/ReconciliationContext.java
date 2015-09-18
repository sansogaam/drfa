package com.drfa.engine;

import com.drfa.engine.meta.ColumnAttribute;

import java.util.List;

public class ReconciliationContext {
    private String fileDelimiter;
    
    private List<ColumnAttribute> columnAttributes;


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
