package com.drfa.report;


import org.apache.log4j.Logger;

import java.util.Date;

public class ReportGenerator {
    private static Logger LOG = Logger.getLogger(ReportListener.class);
    private BreakReport breakReport;
    private ReportEnricher reportEnricher;

    public ReportGenerator() {
        this.breakReport = new BreakReport();
        this.reportEnricher = new ReportEnricher(breakReport);
    }

    public void generateReport(String messageBody) {
        reportEnricher.enrich(messageBody);
        if (messageBody.contains("MATCHED_RECORDS")) {
            reportEnricher.enrichBreakReportWithColumnDetails();
            generateReport();
        }
    }

    private void generateReport() {
        long startTime = System.currentTimeMillis();
        String htmlReportPath = "target/test-output/" + "HTML-" + new Date().getTime() + ".html";
        LOG.info(String.format("Report written on path %s with type %s", htmlReportPath, "BOTH"));
        ReportDecorator reportDecorator = new HTMLReportDecorator(breakReport, "BOTH");
        reportDecorator.decorateReport(htmlReportPath);
        long endTime = System.currentTimeMillis();
        LOG.info(String.format("Total time taken by reconciliation %s milliseconds", endTime - startTime));
    }
}
