package com.drfa.engine;

import java.io.File;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Sanjiv on 2/24/2015.
 */
public class CompareFiles {

    ReconciliationContext context;
    public CompareFiles(ReconciliationContext context){
        this.context = context;
    }


    public BreakReport compare(int primaryKeyIndex, File base, File target) throws ExecutionException, InterruptedException {
        Map<String, String> storageMap = new ConcurrentHashMap<String, String>();
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        ScanFile scanFile = new ScanFile();

        ExecutorService executorServiceBase = Executors.newSingleThreadExecutor();
        executorServiceBase.execute(new BaseExecutor(scanFile,queue,storageMap, primaryKeyIndex, base));
        executorServiceBase.shutdown();

        ExecutorService executorServiceTarget = Executors.newSingleThreadExecutor();
        executorServiceTarget.execute(new TargetExecutor(scanFile,queue,storageMap,primaryKeyIndex,target));
        executorServiceTarget.shutdown();

        ExecutorService executorServiceComparator = Executors.newSingleThreadExecutor();
        Future<BreakReport> breakReportFuture = executorServiceComparator.submit(new ComparatorListener(context, queue, storageMap));
        executorServiceComparator.shutdown();
        BreakReport report  = breakReportFuture.get();
        System.out.println(String.format("Size of the file hash map storage is %s", storageMap.size()));
        return report;
    }
}
