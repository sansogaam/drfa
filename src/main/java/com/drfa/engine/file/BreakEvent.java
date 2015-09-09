package com.drfa.engine.file;

import com.drfa.jms.ActiveMqPublisher;
import com.drfa.util.DrfaProperties;


public class BreakEvent{

    public void publisher(String message, String queueName) throws Exception {
        ActiveMqPublisher mqPublisher = new ActiveMqPublisher();
        mqPublisher.sendMsg(message, queueName, DrfaProperties.BROKER_URL);
    }

}
