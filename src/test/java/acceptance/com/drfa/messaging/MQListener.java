package acceptance.com.drfa.messaging;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MQListener implements MessageListener {

    private CountDownLatch countDownLatch;
    private List<TextMessage> messages = new ArrayList<>();

    public MQListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void startMsgListener(String testQueue, String brokerURL) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination adminQueue = session.createQueue(testQueue);

        //Set up a consumer to consume messages off of the admin queue
        MessageConsumer consumer = session.createConsumer(adminQueue);
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
