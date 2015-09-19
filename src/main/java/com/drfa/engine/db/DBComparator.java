package com.drfa.engine.db;

import com.drfa.cli.Answer;
import com.drfa.engine.Comparator;
import com.drfa.messaging.MessagePublisher;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;


public class DBComparator implements Comparator{
    private Answer answer;
    private MessagePublisher messagePublisher;

    public DBComparator(Answer answer, MessagePublisher messagePublisher) {
        this.answer = answer;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public boolean compare() throws ExecutionException, InterruptedException {
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
        Future<Boolean> compareReport = executorServiceCompare.submit(new DBMessageListener(queue, answer, messagePublisher));
        executorServiceCompare.shutdown();
        return compareReport.get();
    }
}
