package com.drfa.engine.db;

import org.apache.log4j.Logger;
import org.jetel.graph.runtime.EngineInitializer;
import org.jetel.metadata.DataFieldMetadata;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.metadata.DataRecordMetadataXMLReaderWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class MetaDataParser {

    public String metaDataFile;
    public String pluginPath;
    static Logger LOG = Logger.getLogger(MetaDataParser.class);

    public MetaDataParser(String metaDataFile, String pluginPath) {
        this.metaDataFile = metaDataFile;
        this.pluginPath = pluginPath;
    }

    public List<String> getMetaDataColumnNames() {
        List<String> columnNames = new ArrayList<String>();
        EngineInitializer.initEngine(pluginPath, null, null);

        DataRecordMetadataXMLReaderWriter metaReader = new DataRecordMetadataXMLReaderWriter();
        try {
            DataRecordMetadata metadataIn = metaReader.read(new FileInputStream(this.metaDataFile));
            DataFieldMetadata[] dataFieldMetadatas = metadataIn.getFields();
            for (DataFieldMetadata dataFieldMetadata : dataFieldMetadatas) {
                String columnName = dataFieldMetadata.getName();
                columnNames.add(columnName);
            }

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        LOG.info("Column Names.." + columnNames);

        return columnNames;
    }
}
