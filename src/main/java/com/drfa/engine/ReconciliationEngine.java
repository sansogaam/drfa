package com.drfa.engine;

import java.io.File;

/**
 * Created by Sanjiv on 2/10/2015.
 */
public class ReconciliationEngine {

    public static void main(String args[]){
        System.out.println("This is a test of reconciliation...");
        Comparator comparator = new CsvFileComparator();
        comparator.compare(1, new File("D:/dev/test.csv"), new File("D:/dev/test1.csv"));
        System.out.println("Value Comparator...");
    }
}
