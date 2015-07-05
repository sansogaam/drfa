package com.drfa.engine.db;

import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.engine.meta.MetaDataParser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class MetaDataParserTest {

    String metaDataFile = "src/test/resources/customer.fmt";

    @Test
    public void shouldTestMetaDataAttributes() throws Exception{
        MetaDataParser metaDataParser = new MetaDataParser(metaDataFile, "");
        List<ColumnAttribute> columnAttributes = metaDataParser.getColumnAttributes();
        assertEquals(4, columnAttributes.size());
        ColumnAttribute columnAttribute = columnAttributes.get(0);
        assertEquals("customer_id", columnAttribute.getColumnName());
        assertEquals("B-4|T-0", columnAttribute.getColumnMatching());
        assertEquals("Rules", columnAttribute.getColumnRule());
        assertEquals("integer", columnAttribute.getColumnType());
    }

}
