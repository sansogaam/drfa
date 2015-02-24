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

    public MessageDecorator(List<String> columnNames, List<String> lines) {
        this.columnNames = columnNames;
        this.lines = lines;
    }

    public Map<String, List<String>> decorateMessageWithBreak() {
        Map<String, List<String>> mapOfRowBreak = new HashMap<String, List<String>>();
        String firstLine = lines.get(0);
        String secondLine = lines.get(1);
        if(!firstLine.equalsIgnoreCase(secondLine)) {
            String[] firstLineSplit = firstLine.split(Pattern.quote("|"));
            String[] secondLineSplit = secondLine.split(Pattern.quote("|"));
            int valueCounter = 0;
            for (String columnName : columnNames) {
                List<String> columnValues = new ArrayList<String>();
                String firstLineColumnValue =firstLineSplit[valueCounter];
                String secondLineColumnValue = secondLineSplit[valueCounter];
                String compareValue = NOT_MATCHED;
                if(firstLineColumnValue.equalsIgnoreCase(secondLineColumnValue)){
                    compareValue = MATCHED ;
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

}
