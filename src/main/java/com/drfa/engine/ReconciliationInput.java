package com.drfa.engine;

import com.drfa.engine.meta.ColumnAttribute;
import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ReconciliationInput {

    public List<ColumnAttribute> initializeReconciliationInput(String xml) {
        try {
            InputStream inputstream = new FileInputStream(xml);
            XStream xStream = new XStream();
            xStream.alias("columnattribute", ColumnAttribute.class);
            xStream.alias("columnname", String.class);
            xStream.alias("columntype", String.class);
            xStream.alias("columnmatching", String.class);
            xStream.alias("columnrule", String.class);
            List<ColumnAttribute> columnAttributes = (ArrayList<ColumnAttribute>) xStream.fromXML(inputstream);
            return columnAttributes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
