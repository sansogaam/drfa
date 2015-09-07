package com.drfa.engine;

import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.engine.meta.ColumnAttributes;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanjiv on 9/7/2015.
 */
public class ReconciliationInput {

    static Logger LOG = Logger.getLogger(ReconciliationServer.class);

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
            for(ColumnAttribute columnAttribute: columnAttributes){
                LOG.info("****************************************");
                LOG.info(String.format("Name %s", columnAttribute.getColumnName()));
                LOG.info(String.format("Type %s",columnAttribute.getColumnType()));
                LOG.info(String.format("Matching %s",columnAttribute.getColumnMatching()));
                LOG.info(String.format("Rule %s",columnAttribute.getColumnRule()));
                LOG.info("****************************************");
            }
            return columnAttributes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
