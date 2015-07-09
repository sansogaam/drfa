package com.drfa.engine.file;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
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

    public void scanFile(Map<String, String> storageMap,BlockingQueue queue, String threadName, Answer answer, List<ColumnAttribute> columnAttributes) throws FileNotFoundException, InterruptedException {
        ScanUtility scanUtility = new ScanUtility();
        Scanner scanner = new Scanner(fetchTheRelevantFile(threadName, answer));
        String fileDelimiter = answer.getFileDelimiter();
        String primaryKeyIndex = fetchPrimaryKeyIndex(threadName, answer);
        int totalNumberOfRecords = 0;
        while (scanner.hasNextLine()) {
            String originalLine = scanner.nextLine();
            String constructedPrimaryKey = scanUtility.extractTheLineOfPrimaryKey(primaryKeyIndex, originalLine,fileDelimiter);
            LOG.debug(String.format("Constructed primary key %s", constructedPrimaryKey));
            String toBeComparedLine = scanUtility.construtToBeComparedLineFromTheOriginalLine(fileDelimiter,threadName, originalLine, columnAttributes);
            //LOG.info(String.format("This line is for thread %s with content %s",threadName,line));
            String doesKeyExist = storageMap.get(checkPrefixOfTheKey(threadName)+constructedPrimaryKey );
            if (doesKeyExist == null) {
                storageMap.put(threadName+":" + constructedPrimaryKey , toBeComparedLine);
            } else {
                String stringToCompare = threadName + ":" + toBeComparedLine + "$" + doesKeyExist;
                queue.put(stringToCompare);
                storageMap.remove(checkPrefixOfTheKey(threadName)+ constructedPrimaryKey );
            }
            totalNumberOfRecords++;
        }
        
        sharedVariable++;
        if (sharedVariable == 2) {
            flushTheStorageMap(storageMap, queue);
            queue.put("SUMMARY:" + threadName + ":" + totalNumberOfRecords);
            LOG.info("Will publish the exit message in 500 milli-sec");
            Thread.sleep(100); // TODO: This is the workaround and need to seriously think how we can optimize it for the smaller files.
            queue.put("Exit");
        } else{
            System.out.println(String.format("Ending of the thread %s with shared Counter %s ", threadName, sharedVariable));
            queue.put("SUMMARY:" + threadName+":"+totalNumberOfRecords);
            
        }
        LOG.info(String.format("Size of the file hash map storage for thread: %s  is %s", threadName, storageMap.size()));
    }

    private String fetchPrimaryKeyIndex(String threadName, Answer answer) {
        return threadName.equalsIgnoreCase("BASE")? answer.getBaseKeyIndex() : answer.getTargetKeyIndex();
    }

    private File fetchTheRelevantFile(String threadName, Answer answer) {
        return threadName.equals("BASE") ? new File(answer.getBaseFile()) : new File(answer.getTargetFile());
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
