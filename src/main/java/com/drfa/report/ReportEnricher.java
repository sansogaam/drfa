package com.drfa.report;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Sanjiv on 8/15/2015.
 */

public class ReportEnricher implements Enricher {
    public BreakReport report;
    static Logger LOG = Logger.getLogger(ReportEnricher.class);


    public ReportEnricher(BreakReport report){
        this.report = report;    
    }
    
    @Override
    public void enrich(String message) {
        if(message.startsWith("MATCHED_RECORDS")){
            enrichMatchedNumberOfRecords(message);
        }else if(message.startsWith("BASE_TOTAL_RECORDS")){
            enrichBaseTotalRecords(message);
        }else if(message.startsWith("TARGET_TOTAL_RECORDS")){
            enrichTargetTotalRecords(message);
        }else if(message.startsWith("BASE_ONE_SIDED_BREAK")){
            enrichBaseOneSidedBreaksRecords(message);
        }else if(message.startsWith("TARGET_ONE_SIDED_BREAK")){
            enrichTargetOneSideBreakRecords(message);
        }else if(message.startsWith("ONE-SIDED-BASE")){
            enrichOneSideBreakRecords(message, "BASE");
        }else if(message.startsWith("ONE-SIDED-TARGET")){
            enrichOneSideBreakRecords(message, "TARGET");
        }else {
            enrichDetailMessageReport(message);
        }
    }

    private void enrichOneSideBreakRecords(String message, String type) {
        //C3~Exist3$C4~Exist4$C1~Exist1$C2~Exist2$
        String breakMessage= message.substring(message.lastIndexOf("-"), message.length());
        String splitMessages[] = breakMessage.split(Pattern.quote("$"));
        Map<String, String> mapOfColumnBreaks = new HashMap<String, String>();
        for(String splitMessage: splitMessages){
            String columnSplitMessage[] = splitMessage.split(Pattern.quote("~"));
            mapOfColumnBreaks.put(columnSplitMessage[0], columnSplitMessage[1]);
        }
        int rowCount;
        Map<Integer, Map<String, String>> mapOfOneSidedRowBreaks = report.getBaseOneSidedBreaksCollection();
        if(mapOfOneSidedRowBreaks == null || mapOfOneSidedRowBreaks.isEmpty()){
            rowCount=1;
            mapOfOneSidedRowBreaks = new HashMap<Integer, Map<String, String>>();
            mapOfOneSidedRowBreaks.put(new Integer(rowCount), mapOfColumnBreaks);
        }else{
            rowCount = mapOfColumnBreaks.size()+1;
            mapOfOneSidedRowBreaks.put(new Integer(rowCount), mapOfColumnBreaks);
        }
        if("BASE".equalsIgnoreCase(type)){
            report.setBaseOneSidedBreaksCollection(mapOfOneSidedRowBreaks); 
        }else{
            report.setTargetOneSidedBreaksCollection(mapOfOneSidedRowBreaks);
        }
    }

    private void enrichTargetOneSideBreakRecords(String message) {
        String totalRecords = message.substring(message.lastIndexOf("-")+1, message.length());
        LOG.info(String.format("Total number of matched records as per key %s", totalRecords));
        report.setTargetOneSidedBreaks(new Integer(totalRecords));
    }

    private void enrichBaseOneSidedBreaksRecords(String message) {
        String totalRecords = message.substring(message.lastIndexOf("-")+1, message.length());
        LOG.info(String.format("Total number of matched records as per key %s", totalRecords));
        report.setBaseOneSidedBreaks(new Integer(totalRecords));
    }

    private void enrichTargetTotalRecords(String message) {
        String totalRecords = message.substring(message.lastIndexOf("-")+1, message.length());
        LOG.info(String.format("Total number of matched records as per key %s", totalRecords));
        report.setTargetTotalRecords(new Integer(totalRecords));
    }

    private void enrichBaseTotalRecords(String message) {
        String totalRecords = message.substring(message.lastIndexOf("-")+1, message.length());
        LOG.info(String.format("Total number of matched records as per key %s", totalRecords));
        report.setBaseTotalRecords(new Integer(totalRecords));
    }

    private void enrichMatchedNumberOfRecords(String message) {
        String totalMatchedRecords = message.substring(message.lastIndexOf("-")+1, message.length());
        LOG.info(String.format("Total number of matched records as per key %s", totalMatchedRecords));
        report.setMatchedWithNumberOfKeys(new Integer(totalMatchedRecords));
    }

    private void enrichDetailMessageReport(String message) {
        Map<String, List<String>> mapOfBreaks = new HashMap<String, List<String>>();
        String splitMessages[] = message.split(Pattern.quote("$"));
        for(String splitMessage : splitMessages){
            String columnSplitMessage[] = splitMessage.split(Pattern.quote("~"));
            String columnName = columnSplitMessage[0];
            String columnValues[] = columnSplitMessage[1].split(Pattern.quote("#"));
            List<String> columnResults = new ArrayList<String>();
            for(String columnValue : columnValues){
                columnResults.add(columnValue);
            }
            mapOfBreaks.put(columnName,columnResults);
        }
        System.out.println(mapOfBreaks);
        int rowCount;
        Map<Integer, Map<String, List<String>>> mapOfRowBreaks = report.getMapOfBreaks();
        if(mapOfRowBreaks == null || mapOfRowBreaks.isEmpty()){
            rowCount=1;
            mapOfRowBreaks = new HashMap<Integer, Map<String, List<String>>>();
            mapOfRowBreaks.put(new Integer(rowCount), mapOfBreaks);
        }else{
            rowCount = mapOfBreaks.size()+1;
            mapOfRowBreaks.put(new Integer(rowCount), mapOfBreaks);
        }
        report.setMapOfBreaks(mapOfRowBreaks);
    }

    public void enrichBreakReportWithColumnDetails(){
        Map<Integer, Map<String, List<String>>> mapOfBreaks = report.getMapOfBreaks();
        Map<String, List<Integer>> mapOfColumnBreaks = new HashMap<String, List<Integer>>();
        for(Integer i : mapOfBreaks.keySet()){
            Map<String, List<String>> columnBreakes = mapOfBreaks.get(i);
            for(String columnName: columnBreakes.keySet()){
                List<String> values = columnBreakes.get(columnName);
                List<Integer> listNumberOfBreaks = mapOfColumnBreaks.get(columnName);
                if(values.get(2).equalsIgnoreCase("NOT_MATCHED")){
                    if(listNumberOfBreaks == null) {
                        listNumberOfBreaks = new ArrayList<Integer>();
                        listNumberOfBreaks.add(0,new Integer(1));
                        listNumberOfBreaks.add(1,new Integer(0));
                    }else{
                        int existingValue = listNumberOfBreaks.get(0) +1;
                        listNumberOfBreaks.remove(0);
                        listNumberOfBreaks.add(0, existingValue);
                    }
                }else if(values.get(2).equalsIgnoreCase("MATCHED")){
                    if(listNumberOfBreaks == null) {
                        listNumberOfBreaks = new ArrayList<Integer>();
                        listNumberOfBreaks.add(0,new Integer(0));
                        listNumberOfBreaks.add(1,new Integer(1));
                    }else{
                        int existingValue = listNumberOfBreaks.get(1)+1;
                        listNumberOfBreaks.remove(1);
                        listNumberOfBreaks.add(1, existingValue);
                    }
                }
                mapOfColumnBreaks.put(columnName,listNumberOfBreaks);
            }
        }
        report.setColumnBreaksCount(mapOfColumnBreaks);
    }
}
