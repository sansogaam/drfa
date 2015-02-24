package com.drfa.engine.db;

/**
 * Created by Sanjiv on 2/16/2015.
 */
public class DatabaseInput {

    private String sqlQuery;
    private String metaDataFile;
    private String connectionFile;
    private String pluginPath;


    private String outputPath;

    public DatabaseInput(String sqlQuery, String metaDataFile, String connectionFile, String pluginPath, String outputPath) {
        this.sqlQuery = sqlQuery;
        this.metaDataFile = metaDataFile;
        this.connectionFile = connectionFile;
        this.pluginPath = pluginPath;
        this.outputPath = outputPath;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public String getMetaDataFile() {
        return metaDataFile;
    }

    public String getConnectionFile() {
        return connectionFile;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

}
