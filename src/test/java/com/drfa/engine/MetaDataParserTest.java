package com.drfa.engine;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class MetaDataParserTest {

    String metaDataFile = "src/test/resources/customer.fmt";
    @Test
    public void shouldTestMetaDataFile() throws Exception{
        MetaDataParser metaDataParser = new MetaDataParser(metaDataFile, "");
        List<String> columnNames = metaDataParser.getMetaDataColumnNames();
        assertEquals(4, columnNames.size());
    }
}
