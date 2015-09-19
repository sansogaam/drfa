package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.db.DBComparator;
import com.drfa.engine.file.CsvFileComparator;
import com.drfa.messaging.MessagePublisher;


public class ComparatorFactory {

    private Answer answer;
    private MessagePublisher messagePublisher;

    public ComparatorFactory(Answer answer, MessagePublisher messagePublisher) {
        this.answer = answer;
        this.messagePublisher = messagePublisher;
    }

    public Comparator getComparator(String reconciliationType) {
        if ("FILE".equalsIgnoreCase(reconciliationType)) {
            return new CsvFileComparator(answer, messagePublisher);
        } else if ("DATABASE".equalsIgnoreCase(reconciliationType)) {
            return new DBComparator(answer, messagePublisher);
        }
        return null;
    }
}
