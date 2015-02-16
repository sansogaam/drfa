package com.drfa.db;

import org.jetel.connection.jdbc.DBConnectionImpl;
import org.jetel.data.DataField;
import org.jetel.data.DataRecord;
import org.jetel.data.RecordKey;
import org.jetel.data.lookup.Lookup;
import org.jetel.data.lookup.LookupTable;
import org.jetel.graph.runtime.EngineInitializer;
import org.jetel.lookup.DBLookupTable;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.metadata.DataRecordMetadataXMLReaderWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


/**
 * Created by Sanjiv on 2/16/2015.
 */
public class ExecuteDBRead implements Runnable {

    DatabaseInput databaseInput;

    public ExecuteDBRead(DatabaseInput databaseInput) {
        this.databaseInput = databaseInput;
    }

    @Override
    public void run() {

        EngineInitializer.initEngine(databaseInput.getPluginPath(), null, null);
        EngineInitializer.forceActivateAllPlugins();
        DBConnectionImpl dbCon = new DBConnectionImpl("Conn0", databaseInput.getConnectionFile());
        DataRecordMetadataXMLReaderWriter metaReader = new DataRecordMetadataXMLReaderWriter();
        DataRecordMetadata metadataIn = null;
        if(databaseInput.getMetaDataFile()!= null) {
            try {
                metadataIn = metaReader.read(new FileInputStream(databaseInput.getMetaDataFile()));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }

        try {
            dbCon.init();
            LookupTable lookupTable = new DBLookupTable("lookup", dbCon, metadataIn, databaseInput.getSqlQuery());
            lookupTable.init();
            lookupTable.preExecute();

            //creating data record for seeking
            DataRecordMetadata keyMetadata = new DataRecordMetadata("db_key_metadata", DataRecordMetadata.DELIMITED_RECORD);
            RecordKey key = new RecordKey(keyMetadata.getFieldNamesArray(), keyMetadata);
            key.init();

            Lookup lookup = lookupTable.createLookup(key);
            //try to lookup based on specified parameter
            lookup.seek();

            //display results, if there are any

            Path file = Paths.get(databaseInput.getOutputPath() + File.separator + new Date().getTime()+".csv");
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
                while (lookup.hasNext()) {
                    StringBuffer sb = new StringBuffer();
                    for (DataField data : lookup.next()) {
                        sb.append(data.getValue().toString()).append("|");
                    }
                    writer.write("\n");
                    writer.write(sb.toString());
                }
            }catch(IOException x){
                System.err.format("IOException: %s%n", x);
            }
            //free lookup table
            lookupTable.postExecute();
            lookupTable.free();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
