package com.drfa.engine.db;

import com.drfa.cli.Answer;
import com.drfa.engine.Comparator;
import com.drfa.engine.meta.MetaDataParser;
import com.drfa.engine.ReconciliationContext;

import java.io.File;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;

/**
 * Created by Sanjiv on 2/21/2015.
 */
public class DBComparator implements Comparator{
    ReconciliationContext context;
    Answer answer;
    public DBComparator(ReconciliationContext context, Answer answer){
        this.context = context;
        this.answer = answer;
    }

    @Override
    public boolean compare() throws ExecutionException, InterruptedException {
        context.setFileDelimiter(answer.getFileDelimiter());
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        AtomicLong aLong = new AtomicLong(0);
        ExecuteDBRead.initializeEngine(answer.getPluginPath());

        DatabaseInput databaseInputForBase = new DatabaseInput(answer.getSqlQueryBase(), answer.getBaseDatabaseMetaDataFile(),
                                                            answer.getBaseDatabaseCredentialFile(), answer.getPluginPath(),
                                                                answer.getBaseDatabaseFile());
        String baseOutputFile = answer.getBaseFile();
        final ExecutorService executorServiceBase = Executors.newSingleThreadExecutor();

        executorServiceBase.execute(new ExecuteDBRead(databaseInputForBase, queue, aLong, BASE_THREAD_NAME, baseOutputFile));
        executorServiceBase.shutdown();

        DatabaseInput databaseInputForTarget = new DatabaseInput(answer.getSqlQueryTarget(), answer.getTargetDatabaseMetaDataFile(),
                answer.getTargetDatabaseCredentialFile(), answer.getPluginPath(),
                answer.getTargetDatabaseFile());

        final ExecutorService executorServiceTarget = Executors.newSingleThreadExecutor();
        String targetOutputFile = answer.getTargetFile();
        executorServiceTarget.execute(new ExecuteDBRead(databaseInputForTarget, queue, aLong, TARGET_THREAD_NAME, targetOutputFile));
        executorServiceTarget.shutdown();

        final ExecutorService executorServiceCompare = Executors.newSingleThreadExecutor();
        Future<Boolean> compareReport = executorServiceCompare.submit(new DBMessageListener(context, queue, answer));
        executorServiceCompare.shutdown();
        return compareReport.get();
    }
}
