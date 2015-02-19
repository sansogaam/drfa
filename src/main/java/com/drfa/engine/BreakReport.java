package com.drfa.engine;

import java.util.Map;

/**
 * Created by Sanjiv on 2/19/2015.
 */
public class BreakReport {
    private int baseTotalRecords;
    private int targetTotalRecords;
    private int matchedWithNumberOfKeys;
    private int baseOneSidedBreaks;
    private int targetOneSidedBreaks;
    private Map<String,Integer> columnBreaksCount;



    public int getBaseTotalRecords() {
        return baseTotalRecords;
    }

    public void setBaseTotalRecords(int baseTotalRecords) {
        this.baseTotalRecords = baseTotalRecords;
    }

    public int getTargetTotalRecords() {
        return targetTotalRecords;
    }

    public void setTargetTotalRecords(int targetTotalRecords) {
        this.targetTotalRecords = targetTotalRecords;
    }

    public int getMatchedWithNumberOfKeys() {
        return matchedWithNumberOfKeys;
    }

    public void setMatchedWithNumberOfKeys(int matchedWithNumberOfKeys) {
        this.matchedWithNumberOfKeys = matchedWithNumberOfKeys;
    }

    public int getBaseOneSidedBreaks() {
        return baseOneSidedBreaks;
    }

    public void setBaseOneSidedBreaks(int baseOneSidedBreaks) {
        this.baseOneSidedBreaks = baseOneSidedBreaks;
    }

    public int getTargetOneSidedBreaks() {
        return targetOneSidedBreaks;
    }

    public void setTargetOneSidedBreaks(int targetOneSidedBreaks) {
        this.targetOneSidedBreaks = targetOneSidedBreaks;
    }

    public Map<String, Integer> getColumnBreaksCount() {
        return columnBreaksCount;
    }

    public void setColumnBreaksCount(Map<String, Integer> columnBreaksCount) {
        this.columnBreaksCount = columnBreaksCount;
    }
    @Override
    public String toString() {
        return "BreakReport{" +
                "baseTotalRecords=" + baseTotalRecords +
                ", targetTotalRecords=" + targetTotalRecords +
                ", matchedWithNumberOfKeys=" + matchedWithNumberOfKeys +
                ", baseOneSidedBreaks=" + baseOneSidedBreaks +
                ", targetOneSidedBreaks=" + targetOneSidedBreaks +
                '}';
    }
}
