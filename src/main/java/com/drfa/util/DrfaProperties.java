package com.drfa.util;


public interface DrfaProperties {


    String BROKER_URL = "tcp://localhost:5672";
    String BREAK_MESSAGE_QUEUE = "queue://BREAK_MESSAGE";
    String REC_ANSWER = "queue://REC_ANSWER";
}
