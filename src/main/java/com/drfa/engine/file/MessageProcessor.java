package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drfa.engine.EngineConstants.MATCHED;
import static com.drfa.engine.EngineConstants.NOT_MATCHED;

public class MessageProcessor {

    private static Logger LOG = Logger.getLogger(MessageProcessor.class);

    private Answer answer;

    public MessageProcessor(Answer answer) {
        this.answer = answer;
    }

    public String processMessage(String message) {
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> splittedMessage = messageSplitter.splitMessage();
        Map<String, List<String>> mapOfRowBreaks = decorateMessageWithBreak(splittedMessage, answer.quote(), answer.getColumnAttribute());
        if (!mapOfRowBreaks.isEmpty()) {
            String breakValue = covertCompareResultIntoString(mapOfRowBreaks);
            LOG.info(String.format("Converted Break Value %s", breakValue));
            return breakValue;
        }
        return null;
    }

    private Map<String, List<String>> decorateMessageWithBreak(List<String> lines, String quote, List<ColumnAttribute> columnAttributes) {
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

    private String covertCompareResultIntoString(Map<String, List<String>> mapOfRowBreaks) {
        StringBuffer sb = new StringBuffer();
        for (String columnName : mapOfRowBreaks.keySet()) {
            List<String> columnValues = mapOfRowBreaks.get(columnName);
            sb.append(columnName).append("~");
            for (String columnValue : columnValues) {
                sb.append(columnValue).append("#");
            }
            sb.append("$");
        }
        return sb.toString();
    }
}
