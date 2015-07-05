package com.drfa.engine.file;

import com.drfa.engine.meta.ColumnAttribute;
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
        List<ColumnAttribute> columnAttributes = populateColumnAttributes();
        MessageDecorator messageDecorator = new MessageDecorator(columnAttributes, lines, "|");
        Map<String, List<String>> mapOfBreaks = messageDecorator.decorateMessageWithBreak();
        assertEquals(0,mapOfBreaks.size());
    }

    @Test
    public void testTheMapIsPopulatedIfTheTargetAndBaseValueAreDifferent() {
        List<String> lines = new ArrayList<String>();
        lines.add("T1|T2|T3|T4");
        lines.add("T1|T2|T3|T5");
        List<ColumnAttribute> columnAttributes = populateColumnAttributes();
        MessageDecorator messageDecorator = new MessageDecorator(columnAttributes, lines, "|");
        Map<String, List<String>> mapOfBreaks = messageDecorator.decorateMessageWithBreak();
        assertEquals(4, mapOfBreaks.size());
        List<String> breakColumn = mapOfBreaks.get("C4");
        assertEquals("NOT MATCHED", breakColumn.get(2));
        List<String> matchedColumn = mapOfBreaks.get("C1");
        assertEquals("MATCHED", matchedColumn.get(2));
    }
    
    @Test
    public void testTheMessageDecoratorForOneSidedBreak(){
        String line = "T1|T2|T3|T4";
        List<ColumnAttribute> columnAttributes = populateColumnAttributes();
        MessageDecorator messageDecorator = new MessageDecorator(columnAttributes, line, "|");
        Map<String, String> mapOfBreaks = messageDecorator.decorateMessageWithOneSideBreak();
        assertEquals("T1", mapOfBreaks.get("C1"));
        assertEquals("T2", mapOfBreaks.get("C2"));
        assertEquals("T3", mapOfBreaks.get("C3"));
        assertEquals("T4", mapOfBreaks.get("C4"));
    }
    public static List<ColumnAttribute> populateColumnAttributes(){
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", ""));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", ""));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-2", ""));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-3", ""));
        return columnAttributes;
    }
}
