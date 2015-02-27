package com.drfa.engine.file;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;

/**
 * Created by Sanjiv on 2/12/2015.
 */

public class ScanFile {

    volatile int sharedVariable = 0;

    static Logger LOG = Logger.getLogger(ScanFile.class);

    public void scanFile(int primaryKeyIndex, Map<String, String> storageMap, File fileToBeScanned,
                         BlockingQueue queue, String threadName) throws FileNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(fileToBeScanned);
        int totalNumberOfRecords = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ///System.out.println(String.format("This line is for thread %s with content %s",threadName,line));
            String splitLine[] = line.split(Pattern.quote("|"));
            String doesKeyExist = storageMap.get(checkPrefixOfTheKey(threadName)+splitLine[primaryKeyIndex]);
            if (doesKeyExist == null) {
                storageMap.put(threadName+":" + splitLine[primaryKeyIndex], line);
            } else {
                String stringToCompare = threadName + ":" + line + "$" + doesKeyExist;
                queue.put(stringToCompare);
                storageMap.remove(checkPrefixOfTheKey(threadName)+ splitLine[primaryKeyIndex]);
            }
            totalNumberOfRecords++;
        }
        sharedVariable++;
        if (sharedVariable == 2) {
            LOG.info("Publishing the exit message....");
            flushTheStorageMap(storageMap,queue);
            queue.put("SUMMARY:" + threadName+":"+totalNumberOfRecords);
            queue.put("Exit");
        } else {
            System.out.println(String.format("Ending of the thread %s with shared Counter %s", threadName, sharedVariable));
            queue.put("SUMMARY:" + threadName+":"+totalNumberOfRecords);
        }
        LOG.info(String.format("Size of the file hash map storage for thread: %s  is %s", threadName, storageMap.size()));
    }

    public String checkPrefixOfTheKey(String threadName){
        return "BASE".equalsIgnoreCase(threadName) ? TARGET_THREAD_NAME+":" : BASE_THREAD_NAME+":";
    }
    public void flushTheStorageMap(Map<String, String> storageMap, BlockingQueue queue) throws InterruptedException {
        Map<String, String> temporaryMap = new HashMap<String, String>();
        temporaryMap.putAll(storageMap);
        for(String key: temporaryMap.keySet()){
            if(key.startsWith(BASE_THREAD_NAME)){
                String subStringKey = key.substring(key.indexOf(":")+1);
                String columnKey = TARGET_THREAD_NAME+":" + subStringKey;
                if(storageMap.containsKey(columnKey)){
                    String message = BASE_THREAD_NAME+":" + temporaryMap.get(key) + "$" + temporaryMap.get(columnKey);
                    queue.put(message);
                    storageMap.remove(columnKey);
                    storageMap.remove(key);
                }
            }else if(key.startsWith(TARGET_THREAD_NAME)){
                String subStringKey = key.substring(key.indexOf(":")+1);
                String columnKey = BASE_THREAD_NAME+":" + subStringKey;
                if(storageMap.containsKey(columnKey)){
                    String message = BASE_THREAD_NAME+":" + temporaryMap.get(columnKey) + "$" + temporaryMap.get(key);
                    queue.put(message);
                    storageMap.remove(columnKey);
                    storageMap.remove(key);
                }
            }
        }
    }
}
