package com.drfa.engine.file;

import com.drfa.engine.file.MessageSplitter;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sanjiv on 2/13/2015.
 */
public class MessageSplitterTest {

    @Test
    public void testDecoratedMessageIfItsFromBase() throws Exception{
        String message = "BASE:T1|T2|T3|T4$T1|T2|T3|T5";
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> listOfStrings = messageSplitter.splitMessage();
        assertEquals(listOfStrings.size(), 2);
        assertEquals("T1|T2|T3|T4", listOfStrings.get(0));
        assertEquals("T1|T2|T3|T5", listOfStrings.get(1));
    }


    @Test
    public void testDecoratedMessageIfItsFromTarget() throws Exception{
        String message = "TARGET:T1|T2|T3|T4$T1|T2|T3|T5";
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> listOfStrings = messageSplitter.splitMessage();
        assertEquals(listOfStrings.size(), 2);
        assertEquals("T1|T2|T3|T5", listOfStrings.get(0));
        assertEquals("T1|T2|T3|T4", listOfStrings.get(1));
    }
}
