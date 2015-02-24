package com.drfa.engine;

import com.drfa.cli.Answer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public class CsvFileComparator implements Comparator {

    ReconciliationContext context;
    Answer answer;

    public CsvFileComparator(ReconciliationContext context, Answer answer) {
        this.context = context;
        this.answer = answer;
    }


    @Override
    public BreakReport compare() throws ExecutionException, InterruptedException {
        return new CompareFiles(context).compare(answer.getKeyIndex(), new File(answer.getBaseFile()), new File(answer.getTargetFile()));
    }
}
