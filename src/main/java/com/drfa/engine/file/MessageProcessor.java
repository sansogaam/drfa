package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.messaging.MessagePublisher;
import com.drfa.util.DrfaProperties;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MessageProcessor {

    private static Logger LOG = Logger.getLogger(MessageProcessor.class);

    private Answer answer;

    public MessageProcessor(Answer answer) {
        this.answer = answer;
    }

    public void processMessage(MessagePublisher messagePublisher, String message, String processId) {
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> splittedMessage = messageSplitter.splitMessage();
        MessageDecorator messageDecorator = new MessageDecorator(splittedMessage, answer);
        Map<String, List<String>> mapOfRowBreaks = messageDecorator.decorateMessageWithBreak();
        if (!mapOfRowBreaks.isEmpty()) {
            String breakValue = covertCompareResultIntoString(mapOfRowBreaks);
            LOG.info(String.format("Converted Break Value %s", breakValue));
            try {
                messagePublisher.publishResult(processId, breakValue, DrfaProperties.BREAK_MESSAGE_QUEUE);
            } catch (Exception e) {
                LOG.info(String.format("Exception processing the message %s", breakValue));
                e.printStackTrace();
            }
        }
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

    public void processOneSidedMessage(MessagePublisher messagePublisher, String processId, String fileType, String message) {
        MessageDecorator messageDecorator = new MessageDecorator(message, answer);
        Map<String, String> mapOfOneSidedBreaks = messageDecorator.decorateMessageWithOneSideBreak();
        String breakValue = convertOneSidedBreakResultIntoString(mapOfOneSidedBreaks);
        try {
            LOG.info(String.format("ONE_SIDED_BREAK_FOR_%s: %s", fileType, breakValue));
            messagePublisher.publishResult(processId, fileType + "-" + breakValue, DrfaProperties.BREAK_MESSAGE_QUEUE);
        } catch (Exception e) {
            LOG.info(String.format("Exception processing the message %s", breakValue));
            e.printStackTrace();
        }
    }

    private String convertOneSidedBreakResultIntoString(Map<String, String> mapOfOneSidedBreaks) {
        StringBuffer sb = new StringBuffer();
        for (String columnName : mapOfOneSidedBreaks.keySet()) {
            String columnValue = mapOfOneSidedBreaks.get(columnName);
            sb.append(columnName).append("~").append(columnValue).append("$");
        }
        return sb.toString();
    }
}
