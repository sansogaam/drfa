package com.drfa.engine;

import java.util.concurrent.ExecutionException;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public interface Comparator {

    public boolean compare() throws ExecutionException, InterruptedException;

}
