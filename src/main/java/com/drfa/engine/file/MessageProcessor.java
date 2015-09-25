package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drfa.engine.EngineConstants.MATCHED;
import static com.drfa.engine.EngineConstants.NOT_MATCHED;

public class MessageProcessor {

    private Answer answer;

    public MessageProcessor(Answer answer) {
        this.answer = answer;
    }

    public Map<String, List<String>> processMessage(String message) {
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> splittedMessage = messageSplitter.splitMessage();
        Map<String, List<String>> mapOfRowBreak = new HashMap<String, List<String>>();
        String firstLine = splittedMessage.get(0);
        String secondLine = splittedMessage.get(1);
        if (!firstLine.equalsIgnoreCase(secondLine)) {
            String[] firstLineSplit = firstLine.split(answer.quote());
            String[] secondLineSplit = secondLine.split(answer.quote());
            int valueCounter = 0;
            for (ColumnAttribute columnAttribute : answer.getColumnAttribute()) {
                String firstLineColumnValue = firstLineSplit[valueCounter];
                String secondLineColumnValue = secondLineSplit[valueCounter];
                String compareValue = NOT_MATCHED;
                ValueComparator valueComparator = new ValueComparator(columnAttribute.getColumnType(), columnAttribute.getColumnRule());
                if (valueComparator.compareValue(firstLineColumnValue, secondLineColumnValue)) {
                    compareValue = MATCHED;
                }

                List<String> columnValues = new ArrayList<String>();
                columnValues.add(firstLineSplit[valueCounter]);
                columnValues.add(secondLineSplit[valueCounter]);
                columnValues.add(compareValue);
                mapOfRowBreak.put(columnAttribute.getColumnName(), columnValues);
                valueCounter++;
            }
        }
        return mapOfRowBreak;
    }
}
