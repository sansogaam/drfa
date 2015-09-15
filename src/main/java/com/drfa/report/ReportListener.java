package com.drfa.report;

import com.drfa.messaging.Listener;
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

    public static void main(String args[]) throws JMSException {
        ReportListener reportListener = new ReportListener();
        new Listener().startMsgListener(reportListener, DrfaProperties.BREAK_MESSAGE_QUEUE);
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
