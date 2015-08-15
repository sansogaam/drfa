package com.drfa.report;

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
    

    public ReportEnricher(BreakReport report){
        this.report = report;    
    }
    
    @Override
    public void enrich(String message) {
        if(message.startsWith("MATCHED_RECORDS")){
            
        }else if(message.startsWith("BASE_TOTAL_RECORDS")){
            
        }else if(message.startsWith("TARGET_TOTAL_RECORDS")){
            
        }else if(message.startsWith("BASE_ONE_SIDED_BREAK")){
            
        }else if(message.startsWith("TARGET_ONE_SIDED_BREAK")){
            
        }else if(message.startsWith("ONE-SIDED-BASE")){
            
        }else if(message.startsWith("ONE-SIDED-TARGET")){
            
        }else {
            enrichDetailMessageReport(message);
        }
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

}
