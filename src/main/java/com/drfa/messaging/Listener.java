package com.drfa.messaging;


import com.drfa.messaging.jms.ActiveMqListener;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public class Listener {

    public void startMsgListener(MessageListener messageListener, String queueName) throws JMSException {
        new ActiveMqListener(messageListener).startMsgListener(queueName);
    }
}
