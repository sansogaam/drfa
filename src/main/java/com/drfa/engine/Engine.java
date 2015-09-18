package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class Engine {

    static Logger LOG = Logger.getLogger(Engine.class);
    Answer answer;

    public Engine(Answer answer){
        this.answer = answer;

    }

    public void reconcile() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<ColumnAttribute> columnAttributes = answer.getColumnAttribute();
        LOG.info(String.format("Column Attributes parsed %s", columnAttributes));
        ;
        Comparator comparator = new ComparatorFactory(answer).getComparator(answer.getReconciliationType());
        comparator.compare();
    }


}
