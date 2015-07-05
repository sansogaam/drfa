package com.drfa.engine.file;

import com.drfa.engine.ReconciliationContext;
import com.drfa.engine.meta.ColumnAttribute;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MessageProcessor {

    ReconciliationContext context;
    volatile int rowCount = 1;
    Map<Integer, Map<String, List<String>>> mapOfBreaks = new LinkedHashMap<Integer, Map<String, List<String>>>();
    

    public MessageProcessor(ReconciliationContext context) {
        this.context = context;
    }

    public void processMessage(String message) {
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> splittedMessage = messageSplitter.splitMessage();
        List<ColumnAttribute> columnAttributes = context.getColumnAttributes();
        MessageDecorator messageDecorator = new MessageDecorator(columnAttributes, splittedMessage, context.getFileDelimiter());
        if(!messageDecorator.decorateMessageWithBreak().isEmpty()) {
            mapOfBreaks.put(rowCount, messageDecorator.decorateMessageWithBreak());
        }
        rowCount++;
    }

    public Map<String, String> processOneSidedMessage(String message){
        List<ColumnAttribute> columnAttributes = context.getColumnAttributes();
        MessageDecorator messageDecorator = new MessageDecorator(columnAttributes, message, context.getFileDelimiter());
        Map<String, String> mapOfOneSidedBreaks = messageDecorator.decorateMessageWithOneSideBreak();
        return mapOfOneSidedBreaks;
    }
        
    public Map<Integer, Map<String, List<String>>> getMapOfBreaks(){
        return this.mapOfBreaks;
    }

}
