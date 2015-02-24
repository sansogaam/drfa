package com.drfa.engine;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public interface Comparator {

    public BreakReport compare() throws ExecutionException, InterruptedException;

}
