package com.drfa.engine;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by Sanjiv on 2/20/2015.
 */
public class ComparatorListener implements Callable<BreakReport> {
    ReconciliationContext context;
    private BlockingQueue queue;
    private Map<String, String> storageMap;

    public ComparatorListener(ReconciliationContext context,BlockingQueue queue,
                              Map<String, String> storageMap){
        this.context = context;
        this.queue = queue;
        this.storageMap = storageMap;
    }

    @Override
    public BreakReport call() throws Exception {
        BreakReport report = new BreakReport();
        MessageProcessor messageProcessor = new MessageProcessor(context);
        MessageHandler messageHandler = new MessageHandler(report, messageProcessor);
        System.out.println("Matching the keys between the hash-map and storing it in one common place");
        boolean continueConsumingMessage = true;
        try {
            while (continueConsumingMessage) {
                String message = (String) queue.take();
                continueConsumingMessage = messageHandler.handleMessage(message);
            }
            System.out.println(String.format("Size of the storage map %s", storageMap.size()));
            messageHandler.enrichBreakReportWithOneSidedBreak(storageMap);
            messageHandler.enrichBreakReportWithColumnDetails();
            System.out.println("Consumption is completed..." + messageProcessor.getMapOfBreaks().size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return report;
    }
}
