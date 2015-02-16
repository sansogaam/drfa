package com.drfa.db;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sanjiv on 2/16/2015.
 */
public class DBExecutor {

    public static void main(String args[]){
        String pluginPath = "src/main/resources/plugins";
        String dbConnectionFileBase = "src/main/resources/mysql.cfg";
        String sqlQueryBase = "SELECT * FROM EMPLOYEE";
        String outputPath = "D:/dev/drfa/data";
        DatabaseInput databaseInput = new DatabaseInput(sqlQueryBase, null, dbConnectionFileBase, pluginPath, outputPath);
        final ExecutorService executorServiceBase = Executors.newSingleThreadExecutor();
        executorServiceBase.execute(new ExecuteDBRead(databaseInput));
        executorServiceBase.shutdown();
    }
}
