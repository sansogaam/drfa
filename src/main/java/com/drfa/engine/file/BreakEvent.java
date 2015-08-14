package com.drfa.engine.file;

import com.drfa.server.JMSConnection;
import com.drfa.server.Publisher;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;


/**
 * Created by Sanjiv on 8/3/2015.
 */
public class BreakEvent implements Publisher{

    private String constructedMessage;

    @Override
    public void publisher(String message, String queueName) throws Exception {
        Session session = JMSConnection.createSession();
        Destination dest = new QueueImpl(queueName);
        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(session.createTextMessage(message));
    }

}
