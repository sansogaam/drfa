package com.drfa.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public class CsvFileComparator implements Comparator {

    ReconciliationContext context;

    public CsvFileComparator(ReconciliationContext context){
        this.context = context;
    }

    @Override
    public void compare(final int primaryKeyIndex, final File base, final File target) {
        final Map<String, String> storageMap = new ConcurrentHashMap<String, String>();
        final BlockingQueue queue = new ArrayBlockingQueue(1024);
        final ScanFile scanFile =  new ScanFile();
        final ExecutorService executorServiceBase = Executors.newSingleThreadExecutor();
        executorServiceBase.execute(new Runnable(){
            public void run(){
                System.out.println("Parsing the base file for comparision");
                try {
                    scanFile.scanFile(primaryKeyIndex, storageMap, base, queue, "BASE");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorServiceBase.shutdown();

        final ExecutorService executorServiceTarget = Executors.newSingleThreadExecutor();
        executorServiceTarget.execute(new Runnable(){
            public void run(){
                System.out.println("Parsing the base file for comparision");
                try {
                    scanFile.scanFile(primaryKeyIndex, storageMap, target, queue, "TARGET");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        executorServiceTarget.shutdown();

        final MessageProcessor messageProcessor = new MessageProcessor(context);
        ExecutorService executorServiceComparator = Executors.newSingleThreadExecutor();
        executorServiceComparator.execute(new Runnable(){
            public void run(){
                System.out.println("Matching the keys between the hash-map and storing it in one common place");
                boolean continueConsumingMessage = true;
                try {
                    while(continueConsumingMessage) {
                        String message = (String)queue.take();
                        if("Exit".equalsIgnoreCase(message)){
                            System.out.println("Exit message recieved.." + message);
                            continueConsumingMessage = false;
                        }else {
                                System.out.println(String.format("Take the message %s from the queue", message));
                                messageProcessor.processMessage(message);
                        }
                    }
                    System.out.println("Consumption is completed..." + messageProcessor.getMapOfBreaks().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorServiceComparator.shutdown();
        System.out.println(String.format("Size of the file hash map storage is %s",  storageMap.size()));
    }
}
