package com.drfa.cli;

import com.drfa.engine.ReconciliationInput;
import com.drfa.engine.meta.ColumnAttribute;

import java.io.File;
import java.util.List;


public class Answer {

    private int processId;
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
    private String baseDatabaseMetaDataFile;
    private String targetDatabaseMetaDataFile;
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


    public void setBaseDatabaseType(String baseDatabaseType) {
        this.baseDatabaseType = baseDatabaseType;
    }

    public String getBaseDatabaseFile() {
        return baseDatabaseFile;
    }

    public void setBaseDatabaseFile(String baseDatabaseFile) {
        this.baseDatabaseFile = baseDatabaseFile;
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

    public List<ColumnAttribute> getColumnAttribute() {
        return new ReconciliationInput().initializeReconciliationInput(metaDataFile);
    }

    public void setMetaDataFile(String metaDataFile) {
        this.metaDataFile = metaDataFile;
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


    public void setReportCategory(String reportCategory) {
        this.reportCategory = reportCategory;
    }


    public void setReportOutputPath(String reportOutputPath) {
        this.reportOutputPath = reportOutputPath;
    }

    public void setTargetKeyIndex(String targetKeyIndex) {
        this.targetKeyIndex = targetKeyIndex;
    }

    public void setBaseKeyIndex(String baseKeyIndex) {
        this.baseKeyIndex = baseKeyIndex;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getBaseDatabaseMetaDataFile() {
        return baseDatabaseMetaDataFile;
    }

    public void setBaseDatabaseMetaDataFile(String baseDatabaseMetaDataFile) {
        this.baseDatabaseMetaDataFile = baseDatabaseMetaDataFile;
    }

    public String getTargetDatabaseMetaDataFile() {
        return targetDatabaseMetaDataFile;
    }

    public void setTargetDatabaseMetaDataFile(String targetDatabaseMetaDataFile) {
        this.targetDatabaseMetaDataFile = targetDatabaseMetaDataFile;
    }

    public String fetchPrimaryKeyIndex(String threadName) {
        return threadName.equalsIgnoreCase("BASE") ? baseKeyIndex : targetKeyIndex;
    }

    public File fetchTheRelevantFile(String threadName) {
        return threadName.equals("BASE") ? new File(baseFile) : new File(targetFile);
    }

    public String processPrefix() {
        return "PROCESS_ID:" + processId + "-";
    }

    @Override
    public String toString() {
        return "Answer{" +
                "processId=" + processId +
                ", baseFile='" + baseFile + '\'' +
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
                ", baseDatabaseMetaDataFile='" + baseDatabaseMetaDataFile + '\'' +
                ", targetDatabaseMetaDataFile='" + targetDatabaseMetaDataFile + '\'' +
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
