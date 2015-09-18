package com.drfa.engine.db;

import com.drfa.cli.Answer;
import com.drfa.engine.file.CompareFiles;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import static com.drfa.engine.EngineConstants.START_PROCESSING;


public class DBMessageListener implements Callable<Boolean>{

    static Logger LOG = Logger.getLogger(DBMessageListener.class);
    BlockingQueue queue;
    Answer answer;

    public DBMessageListener(BlockingQueue queue, Answer answer) {
        this.queue = queue;
        this.answer = answer;
    }


    @Override
    public Boolean call() throws Exception {
        try {
            String message = (String)queue.take();
            if(START_PROCESSING.equalsIgnoreCase(message)){
                LOG.info(String.format("Start Comparing the BASE %s and TARGET %s", answer.getBaseFile(), answer.getTargetFile()));
                return new CompareFiles().compare(answer);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
