package com.drfa.engine;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Sanjiv on 2/19/2015.
 */
public class MessageHandlerTest {

    @Test
    public void testMessageHandlerForExitMessage() throws Exception{
        BreakReport breakReport = mock(BreakReport.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakReport, processor);
        assertFalse(handler.handleMessage("Exit"));
    }

    @Test
    public void testMessageHandlerForSummaryMessageOfBase() throws Exception{
        BreakReport breakReport = new BreakReport();
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakReport, processor);
        String message = "SUMMARY:BASE:24";
        boolean continueMessage = handler.handleMessage(message);
        assertTrue(continueMessage);
        assertEquals(24, breakReport.getBaseTotalRecords());
    }
    @Test
    public void testMessageHandlerForSummaryMessageOfTarget() throws Exception{
        BreakReport breakReport = new BreakReport();
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakReport, processor);
        String message = "SUMMARY:TARGET:25";
        boolean continueMessage = handler.handleMessage(message);
        assertTrue(continueMessage);
        assertEquals(25, breakReport.getTargetTotalRecords());
    }

    @Test
    public void testEnrichBreakReportForOneSidedBreak()throws  Exception{
        Map<String, String> storageMap = new HashMap<String, String>();
        storageMap.put("BASE:C1", "Exist");
        storageMap.put("BASE:C2", "Exist");
        storageMap.put("TARGET:C1", "Exist");
        BreakReport breakReport = new BreakReport();
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakReport, processor);
        handler.enrichBreakReportWithOneSidedBreak(storageMap);
        assertEquals(2, breakReport.getBaseOneSidedBreaks());
        assertEquals(1, breakReport.getTargetOneSidedBreaks());
    }
    @Test
    public void testEnrichBreakReportForColumnBreaks(){
        BreakReport breakReport = new BreakReport();
        MessageProcessor processor = mock(MessageProcessor.class);
        when(processor.getMapOfBreaks()).thenReturn(populateMapOfBreaks());
        MessageHandler handler = new MessageHandler(breakReport, processor);
        handler.enrichBreakReportWithColumnDetails();

        List<Integer> columnBreakCountNonMatched = breakReport.getColumnBreaksCount().get("C1");
        assertEquals(10, columnBreakCountNonMatched.get(0).longValue());
        assertEquals(0, columnBreakCountNonMatched.get(1).longValue());
        List<Integer> columnBreakCountMatched = breakReport.getColumnBreaksCount().get("C3");

        assertEquals(0, columnBreakCountMatched.get(0).longValue());
        assertEquals(10, columnBreakCountMatched.get(1).longValue());

    }
    @Test
    public void testEnrichBreakReportForColumnBreaksWithMatchedNonMatched(){
        BreakReport breakReport = new BreakReport();
        MessageProcessor processor = mock(MessageProcessor.class);
        when(processor.getMapOfBreaks()).thenReturn(populateMapOfBreakswithMatchAndUnMatch());
        MessageHandler handler = new MessageHandler(breakReport, processor);
        handler.enrichBreakReportWithColumnDetails();
        System.out.println("Break Report: " + breakReport);

        List<Integer> columnBreakCountNonMatched = breakReport.getColumnBreaksCount().get("C1");
        assertEquals(5, columnBreakCountNonMatched.get(0).longValue());
        assertEquals(5, columnBreakCountNonMatched.get(1).longValue());
        List<Integer> columnBreakCountMatched = breakReport.getColumnBreaksCount().get("C3");

        assertEquals(5, columnBreakCountMatched.get(0).longValue());
        assertEquals(5, columnBreakCountMatched.get(1).longValue());

    }

    private Map<Integer, Map<String, List<String>>> populateMapOfBreaks(){
        Map<Integer, Map<String, List<String>>> mapOfBreaks = new HashMap<Integer, Map<String, List<String>>>();
        for(int j = 0; j<10; j++){
            Map<String, List<String>> mapOfRowBreaks = new HashMap<String, List<String>>();
            for (int i = 0; i < 10; i++) {
                List<String> column1 = new ArrayList<String>();
                column1.add("T" + i);
                column1.add("T" + (i + 1));
                if(i%3 == 0){
                    column1.add("MATCHED");
                }else {
                    column1.add("NOT MATCHED");
                }
                mapOfRowBreaks.put("C" + i, column1);
            }
            mapOfBreaks.put(new Integer(j), mapOfRowBreaks);
        }
        return mapOfBreaks;
    }

    private Map<Integer, Map<String, List<String>>> populateMapOfBreakswithMatchAndUnMatch(){
        Map<Integer, Map<String, List<String>>> mapOfBreaks = new HashMap<Integer, Map<String, List<String>>>();
        for(int j = 0; j<10; j++){
            Map<String, List<String>> mapOfRowBreaks = new HashMap<String, List<String>>();
            for (int i = 0; i < 10; i++) {
                List<String> column1 = new ArrayList<String>();
                column1.add("T" + i);
                column1.add("T" + (i + 1));
                if(i%3 == 0){
                    if(j%2 == 0) {
                        column1.add("MATCHED");
                    }else{
                        column1.add("NOT MATCHED");
                    }
                }else {
                    if(j%2 == 0) {
                        column1.add("MATCHED");
                    }else {
                        column1.add("NOT MATCHED");
                    }
                }
                mapOfRowBreaks.put("C" + i, column1);
            }
            mapOfBreaks.put(new Integer(j), mapOfRowBreaks);
        }
        return mapOfBreaks;
    }

}
