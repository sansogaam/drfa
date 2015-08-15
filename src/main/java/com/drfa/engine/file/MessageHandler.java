package com.drfa.engine.file;

import org.apache.log4j.Logger;

import java.util.Map;

import static com.drfa.engine.EngineConstants.*;

/**
 * Created by Sanjiv on 2/19/2015.
 */
public class MessageHandler {
    MessageProcessor messageProcessor;
    static Logger LOG = Logger.getLogger(MessageHandler.class);
    int matchedRecords = 0;
    BreakEvent breakEvent;
    String queueName = "queue://BREAK_MESSAGE";
    
    public MessageHandler(BreakEvent breakEvent, MessageProcessor messageProcessor){
        this.breakEvent = breakEvent;
        this.messageProcessor = messageProcessor;
    }

    public boolean handleMessage(String message) throws Exception {
        if ("Exit".equalsIgnoreCase(message)) {
            return processExitMessage(message);
        }else if(message.startsWith("SUMMARY:")){
            return processSummaryMessage(message);
        } else{
            matchedRecords ++;
            messageProcessor.processMessage(breakEvent,message);
            return true;
        }
    }

    private boolean processSummaryMessage(String message) throws Exception {
        LOG.info(String.format("Received the summary message %s", message));
        String threadName = message.substring(message.indexOf(":")+1, message.lastIndexOf(":"));
        LOG.info(String.format("Processing the thread name %s", threadName));
        if(BASE_THREAD_NAME.equalsIgnoreCase(threadName)){
            String numberOfRecords = message.substring(message.lastIndexOf(":")+1, message.length());
            breakEvent.publisher("BASE_TOTAL_RECORDS-" + numberOfRecords, queueName);
        }else if(TARGET_THREAD_NAME.equalsIgnoreCase(threadName)){
            String numberOfRecords = message.substring(message.lastIndexOf(":")+1, message.length());
            breakEvent.publisher("TARGET_TOTAL_RECORDS-" + numberOfRecords, queueName);
        }
        return true;
    }

    private boolean processExitMessage(String message) throws Exception {
        LOG.info("Exit message recieved: " + message);
        breakEvent.publisher("MATCHED_RECORDS-" + matchedRecords, queueName);
        return false;
    }

    public void enrichBreakReportWithOneSidedBreak(final Map<String, String> storageMap) throws Exception {
        int baseOneSidedBreak = 0;
        int targetOneSidedBreak = 0;
        for(String key: storageMap.keySet()) {
            String value = storageMap.get(key);
            if(key.startsWith(BASE_THREAD_NAME)){
                messageProcessor.processOneSidedMessage(breakEvent,"ONE-SIDED-BASE", value);
                baseOneSidedBreak++;
            }else if(key.startsWith(TARGET_THREAD_NAME)){
                messageProcessor.processOneSidedMessage(breakEvent,"ONE-SIDED-TARGET",value);
                targetOneSidedBreak++;
            }
        }
        breakEvent.publisher("BASE_ONE_SIDED_BREAK-"+ baseOneSidedBreak, queueName);
        breakEvent.publisher("TARGET_ONE_SIDED_BREAK-"+ targetOneSidedBreak, queueName);
    }
}
