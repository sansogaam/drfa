package com.drfa.engine.db;

import org.apache.log4j.Logger;
import org.jetel.connection.jdbc.DBConnectionImpl;
import org.jetel.data.DataField;
import org.jetel.data.RecordKey;
import org.jetel.data.lookup.Lookup;
import org.jetel.data.lookup.LookupTable;
import org.jetel.graph.runtime.EngineInitializer;
import org.jetel.lookup.DBLookupTable;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.metadata.DataRecordMetadataXMLReaderWriter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import static com.drfa.engine.EngineConstants.START_PROCESSING;


/**
 * Created by Sanjiv on 2/16/2015.
 */
public class ExecuteDBRead implements Runnable {

    static Logger LOG = Logger.getLogger(ExecuteDBRead.class);
    DatabaseInput databaseInput;
    BlockingQueue queue;
    AtomicLong aLong;
    private String threadName;
    private String outputFile;

    public ExecuteDBRead(DatabaseInput databaseInput, BlockingQueue queue, AtomicLong aLong, String threadName, String outputFile) {
        this.databaseInput = databaseInput;
        this.queue = queue;
        this.aLong = aLong;
        this.threadName = threadName;
        this.outputFile = outputFile;
    }

    public static void initializeEngine(String pluginPath) {
        if (!EngineInitializer.isInitialized()) {
            System.out.println("Initializing the engine for processing....");
            EngineInitializer.initEngine(pluginPath, null, null);
            EngineInitializer.forceActivateAllPlugins();
        }
    }

    @Override
    public void run() {
        LOG.info("Running the thread: " + threadName);

        DBConnectionImpl dbCon = new DBConnectionImpl("Conn0", databaseInput.getConnectionFile());
        DataRecordMetadataXMLReaderWriter metaReader = new DataRecordMetadataXMLReaderWriter();
        DataRecordMetadata metadataIn = null;
        if (databaseInput.getMetaDataFile() != null) {
            System.out.println(String.format("Processing the metadata file %s of  thread %s", databaseInput.getMetaDataFile(),threadName));
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
            LOG.info(String.format("Processing the results %s on the Output File %s", threadName, outputFile));

            Path file = Paths.get(outputFile);

            System.out.println(String.format("Creating the file %s on thread %s", file.getFileName(), threadName));
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
                while (lookup.hasNext()) {
                    StringBuffer sb = new StringBuffer();
                    for (DataField data : lookup.next()) {
                        String valueString =data.getValue().toString();
                        LOG.info(String.format("Displaying the value of %s thread with content %s", threadName, valueString));
                        sb.append(valueString).append("|");
                    }
                    writer.write(sb.toString());
                    writer.write("\n");
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
            //free lookup table
            lookupTable.postExecute();
            lookupTable.free();
            LOG.info(String.format("Processed the file format %s",threadName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long value = aLong.getAndIncrement();
        LOG.info(String.format("The value of atomic long is %s for thread %s", value, threadName));
        if (value == 1) {
            LOG.info("Sending the message now......");
            try {
                queue.put(START_PROCESSING);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
