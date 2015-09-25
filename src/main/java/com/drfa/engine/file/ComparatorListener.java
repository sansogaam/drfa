package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.messaging.MessagePublisher;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;


public class ComparatorListener implements Callable<Boolean> {
    static Logger LOG = Logger.getLogger(ComparatorListener.class);
    private BlockingQueue queue;
    private Map<String, String> storageMap;
    private Answer answer;
    private MessagePublisher messagePublisher;

    public ComparatorListener(BlockingQueue queue,
                              Map<String, String> storageMap, Answer answer, MessagePublisher messagePublisher) {
        this.queue = queue;
        this.storageMap = storageMap;
        this.answer = answer;
        this.messagePublisher = messagePublisher;

    }

    @Override
    public Boolean call() throws Exception {
        MessageProcessor messageProcessor = new MessageProcessor(answer);
        MessageHandler messageHandler = new MessageHandler(messageProcessor, messagePublisher, answer);
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
