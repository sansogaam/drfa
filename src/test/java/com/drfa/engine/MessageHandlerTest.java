package com.drfa.engine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

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

}
