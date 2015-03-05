package com.drfa.engine.report;

import java.util.LinkedHashMap;
import java.util.List;
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
    private Map<String,List<Integer>> columnBreaksCount;
    private Map<Integer,Map<String, String>> baseOneSidedBreaksCollection;
    private Map<Integer,Map<String, String>> targetOneSidedBreaksCollection;

    

    private Map<Integer, Map<String, List<String>>> mapOfBreaks = new LinkedHashMap<Integer, Map<String, List<String>>>();


    public Map<Integer, Map<String, String>> getBaseOneSidedBreaksCollection() {
        return baseOneSidedBreaksCollection;
    }

    public void setBaseOneSidedBreaksCollection(Map<Integer, Map<String, String>> baseOneSidedBreaksCollection) {
        this.baseOneSidedBreaksCollection = baseOneSidedBreaksCollection;
    }

    public Map<Integer, Map<String, String>> getTargetOneSidedBreaksCollection() {
        return targetOneSidedBreaksCollection;
    }

    public void setTargetOneSidedBreaksCollection(Map<Integer, Map<String, String>> targetOneSidedBreaksCollection) {
        this.targetOneSidedBreaksCollection = targetOneSidedBreaksCollection;
    }

    public Map<Integer, Map<String, List<String>>> getMapOfBreaks() {
        return mapOfBreaks;
    }

    public void setMapOfBreaks(Map<Integer, Map<String, List<String>>> mapOfBreaks) {
        this.mapOfBreaks = mapOfBreaks;
    }

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

    public Map<String, List<Integer>> getColumnBreaksCount() {
        return columnBreaksCount;
    }

    public void setColumnBreaksCount(Map<String, List<Integer>> columnBreaksCount) {
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
                ", columnBreaksCount=" + columnBreaksCount +
                '}';
    }
}
