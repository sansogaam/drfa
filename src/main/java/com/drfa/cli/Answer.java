package com.drfa.cli;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class Answer {

    private String baseFile;
    private String targetFile;
    private String baseDatabaseType;
    private String baseDatabaseCredentials;
    private String targetDatabaseType;
    private String targetDatabaseCredentials;
    private String metaDataFile;
    private String typeOfReport;
    private String summaryOutputPath;
    private String detailedOutputPath;
    private int keyIndex;

    public int getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getBaseFile() {
        return baseFile;
    }

    public void setBaseFile(String baseFile) {
        this.baseFile = baseFile;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getBaseDatabaseType() {
        return baseDatabaseType;
    }

    public void setBaseDatabaseType(String baseDatabaseType) {
        this.baseDatabaseType = baseDatabaseType;
    }

    public String getBaseDatabaseCredentials() {
        return baseDatabaseCredentials;
    }

    public void setBaseDatabaseCredentials(String baseDatabaseCredentials) {
        this.baseDatabaseCredentials = baseDatabaseCredentials;
    }

    public String getTargetDatabaseType() {
        return targetDatabaseType;
    }

    public void setTargetDatabaseType(String targetDatabaseType) {
        this.targetDatabaseType = targetDatabaseType;
    }

    public String getTargetDatabaseCredentials() {
        return targetDatabaseCredentials;
    }

    public void setTargetDatabaseCredentials(String targetDatabaseCredentials) {
        this.targetDatabaseCredentials = targetDatabaseCredentials;
    }

    public String getMetaDataFile() {
        return metaDataFile;
    }

    public void setMetaDataFile(String metaDataFile) {
        this.metaDataFile = metaDataFile;
    }

    public String getTypeOfReport() {
        return typeOfReport;
    }

    public void setTypeOfReport(String typeOfReport) {
        this.typeOfReport = typeOfReport;
    }

    public String getSummaryOutputPath() {
        return summaryOutputPath;
    }

    public void setSummaryOutputPath(String summaryOutputPath) {
        this.summaryOutputPath = summaryOutputPath;
    }

    public String getDetailedOutputPath() {
        return detailedOutputPath;
    }

    public void setDetailedOutputPath(String detailedOutputPath) {
        this.detailedOutputPath = detailedOutputPath;
    }
}
