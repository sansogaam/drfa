package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.db.MetaDataParser;
import com.drfa.engine.report.BreakReport;
import com.drfa.engine.file.CsvFileComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sanjiv on 2/10/2015.
 */
public class ReconciliationEngine {

    Answer answer;

    public ReconciliationEngine(Answer answer){
        this.answer = answer;

    }

    public void reconcile() throws ExecutionException, InterruptedException {
        MetaDataParser dataParser = new MetaDataParser(answer.getMetaDataFile(), answer.getPluginPath());
        List<String> columnNames = dataParser.getMetaDataColumnNames();
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnNames(columnNames);
        Comparator comparator = new ComparatorFactory(context, answer).getComparator(answer.getReconciliationType());
        comparator.compare();
        System.out.println("Reconciliation is completed...");
    }

    public static void main(String args[]){
        System.out.println("This is a test of reconciliation...");
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnNames(populateColumnNames());
        Answer answer = new Answer();
        answer.setKeyIndex(1);
        answer.setReconciliationType("FILE");
        answer.setBaseFile("D:/dev/test.csv");
        answer.setTargetFile("D:/dev/test1.csv");
        Comparator comparator = new CsvFileComparator(context, answer);
        long startTime = System.currentTimeMillis();
        try {
            BreakReport report = comparator.compare();
            System.out.println("Reporting tool: " + report);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Total time taken by comparator %s milliseconds", endTime-startTime));
    }

    public static List<String> populateColumnNames(){
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("C1");
        columnNames.add("C2");
        columnNames.add("C3");
        columnNames.add("C4");
        return columnNames;
    }

}
