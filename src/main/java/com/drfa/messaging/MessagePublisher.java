package com.drfa.messaging;


import com.drfa.messaging.jms.ActiveMqPublisher;
import com.drfa.report.ResultMessageConstants;
import org.json.JSONObject;

public class MessagePublisher {

    public void publish(String message, String queue) {
        new ActiveMqPublisher().sendMsg(message, queue);
    }

    public void publishResult(String message, String queue) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.FULL_TEXT, message);
        new ActiveMqPublisher().sendMsg(jsonObject.toString(), queue);
    }

}
