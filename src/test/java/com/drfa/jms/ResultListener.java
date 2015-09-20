package com.drfa.jms;


import org.json.JSONObject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ResultListener implements MessageListener {
    private CountDownLatch countDownLatch;
    private List<JSONObject> messages = new ArrayList<>();

    public ResultListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;
            try {
                messages.add(new JSONObject(txtMsg.getText()));
            } catch (JMSException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }


    public List<JSONObject> getMessages() {
        return messages;
    }
}
