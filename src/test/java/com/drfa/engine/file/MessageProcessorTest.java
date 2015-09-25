package com.drfa.engine.file;


import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageProcessorTest {


    @Test
    public void testProcessMessageForDeterminingTheBase() throws Exception {
        String message = "BASE:T1|T2|T3|T4$T1|T2.1|T3|T4";

        Answer answer = mock(Answer.class);
        when(answer.getColumnAttribute()).thenReturn(getColumnAttributes());
        when(answer.quote()).thenReturn(Pattern.quote("|"));


        MessageProcessor messageProcessor = new MessageProcessor(answer);
        Map<String, List<String>> msg = messageProcessor.processMessage(message);

        assertThat(msg.size(), is(4));
        assertThat(msg.get("C1"), hasItems("T1", "MATCHED"));
        assertThat(msg.get("C2"), hasItems("T2", "T2.1", "NOT MATCHED"));
        assertThat(msg.get("C3"), hasItems("T3", "MATCHED"));
        assertThat(msg.get("C4"), hasItems("T4", "MATCHED"));
    }


    @Test
    public void testTheMapIsEmptyIfTheTargetAndBaseValueAreSame() {
        String message = "BASE:T1|T2|T3|T4$T1|T2|T3|T4";

        Answer answer = mock(Answer.class);
        when(answer.getColumnAttribute()).thenReturn(getColumnAttributes());
        when(answer.getFileDelimiter()).thenReturn("|");


        MessageProcessor messageProcessor = new MessageProcessor(answer);
        Map<String, List<String>> msg = messageProcessor.processMessage(message);
        assertThat(msg.size(), is(0));
    }

    private List<ColumnAttribute> getColumnAttributes() {
        List<ColumnAttribute> columnAttributes = new ArrayList<>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-2", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-3", "SP-(B-NR|T-NR)-(R-NA)"));
        return columnAttributes;
    }

}
