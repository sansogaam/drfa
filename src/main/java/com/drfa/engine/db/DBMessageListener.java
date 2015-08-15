package com.drfa.engine.db;

import com.drfa.cli.Answer;
import com.drfa.engine.file.CompareFiles;
import com.drfa.engine.ReconciliationContext;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import static com.drfa.engine.EngineConstants.START_PROCESSING;

/**
 * Created by Sanjiv on 2/21/2015.
 */
public class DBMessageListener implements Callable<Boolean>{

    ReconciliationContext context;
    BlockingQueue queue;
    Answer answer;
    static Logger LOG = Logger.getLogger(DBMessageListener.class);

    public DBMessageListener(ReconciliationContext context,BlockingQueue queue, Answer answer){
        this.queue = queue;
        this.answer = answer;
        this.context = context;
    }


    @Override
    public Boolean call() throws Exception {
        try {
            String message = (String)queue.take();
            if(START_PROCESSING.equalsIgnoreCase(message)){
                LOG.info(String.format("Start Comparing the BASE %s and TARGET %s", answer.getBaseFile(), answer.getTargetFile()));
                return new CompareFiles(context).compare(answer);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
