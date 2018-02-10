package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.messaging.MessagePublisher;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class Engine {

    static Logger LOG = Logger.getLogger(Engine.class);
    private Answer answer;
    private MessagePublisher messagePublisher;

    public Engine(Answer answer, MessagePublisher messagePublisher) {
        this.answer = answer;
        this.messagePublisher = messagePublisher;
    }
    

    public void reconcile() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<ColumnAttribute> columnAttributes = answer.getColumnAttribute();
        LOG.info(String.format("Column Attributes parsed %s", columnAttributes));
        Comparator comparator = new ComparatorFactory(answer, messagePublisher).getComparator(answer.getReconciliationType());
        comparator.compare();
    }


}
