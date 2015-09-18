package com.drfa.engine.db;

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

}
