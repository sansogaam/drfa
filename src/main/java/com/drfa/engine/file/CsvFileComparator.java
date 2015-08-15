package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.Comparator;
import com.drfa.engine.ReconciliationContext;

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
    public boolean compare() throws ExecutionException, InterruptedException {
        context.setFileDelimiter(answer.getFileDelimiter());
        return new CompareFiles(context).compare(answer);
    }
}
