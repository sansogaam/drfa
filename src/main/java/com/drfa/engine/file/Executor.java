package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.file.scan.ScanFile;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class Executor implements Runnable {
    private ScanFile scanFile;
    private BlockingQueue queue;
    private Map<String, String> storageMap;
    private Answer answer;
    private String type;

    public Executor(ScanFile scanFile, BlockingQueue queue, Map<String, String> storageMap, Answer answer, String type) {

        this.scanFile = scanFile;
        this.queue = queue;
        this.storageMap = storageMap;
        this.answer = answer;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            scanFile.scanFile(storageMap, queue, type, answer, answer.getColumnAttribute());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
