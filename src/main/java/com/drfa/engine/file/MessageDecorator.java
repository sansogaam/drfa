package com.drfa.engine.file;

import com.drfa.engine.EngineConstants;

import java.util.*;
import java.util.regex.Pattern;

import static com.drfa.engine.EngineConstants.MATCHED;
import static com.drfa.engine.EngineConstants.NOT_MATCHED;

/**
 * Created by Sanjiv on 2/13/2015.
 */
public class MessageDecorator {

    List<String> columnNames;
    List<String> lines;
    private String fileDelimiter;
    private String oneSidedMessage;

    public MessageDecorator(List<String> columnNames, List<String> lines, String fileDelimiter) {
        this.columnNames = columnNames;
        this.lines = lines;
        this.fileDelimiter = fileDelimiter;
    }

    public MessageDecorator(List<String> columnNames, String oneSidedMessage, String fileDelimiter) {
        this.columnNames = columnNames;
        this.oneSidedMessage = oneSidedMessage;
        this.fileDelimiter = fileDelimiter;
    }

    public Map<String, List<String>> decorateMessageWithBreak() {
        Map<String, List<String>> mapOfRowBreak = new HashMap<String, List<String>>();
        String firstLine = lines.get(0);
        String secondLine = lines.get(1);
        if (!firstLine.equalsIgnoreCase(secondLine)) {
            String[] firstLineSplit = firstLine.split(Pattern.quote(fileDelimiter));
            String[] secondLineSplit = secondLine.split(Pattern.quote(fileDelimiter));
            int valueCounter = 0;
            for (String columnName : columnNames) {
                List<String> columnValues = new ArrayList<String>();
                String firstLineColumnValue = firstLineSplit[valueCounter];
                String secondLineColumnValue = secondLineSplit[valueCounter];
                String compareValue = NOT_MATCHED;
                if (firstLineColumnValue.equalsIgnoreCase(secondLineColumnValue)) {
                    compareValue = MATCHED;
                }
                columnValues.add(firstLineSplit[valueCounter]);
                columnValues.add(secondLineSplit[valueCounter]);
                columnValues.add(compareValue);
                mapOfRowBreak.put(columnName, columnValues);
                valueCounter++;
            }
        }
        return mapOfRowBreak;
    }
    
    public Map<String, String> decorateMessageWithOneSideBreak(){
        Map<String, String> mapOfOneSideBreaks = new HashMap<String, String>();
        String[] lineSplit = oneSidedMessage.split(Pattern.quote(fileDelimiter));
        int valueCounter=0;
        for(String columnName: columnNames){
            mapOfOneSideBreaks.put(columnName, lineSplit[valueCounter]);
            valueCounter++;
        }
        return mapOfOneSideBreaks;
    }
}
