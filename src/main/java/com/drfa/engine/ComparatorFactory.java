package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.db.DBComparator;
import com.drfa.engine.file.CsvFileComparator;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class ComparatorFactory {

    public ReconciliationContext context;
    public Answer answer;

    public ComparatorFactory(ReconciliationContext context,Answer answer){
        this.context = context;
        this.answer = answer;
    }

    public Comparator getComparator(String reconciliationType){
        if("FILE".equalsIgnoreCase(reconciliationType)){
            return new CsvFileComparator(context, answer);
        }else if("DATABASE".equalsIgnoreCase(reconciliationType)){
            return new DBComparator(context,answer);
        }
        return null;
    }
}
