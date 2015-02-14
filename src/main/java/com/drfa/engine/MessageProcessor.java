package com.drfa.engine;

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
        List<String> columnNames = context.getColumnNames();
        MessageDecorator messageDecorator = new MessageDecorator(columnNames, splittedMessage);
        if(!messageDecorator.decorateMessageWithBreak().isEmpty()) {
            mapOfBreaks.put(rowCount, messageDecorator.decorateMessageWithBreak());
        }
        rowCount++;
    }

    public Map<Integer, Map<String, List<String>>> getMapOfBreaks(){
        return this.mapOfBreaks;
    }

}
