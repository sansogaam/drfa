package com.drfa.engine.db;

import com.drfa.cli.Answer;
import com.drfa.engine.EngineConstants;
import com.drfa.engine.report.BreakReport;
import com.drfa.engine.file.CompareFiles;
import com.drfa.engine.ReconciliationContext;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import static com.drfa.engine.EngineConstants.START_PROCESSING;

/**
 * Created by Sanjiv on 2/21/2015.
 */
public class DBMessageListener implements Callable<BreakReport>{

    ReconciliationContext context;
    BlockingQueue queue;
    Answer answer;
    public DBMessageListener(ReconciliationContext context,BlockingQueue queue, Answer answer){
        this.queue = queue;
        this.answer = answer;
        this.context = context;
    }


    @Override
    public BreakReport call() throws Exception {
        try {
            String message = (String)queue.take();
            if(START_PROCESSING.equalsIgnoreCase(message)){
                System.out.println(String.format("Start Comparing the BASE %s and TARGET %s", answer.getBaseFile(), answer.getTargetFile()));
                return new CompareFiles(context).compare(answer.getKeyIndex(), new File(answer.getBaseFile()), new File(answer.getTargetFile()));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
