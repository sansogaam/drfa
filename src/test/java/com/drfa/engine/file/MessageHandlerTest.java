package com.drfa.engine.file;

import com.drfa.engine.ReconciliationContext;
import com.drfa.engine.meta.ColumnAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * Created by Sanjiv on 2/19/2015.
 */
public class MessageHandlerTest {


    @Test
    public void testMessageHandlerForExitMessage() throws Exception{
        BreakEvent breakEvent = mock(BreakEvent.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakEvent, processor);
        handler.handleMessage("Exit");
        verify(breakEvent).publisher(eq("MATCHED_RECORDS-0"),anyString());
    }

    @Test
    public void testMessageHandlerForSummaryMessageOfBase() throws Exception {
        BreakEvent breakEvent = mock(BreakEvent.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakEvent, processor);
        String message = "SUMMARY:BASE:24";
        handler.handleMessage(message);
        verify(breakEvent).publisher(eq("BASE_TOTAL_RECORDS-24"), anyString());
    }
        
    @Test
    public void testMessageHandlerForSummaryMessageOfTarget() throws Exception{
        BreakEvent breakEvent = mock(BreakEvent.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakEvent, processor);
        String message = "SUMMARY:TARGET:25";
        handler.handleMessage(message);
        verify(breakEvent).publisher(eq("TARGET_TOTAL_RECORDS-25"), anyString());
    }

    @Test
    public void testEnrichBreakReportForOneSidedBreak()throws  Exception{
        Map<String, String> storageMap = new HashMap<String, String>();
        storageMap.put("BASE:C1", "Exist");
        storageMap.put("BASE:C2", "Exist");
        storageMap.put("TARGET:C1", "Exist");
        BreakEvent breakEvent = mock(BreakEvent.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(breakEvent, processor);
        handler.enrichBreakReportWithOneSidedBreak(storageMap);
        verify(breakEvent, times(2)).publisher(anyString(), anyString());
        verify(breakEvent, times(1)).publisher(eq("BASE_ONE_SIDED_BREAK-2"), anyString());
        verify(breakEvent, times(1)).publisher(eq("TARGET_ONE_SIDED_BREAK-1"), anyString());
    }

    @Test
    public void testEnrichBreakReportForOneSidedBreakWithDetails()throws  Exception{
        Map<String, String> storageMap = new HashMap<String, String>();
        storageMap.put("BASE:Exist1", "Exist1|Exist2|Exist3|Exist4");
        storageMap.put("BASE:Exist2", "Exist5|Exist6|Exist7|Exist8");
        storageMap.put("TARGET:Exist1", "Exist1|Exist2|Exist3|Exist4");
        BreakEvent breakEvent = mock(BreakEvent.class);
        ReconciliationContext context = mock(ReconciliationContext.class);
        when(context.getColumnAttributes()).thenReturn(populateColumnNames());
        when(context.getFileDelimiter()).thenReturn("|");
        MessageProcessor processor = new MessageProcessor(context);
        MessageHandler handler = new MessageHandler(breakEvent, processor);
        handler.enrichBreakReportWithOneSidedBreak(storageMap);
        verify(breakEvent, times(5)).publisher(anyString(), anyString());
        verify(breakEvent, times(1)).publisher(eq("BASE_ONE_SIDED_BREAK-2"), anyString());
        verify(breakEvent, times(1)).publisher(eq("TARGET_ONE_SIDED_BREAK-1"), anyString());
        verify(breakEvent, times(1)).publisher(eq("C3~Exist3$C4~Exist4$C1~Exist1$C2~Exist2$"), eq("queue://BREAK_MESSAGE-BASE"));
        verify(breakEvent, times(1)).publisher(eq("C3~Exist3$C4~Exist4$C1~Exist1$C2~Exist2$"), eq("queue://BREAK_MESSAGE-TARGET"));
        verify(breakEvent, times(1)).publisher(eq("C3~Exist7$C4~Exist8$C1~Exist5$C2~Exist6$"), eq("queue://BREAK_MESSAGE-BASE"));
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

    public static List<ColumnAttribute> populateColumnNames(){
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", ""));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", ""));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-2", ""));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-3", ""));
        return columnAttributes;
    }
}
