package acceptance.com.drfa.messaging;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MQListener implements MessageListener {
    private static String messageQueueName = "testQueue";
    private String messageBrokerUrl = "tcp://localhost:61616";
    private Session session;
    private boolean transacted = false;

    private CountDownLatch countDownLatch;
    private List<TextMessage> messages = new ArrayList<>();

    public MQListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void startMsgListener() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        this.session = connection.createSession(this.transacted, Session.AUTO_ACKNOWLEDGE);
        Destination adminQueue = this.session.createQueue(messageQueueName);

        //Set up a consumer to consume messages off of the admin queue
        MessageConsumer consumer = this.session.createConsumer(adminQueue);
        consumer.setMessageListener(this);
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;
            messages.add(txtMsg);
            countDownLatch.countDown();
        }
    }

    public List<TextMessage> getMessages() {
        return messages;
    }
}
