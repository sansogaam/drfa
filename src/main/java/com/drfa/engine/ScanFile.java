package com.drfa.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

/**
 * Created by Sanjiv on 2/12/2015.
 */

public class ScanFile {

    volatile int sharedVariable = 0;

    public void scanFile(int primaryKeyIndex, Map<String, String> storageMap, File fileToBeScanned, BlockingQueue queue, String threadName) throws FileNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(fileToBeScanned);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ///System.out.println(String.format("This line is for thread %s with content %s",threadName,line));
            String splitLine[] = line.split(Pattern.quote("|"));
            String doesKeyExist = storageMap.get(splitLine[primaryKeyIndex]);
            if (doesKeyExist == null) {
                storageMap.put(splitLine[primaryKeyIndex], line);
            } else {
                String stringToCompare = threadName + ":" + line + "$" + doesKeyExist;
                queue.put(stringToCompare);
                storageMap.remove(splitLine[primaryKeyIndex]);
            }
        }
        sharedVariable++;
        if (sharedVariable == 2) {
            System.out.println("Publishing the exit message....");
            queue.put("Exit");
        } else {
            System.out.println(String.format("Ending of the thread %s with shared Counter %s", threadName, sharedVariable));
        }
        System.out.println(String.format("Size of the file hash map storage for thread: %s  is %s", threadName, storageMap.size()));
    }
}
