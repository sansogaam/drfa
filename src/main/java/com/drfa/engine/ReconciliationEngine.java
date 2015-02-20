package com.drfa.engine;

import com.drfa.cli.Answer;

import java.io.File;
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
        MetaDataParser dataParser = new MetaDataParser(answer.getMetaDataFile());
        List<String> columnNames = dataParser.getMetaDataColumnNames();
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnNames(columnNames);
        Comparator comparator = new CsvFileComparator(context);
        comparator.compare(answer.getKeyIndex(), new File(answer.getBaseFile()), new File(answer.getTargetFile()));
        System.out.println("Reconciliation is completed...");
    }

    public static void main(String args[]){
        System.out.println("This is a test of reconciliation...");
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnNames(populateColumnNames());
        Comparator comparator = new CsvFileComparator(context);
        long startTime = System.currentTimeMillis();
        try {
            BreakReport report = comparator.compare(1, new File("D:/dev/test.csv"), new File("D:/dev/test1.csv"));
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
