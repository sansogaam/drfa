package com.drfa.engine.file;

import com.drfa.cli.Answer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Sanjiv on 2/20/2015.
 */
public class TargetExecutor implements Runnable {

    private ScanFile scanFile;
    private BlockingQueue queue;
    private Map<String, String> storageMap;
    private Answer answer;
    public TargetExecutor(ScanFile scanFile, BlockingQueue queue,
                        Map<String, String> storageMap, Answer answer){

        this.scanFile = scanFile;
        this.queue = queue;
        this.storageMap = storageMap;
        this.answer = answer;
    }

    @Override
    public void run() {
        System.out.println("Parsing the base file for comparision");
        try {
            scanFile.scanFile(storageMap, queue, "TARGET", answer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
