package com.drfa.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanjiv on 2/10/2015.
 */
public class ReconciliationEngine {

    public static void main(String args[]){
        System.out.println("This is a test of reconciliation...");
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnNames(populateColumnNames());
        Comparator comparator = new CsvFileComparator(context);
        comparator.compare(1, new File("D:/dev/test.csv"), new File("D:/dev/test1.csv"));
        System.out.println("Value Comparator...");
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
