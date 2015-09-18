package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.file.scan.ScanFile;
import com.drfa.engine.meta.ColumnAttribute;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class Executor implements Runnable {
    private ScanFile scanFile;
    private BlockingQueue queue;
    private Map<String, String> storageMap;
    private Answer answer;
    private List<ColumnAttribute> columnAttributes;
    private String type;

    public Executor(ScanFile scanFile, BlockingQueue queue,
                    Map<String, String> storageMap, Answer answer, List<ColumnAttribute> columnAttributes, String type) {

        this.scanFile = scanFile;
        this.queue = queue;
        this.storageMap = storageMap;
        this.answer = answer;
        this.columnAttributes = columnAttributes;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            scanFile.scanFile(storageMap, queue, type, answer, columnAttributes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
