package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.messaging.MessagePublisher;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;


public class MessageHandler {
    private static Logger LOG = Logger.getLogger(MessageHandler.class);
    private MessageProcessor messageProcessor;
    private int matchedRecords = 0;
    private MessagePublisher messagePublisher;
    private Answer answer;

    public MessageHandler(MessageProcessor messageProcessor, MessagePublisher messagePublisher, Answer answer) {
        this.messageProcessor = messageProcessor;
        this.messagePublisher = messagePublisher;
        this.answer = answer;
    }

    public boolean handleMessage(String message) {
        String messageWithoutProcessId = extractMessageWithoutProcessId(message);
        String messageProcessId = extractProcessIdFromMessage(message);
        if ("Exit".equalsIgnoreCase(messageWithoutProcessId)) {
            return processExitMessage(message);
        } else if (messageWithoutProcessId.startsWith("SUMMARY:")) {
            return processSummaryMessage(message);
        } else {
            matchedRecords++;
            Map<String, List<String>> mapOfRowBreaks = messageProcessor.processMessage(messageWithoutProcessId);
            //messagePublisher.publishResult(messageProcessId, mapOfRowBreaks);
            messagePublisher.publishKafkaResult(messageProcessId, mapOfRowBreaks);
            return true;
        }
    }

    private String extractMessageWithoutProcessId(String message) {
        return message.substring(message.indexOf("-") + 1, message.length());
    }

    private String extractProcessIdFromMessage(String message) {
        return message.substring(0, message.indexOf("-") + 1);
    }

    private boolean processSummaryMessage(String message) {
        LOG.info(String.format("Received the summary message %s", message));
        String messageWithoutProcessId = extractMessageWithoutProcessId(message);
        String processIdMessage = extractProcessIdFromMessage(message);
        String threadName = messageWithoutProcessId.substring(messageWithoutProcessId.indexOf(":") + 1, messageWithoutProcessId.lastIndexOf(":"));
        LOG.info(String.format("Processing the thread name %s", threadName));
        if (BASE_THREAD_NAME.equalsIgnoreCase(threadName)) {
            String numberOfRecords = message.substring(message.lastIndexOf(":") + 1, message.length());
            messagePublisher.publishResult(processIdMessage, "BASE_TOTAL_RECORDS", numberOfRecords);
        } else if (TARGET_THREAD_NAME.equalsIgnoreCase(threadName)) {
            String numberOfRecords = message.substring(message.lastIndexOf(":") + 1, message.length());
            messagePublisher.publishResult(processIdMessage, "TARGET_TOTAL_RECORDS", numberOfRecords);
        }
        return true;
    }

    private boolean processExitMessage(String message) {
        LOG.info("Exit message recieved: " + message);
        String processIdMessage = extractProcessIdFromMessage(message);
        messagePublisher.publishResult(processIdMessage, "MATCHED_RECORDS", matchedRecords + "");
        return false;
    }

    public void publishOneSidedBreak(final Map<String, String> storageMap, String processId) {
        int baseOneSidedBreak = 0;
        int targetOneSidedBreak = 0;
        for (String key : storageMap.keySet()) {
            String value = storageMap.get(key);
            if (key.startsWith(BASE_THREAD_NAME)) {
                publishOneSidedMessage(processId, value, messagePublisher, "ONE-SIDED-BASE");
                baseOneSidedBreak++;
            } else if (key.startsWith(TARGET_THREAD_NAME)) {
                publishOneSidedMessage(processId, value, messagePublisher, "ONE-SIDED-TARGET");
                targetOneSidedBreak++;
            }
        }
        messagePublisher.publishResult(processId, "BASE_ONE_SIDED_BREAK", baseOneSidedBreak + "");
        messagePublisher.publishResult(processId, "TARGET_ONE_SIDED_BREAK", targetOneSidedBreak + "");
    }

    private void publishOneSidedMessage(String processId, String message, MessagePublisher messagePublisher, String fileType) {
        Map<String, String> mapOfOneSidedBreaks = decorateMessageWithOneSideBreak(message);
        messagePublisher.publishOneSideBreak(processId, fileType, mapOfOneSidedBreaks);
    }

    private Map<String, String> decorateMessageWithOneSideBreak(String message) {
        Map<String, String> mapOfOneSideBreaks = new HashMap<String, String>();
        String[] lineSplit = message.split(answer.quote());
        int valueCounter = 0;
        for (ColumnAttribute columnAttribute : answer.getColumnAttribute()) {
            String columnName = columnAttribute.getColumnName();
            mapOfOneSideBreaks.put(columnName, lineSplit[valueCounter]);
            valueCounter++;
        }
        return mapOfOneSideBreaks;
    }
}
