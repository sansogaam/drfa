package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.file.scan.ScanCompleteRunner;
import com.drfa.engine.file.scan.ScanFile;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.*;

public class CompareFiles {

    static Logger LOG = Logger.getLogger(CompareFiles.class);

    public Boolean compare(Answer answer) throws ExecutionException, InterruptedException {

        Map<String, String> storageMap = new ConcurrentHashMap<String, String>();
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        ScanFile scanFile = new ScanFile();

        ExecutorService executorServiceBase = Executors.newFixedThreadPool(3);
        Future<?> baseFileScan = executorServiceBase.submit(new Executor(scanFile, queue, storageMap, answer, "BASE"));
        Future<?> targetFileScan = executorServiceBase.submit(new Executor(scanFile, queue, storageMap, answer, "TARGET"));
        executorServiceBase.submit(new ScanCompleteRunner(answer, baseFileScan, targetFileScan, storageMap, queue));
        executorServiceBase.shutdown();

        ExecutorService executorServiceComparator = Executors.newSingleThreadExecutor();
        Future<Boolean> compareFlagFuture = executorServiceComparator.submit(new ComparatorListener(queue, storageMap, answer));
        executorServiceComparator.shutdown();
        Boolean compareFlag = compareFlagFuture.get();
        LOG.info(String.format("Size of the file hash map storage is %s", storageMap.size()));
        return compareFlag;

    }
}
