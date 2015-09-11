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

    public static void main(String args[]){
        Answer answer = new Answer();
        answer.setBaseDatabaseCredentialFile("D:/dev/drfa/src/test/resources/mysql-base.cfg");
        answer.setBaseDatabaseFile("D:/dev");
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setTargetDatabaseCredentialFile("D:/dev/drfa/src/test/resources/mysql-target.cfg");
        answer.setTargetDatabaseFile("D:/dev");
        answer.setPluginPath("D:/dev/drfa/src/main/resources/plugins");
        answer.setSqlQueryBase("SELECT ID, first_name, last_name, email_address,DATE_FORMAT(date_of_joining,'%d/%m/%Y') as date_of_joining FROM EMPLOYEE");
        answer.setSqlQueryTarget("SELECT ID,name,address, email_detail, DATE_FORMAT(joining_date,'%d/%m/%Y') as joining_date FROM PERSON");
        answer.setMetaDataFile("D:/dev/drfa/src/test/resources/reconciliation-input.xml");
        answer.setBaseDatabaseMetaDataFile("D:/dev/drfa/src/test/resources/rec-db-base.fmt");
        answer.setTargetDatabaseMetaDataFile("D:/dev/drfa/src/test/resources/rec-db-target.fmt");
        String baseOutputFile = answer.getBaseDatabaseFile() + File.separator + BASE_THREAD_NAME+"-"+ new Date().getTime() + ".csv";
        answer.setBaseFile(baseOutputFile);
        String targetOutputFile = answer.getTargetDatabaseFile() + File.separator + TARGET_THREAD_NAME+"-"+ new Date().getTime() + ".csv";
        answer.setTargetFile(targetOutputFile);
        ReconciliationContext context = new ReconciliationContext();
        context.setFileDelimiter("|");
        context.setColumnAttributes(new MetaDataParser(answer.getMetaDataFile(), answer.getPluginPath()).getColumnAttributes());
        DBComparator dbCompare = new DBComparator(context, answer);
        try {
            Boolean compareFlag = dbCompare.compare();
            System.out.println("Comparing the results..." + compareFlag);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
