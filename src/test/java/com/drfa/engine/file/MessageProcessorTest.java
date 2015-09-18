package com.drfa.engine.file;


import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.messaging.MessagePublisher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MessageProcessorTest {

    public static List<ColumnAttribute> populateColumnNames() {
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-2", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-3", "SP-(B-NR|T-NR)-(R-NA)"));
        return columnAttributes;
    }

    @Test
    public void testProcessMessageForDeterminingTheBase() throws Exception {
        String message = "BASE:T1|T2|T3|T4$T1|T2.1|T3|T4";
        MessagePublisher publisher = mock(MessagePublisher.class);

        Answer answer = mock(Answer.class);
        when(answer.getColumnAttribute()).thenReturn(populateColumnNames());
        when(answer.getFileDelimiter()).thenReturn("|");

        MessageProcessor messageProcessor = new MessageProcessor(answer);
        messageProcessor.processMessage(publisher, message, "PROCESS_ID:786-");
        verify(publisher, times(1)).publish(anyString(), anyString());
        verify(publisher, times(1)).publish(startsWith("PROCESS_ID:786-"), anyString());
        verify(publisher, times(1)).publish(contains("C3~T3#T3#MATCHED"), anyString());
        verify(publisher, times(1)).publish(contains("C4~T4#T4#MATCHED"), anyString());
        verify(publisher, times(1)).publish(contains("C1~T1#T1#MATCHED"), anyString());
        verify(publisher, times(1)).publish(contains("C2~T2#T2.1#NOT MATCHED"), anyString());
    }

}
