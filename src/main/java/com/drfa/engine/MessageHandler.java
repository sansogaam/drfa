package com.drfa.engine;

import java.util.Map;

/**
 * Created by Sanjiv on 2/19/2015.
 */
public class MessageHandler {
    BreakReport report;
    MessageProcessor messageProcessor;

    public MessageHandler(BreakReport report, MessageProcessor messageProcessor){
        this.report = report;
        this.messageProcessor = messageProcessor;
    }

    public boolean handleMessage(String message){
        if ("Exit".equalsIgnoreCase(message)) {
            System.out.println("Exit message recieved.." + message);
            System.out.println(report);
            return false;
        }else if(message.startsWith("SUMMARY:")){
            String threadName = message.substring(message.indexOf(":")+1, message.lastIndexOf(":"));
            System.out.println("Thread Name..." + threadName);
            if("BASE".equalsIgnoreCase(threadName)){
                String numberOfRecords = message.substring(message.lastIndexOf(":")+1, message.length());
                report.setBaseTotalRecords(new Integer(numberOfRecords));
            }else if("TARGET".equalsIgnoreCase(threadName)){
                String numberOfRecords = message.substring(message.lastIndexOf(":")+1, message.length());
                report.setTargetTotalRecords(new Integer(numberOfRecords));
            }
            return true;
        } else{
            System.out.println(String.format("Take the message %s from the queue", message));
            messageProcessor.processMessage(message);
            return true;
        }
    }
    public void enrichBreakReport(final Map<String, String> storageMap){
        int baseOneSidedBreak = 0;
        int targetOneSidedBreak = 0;
        for(String key: storageMap.keySet()) {
            System.out.println("Key:" + key);
            if(key.startsWith("BASE")){
                baseOneSidedBreak++;
            }else if(key.startsWith("TARGET")){
                targetOneSidedBreak++;
            }
        }
        report.setBaseOneSidedBreaks(baseOneSidedBreak);
        report.setTargetOneSidedBreaks(targetOneSidedBreak);
    }
}
