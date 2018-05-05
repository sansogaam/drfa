package com.drfa.engine.file;

import com.drfa.util.DrfaProperties;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sanjiv on 2/13/2015.
 */
public class MessageSplitterTest {

    @Test
    public void testDecoratedMessageIfItsFromBase() throws Exception{
        String message = DrfaProperties.BASE_PREFIX +"T1|T2|T3|T4"+ DrfaProperties.BASE_AND_TARGET_JOINER+"T1|T2|T3|T5";
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> listOfStrings = messageSplitter.splitMessage();
        assertEquals(listOfStrings.size(), 2);
        assertEquals("T1|T2|T3|T4", listOfStrings.get(0));
        assertEquals("T1|T2|T3|T5", listOfStrings.get(1));
    }


    @Test
    public void testDecoratedMessageIfItsFromTarget() throws Exception{
        String message = DrfaProperties.TARGET_PREFIX+"T1|T2|T3|T4"+DrfaProperties.BASE_AND_TARGET_JOINER+"T1|T2|T3|T5";
        MessageSplitter messageSplitter = new MessageSplitter(message);
        List<String> listOfStrings = messageSplitter.splitMessage();
        assertEquals(listOfStrings.size(), 2);
        assertEquals("T1|T2|T3|T5", listOfStrings.get(0));
        assertEquals("T1|T2|T3|T4", listOfStrings.get(1));
    }
}
