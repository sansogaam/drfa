package com.drfa.engine.file.scan;

import com.drfa.cli.Answer;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.util.DrfaProperties;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;


public class ScanFile {

    private static Logger LOG = Logger.getLogger(ScanFile.class);

    public void scanFile(Map<String, String> storageMap, BlockingQueue queue, String threadName, Answer answer, List<ColumnAttribute> columnAttributes) throws FileNotFoundException, InterruptedException {
        ScanUtility scanUtility = new ScanUtility();
        Scanner scanner = new Scanner(answer.fetchTheRelevantFile(threadName));
        String fileDelimiter = answer.getFileDelimiter();
        String primaryKeyIndex = answer.fetchPrimaryKeyIndex(threadName);
        int totalNumberOfRecords = 0;
        while (scanner.hasNextLine()) {
            String originalLine = scanner.nextLine();
            String constructedPrimaryKey = scanUtility.extractTheLineOfPrimaryKey(primaryKeyIndex, originalLine, fileDelimiter);
            LOG.debug(String.format("Constructed primary key %s", constructedPrimaryKey));
            String toBeComparedLine = scanUtility.constructToBeComparedLineFromTheOriginalLine(fileDelimiter, threadName, originalLine, columnAttributes);
            LOG.info(String.format("This line is for thread %s with content %s", threadName, toBeComparedLine));
            String doesKeyExist = storageMap.get(checkPrefixOfTheKey(threadName) + constructedPrimaryKey);
            if (doesKeyExist == null) {
                storageMap.put(threadName + DrfaProperties.THREAD_NAMES_JOINER + constructedPrimaryKey, toBeComparedLine);
            } else {
                String stringToCompare = answer.processPrefix() + threadName + DrfaProperties.THREAD_NAMES_JOINER + toBeComparedLine + DrfaProperties.BASE_AND_TARGET_JOINER + doesKeyExist;
                LOG.info(String.format("Comparing the line %s", stringToCompare));
                queue.put(stringToCompare);
                storageMap.remove(checkPrefixOfTheKey(threadName) + constructedPrimaryKey);
            }
            totalNumberOfRecords++;
        }

        queue.put(answer.processPrefix() + DrfaProperties.SUMMARY_PREFIX+ threadName + DrfaProperties.THREAD_NAMES_JOINER + totalNumberOfRecords);

    }


    public String checkPrefixOfTheKey(String threadName) {
        return "BASE".equalsIgnoreCase(threadName) ? TARGET_THREAD_NAME + DrfaProperties.THREAD_NAMES_JOINER : BASE_THREAD_NAME + DrfaProperties.THREAD_NAMES_JOINER;
    }


}
