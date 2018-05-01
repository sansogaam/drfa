package com.drfa.messaging;


import com.drfa.messaging.jms.ActiveMqPublisher;
import com.drfa.messaging.kafka.KafkaPublisher;
import com.drfa.report.ResultMessageConstants;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static com.drfa.util.DrfaProperties.BREAK_MESSAGE_QUEUE;

public class MessagePublisher {

    Publisher publisher;

    public MessagePublisher(Publisher publisher){
        this.publisher = publisher;
    }

    public void publish(String message, String queue) {
        publisher.publish(message, queue);
    }

    public void publishResult(String processId, Map<String, List<String>> rowBreaks) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.PROCESS_ID, processId);
        jsonObject.put(ResultMessageConstants.ROW_BREAKS, rowBreaks);
        publisher.publish(jsonObject.toString(), BREAK_MESSAGE_QUEUE);
    }

    public void publishResult(String processId, String type, String numberOfRecords) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.PROCESS_ID, processId);
        jsonObject.put(ResultMessageConstants.TYPE, type);
        jsonObject.put(ResultMessageConstants.TYPE_NO_RECORDS, numberOfRecords);
        publisher.publish(jsonObject.toString(), BREAK_MESSAGE_QUEUE);
    }


    public void publishOneSideBreak(String processId, String type, Map<String, String> mapOfOneSidedBreaks) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultMessageConstants.PROCESS_ID, processId);
        jsonObject.put(ResultMessageConstants.TYPE, type);
        jsonObject.put(ResultMessageConstants.TYPE_NO_RECORDS, mapOfOneSidedBreaks.size() + "");
        jsonObject.put(ResultMessageConstants.ONE_SIDE_BREAKS, mapOfOneSidedBreaks);
        publisher.publish(jsonObject.toString(), BREAK_MESSAGE_QUEUE);
    }


}
