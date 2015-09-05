package com.drfa.report;

import com.drfa.jms.JMSConnection;
import com.drfa.jms.Listener;
import org.apache.log4j.Logger;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;

import javax.jms.*;
import java.io.File;
import java.util.Date;

/**
 * Created by Sanjiv on 8/15/2015.
 */
public class ReportServer implements Listener{

    static Logger LOG = Logger.getLogger(ReportServer.class);

    @Override
    public void listener(String queueName) throws Exception {
        listenBreakMessage(queueName);
    }

    private void listenBreakMessage(String queueName) throws JMSException {
        Session session = JMSConnection.createSession();
        Destination dest = new QueueImpl(queueName);
        MessageConsumer consumer = session.createConsumer(dest);
        LOG.info(String.format("Server Started successfully and listening on the queue %s", queueName));
        System.out.println(String.format("Server Started successfully and listening on the queue %s", queueName));
        //This seems to be single threaded has to change to multi threaded
        BreakReport breakReport = new BreakReport();
        ReportEnricher reportEnricher = new ReportEnricher(breakReport);
        
        while (true) {
            System.out.println("Waiting for the message....");
            Message message = consumer.receive();
            if(message instanceof TextMessage) {
                String messageBody = ((TextMessage)message).getText();
                LOG.info(String.format("Received the message detail %s ", messageBody));
                reportEnricher.enrich(messageBody);
                if(messageBody.contains("MATCHED_RECORDS")){
                    break;
                }
            }
        }
        LOG.info(String.format("Break Report looks like %s", breakReport));
        reportEnricher.enrichBreakReportWithColumnDetails();
        generateReport(breakReport);
    }

    public void generateReport(BreakReport breakReport){
        long startTime = System.currentTimeMillis();
        String htmlReportPath = "D:/dev" + File.separator + "HTML-"+new Date().getTime()+".html";
        LOG.info(String.format("Report written on path %s with type %s", htmlReportPath, "BOTH"));
        ReportDecorator reportDecorator = new HTMLReportDecorator(breakReport, "BOTH");
        reportDecorator.decorateReport(htmlReportPath);
        long endTime = System.currentTimeMillis();
        LOG.info(String.format("Total time taken by reconciliation %s milliseconds", endTime-startTime));
    }
    
    public static void main(String args[]){
        try {
            new ReportServer().listener("queue://BREAK_MESSAGE");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        LOG.info(String.format("Report output path %s", compareFlag));
//        String htmlReportPath = answer.getReportOutputPath() + File.separator + "HTML-"+new Date().getTime()+".html";
//        LOG.info(String.format("Report written on path %s with type %s", htmlReportPath, answer.getReportCategory()));
//        ReportDecorator reportDecorator = new HTMLReportDecorator(compareFlag, answer.getReportCategory());
//        reportDecorator.decorateReport(htmlReportPath);
//        long endTime = System.currentTimeMillis();
//        LOG.info(String.format("Total time taken by reconciliation %s milliseconds", endTime-startTime));
    }
}
