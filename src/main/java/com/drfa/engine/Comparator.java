package com.drfa.engine;

import com.drfa.engine.report.BreakReport;

import java.util.concurrent.ExecutionException;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public interface Comparator {

    public BreakReport compare() throws ExecutionException, InterruptedException;

}
