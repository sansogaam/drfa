package com.drfa.util;


public interface DrfaProperties {


    String BROKER_URL = "tcp://localhost:5672";
    String BREAK_MESSAGE_QUEUE = "queue://BREAK_MESSAGE";
    String BREAK_MESSAGE_TOPIC = "reconciliation-topic";
    String REC_ANSWER = "queue://REC_ANSWER";
    //Delimiter
    String BASE_AND_TARGET_JOINER = "$";
    String BASE_AND_TARGET_TWO_COLUMNS_JOINER = "$";
    String BASE_AND_TARGET_COLUMN_NAME_APPENDER = "~";
    String BASE_AND_TARGET_COLUMN_VALUE_APPENDER = "#";
    String THREAD_NAMES_JOINER = ":";
    String BASE_PREFIX ="BASE:";
    String TARGET_PREFIX ="TARGET:";
    String PROCESS_PREFIX="PROCESS_ID:";
    String SUMMARY_PREFIX="SUMMARY:";
}
