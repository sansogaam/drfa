package com.drfa.engine.file;

import com.drfa.messaging.MessagePublisher;


public class BreakEvent {

    public void publisher(String message, String queueName) throws Exception {
        new MessagePublisher().sendMsg(message, queueName);
    }

}
