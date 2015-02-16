package com.drfa.db;

import org.jetel.connection.jdbc.DBConnectionImpl;
import org.jetel.data.RecordKey;
import org.jetel.data.lookup.Lookup;
import org.jetel.data.lookup.LookupTable;
import org.jetel.graph.runtime.EngineInitializer;
import org.jetel.lookup.DBLookupTable;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.metadata.DataRecordMetadataXMLReaderWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sanjiv on 2/16/2015.
 */
public class DatabaseReader {

    //requested parameters
    private final static String PARAMETER_FILE = "src/main/resources/params.txt";
    private final static String PLUGINS_PROPERTY = "plugins";
    private final static String PROPERTIES_FILE_PROPERTY = "config";
    private final static String CONNECTION_PROPERTY = "connection";
    private final static String QUERY_PROPERTY = "query";
    private final static String KEY_PROPERTY = "key";
    private final static String METADATA_PROPERTY = "metadataFile";

    public static void main(String args[]){
        DBConnectionImpl dbCon;

        //reading parameters from params.txt file
        Properties arguments = new Properties();
        if ((new File(PARAMETER_FILE)).exists()) {
            try {
                arguments.load(new FileInputStream(PARAMETER_FILE));
            } catch (FileNotFoundException e) {
                //do nothing: we checked it
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //overriding requested parameters from program parameters
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-" + PLUGINS_PROPERTY)) {
                arguments.setProperty(PLUGINS_PROPERTY, args[++i]);
            }else if (args[i].startsWith("-" +PROPERTIES_FILE_PROPERTY)){
                arguments.setProperty(PROPERTIES_FILE_PROPERTY, args[++i]);
            }else if (args[i].startsWith("-" + CONNECTION_PROPERTY)){
                arguments.setProperty(CONNECTION_PROPERTY, args[++i]);
            }else if (args[i].startsWith("-" + QUERY_PROPERTY)){
                arguments.setProperty(QUERY_PROPERTY, args[++i]);
            }else if (args[i].startsWith("-" + KEY_PROPERTY)){
                arguments.setProperty(KEY_PROPERTY, args[++i]);
            }else if (args[i].startsWith("-" + METADATA_PROPERTY)){
                arguments.setProperty(METADATA_PROPERTY, args[++i]);
            }
        }

        boolean missingProperty = false;
        if (!arguments.containsKey(CONNECTION_PROPERTY)){
            missingProperty = true;
            System.out.println(CONNECTION_PROPERTY + " property not found");
        }
        if (!arguments.containsKey(QUERY_PROPERTY)){
            missingProperty = true;
            System.out.println(QUERY_PROPERTY + " property not found");
        }
        if (!arguments.containsKey(KEY_PROPERTY)){
            missingProperty = true;
            System.out.println(KEY_PROPERTY + " property not found");
        }
        if (missingProperty) {
            System.exit(1);
        }

        System.out.println("**************** Input parameters: ****************");
        System.out.println("Plugins directory: "+ arguments.getProperty(PLUGINS_PROPERTY));
        System.out.println("Properties file: "+ arguments.getProperty(PROPERTIES_FILE_PROPERTY));
        System.out.println("Connection propeties: "+ arguments.getProperty(CONNECTION_PROPERTY));
        System.out.println("SQL query: "+ arguments.getProperty(QUERY_PROPERTY));
        System.out.println("Key: "+ arguments.getProperty(KEY_PROPERTY));
        System.out.println("Metadata file: " + arguments.getProperty(METADATA_PROPERTY));
        System.out.println("***************************************************");

        //initialization; must be present
        EngineInitializer.initEngine(arguments.getProperty(PLUGINS_PROPERTY), arguments.getProperty(PROPERTIES_FILE_PROPERTY), null);
        EngineInitializer.forceActivateAllPlugins();

        //reading metadata from fmt file
        DataRecordMetadata metadataIn = null;
        if (arguments.containsKey(METADATA_PROPERTY)) {
            DataRecordMetadataXMLReaderWriter metaReader = new DataRecordMetadataXMLReaderWriter();
            try {
                metadataIn = metaReader.read(new FileInputStream(arguments.getProperty(METADATA_PROPERTY)));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        //create connection object. Get driver and connect string from cfg file specified as a first argument
        dbCon=new DBConnectionImpl("Conn0",arguments.getProperty(CONNECTION_PROPERTY));
        try{
            dbCon.init();

            LookupTable lookupTable=new DBLookupTable("lookup",dbCon,metadataIn,arguments.getProperty(QUERY_PROPERTY));
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
            while(lookup.hasNext()){
                System.out.println(lookup.next());
            }

            //free lookup table
            lookupTable.postExecute();
            lookupTable.free();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
