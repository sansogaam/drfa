package com.drfa.report;

import com.drfa.messaging.jms.ActiveMqListener;
import com.drfa.util.DrfaProperties;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class ReportListener implements MessageListener {

    private static Logger LOG = Logger.getLogger(ReportListener.class);

    private ReportGenerator reportGenerator;

    public ReportListener() {
        this.reportGenerator = new ReportGenerator();
    }

    public static void main(String args[]) {
        ReportListener reportListener = new ReportListener();
        try {
            new ActiveMqListener(reportListener).startMsgListener(DrfaProperties.BREAK_MESSAGE_QUEUE, DrfaProperties.BROKER_URL);
        } catch (JMSException e) {
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
            reportGenerator.generateReport(messageBody);
        }
    }

}
