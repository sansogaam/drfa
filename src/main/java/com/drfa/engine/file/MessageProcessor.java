package com.drfa.engine.file;

import com.drfa.cli.Answer;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MessageProcessor {

    private static Logger LOG = Logger.getLogger(MessageProcessor.class);

    private Answer answer;

    public MessageProcessor(Answer answer) {
        this.answer = answer;
    }

    public String processMessage(String message) {
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> splittedMessage = messageSplitter.splitMessage();
        MessageDecorator messageDecorator = new MessageDecorator(splittedMessage, answer);
        Map<String, List<String>> mapOfRowBreaks = messageDecorator.decorateMessageWithBreak();
        if (!mapOfRowBreaks.isEmpty()) {
            String breakValue = covertCompareResultIntoString(mapOfRowBreaks);
            LOG.info(String.format("Converted Break Value %s", breakValue));
            return breakValue;
        }

        return null;
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
