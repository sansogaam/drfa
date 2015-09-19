package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.Comparator;
import com.drfa.messaging.MessagePublisher;

import java.util.concurrent.ExecutionException;


public class CsvFileComparator implements Comparator {

    private Answer answer;
    private MessagePublisher messagePublisher;

    public CsvFileComparator(Answer answer, MessagePublisher messagePublisher) {
        this.answer = answer;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public boolean compare() throws ExecutionException, InterruptedException {
        return new CompareFiles(messagePublisher).compare(answer);
    }
}
