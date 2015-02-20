package com.drfa.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * Created by Sanjiv on 2/20/2015.
 */
public class BaseExecutor implements Runnable {
    private ScanFile scanFile;
    private BlockingQueue queue;
    private Map<String, String> storageMap;
    private int primaryKeyIndex;
    private File base;

    public BaseExecutor(ScanFile scanFile, BlockingQueue queue,
                        Map<String, String> storageMap, int primaryKeyIndex, File base){

        this.scanFile = scanFile;
        this.queue = queue;
        this.storageMap = storageMap;
        this.primaryKeyIndex=primaryKeyIndex;
        this.base = base;
    }

    @Override
    public void run() {
        System.out.println("Parsing the base file for comparision");
        try {
            scanFile.scanFile(primaryKeyIndex, storageMap, base, queue, "BASE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
