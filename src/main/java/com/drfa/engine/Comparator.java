package com.drfa.engine;

import java.util.concurrent.ExecutionException;


public interface Comparator {

    boolean compare() throws ExecutionException, InterruptedException;

}
