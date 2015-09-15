package com.drfa.messaging;


import com.drfa.messaging.jms.ActiveMqPublisher;

public class MessagePublisher {
    public void publish(String message, String queue) {
        new ActiveMqPublisher().sendMsg(message, queue);
    }
}
