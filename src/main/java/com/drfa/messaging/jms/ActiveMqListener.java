package com.drfa.messaging.jms;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMqListener implements MessageListener {

    private MessageListener messageListener;

    public ActiveMqListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void startMsgListener(String queueName, String brokerURL) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue(queueName);

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);
    }

    public void onMessage(Message message) {
        messageListener.onMessage(message);
    }

}