package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drfa.engine.EngineConstants.MATCHED;
import static com.drfa.engine.EngineConstants.NOT_MATCHED;

public class MessageDecorator {

    private final List<ColumnAttribute> columnAttributes;
    private List<String> lines;
    private String quote;


    public MessageDecorator(List<String> lines, Answer answer) {
        this.lines = lines;
        this.quote = answer.quote();
        this.columnAttributes = answer.getColumnAttribute();
    }

    public Map<String, List<String>> decorateMessageWithBreak() {
        Map<String, List<String>> mapOfRowBreak = new HashMap<String, List<String>>();
        String firstLine = lines.get(0);
        String secondLine = lines.get(1);
        if (!firstLine.equalsIgnoreCase(secondLine)) {
            String[] firstLineSplit = firstLine.split(quote);
            String[] secondLineSplit = secondLine.split(quote);
            int valueCounter = 0;
            for (ColumnAttribute columnAttribute : columnAttributes) {
                String columnName = columnAttribute.getColumnName();
                List<String> columnValues = new ArrayList<String>();
                String firstLineColumnValue = firstLineSplit[valueCounter];
                String secondLineColumnValue = secondLineSplit[valueCounter];
                String compareValue = NOT_MATCHED;
                ValueComparator valueComparator = new ValueComparator(columnAttribute.getColumnType(), columnAttribute.getColumnRule());
                if (valueComparator.compareValue(firstLineColumnValue, secondLineColumnValue, valueComparator.parseColumnExpression())) {
                    compareValue = MATCHED;
                }
                //System.out.println(String.format("firstLineColumnValue %s secondLineColumnValue %s compareValue %s columnType %s columnRule %s",firstLineColumnValue, secondLineColumnValue, compareValue, columnAttribute.getColumnType(), columnAttribute.getColumnRule()));
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
