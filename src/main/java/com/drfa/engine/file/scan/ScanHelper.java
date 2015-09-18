package com.drfa.engine.file.scan;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;

public class ScanHelper {

    public void flushTheStorageMap(Map<String, String> storageMap, BlockingQueue queue, String processPrefix) throws InterruptedException {
        Map<String, String> temporaryMap = new HashMap<String, String>();
        temporaryMap.putAll(storageMap);
        for (String key : temporaryMap.keySet()) {
            if (key.startsWith(BASE_THREAD_NAME)) {
                String subStringKey = key.substring(key.indexOf(":") + 1);
                String columnKey = TARGET_THREAD_NAME + ":" + subStringKey;
                if (storageMap.containsKey(columnKey)) {
                    String message = processPrefix + BASE_THREAD_NAME + ":" + temporaryMap.get(key) + "$" + temporaryMap.get(columnKey);
                    queue.put(message);
                    storageMap.remove(columnKey);
                    storageMap.remove(key);
                }
            } else if (key.startsWith(TARGET_THREAD_NAME)) {
                String subStringKey = key.substring(key.indexOf(":") + 1);
                String columnKey = BASE_THREAD_NAME + ":" + subStringKey;
                if (storageMap.containsKey(columnKey)) {
                    String message = processPrefix + BASE_THREAD_NAME + ":" + temporaryMap.get(columnKey) + "$" + temporaryMap.get(key);
                    queue.put(message);
                    storageMap.remove(columnKey);
                    storageMap.remove(key);
                }
            }
        }
    }
}
