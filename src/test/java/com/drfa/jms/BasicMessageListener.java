package com.drfa.jms;


import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BasicMessageListener implements MessageListener {
    private CountDownLatch countDownLatch;
    private List<TextMessage> messages = new ArrayList<>();

    public BasicMessageListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
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
