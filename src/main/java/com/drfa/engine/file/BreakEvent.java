package com.drfa.engine.file;

import com.drfa.messaging.jms.ActiveMqPublisher;


public class BreakEvent{

    public void publisher(String message, String queueName) throws Exception {
        ActiveMqPublisher mqPublisher = new ActiveMqPublisher();
        mqPublisher.sendMsg(message, queueName);
    }

}
