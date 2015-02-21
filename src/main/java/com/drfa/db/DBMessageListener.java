package com.drfa.db;

import com.drfa.cli.Answer;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Sanjiv on 2/21/2015.
 */
public class DBMessageListener implements Runnable{

    BlockingQueue queue;
    Answer answer;
    public DBMessageListener(BlockingQueue queue, Answer answer){
        this.queue = queue;
        this.answer = answer;
    }

    @Override
    public void run() {
        try {
            String message = (String)queue.take();
            if("START_PROCESSING".equalsIgnoreCase(message)){
                System.out.println("Start Processing the DB Output file......");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
