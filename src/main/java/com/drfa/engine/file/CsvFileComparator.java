package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.Comparator;

import java.util.concurrent.ExecutionException;


public class CsvFileComparator implements Comparator {

    private Answer answer;

    public CsvFileComparator(Answer answer) {
        this.answer = answer;
    }


    @Override
    public boolean compare() throws ExecutionException, InterruptedException {
        return new CompareFiles().compare(answer);
    }
}
