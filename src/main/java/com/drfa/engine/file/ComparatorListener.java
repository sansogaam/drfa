package com.drfa.engine.file;

import com.drfa.engine.ReconciliationContext;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by Sanjiv on 2/20/2015.
 */
public class ComparatorListener implements Callable<Boolean> {
    ReconciliationContext context;
    private BlockingQueue queue;
    private Map<String, String> storageMap;
    static Logger LOG = Logger.getLogger(ComparatorListener.class);

    public ComparatorListener(ReconciliationContext context,BlockingQueue queue,
                              Map<String, String> storageMap){
        this.context = context;
        this.queue = queue;
        this.storageMap = storageMap;
    }

    @Override
    public Boolean call() throws Exception {
        BreakEvent breakEvent = new BreakEvent();
        MessageProcessor messageProcessor = new MessageProcessor(context);
        MessageHandler messageHandler = new MessageHandler(breakEvent, messageProcessor);
        LOG.info("Matching the keys between the hash-map and storing it in one common place");
        boolean continueConsumingMessage = true;
        String processId = null;
        try {
            while (continueConsumingMessage) {
                String message = (String) queue.take();
                LOG.info(String.format("Continue consuming the message %s", message));
                processId = message.substring(0, message.indexOf("-")+1);
                continueConsumingMessage = messageHandler.handleMessage(message);
            }
            LOG.info(String.format("Size of the storage map %s", storageMap.size()));
            messageHandler.publishOneSidedBreak(storageMap, processId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
