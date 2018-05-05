package com.drfa.engine.file.scan;


import com.drfa.util.DrfaProperties;

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
                String subStringKey = key.substring(key.indexOf(DrfaProperties.THREAD_NAMES_JOINER) + 1);
                String columnKey = TARGET_THREAD_NAME + DrfaProperties.THREAD_NAMES_JOINER + subStringKey;
                if (storageMap.containsKey(columnKey)) {
                    String message = processPrefix + BASE_THREAD_NAME + DrfaProperties.THREAD_NAMES_JOINER + temporaryMap.get(key) + DrfaProperties.BASE_AND_TARGET_JOINER + temporaryMap.get(columnKey);
                    queue.put(message);
                    storageMap.remove(columnKey);
                    storageMap.remove(key);
                }
            } else if (key.startsWith(TARGET_THREAD_NAME)) {
                String subStringKey = key.substring(key.indexOf(DrfaProperties.THREAD_NAMES_JOINER) + 1);
                String columnKey = BASE_THREAD_NAME + DrfaProperties.THREAD_NAMES_JOINER + subStringKey;
                if (storageMap.containsKey(columnKey)) {
                    String message = processPrefix + BASE_THREAD_NAME + DrfaProperties.THREAD_NAMES_JOINER + temporaryMap.get(columnKey) + DrfaProperties.BASE_AND_TARGET_JOINER + temporaryMap.get(key);
                    queue.put(message);
                    storageMap.remove(columnKey);
                    storageMap.remove(key);
                }
            }
        }
    }
}
