package com.drfa.messaging.jms;

import com.drfa.util.DrfaProperties;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Sanjiv on 9/9/2015.
 */
public class ActiveMqPublisher {

    public void sendMsg(String msg, String testQueue, String brokerURL) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DrfaProperties.BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(testQueue);

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage message = session.createTextMessage(msg);

            producer.send(message);

            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
