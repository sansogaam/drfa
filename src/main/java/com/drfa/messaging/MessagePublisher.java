package com.drfa.messaging;


import com.drfa.messaging.jms.ActiveMqPublisher;
import com.drfa.report.ResultMessageConstants;
import org.json.JSONObject;

import static com.drfa.util.DrfaProperties.BREAK_MESSAGE_QUEUE;

public class MessagePublisher {

    public void publish(String message, String queue) {
        new ActiveMqPublisher().sendMsg(message, queue);
    }

    public void publishResult(String processId, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.PROCESS_ID, processId);
        jsonObject.put(ResultMessageConstants.FULL_TEXT, message);
        new ActiveMqPublisher().sendMsg(jsonObject.toString(), BREAK_MESSAGE_QUEUE);
    }

}
