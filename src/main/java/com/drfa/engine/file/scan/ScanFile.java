package com.drfa.engine.file.scan;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;



public class ScanFile {

    static Logger LOG = Logger.getLogger(ScanFile.class);
    volatile int sharedVariable = 0;

    public void scanFile(Map<String, String> storageMap,BlockingQueue queue, String threadName, Answer answer, List<ColumnAttribute> columnAttributes) throws FileNotFoundException, InterruptedException {
        String processPrefix = "PROCESS_ID:"+answer.getProcessId()+"-";
        ScanUtility scanUtility = new ScanUtility();
        Scanner scanner = new Scanner(answer.fetchTheRelevantFile(threadName));
        String fileDelimiter = answer.getFileDelimiter();
        String primaryKeyIndex = answer.fetchPrimaryKeyIndex(threadName);
        int totalNumberOfRecords = 0;
        while (scanner.hasNextLine()) {
            String originalLine = scanner.nextLine();
            String constructedPrimaryKey = scanUtility.extractTheLineOfPrimaryKey(primaryKeyIndex, originalLine,fileDelimiter);
            LOG.debug(String.format("Constructed primary key %s", constructedPrimaryKey));
            String toBeComparedLine = scanUtility.constructToBeComparedLineFromTheOriginalLine(fileDelimiter, threadName, originalLine, columnAttributes);
            LOG.info(String.format("This line is for thread %s with content %s",threadName,toBeComparedLine));
            String doesKeyExist = storageMap.get(checkPrefixOfTheKey(threadName)+constructedPrimaryKey );
            if (doesKeyExist == null) {
                storageMap.put(threadName+":" + constructedPrimaryKey , toBeComparedLine);
            } else {
                String stringToCompare = processPrefix+threadName + ":" + toBeComparedLine + "$" + doesKeyExist;
                LOG.info(String.format("Comparing the line %s", stringToCompare));
                queue.put(stringToCompare);
                storageMap.remove(checkPrefixOfTheKey(threadName)+ constructedPrimaryKey );
            }
            totalNumberOfRecords++;
        }
        
        sharedVariable++;
        if (sharedVariable == 2) {
            new ScanHelper().flushTheStorageMap(storageMap, queue, processPrefix);
            queue.put(processPrefix+"SUMMARY:" + threadName + ":" + totalNumberOfRecords);
            LOG.info("Will publish the exit message in 500 milli-sec");
            Thread.sleep(100); // TODO: This is the workaround and need to seriously think how we can optimize it for the smaller files.
            queue.put(processPrefix+"Exit");
        } else{
            System.out.println(String.format("Ending of the thread %s with shared Counter %s ", threadName, sharedVariable));
            queue.put(processPrefix+"SUMMARY:" + threadName+":"+totalNumberOfRecords);
            
        }
        LOG.info(String.format("Size of the file hash map storage for thread: %s  is %s", threadName, storageMap.size()));
    }




    public String checkPrefixOfTheKey(String threadName){
        return "BASE".equalsIgnoreCase(threadName) ? TARGET_THREAD_NAME+":" : BASE_THREAD_NAME+":";
    }


}
