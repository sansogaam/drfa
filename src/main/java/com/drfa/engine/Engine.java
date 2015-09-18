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
        //MetaDataParser dataParser = new MetaDataParser(answer.getMetaDataFile(), answer.getPluginPath());
        ReconciliationInput reconciliationInput = new ReconciliationInput();
        List<ColumnAttribute> columnAttributes = reconciliationInput.initializeReconciliationInput(answer.getMetaDataFile());
        LOG.info(String.format("Column Attributes parsed %s", columnAttributes));
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnAttributes(columnAttributes);
        Comparator comparator = new ComparatorFactory(context, answer).getComparator(answer.getReconciliationType());
        comparator.compare();
    }


}
