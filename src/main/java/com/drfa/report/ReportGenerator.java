package com.drfa.report;


import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Date;

import static com.drfa.report.ResultMessageConstants.TYPE;

class ReportGenerator {
    private static Logger LOG = Logger.getLogger(ReportGenerator.class);
    private BreakReport breakReport;
    private ReportEnricher reportEnricher;

    public ReportGenerator() {
        this.breakReport = new BreakReport();
        this.reportEnricher = new ReportEnricher(breakReport);
    }

    public void generateReport(JSONObject json) {
        reportEnricher.enrich(json);
        if (json.has(TYPE) && "MATCHED_RECORDS".equals(json.get(TYPE))) {
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
