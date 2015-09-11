package com.drfa.report;

import com.drfa.jms.ActiveMqListener;
import com.drfa.util.DrfaProperties;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Date;


public class ReportServer implements MessageListener {

    static Logger LOG = Logger.getLogger(ReportServer.class);

    private BreakReport breakReport;
    private ReportEnricher reportEnricher;

    public ReportServer() {
        this.breakReport = new BreakReport();
        this.reportEnricher = new ReportEnricher(breakReport);
    }


    public static void main(String args[]) {
        try {
            ReportServer reportServer = new ReportServer();
            new ActiveMqListener(reportServer).startMsgListener(DrfaProperties.BREAK_MESSAGE_QUEUE, DrfaProperties.BROKER_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMessage(Message message) {
        System.out.println("Got the message....");
        if (message instanceof TextMessage) {
            String messageBody = null;
            try {
                messageBody = ((TextMessage) message).getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            LOG.info(String.format("Received the message %s ", messageBody));
            System.out.println(String.format("Received the message %s ", messageBody));
            reportEnricher.enrich(messageBody);
            if (messageBody.contains("MATCHED_RECORDS")) {
                reportEnricher.enrichBreakReportWithColumnDetails();
                generateReport(breakReport);
            }
        }

    }

    public void generateReport(BreakReport breakReport) {
        long startTime = System.currentTimeMillis();
        String htmlReportPath = "target/test-output/" + "HTML-" + new Date().getTime() + ".html";
        LOG.info(String.format("Report written on path %s with type %s", htmlReportPath, "BOTH"));
        ReportDecorator reportDecorator = new HTMLReportDecorator(breakReport, "BOTH");
        reportDecorator.decorateReport(htmlReportPath);
        long endTime = System.currentTimeMillis();
        LOG.info(String.format("Total time taken by reconciliation %s milliseconds", endTime - startTime));
    }
}
