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
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Sanjiv on 2/16/2015.
 */
public class ExecuteDBRead implements Runnable {

    DatabaseInput databaseInput;
    BlockingQueue queue;
    AtomicLong aLong;
    private String threadName;

    public ExecuteDBRead(DatabaseInput databaseInput, BlockingQueue queue, AtomicLong aLong, String threadName) {
        this.databaseInput = databaseInput;
        this.queue = queue;
        this.aLong = aLong;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Running the thread: " + threadName);

        DBConnectionImpl dbCon = new DBConnectionImpl("Conn0", databaseInput.getConnectionFile());
        DataRecordMetadataXMLReaderWriter metaReader = new DataRecordMetadataXMLReaderWriter();
        DataRecordMetadata metadataIn = null;
        if (databaseInput.getMetaDataFile() != null) {
            System.out.println(String.format("Processing the metadata file %s", threadName));
            try {
                metadataIn = metaReader.read(new FileInputStream(databaseInput.getMetaDataFile()));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }

        try {
            System.out.println(String.format("Initializing the database %s", threadName));
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
            System.out.println(String.format("Processing the results %s", threadName));

            Path file = Paths.get(databaseInput.getOutputPath() + File.separator + threadName+"-"+ new Date().getTime() + ".csv");

            System.out.println(String.format("Creating the file %s on thread %s", file.getFileName(), threadName));
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
                while (lookup.hasNext()) {
                    StringBuffer sb = new StringBuffer();
                    for (DataField data : lookup.next()) {
                        String valueString =data.getValue().toString();
                    //    System.out.println(String.format("Displaying the value of %s thread with content %s", threadName, valueString));
                        sb.append(valueString).append("|");
                    }
                    writer.write("\n");
                    writer.write(sb.toString());
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
            //free lookup table
            lookupTable.postExecute();
            lookupTable.free();
            System.out.println(String.format("Processed the file format %s",threadName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long value = aLong.getAndIncrement();
        System.out.println(String.format("The value of atomic long is %s for thread %s", value, threadName));
        if (value == 1) {
            System.out.println("Sending the message now......");
            try {
                queue.put("START_PROCESSING");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initializeEngine(String pluginPath) {
        if (!EngineInitializer.isInitialized()) {
            System.out.println("Initializing the engine for processing....");
            EngineInitializer.initEngine(pluginPath, null, null);
            EngineInitializer.forceActivateAllPlugins();
        }
    }
}
