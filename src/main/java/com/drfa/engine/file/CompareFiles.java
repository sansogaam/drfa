package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.ReconciliationContext;
import com.drfa.engine.file.scan.ScanCompleteRunner;
import com.drfa.engine.file.scan.ScanFile;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Sanjiv on 2/24/2015.
 */
public class CompareFiles {

    static Logger LOG = Logger.getLogger(CompareFiles.class);
    ReconciliationContext context;

    public CompareFiles(ReconciliationContext context) {
        this.context = context;
    }


    public Boolean compare(Answer answer) throws ExecutionException, InterruptedException {

        Map<String, String> storageMap = new ConcurrentHashMap<String, String>();
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        ScanFile scanFile = new ScanFile();

        ExecutorService executorServiceBase = Executors.newFixedThreadPool(3);
        Future<?> baseFileScan = executorServiceBase.submit(new Executor(scanFile, queue, storageMap, answer, context.getColumnAttributes(), "BASE"));
        Future<?> targetFileScan = executorServiceBase.submit(new Executor(scanFile, queue, storageMap, answer, context.getColumnAttributes(), "TARGET"));
        executorServiceBase.submit(new ScanCompleteRunner(answer, baseFileScan, targetFileScan, storageMap, queue));
        executorServiceBase.shutdown();

        ExecutorService executorServiceComparator = Executors.newSingleThreadExecutor();
        Future<Boolean> compareFlagFuture = executorServiceComparator.submit(new ComparatorListener(context, queue, storageMap));
        executorServiceComparator.shutdown();
        Boolean compareFlag = compareFlagFuture.get();
        LOG.info(String.format("Size of the file hash map storage is %s", storageMap.size()));
        return compareFlag;

    }
}
