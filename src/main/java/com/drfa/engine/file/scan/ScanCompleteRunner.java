package com.drfa.engine.file.scan;


import com.drfa.cli.Answer;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

public class ScanCompleteRunner implements Runnable {
    private Future<?> baseFuture;
    private Future<?> targetFuture;
    private Map<String, String> storageMap;
    private BlockingQueue queue;
    private Answer answer;

    public ScanCompleteRunner(Answer answer, Future<?> baseFuture, Future<?> targetFuture, Map<String, String> storageMap, BlockingQueue queue) {
        this.baseFuture = baseFuture;
        this.targetFuture = targetFuture;
        this.storageMap = storageMap;
        this.queue = queue;
        this.answer = answer;
    }

    @Override
    public void run() {
        try {
            this.baseFuture.get();
            this.targetFuture.get();

            new ScanHelper().flushTheStorageMap(storageMap, queue, answer.processPrefix());
            queue.put(answer.processPrefix() + "Exit");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
