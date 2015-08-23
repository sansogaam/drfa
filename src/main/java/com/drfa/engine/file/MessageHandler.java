package com.drfa.engine.file;

import org.apache.log4j.Logger;

import java.util.Map;

import static com.drfa.engine.EngineConstants.*;


public class MessageHandler {
    private MessageProcessor messageProcessor;
    private static Logger LOG = Logger.getLogger(MessageHandler.class);
    private int matchedRecords = 0;
    private BreakEvent breakEvent;
    private String queueName = "queue://BREAK_MESSAGE";
    
    public MessageHandler(BreakEvent breakEvent, MessageProcessor messageProcessor){
        this.breakEvent = breakEvent;
        this.messageProcessor = messageProcessor;
    }

    public boolean handleMessage(String message) throws Exception {
        String messageWithoutProcessId = extractMessageWithoutProcessId(message);
        String messageProcessId = extractProcessIdFromMessage(message);
        if ("Exit".equalsIgnoreCase(messageWithoutProcessId)) {
            return processExitMessage(message);
        }else if(messageWithoutProcessId.startsWith("SUMMARY:")){
            return processSummaryMessage(message);
        } else{
            matchedRecords ++;
            messageProcessor.processMessage(breakEvent,messageWithoutProcessId, messageProcessId);
            return true;
        }
    }

    private String extractMessageWithoutProcessId(String message) {
        return message.substring(message.indexOf("-")+1, message.length());
    }

    private String extractProcessIdFromMessage(String message) {
        return message.substring(0, message.indexOf("-")+1);
    }

    private boolean processSummaryMessage(String message) throws Exception {
        LOG.info(String.format("Received the summary message %s", message));
        String messageWithoutProcessId=extractMessageWithoutProcessId(message);
        String processIdMessage = extractProcessIdFromMessage(message);
        String threadName = messageWithoutProcessId.substring(messageWithoutProcessId.indexOf(":")+1, messageWithoutProcessId.lastIndexOf(":"));
        LOG.info(String.format("Processing the thread name %s", threadName));
        if(BASE_THREAD_NAME.equalsIgnoreCase(threadName)){
            String numberOfRecords = message.substring(message.lastIndexOf(":")+1, message.length());
            breakEvent.publisher(processIdMessage+"BASE_TOTAL_RECORDS-" + numberOfRecords, queueName);
        }else if(TARGET_THREAD_NAME.equalsIgnoreCase(threadName)){
            String numberOfRecords = message.substring(message.lastIndexOf(":")+1, message.length());
            breakEvent.publisher(processIdMessage+"TARGET_TOTAL_RECORDS-" + numberOfRecords, queueName);
        }
        return true;
    }

    private boolean processExitMessage(String message) throws Exception {
        LOG.info("Exit message recieved: " + message);
        String processIdMessage = extractProcessIdFromMessage(message);
        breakEvent.publisher(processIdMessage+"MATCHED_RECORDS-" + matchedRecords, queueName);
        return false;
    }

    public void publishOneSidedBreak(final Map<String, String> storageMap, String processId) throws Exception {
        int baseOneSidedBreak = 0;
        int targetOneSidedBreak = 0;
        for(String key: storageMap.keySet()) {
            String value = storageMap.get(key);
            if(key.startsWith(BASE_THREAD_NAME)){
                messageProcessor.processOneSidedMessage(breakEvent,processId+"ONE-SIDED-BASE", value);
                baseOneSidedBreak++;
            }else if(key.startsWith(TARGET_THREAD_NAME)){
                messageProcessor.processOneSidedMessage(breakEvent,processId+"ONE-SIDED-TARGET",value);
                targetOneSidedBreak++;
            }
        }
        breakEvent.publisher(processId+"BASE_ONE_SIDED_BREAK-"+ baseOneSidedBreak, queueName);
        breakEvent.publisher(processId+"TARGET_ONE_SIDED_BREAK-"+ targetOneSidedBreak, queueName);
    }
}
