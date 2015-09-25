package com.drfa.messaging;


import com.drfa.messaging.jms.ActiveMqPublisher;
import com.drfa.report.ResultMessageConstants;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static com.drfa.util.DrfaProperties.BREAK_MESSAGE_QUEUE;

public class MessagePublisher {

    public void publish(String message, String queue) {
        new ActiveMqPublisher().sendMsg(message, queue);
    }

    public void publishResult(String processId, Map<String, List<String>> rowBreaks) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.PROCESS_ID, processId);
        jsonObject.put(ResultMessageConstants.ROW_BREAKS, rowBreaks);
        new ActiveMqPublisher().sendMsg(jsonObject.toString(), BREAK_MESSAGE_QUEUE);
    }

    public void publishResult(String processId, String type, String numberOfRecords) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.PROCESS_ID, processId);
        jsonObject.put(ResultMessageConstants.TYPE, type);
        jsonObject.put(ResultMessageConstants.TYPE_NO_RECORDS, numberOfRecords);
        new ActiveMqPublisher().sendMsg(jsonObject.toString(), BREAK_MESSAGE_QUEUE);
    }

    public void publishOneSideBreak(String processId, String type, Map<String, String> mapOfOneSidedBreaks) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.PROCESS_ID, processId);
        jsonObject.put(ResultMessageConstants.TYPE, type);
        jsonObject.put(ResultMessageConstants.TYPE_NO_RECORDS, mapOfOneSidedBreaks.size() + "");
        jsonObject.put(ResultMessageConstants.ONE_SIDE_BREAKS, mapOfOneSidedBreaks);
        new ActiveMqPublisher().sendMsg(jsonObject.toString(), BREAK_MESSAGE_QUEUE);
    }


}
