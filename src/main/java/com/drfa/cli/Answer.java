package com.drfa.cli;

import java.io.File;

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
    private String reportCategory;
    private String reportOutputPath;
    private String reconciliationType;
    private String pluginPath;
    private String fileDelimiter ="|";
    private String baseKeyIndex;
    private String targetKeyIndex;
    private int keyIndex;

    
    public String getFileDelimiter() {
        return fileDelimiter != null ? fileDelimiter : "|";
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
        if("PLUGINS".equalsIgnoreCase(pluginPath)){
            this.pluginPath = new File("plugins").getAbsolutePath();
        }else {
            this.pluginPath = pluginPath;
        }
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

    public String getReportCategory() {
        return reportCategory;
    }

    public void setReportCategory(String reportCategory) {
        this.reportCategory = reportCategory;
    }

    public String getReportOutputPath() {
        return reportOutputPath;
    }

    public void setReportOutputPath(String reportOutputPath) {
        this.reportOutputPath = reportOutputPath;
    }

    public String getTargetKeyIndex() {
        return targetKeyIndex;
    }

    public void setTargetKeyIndex(String targetKeyIndex) {
        this.targetKeyIndex = targetKeyIndex;
    }

    public String getBaseKeyIndex() {
        return baseKeyIndex;
    }

    public void setBaseKeyIndex(String baseKeyIndex) {
        this.baseKeyIndex = baseKeyIndex;
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
                ", reportCategory='" + reportCategory + '\'' +
                ", reportOutputPath='" + reportOutputPath + '\'' +
                ", reconciliationType='" + reconciliationType + '\'' +
                ", pluginPath='" + pluginPath + '\'' +
                ", fileDelimiter='" + fileDelimiter + '\'' +
                ", baseKeyIndex='" + baseKeyIndex + '\'' +
                ", targetKeyIndex='" + targetKeyIndex + '\'' +
                ", keyIndex=" + keyIndex +
                '}';
    }
}
