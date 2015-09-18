package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.db.DBComparator;
import com.drfa.engine.file.CsvFileComparator;


public class ComparatorFactory {

    public Answer answer;

    public ComparatorFactory(Answer answer) {
        this.answer = answer;
    }

    public Comparator getComparator(String reconciliationType){
        if("FILE".equalsIgnoreCase(reconciliationType)){
            return new CsvFileComparator(answer);
        }else if("DATABASE".equalsIgnoreCase(reconciliationType)){
            return new DBComparator(answer);
        }
        return null;
    }
}
