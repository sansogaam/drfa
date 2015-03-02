package com.drfa.engine.file;

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
    private int primaryKeyIndex;
    private File target;
    private String fileDelimiter;

    public TargetExecutor(ScanFile scanFile, BlockingQueue queue,
                        Map<String, String> storageMap, int primaryKeyIndex, File target, String fileDelimiter){

        this.scanFile = scanFile;
        this.queue = queue;
        this.storageMap = storageMap;
        this.primaryKeyIndex=primaryKeyIndex;
        this.target = target;
        this.fileDelimiter = fileDelimiter;
    }

    @Override
    public void run() {
        System.out.println("Parsing the base file for comparision");
        try {
            scanFile.scanFile(primaryKeyIndex, storageMap, target, queue, "TARGET", fileDelimiter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
