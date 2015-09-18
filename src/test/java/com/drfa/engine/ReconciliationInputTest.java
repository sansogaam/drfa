package com.drfa.engine;

import com.drfa.engine.meta.ColumnAttribute;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ReconciliationInputTest{

    @Test
    public void shouldTestTheColumnAttribute() throws Exception{
        ReconciliationInput reconciliationInput = new ReconciliationInput();
        List<ColumnAttribute> columnAttributeList = reconciliationInput.initializeReconciliationInput("src/test/resources/reconciliation-input.xml");
        assertThat(columnAttributeList.size(), is(5));
        assertThat("C1", is(columnAttributeList.get(0).getColumnName()));
        assertThat("String", is(columnAttributeList.get(0).getColumnType()));
        assertThat("B-0|T-0", is(columnAttributeList.get(0).getColumnMatching()));
        assertThat("SP-(B-NR|T-NR)-(R-NA)", is(columnAttributeList.get(0).getColumnRule()));
    }
}