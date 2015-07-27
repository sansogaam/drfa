package com.drfa.engine;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.engine.meta.MetaDataParser;
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
        List<ColumnAttribute> columnAttributes = dataParser.getColumnAttributes();
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnAttributes(columnAttributes);
        Comparator comparator = new ComparatorFactory(context, answer).getComparator(answer.getReconciliationType());
        BreakReport report = comparator.compare();
        String htmlReportPath = answer.getReportOutputPath() + File.separator + "HTML-"+new Date().getTime()+".html";
        LOG.info(String.format("Report written on path %s with type %s", htmlReportPath, answer.getReportCategory()));
        ReportDecorator reportDecorator = new HTMLReportDecorator(report, answer.getReportCategory());
        reportDecorator.decorateReport(htmlReportPath);
        long endTime = System.currentTimeMillis();
        LOG.info(String.format("Total time taken by reconciliation %s milliseconds", endTime-startTime));
    }

    public static void main(String args[]){
        System.out.println("This is a test of reconciliation...");
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnAttributes(populateColumnNames());
        Answer answer = new Answer();
        answer.setKeyIndex(0);
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("FILE");
        answer.setBaseFile("D:/dev/test.csv");
        answer.setFileDelimiter("|");
        answer.setTargetFile("D:/dev/test1.csv");
        answer.setMetaDataFile("D:/dev/testing.fmt");
        answer.setPluginPath("D:/dev");
        answer.setReportOutputPath("D:/dev");
        Comparator comparator = new CsvFileComparator(context, answer);
        long startTime = System.currentTimeMillis();
        try {
            BreakReport report = comparator.compare();
            String htmlReportPath = answer.getReportOutputPath() + File.separator + "HTML-"+new Date().getTime()+".html";
            LOG.info(String.format("Report written on path %s", htmlReportPath));
            ReportDecorator reportDecorator = new HTMLReportDecorator(report, "BOTH");
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

    public static List<ColumnAttribute> populateColumnNames(){
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C2", "Date", "B-1|T-1", "DF-(B-dd-MM-yyyy|T-dd/MM/yyyy)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C3", "Integer", "B-2|T-2", "TA-(B-NR|T-NR)-(R-1)"));
        columnAttributes.add(new ColumnAttribute("C4", "Date", "B-3|T-3", "DF-(B-dd-MM-yyyy|T-dd-MM-yyyy)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C5", "Integer", "B-4|T-4", "TP-(B-NR|T-NR)-(R-10)"));
        return columnAttributes;
    }

}
