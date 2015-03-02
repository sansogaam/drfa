package com.drfa.cli;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class Answer {

    private String baseFile;
    private String targetFile;
    private String sqlQueryBase;
    private String sqlQueryTarget;
    private String baseDatabaseType;
    private String baseDatabaseCredentialFile;
    private String baseDatabaseFile;
    private String targetDatabaseType;
    private String targetDatabaseCredentialFile;
    private String targetDatabaseFile;
    private String metaDataFile;
    private String typeOfReport;
    private String summaryOutputPath;
    private String detailedOutputPath;
    private String reconciliationType;
    private String pluginPath;
    private String fileDelimiter ="|";
    private int keyIndex;

    public String getFileDelimiter() {
        return fileDelimiter;
    }

    public void setFileDelimiter(String fileDelimiter) {
        this.fileDelimiter = fileDelimiter;
    }

    public String getReconciliationType() {
        return reconciliationType;
    }

    public void setReconciliationType(String reconciliationType) {
        this.reconciliationType = reconciliationType;
    }

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

    public String getBaseDatabaseFile() {
        return baseDatabaseFile;
    }

    public void setBaseDatabaseFile(String baseDatabaseFile) {
        this.baseDatabaseFile = baseDatabaseFile;
    }

    public String getTargetDatabaseType() {
        return targetDatabaseType;
    }

    public void setTargetDatabaseType(String targetDatabaseType) {
        this.targetDatabaseType = targetDatabaseType;
    }

    public String getTargetDatabaseFile() {
        return targetDatabaseFile;
    }

    public void setTargetDatabaseFile(String targetDatabaseFile) {
        this.targetDatabaseFile = targetDatabaseFile;
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

    public String getSqlQueryBase() {
        return sqlQueryBase;
    }

    public void setSqlQueryBase(String sqlQueryBase) {
        this.sqlQueryBase = sqlQueryBase;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    public String getBaseDatabaseCredentialFile() {
        return baseDatabaseCredentialFile;
    }

    public void setBaseDatabaseCredentialFile(String baseDatabaseCredentialFile) {
        this.baseDatabaseCredentialFile = baseDatabaseCredentialFile;
    }

    public String getTargetDatabaseCredentialFile() {
        return targetDatabaseCredentialFile;
    }

    public void setTargetDatabaseCredentialFile(String targetDatabaseCredentialFile) {
        this.targetDatabaseCredentialFile = targetDatabaseCredentialFile;
    }

    public String getSqlQueryTarget() {
        return sqlQueryTarget;
    }

    public void setSqlQueryTarget(String sqlQueryTarget) {
        this.sqlQueryTarget = sqlQueryTarget;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "baseFile='" + baseFile + '\'' +
                ", targetFile='" + targetFile + '\'' +
                ", sqlQueryBase='" + sqlQueryBase + '\'' +
                ", sqlQueryTarget='" + sqlQueryTarget + '\'' +
                ", baseDatabaseType='" + baseDatabaseType + '\'' +
                ", baseDatabaseCredentialFile='" + baseDatabaseCredentialFile + '\'' +
                ", baseDatabaseFile='" + baseDatabaseFile + '\'' +
                ", targetDatabaseType='" + targetDatabaseType + '\'' +
                ", targetDatabaseCredentialFile='" + targetDatabaseCredentialFile + '\'' +
                ", targetDatabaseFile='" + targetDatabaseFile + '\'' +
                ", metaDataFile='" + metaDataFile + '\'' +
                ", typeOfReport='" + typeOfReport + '\'' +
                ", summaryOutputPath='" + summaryOutputPath + '\'' +
                ", detailedOutputPath='" + detailedOutputPath + '\'' +
                ", reconciliationType='" + reconciliationType + '\'' +
                ", pluginPath='" + pluginPath + '\'' +
                ", keyIndex=" + keyIndex +
                '}';
    }
}
