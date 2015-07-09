package com.drfa.engine.file;

import com.drfa.engine.meta.ColumnAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScanUtilityTest{

    @Test
    public void shouldTestThatTheKeyParseIsAbsolutelyRight() throws Exception{
        String primaryKeyIndexes = "0-1-3";
        String line="T0|T1|T2|T3";
        ScanUtility scanUtility = new ScanUtility();
        String primaryKeyLine = scanUtility.extractTheLineOfPrimaryKey(primaryKeyIndexes, line, "|");
        assertEquals("T0|T1|T3", primaryKeyLine);
    }
    
    @Test
    public void shouldTestIfTheBaseFileLineReturnsCorrect() throws Exception{
        String line="T0|T1|T2|T3|T4|T5|T6|T7";
        List<ColumnAttribute> columnAttributes = populateColumnNames();
        String fileDelimiter = "|";
        ScanUtility scanUtility = new ScanUtility();
        String toBeComparedLine = scanUtility.construtToBeComparedLineFromTheOriginalLine(fileDelimiter, "BASE", line, columnAttributes);
        assertEquals("T0|T1|T2|T3", toBeComparedLine);
    }

    @Test
    public void shouldTestIfTheTargetFileLineReturnsCorrect() throws Exception{
        String line="T0|T1|T2|T3|T4|T5|T6|T7";
        List<ColumnAttribute> columnAttributes = populateColumnNames();
        String fileDelimiter = "|";
        ScanUtility scanUtility = new ScanUtility();
        String toBeComparedLine = scanUtility.construtToBeComparedLineFromTheOriginalLine(fileDelimiter, "TARGET", line, columnAttributes);
        assertEquals("T0|T1|T3|T2", toBeComparedLine);
    }

    public static List<ColumnAttribute> populateColumnNames(){
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", ""));
        columnAttributes.add(new ColumnAttribute("C2", "String", "B-1|T-1", ""));
        columnAttributes.add(new ColumnAttribute("C3", "String", "B-2|T-3", ""));
        columnAttributes.add(new ColumnAttribute("C4", "String", "B-3|T-2", ""));
        return columnAttributes;
    }

}