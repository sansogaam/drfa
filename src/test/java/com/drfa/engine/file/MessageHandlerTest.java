package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.messaging.MessagePublisher;
import com.drfa.report.ResultMessageConstants;
import com.drfa.util.DrfaProperties;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
        Answer answer = mock(Answer.class);
        MessageHandler handler = new MessageHandler(processor, publisher, answer);
        handler.handleMessage(DrfaProperties.PROCESS_PREFIX+"786-Exit");
        verify(publisher).publishResult(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.MATCHED_RECORDS), eq("0"));
    }

    @Test
    public void testMessageHandlerForSummaryMessageOfBase() throws Exception {
        MessagePublisher publisher = mock(MessagePublisher.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        Answer answer = mock(Answer.class);
        MessageHandler handler = new MessageHandler(processor, publisher, answer);
        String message = DrfaProperties.PROCESS_PREFIX+"786-"+DrfaProperties.SUMMARY_PREFIX+DrfaProperties.BASE_PREFIX+"24";
        handler.handleMessage(message);
        verify(publisher).publishResult(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.BASE_TOTAL_RECORDS), eq("24"));
    }

    @Test
    public void testMessageHandlerForSummaryMessageOfTarget() throws Exception{
        MessagePublisher publisher = mock(MessagePublisher.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        Answer answer = mock(Answer.class);
        MessageHandler handler = new MessageHandler(processor, publisher, answer);
        String message = DrfaProperties.PROCESS_PREFIX+"786-"+DrfaProperties.SUMMARY_PREFIX+DrfaProperties.TARGET_PREFIX+"25";
        handler.handleMessage(message);
        verify(publisher).publishResult(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.TARGET_TOTAL_RECORDS), eq("25"));
    }

    @Test
    public void testEnrichBreakReportForOneSidedBreak()throws  Exception{
        Map<String, String> storageMap = new HashMap<String, String>();
        storageMap.put(DrfaProperties.BASE_PREFIX+"C1", "Exist");
        storageMap.put(DrfaProperties.BASE_PREFIX+"C2", "Exist");
        storageMap.put(DrfaProperties.TARGET_PREFIX+"C1", "Exist");
        MessagePublisher publisher = mock(MessagePublisher.class);
        MessageProcessor processor = mock(MessageProcessor.class);
        Answer answer = mock(Answer.class);
        when(answer.quote()).thenReturn(Pattern.quote("|"));
        MessageHandler handler = new MessageHandler(processor, publisher, answer);
        handler.publishOneSidedBreak(storageMap, DrfaProperties.PROCESS_PREFIX+"786-");
        verify(publisher, times(1)).publishResult(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.BASE_ONE_SIDED_BREAK), eq("2"));
        verify(publisher, times(1)).publishResult(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.TARGET_ONE_SIDED_BREAK), eq("1"));
    }

    @Test
    public void testEnrichBreakReportForOneSidedBreakWithDetails()throws  Exception{
        Map<String, String> storageMap = new HashMap<String, String>();
        storageMap.put(DrfaProperties.BASE_PREFIX+"Exist1", "Exist1|Exist2|Exist3|Exist4");
        storageMap.put(DrfaProperties.BASE_PREFIX+"Exist2", "Exist5|Exist6|Exist7|Exist8");
        storageMap.put(DrfaProperties.TARGET_PREFIX+"Exist1", "Exist1|Exist2|Exist3|Exist4");
        MessagePublisher publisher = mock(MessagePublisher.class);

        Answer answer = mock(Answer.class);
        when(answer.getColumnAttribute()).thenReturn(populateColumnNames());
        when(answer.quote()).thenReturn(Pattern.quote("|"));

        MessageProcessor processor = new MessageProcessor(answer);
        MessageHandler handler = new MessageHandler(processor, publisher, answer);
        handler.publishOneSidedBreak(storageMap, DrfaProperties.PROCESS_PREFIX+"786-");
        verify(publisher, times(1)).publishResult(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.BASE_ONE_SIDED_BREAK), eq("2"));
        verify(publisher, times(1)).publishResult(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.TARGET_ONE_SIDED_BREAK), eq("1"));
        verify(publisher, times(2)).publishOneSideBreak(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.ONE_SIDED_BASE), any(Map.class));
        verify(publisher, times(1)).publishOneSideBreak(eq(DrfaProperties.PROCESS_PREFIX+"786-"), eq(ResultMessageConstants.ONE_SIDED_TARGET), any(Map.class));
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
                    column1.add(ResultMessageConstants.MATCHED);
                }else {
                    column1.add(ResultMessageConstants.NOT_MATCHED);
                }
                mapOfRowBreaks.put("C" + i, column1);
            }
            mapOfBreaks.put(new Integer(j), mapOfRowBreaks);
        }
        return mapOfBreaks;
    }
}
