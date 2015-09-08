package com.drfa.jms;

import com.drfa.util.DrfaProperties;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;


public class JMSConnection {
    
    public static Session createSession() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DrfaProperties.BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}
