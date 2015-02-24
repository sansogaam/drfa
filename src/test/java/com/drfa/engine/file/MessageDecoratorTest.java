package com.drfa.engine.file;

import com.drfa.engine.file.MessageDecorator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sanjiv on 2/13/2015.
 */
public class MessageDecoratorTest {


    @Test
    public void testTheMapIsEmptyIfTheTargetAndBaseValueAreSame() {
        List<String> lines = new ArrayList<String>();
        lines.add("T1|T2|T3|T4");
        lines.add("T1|T2|T3|T4");
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("C1");
        columnNames.add("C2");
        columnNames.add("C3");
        columnNames.add("C4");
        MessageDecorator messageDecorator = new MessageDecorator(columnNames, lines);
        Map<String, List<String>> mapOfBreaks = messageDecorator.decorateMessageWithBreak();
        assertEquals(0,mapOfBreaks.size());
    }

    @Test
    public void testTheMapIsPopulatedIfTheTargetAndBaseValueAreDifferent() {
        List<String> lines = new ArrayList<String>();
        lines.add("T1|T2|T3|T4");
        lines.add("T1|T2|T3|T5");
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("C1");
        columnNames.add("C2");
        columnNames.add("C3");
        columnNames.add("C4");
        MessageDecorator messageDecorator = new MessageDecorator(columnNames, lines);
        Map<String, List<String>> mapOfBreaks = messageDecorator.decorateMessageWithBreak();
        assertEquals(4, mapOfBreaks.size());
        List<String> breakColumn = mapOfBreaks.get("C4");
        assertEquals("NOT MATCHED", breakColumn.get(2));
        List<String> matchedColumn = mapOfBreaks.get("C1");
        assertEquals("MATCHED", matchedColumn.get(2));
    }
}
