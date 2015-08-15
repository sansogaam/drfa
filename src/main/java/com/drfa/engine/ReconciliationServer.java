package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.jms.JMSConnection;
import com.drfa.jms.Listener;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;

import javax.jms.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sanjiv on 8/1/2015.
 */
public class ReconciliationServer implements Listener {

    static Logger LOG = Logger.getLogger(ReconciliationServer.class);

    @Override
    public void listener(String queueName) throws Exception{
        Session session = JMSConnection.createSession();
        Destination dest = new QueueImpl(queueName);
        MessageConsumer consumer = session.createConsumer(dest);
        LOG.info(String.format("Server Started successfully and listening on the queue %s", queueName));
        System.out.println(String.format("Server Started successfully and listening on the queue %s", queueName));
        while (true) {
            System.out.println("Waiting for the message....");
            Message message = consumer.receive();
            if(message instanceof TextMessage) {
                String messageBody = ((TextMessage)message).getText();
                LOG.info(String.format("Received the message %s ", messageBody));
                System.out.println(String.format("Received the message %s ", messageBody));
                processMessage(messageBody);
            }
        }
    }

    private void processMessage(String messageBody) {
        Answer answer = convertToAnswerObject(messageBody);
        Engine engine = new Engine(answer);
        try {
            engine.reconcile();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Answer convertToAnswerObject(String xmlString){
        XStream xst = new XStream();
        Answer answer = (Answer)xst.fromXML(xmlString);
        LOG.info(String.format("Answer Object recieved %s", answer.toString()));
        return answer;
    }
    public static void main(String args[]){
        try{
            ReconciliationServer reconciliationServer = new ReconciliationServer();
            reconciliationServer.listener("queue://REC_ANSWER");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    
}
