package com.drfa.messaging.jms;

import com.drfa.messaging.Publisher;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static com.drfa.util.DrfaProperties.BROKER_URL;
import static javax.jms.DeliveryMode.NON_PERSISTENT;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;


public class ActiveMqPublisher implements Publisher{

    private String queueName;

    public ActiveMqPublisher(String queueName){
        this.queueName = queueName;
    }

    private void sendMsg(String msg) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(this.queueName);

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(NON_PERSISTENT);

            TextMessage message = session.createTextMessage(msg);

            producer.send(message);

            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void publish(String message) {
        sendMsg(message);
    }
}
