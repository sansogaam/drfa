package com.drfa.report;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.drfa.report.ResultMessageConstants.*;


class ReportEnricher implements Enricher {
    static Logger LOG = Logger.getLogger(ReportEnricher.class);
    public BreakReport report;


    public ReportEnricher(BreakReport report) {
        this.report = report;
    }

    @Override
    public void enrich(JSONObject json) {
        if (json.has(TYPE)) {
            String records = (String) json.get(TYPE_NO_RECORDS);
            if ("MATCHED_RECORDS".equals(json.get(TYPE))) {
                enrichMatchedNumberOfRecords(records);
            } else if ("BASE_TOTAL_RECORDS".equals(json.get(TYPE))) {
                enrichBaseTotalRecords(records);
            } else if ("TARGET_TOTAL_RECORDS".equals(json.get(TYPE))) {
                enrichTargetTotalRecords(records);
            } else if ("BASE_ONE_SIDED_BREAK".equals(json.get(TYPE))) {
                enrichBaseOneSidedBreaksRecords(records);
            }

        } else {
            String messageWithoutProcessId = (String) json.get(FULL_TEXT);
            if (messageWithoutProcessId.startsWith("TARGET_ONE_SIDED_BREAK")) {
                enrichTargetOneSideBreakRecords(messageWithoutProcessId);
            } else if (messageWithoutProcessId.startsWith("ONE-SIDED-BASE")) {
                enrichOneSideBreakRecords(messageWithoutProcessId, "BASE");
            } else if (messageWithoutProcessId.startsWith("ONE-SIDED-TARGET")) {
                enrichOneSideBreakRecords(messageWithoutProcessId, "TARGET");
            } else {
                enrichDetailMessageReport(messageWithoutProcessId);
            }
        }

    }

    private void enrichOneSideBreakRecords(String message, String type) {
        //C3~Exist3$C4~Exist4$C1~Exist1$C2~Exist2$
        String breakMessage = message.substring(message.lastIndexOf("-"), message.length());
        String splitMessages[] = breakMessage.split(Pattern.quote("$"));
        Map<String, String> mapOfColumnBreaks = new HashMap<String, String>();
        for (String splitMessage : splitMessages) {
            String columnSplitMessage[] = splitMessage.split(Pattern.quote("~"));
            mapOfColumnBreaks.put(columnSplitMessage[0], columnSplitMessage[1]);
        }
        int rowCount;
        Map<Integer, Map<String, String>> mapOfOneSidedRowBreaks = report.getBaseOneSidedBreaksCollection();
        if (mapOfOneSidedRowBreaks == null || mapOfOneSidedRowBreaks.isEmpty()) {
            rowCount = 1;
            mapOfOneSidedRowBreaks = new HashMap<Integer, Map<String, String>>();
            mapOfOneSidedRowBreaks.put(new Integer(rowCount), mapOfColumnBreaks);
        } else {
            rowCount = mapOfColumnBreaks.size() + 1;
            mapOfOneSidedRowBreaks.put(new Integer(rowCount), mapOfColumnBreaks);
        }
        if ("BASE".equalsIgnoreCase(type)) {
            report.setBaseOneSidedBreaksCollection(mapOfOneSidedRowBreaks);
        } else {
            report.setTargetOneSidedBreaksCollection(mapOfOneSidedRowBreaks);
        }
    }

    private void enrichTargetOneSideBreakRecords(String message) {
        String totalRecords = message.substring(message.lastIndexOf("-") + 1, message.length());
        LOG.info(String.format("Total number of matched records as per key %s", totalRecords));
        report.setTargetOneSidedBreaks(new Integer(totalRecords));
    }

    private void enrichBaseOneSidedBreaksRecords(String totalRecords) {
        report.setBaseOneSidedBreaks(new Integer(totalRecords));
    }

    private void enrichTargetTotalRecords(String totalRecords) {
        report.setTargetTotalRecords(new Integer(totalRecords));
    }

    private void enrichBaseTotalRecords(String totalRecords) {
        report.setBaseTotalRecords(new Integer(totalRecords));
    }

    private void enrichMatchedNumberOfRecords(String totalMatchedRecords) {
        report.setMatchedWithNumberOfKeys(new Integer(totalMatchedRecords));
    }

    private void enrichDetailMessageReport(String message) {
        Map<String, List<String>> mapOfBreaks = new HashMap<String, List<String>>();
        String splitMessages[] = message.split(Pattern.quote("$"));
        for (String splitMessage : splitMessages) {
            String columnSplitMessage[] = splitMessage.split(Pattern.quote("~"));
            String columnName = columnSplitMessage[0];
            String columnValues[] = columnSplitMessage[1].split(Pattern.quote("#"));
            List<String> columnResults = new ArrayList<String>();
            for (String columnValue : columnValues) {
                columnResults.add(columnValue);
            }
            mapOfBreaks.put(columnName, columnResults);
        }
        int rowCount;
        Map<Integer, Map<String, List<String>>> mapOfRowBreaks = report.getMapOfBreaks();
        if (mapOfRowBreaks == null || mapOfRowBreaks.isEmpty()) {
            rowCount = 1;
            mapOfRowBreaks = new HashMap<Integer, Map<String, List<String>>>();
            mapOfRowBreaks.put(new Integer(rowCount), mapOfBreaks);
        } else {
            rowCount = mapOfRowBreaks.size() + 1;
            mapOfRowBreaks.put(new Integer(rowCount), mapOfBreaks);
        }
        LOG.info(String.format("Map of row breaks size %s", mapOfRowBreaks.size()));
        report.setMapOfBreaks(mapOfRowBreaks);
    }

    public void enrichBreakReportWithColumnDetails() {
        Map<Integer, Map<String, List<String>>> mapOfBreaks = report.getMapOfBreaks();
        Map<String, List<Integer>> mapOfColumnBreaks = new HashMap<String, List<Integer>>();
        for (Integer i : mapOfBreaks.keySet()) {
            Map<String, List<String>> columnBreakes = mapOfBreaks.get(i);
            for (String columnName : columnBreakes.keySet()) {
                List<String> values = columnBreakes.get(columnName);
                List<Integer> listNumberOfBreaks = mapOfColumnBreaks.get(columnName);
                if (values.get(2).equalsIgnoreCase("NOT_MATCHED")) {
                    if (listNumberOfBreaks == null) {
                        listNumberOfBreaks = new ArrayList<Integer>();
                        listNumberOfBreaks.add(0, new Integer(1));
                        listNumberOfBreaks.add(1, new Integer(0));
                    } else {
                        int existingValue = listNumberOfBreaks.get(0) + 1;
                        listNumberOfBreaks.remove(0);
                        listNumberOfBreaks.add(0, existingValue);
                    }
                } else if (values.get(2).equalsIgnoreCase("MATCHED")) {
                    if (listNumberOfBreaks == null) {
                        listNumberOfBreaks = new ArrayList<Integer>();
                        listNumberOfBreaks.add(0, new Integer(0));
                        listNumberOfBreaks.add(1, new Integer(1));
                    } else {
                        int existingValue = listNumberOfBreaks.get(1) + 1;
                        listNumberOfBreaks.remove(1);
                        listNumberOfBreaks.add(1, existingValue);
                    }
                }
                mapOfColumnBreaks.put(columnName, listNumberOfBreaks);
            }
        }
        report.setColumnBreaksCount(mapOfColumnBreaks);
    }
}
