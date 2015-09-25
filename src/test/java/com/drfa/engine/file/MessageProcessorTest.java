package com.drfa.engine.file;


import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageProcessorTest {


    @Test
    public void testProcessMessageForDeterminingTheBase() throws Exception {
        String message = "BASE:T1|T2|T3|T4$T1|T2.1|T3|T4";

        Answer answer = mock(Answer.class);
        when(answer.getColumnAttribute()).thenReturn(populateColumnNames());
        when(answer.getFileDelimiter()).thenReturn("|");

        MessageProcessor messageProcessor = new MessageProcessor(answer);
        String msg = messageProcessor.processMessage(message);

        assertThat(msg, containsString("C3~T3#T3#MATCHED"));
        assertThat(msg, containsString("C4~T4#T4#MATCHED"));
        assertThat(msg, containsString("C1~T1#T1#MATCHED"));
        assertThat(msg, containsString("C2~T2#T2.1#NOT MATCHED"));
    }

    private List<ColumnAttribute> populateColumnNames() {
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-2", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-3", "SP-(B-NR|T-NR)-(R-NA)"));
        return columnAttributes;
    }

}
