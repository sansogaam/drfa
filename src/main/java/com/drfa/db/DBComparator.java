package com.drfa.db;

import com.drfa.cli.Answer;
import com.drfa.engine.BreakReport;
import com.drfa.engine.Comparator;
import com.drfa.engine.ReconciliationContext;

import java.io.File;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Sanjiv on 2/21/2015.
 */
public class DBComparator{
    ReconciliationContext context;
    Answer answer;
    public DBComparator(ReconciliationContext context, Answer answer){
        this.context = context;
        this.answer = answer;
    }

    public BreakReport compare() throws ExecutionException, InterruptedException {
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        AtomicLong aLong = new AtomicLong(0);
        ExecuteDBRead.initializeEngine(answer.getPluginPath());

        DatabaseInput databaseInputForBase = new DatabaseInput(answer.getSqlQueryBase(), answer.getMetaDataFile(),
                                                            answer.getBaseDatabaseCredentialFile(), answer.getPluginPath(),
                                                                answer.getBaseDatabaseFile());

        final ExecutorService executorServiceBase = Executors.newSingleThreadExecutor();
        executorServiceBase.execute(new ExecuteDBRead(databaseInputForBase, queue, aLong, "BASE"));
        executorServiceBase.shutdown();

        DatabaseInput databaseInputForTarget = new DatabaseInput(answer.getSqlQueryTarget(), answer.getMetaDataFile(),
                answer.getTargetDatabaseCredentialFile(), answer.getPluginPath(),
                answer.getTargetDatabaseFile());

        final ExecutorService executorServiceTarget = Executors.newSingleThreadExecutor();
        executorServiceTarget.execute(new ExecuteDBRead(databaseInputForTarget, queue, aLong, "TARGET"));
        executorServiceTarget.shutdown();

        final ExecutorService executorServiceCompare = Executors.newSingleThreadExecutor();
        executorServiceCompare.execute(new DBMessageListener(queue, answer));
        executorServiceCompare.shutdown();
        return null;
    }

    public static void main(String args[]){
        Answer answer = new Answer();
        answer.setBaseDatabaseCredentialFile("D:/dev/drfa/src/main/resources/mysql-base.cfg");
        answer.setBaseDatabaseFile("D:/dev");
        answer.setTargetDatabaseCredentialFile("D:/dev/drfa/src/main/resources/mysql-target.cfg");
        answer.setTargetDatabaseFile("D:/dev");

        answer.setPluginPath("D:/dev/drfa/src/main/resources/plugins");
        answer.setSqlQueryBase("SELECT * FROM EMPLOYEE");
        answer.setSqlQueryTarget("SELECT * FROM EMPLOYEE");
        answer.setMetaDataFile("D:/dev/drfa/src/test/resources/dbformat.fmt");
        DBComparator dbCompare = new DBComparator(null, answer);
        try {
            dbCompare.compare();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
