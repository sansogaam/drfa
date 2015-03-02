package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.db.MetaDataParser;
import com.drfa.engine.report.BreakReport;
import com.drfa.engine.file.CsvFileComparator;
import com.drfa.engine.report.HTMLReportDecorator;
import com.drfa.engine.report.ReportDecorator;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sanjiv on 2/10/2015.
 */
public class ReconciliationEngine {

    Answer answer;

    static Logger LOG = Logger.getLogger(ReconciliationEngine.class);

    public ReconciliationEngine(Answer answer){
        this.answer = answer;

    }

    public void reconcile() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        MetaDataParser dataParser = new MetaDataParser(answer.getMetaDataFile(), answer.getPluginPath());
        List<String> columnNames = dataParser.getMetaDataColumnNames();
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnNames(columnNames);
        Comparator comparator = new ComparatorFactory(context, answer).getComparator(answer.getReconciliationType());
        BreakReport report = comparator.compare();
        String htmlReportPath = answer.getSummaryOutputPath() + File.separator + "HTML-"+new Date().getTime()+".html";
        LOG.info(String.format("Report written on path %s", htmlReportPath));
        ReportDecorator reportDecorator = new HTMLReportDecorator(report);
        reportDecorator.decorateReport(htmlReportPath);
        long endTime = System.currentTimeMillis();
        LOG.info(String.format("Total time taken by reconciliation %s milliseconds", endTime-startTime));
    }

    public static void main(String args[]){
        System.out.println("This is a test of reconciliation...");
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnNames(populateColumnNames());
        Answer answer = new Answer();
        answer.setKeyIndex(1);
        answer.setReconciliationType("FILE");
        answer.setBaseFile("D:/dev/test.csv");
        answer.setTargetFile("D:/dev/test1.csv");
        answer.setMetaDataFile("D:/dev/testing.fmt");
        answer.setPluginPath("D:/dev");
        answer.setSummaryOutputPath("D:/dev");
        Comparator comparator = new CsvFileComparator(context, answer);
        long startTime = System.currentTimeMillis();
        try {
            BreakReport report = comparator.compare();
            String htmlReportPath = answer.getSummaryOutputPath() + File.separator + "HTML-"+new Date().getTime()+".html";
            LOG.info(String.format("Report written on path %s", htmlReportPath));
            ReportDecorator reportDecorator = new HTMLReportDecorator(report);
            reportDecorator.decorateReport(htmlReportPath);
            System.out.println("Reporting tool: " + report);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Total time taken by comparator %s milliseconds", endTime-startTime));
    }

    public static List<String> populateColumnNames(){
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("C1");
        columnNames.add("C2");
        columnNames.add("C3");
        columnNames.add("C4");
        return columnNames;
    }

}
