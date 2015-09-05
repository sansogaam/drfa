package com.drfa.report;

import com.drfa.engine.EngineConstants;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by Sanjiv on 2/28/2015.
 */
public class HTMLReportDecorator implements ReportDecorator {
    private BreakReport report;
    private String typeOfReport;
    StringBuffer sb = new StringBuffer();

    public HTMLReportDecorator(BreakReport report, String typeOfReport) {
        this.report = report;
        this.typeOfReport = typeOfReport;
    }

    @Override
    public void decorateReport(String filePath) {
        applyHTMLHeader();
        applyStyleSheet();
        applyBodyStart();
        applyBodyContent();
        applyBodyEnd();
        applyHTMLFooter();
        writeHTMLString(filePath);
    }

    private void writeHTMLString(String filePath) {
        Path file = Paths.get(filePath);
        Charset charset = Charset.forName("US-ASCII");
        String s = sb.toString();
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void applyBodyContent() {
        if("SUMMARY".equalsIgnoreCase(typeOfReport)){
            applySummaryReport();
            applyColumnSummaryReport();
        }else if("DETAILED".equalsIgnoreCase(typeOfReport)) {
            applyColumnDetailedReport();
        }else if("BOTH".equalsIgnoreCase(typeOfReport)){
            applySummaryReport();
            applyColumnSummaryReport();
            applyColumnDetailedReport();
        }
    }

    private void applyColumnDetailedReport() {
        int numberOfColumns = report.getColumnBreaksCount().size();
        sb.append("<br/><br/>");
        sb.append("<table id=\"columnsummary\">");
        sb.append("<tr><th colspan='").append(numberOfColumns *4).append("'> Column Wise Detailed Report</th></tr>");
        sb.append("<tr>");
        for (String columnName : report.getColumnBreaksCount().keySet()) {
            sb.append("<th colspan='4'>").append(columnName).append("</th>");
        }
        sb.append("</tr>");
        Map<Integer, Map<String, List<String>>> mapOfBreaks = report.getMapOfBreaks();
        int count = 0;
        for (Integer row : mapOfBreaks.keySet()) {
            if (count % 2 == 0) {
                sb.append("<tr class='alt'>");
            }else {
                sb.append("<tr>");
            }
            Map<String, List<String>> mapOfColumnBreaks = mapOfBreaks.get(row);
            for (String columnName : mapOfColumnBreaks.keySet()) {
                sb.append("<td>").append(columnName).append("</td>");
                List<String> values = mapOfColumnBreaks.get(columnName);
                for (String value : values) {
                    if (value != null && value.length() > 256) {
                        value = value.substring(0, 256) + "~More";
                    }
                    if(EngineConstants.NOT_MATCHED.equalsIgnoreCase(value)){
                        sb.append("<td><font color='red'>").append(value).append("</font></td>");
                    }else {
                        sb.append("<td>").append(value).append("</td>");
                    }
                }
            }
            sb.append("</tr>");
            count++;
        }
        sb.append("</table>");
        applyOneSidedBreaks(report.getBaseOneSidedBreaksCollection(), EngineConstants.BASE_THREAD_NAME);
        applyOneSidedBreaks(report.getTargetOneSidedBreaksCollection(), EngineConstants.TARGET_THREAD_NAME);
    }
    
    private void applyOneSidedBreaks(Map<Integer,Map<String, String>> mapOfOneSidedBreaks,String threadName){
        if(mapOfOneSidedBreaks == null || mapOfOneSidedBreaks.isEmpty()){
            return;
        }
        sb.append("<br/><br/>");
        int numberOfColumns = report.getColumnBreaksCount().size();
        sb.append("<table id=\"columnsummary\">");
        sb.append("<tr><th colspan='").append(numberOfColumns *2).append("'> One Side Detailed Report For: ").append(threadName).append("</th></tr>");
        sb.append("<tr>");
        for (String columnName : report.getColumnBreaksCount().keySet()) {
            sb.append("<th colspan='2'>").append(columnName).append("</th>");
        }
        sb.append("</tr>");
        int altCount =0;
        for(Integer rowCount : mapOfOneSidedBreaks.keySet()){
            if(altCount % 2 == 0) {
                sb.append("<tr class='alt'>");
            }else{
                sb.append("<tr>");
            }
            Map<String, String> mapOfBreaks = mapOfOneSidedBreaks.get(rowCount);
            for(String columnName: mapOfBreaks.keySet()) {
                sb.append("<td>").append(columnName).append("</td>");
                sb.append("<td>").append(mapOfBreaks.get(columnName)).append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
    }
    

    private void applyColumnSummaryReport() {
        sb.append("<table id=\"columnsummary\">");
        sb.append("<tr><th colspan='3'> Column Wise Summary Report</th></tr>");
        sb.append("<tr>");
        sb.append("<th>Column Name</th>");
        sb.append("<th>Non Matched Records</th>");
        sb.append("<th>Matched Records</th>");
        sb.append("</tr>");
        Map<String, List<Integer>> columnValues = report.getColumnBreaksCount();
        int count = 0;
        for (String key : columnValues.keySet()) {
            List<Integer> columnValue = columnValues.get(key);
            if(columnValue != null) {
                int nonMatchedRecords = columnValue.get(0);
                int matchedRecords = columnValue.get(1);
                if (count % 2 == 0) {
                    sb.append("<tr class='alt'>");
                } else {
                    sb.append("<tr>");
                }
                sb.append("<td>").append(key).append("</td>");
                sb.append("<td>").append(nonMatchedRecords).append("</td>");
                sb.append("<td>").append(matchedRecords).append("</td>");
                sb.append("</tr>");
            }
            count++;
        }
        sb.append("</table>");
    }

    private void applySummaryReport() {
        sb.append("<table id=\"summary\">");
        sb.append("<tr><th colspan='2'> Summary Report </th></tr>");

        sb.append("<tr>");
        sb.append("<td> Number Of Total Records in Base</td>");
        sb.append("<td>").append(report.getBaseTotalRecords()).append("</td>");
        sb.append("</tr>");

        sb.append("<tr class='alt'>");
        sb.append("<td> Number Of Total Records in Target</td>");
        sb.append("<td>").append(report.getTargetTotalRecords()).append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td> Number Of Matched Records as per key</td>");
        sb.append("<td>").append(report.getMatchedWithNumberOfKeys()).append("</td>");
        sb.append("</tr>");

        sb.append("<tr class='alt'>");
        sb.append("<td> Number Of extra records in Base</td>");
        sb.append("<td>").append(report.getBaseOneSidedBreaks()).append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td> Number Of extra records in Target</td>");
        sb.append("<td>").append(report.getTargetOneSidedBreaks()).append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("<br/><br/>");
    }

    private void applyHTMLHeader() {
        sb.append("<html>");
    }

    private void applyBodyStart() {
        sb.append("<body>");
    }

    private void applyBodyEnd() {
        sb.append("</body>");
    }

    private void applyHTMLFooter() {
        sb.append("</html>");
    }

    private void applyStyleSheet() {
        sb.append("<head>");
        sb.append("<style>");
        sb.append("#summary");
        sb.append("{");
        sb.append("	font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;");
        sb.append("	width:100%;");
        sb.append("	border-collapse:collapse;");
        sb.append("}");
        sb.append("#summary td, #summary th ");
        sb.append("{");
        sb.append("	font-size:1.2em;");
        sb.append("	border:1px solid #98bf21;");
        sb.append("	padding:3px 7px 2px 7px;");
        sb.append("}");
        sb.append("#summary th ");
        sb.append("{");
        sb.append("	font-size:1.4em;");
        sb.append("	text-align:center;");
        sb.append("	padding-top:5px;");
        sb.append("	padding-bottom:4px;");
        sb.append("	background-color:#A7C942;");
        sb.append("	color:#fff;");
        sb.append("}");
        sb.append("#summary tr.alt td ");
        sb.append("{");
        sb.append("	color:#000;");
        sb.append("	background-color:#EAF2D3;");
        sb.append("}");
        sb.append("#columnsummary");
        sb.append("{");
        sb.append("	font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;");
        sb.append("	width:100%;");
        sb.append("	border-collapse:collapse;");
        sb.append("}");
        sb.append("#columnsummary td, #columnsummary th ");
        sb.append("{");
        sb.append("	font-size:1.2em;");
        sb.append("	border:1px solid #98bf21;");
        sb.append("	padding:3px 7px 2px 7px;");
        sb.append("}");
        sb.append("#columnsummary th ");
        sb.append("{");
        sb.append("	font-size:1.4em;");
        sb.append("	text-align:center;");
        sb.append("	padding-top:5px;");
        sb.append("	padding-bottom:4px;");
        sb.append("	background-color:#A7C942;");
        sb.append("	color:#fff;");
        sb.append("}");
        sb.append("#columnsummary tr.alt td ");
        sb.append("{");
        sb.append("	color:#000;");
        sb.append("	background-color:#EAF2D3;");
        sb.append("}");
        sb.append("");

        sb.append("#columndetailed");
        sb.append("{");
        sb.append("	font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;");
        sb.append("	width:100%;");
        sb.append("	border-collapse:collapse;");
        sb.append("}");
        sb.append("#columndetailed td, #columndetailed th ");
        sb.append("{");
        sb.append("	font-size:1.2em;");
        sb.append("	border:1px solid #98bf21;");
        sb.append("	padding:3px 7px 2px 7px;");
        sb.append("}");
        sb.append("#columndetailed th ");
        sb.append("{");
        sb.append("	font-size:1.4em;");
        sb.append("	text-align:center;");
        sb.append("	padding-top:5px;");
        sb.append("	padding-bottom:4px;");
        sb.append("	background-color:#A7C942;");
        sb.append("	color:#fff;");
        sb.append("}");
        sb.append("#columndetailed tr.alt td ");
        sb.append("{");
        sb.append("	color:#000;");
        sb.append("	background-color:#EAF2D3;");
        sb.append("}");
        sb.append("");

        sb.append("</style>");
        sb.append("</head>");
    }
}
