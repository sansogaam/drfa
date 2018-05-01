package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.messaging.Listener;
import com.drfa.messaging.MessagePublisher;
import com.drfa.messaging.Publisher;
import com.drfa.messaging.jms.ActiveMqPublisher;
import com.drfa.messaging.kafka.KafkaPublisher;
import com.drfa.util.DrfaProperties;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.ExecutionException;


public class ReconciliationServer implements MessageListener {

    private static Logger LOG = Logger.getLogger(ReconciliationServer.class);

    public static void main(String args[]) {
        try {
            ReconciliationServer reconciliationServer = new ReconciliationServer();
            new Listener().startMsgListener(reconciliationServer, DrfaProperties.REC_ANSWER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            String messageBody = null;
            try {
                messageBody = ((TextMessage) message).getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            LOG.info(String.format("Received the message %s ", messageBody));
            processMessage(messageBody);
        }
    }

    private void processMessage(String messageBody) {
        Answer answer = convertToAnswerObject(messageBody);
        Engine engine = new Engine(answer, new MessagePublisher(determinResultPublisher(answer)));
        try {
            engine.reconcile();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Publisher determinResultPublisher(Answer answer){
        if(answer.getResultPublishingServer().equalsIgnoreCase("ACTIVE-MQ")){
            return new ActiveMqPublisher();
        }else{
            return new KafkaPublisher();
        }
    }
    public Publisher checkTheBreakMessagingServer(){
        return null;
    }

    private Answer convertToAnswerObject(String xmlString) {
        XStream xst = new XStream();
        Answer answer = (Answer) xst.fromXML(xmlString);
        LOG.info(String.format("Answer Object recieved %s", answer.toString()));
        return answer;
    }
}
