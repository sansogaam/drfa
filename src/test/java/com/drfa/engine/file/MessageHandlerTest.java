package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.messaging.MessagePublisher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;



public class MessageHandlerTest {

    public static List<ColumnAttribute> populateColumnNames() {
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-2", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-3", "SP-(B-NR|T-NR)-(R-NA)"));
        return columnAttributes;
    }

    @Test
    public void testMessageHandlerForExitMessage() throws Exception{
        MessagePublisher publisher = mock(MessagePublisher.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(processor, publisher);
        handler.handleMessage("PROCESS_ID:786-Exit");
        verify(publisher).publishResult(eq("PROCESS_ID:786-"), eq("MATCHED_RECORDS"), eq("0"));
    }

    @Test
    public void testMessageHandlerForSummaryMessageOfBase() throws Exception {
        MessagePublisher publisher = mock(MessagePublisher.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(processor, publisher);
        String message = "PROCESS_ID:786-SUMMARY:BASE:24";
        handler.handleMessage(message);
        verify(publisher).publishResult(eq("PROCESS_ID:786-"), eq("BASE_TOTAL_RECORDS"), eq("24"));
    }

    @Test
    public void testMessageHandlerForSummaryMessageOfTarget() throws Exception{
        MessagePublisher publisher = mock(MessagePublisher.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(processor, publisher);
        String message = "PROCESS_ID:786-SUMMARY:TARGET:25";
        handler.handleMessage(message);
        verify(publisher).publishResult(eq("PROCESS_ID:786-"), eq("TARGET_TOTAL_RECORDS"), eq("25"));
    }

    @Test
    public void testEnrichBreakReportForOneSidedBreak()throws  Exception{
        Map<String, String> storageMap = new HashMap<String, String>();
        storageMap.put("BASE:C1", "Exist");
        storageMap.put("BASE:C2", "Exist");
        storageMap.put("TARGET:C1", "Exist");
        MessagePublisher publisher = mock(MessagePublisher.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        MessageHandler handler = new MessageHandler(processor, publisher);
        handler.publishOneSidedBreak(storageMap, "PROCESS_ID:786-");
        verify(publisher, times(1)).publishResult(eq("PROCESS_ID:786-"), eq("BASE_ONE_SIDED_BREAK"), eq("2"));
        verify(publisher, times(1)).publishResult(eq("PROCESS_ID:786-"), eq("TARGET_ONE_SIDED_BREAK"), eq("1"));
    }

    @Test
    public void testEnrichBreakReportForOneSidedBreakWithDetails()throws  Exception{
        Map<String, String> storageMap = new HashMap<String, String>();
        storageMap.put("BASE:Exist1", "Exist1|Exist2|Exist3|Exist4");
        storageMap.put("BASE:Exist2", "Exist5|Exist6|Exist7|Exist8");
        storageMap.put("TARGET:Exist1", "Exist1|Exist2|Exist3|Exist4");
        MessagePublisher publisher = mock(MessagePublisher.class);

        Answer answer = mock(Answer.class);
        when(answer.getColumnAttribute()).thenReturn(populateColumnNames());
        when(answer.getFileDelimiter()).thenReturn("|");

        MessageProcessor processor = new MessageProcessor(answer);
        MessageHandler handler = new MessageHandler(processor, publisher);
        handler.publishOneSidedBreak(storageMap, "PROCESS_ID:786-");
        verify(publisher, times(1)).publishResult(eq("PROCESS_ID:786-"), eq("BASE_ONE_SIDED_BREAK"), eq("2"));
        verify(publisher, times(1)).publishResult(eq("PROCESS_ID:786-"), eq("TARGET_ONE_SIDED_BREAK"), eq("1"));
        verify(publisher, times(1)).publishResult(eq("PROCESS_ID:786-"), eq("ONE-SIDED-BASE-C3~Exist3$C4~Exist4$C1~Exist1$C2~Exist2$"));
        verify(publisher, times(1)).publishResult(eq("PROCESS_ID:786-"), eq("ONE-SIDED-TARGET-C3~Exist3$C4~Exist4$C1~Exist1$C2~Exist2$"));
        verify(publisher, times(1)).publishResult(eq("PROCESS_ID:786-"), eq("ONE-SIDED-BASE-C3~Exist7$C4~Exist8$C1~Exist5$C2~Exist6$"));
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
