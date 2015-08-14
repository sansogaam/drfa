package com.drfa.engine.file;


import com.drfa.engine.ReconciliationContext;
import com.drfa.engine.file.MessageProcessor;
import com.drfa.engine.meta.ColumnAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MessageProcessorTest {

    @Test
    public void testProcessMessageForDeterminingTheBase() throws Exception{
        String message = "BASE:T1|T2|T3|T4$T1|T2.1|T3|T4";
        BreakEvent breakEvent = mock(BreakEvent.class);
        ReconciliationContext context = mock(ReconciliationContext.class);
        when(context.getColumnAttributes()).thenReturn(populateColumnNames());
        when(context.getFileDelimiter()).thenReturn("|");
        MessageProcessor messageProcessor = new MessageProcessor(context);
        messageProcessor.processMessage(breakEvent,message);
        verify(breakEvent, times(1)).publisher(anyString(), anyString());
        verify(breakEvent, times(1)).publisher(eq("C3~T3#T3#MATCHED#$C4~T4#T4#MATCHED#$C1~T1#T1#MATCHED#$C2~T2#T2.1#NOT MATCHED#$"), anyString());
    }
    
    public static List<ColumnAttribute> populateColumnNames(){
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-2", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-3", "SP-(B-NR|T-NR)-(R-NA)"));
        return columnAttributes;
    }

}
