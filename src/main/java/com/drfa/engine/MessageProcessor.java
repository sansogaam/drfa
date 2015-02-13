package com.drfa.engine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MessageProcessor {

    ReconciliationContext context;
    volatile int rowCount;
    Map<Integer, String> mapOfBreaks= new LinkedHashMap<Integer,String>();

    public MessageProcessor(ReconciliationContext context){
        this.context = context;
    }

    public void processMessage(String message){
        rowCount ++;
    }

}
