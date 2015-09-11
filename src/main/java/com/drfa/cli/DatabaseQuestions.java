package com.drfa.cli;

import com.drfa.validator.*;

import java.io.File;
import java.util.Date;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;

/**
 * Created by Sanjiv on 2/24/2015.
 */
public class DatabaseQuestions implements Questions {
    @Override
    public Answer askQuestions() {
        Answer answer = new Answer();

        String pluginPath = new DisplayQuestion(new PluginValidator()).displayQuestion("Please provide the absolute path of the plugin, if not sure then type (PLUGINS):");
        answer.setPluginPath(pluginPath);

        String metaDataFile = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of the rule file: ");
        answer.setMetaDataFile(metaDataFile);

        String fileDelimiter = new DisplayQuestion(new NoValidator()).displayQuestion("Please specify the file delimiter | or ,");
        answer.setFileDelimiter(fileDelimiter);

        String processId = new DisplayQuestion(new NumberValidator()).displayQuestion("Please enter the unique process id: " );
        answer.setProcessId(new Integer(processId));

        String baseDatabaseType = new DisplayQuestion(new DatabaseTypeValidator()).displayQuestion("What is the Base Database type (MYSQL, ORACLE, MS-SQL)");
        answer.setBaseDatabaseType(baseDatabaseType);
        String baseCredentialFile = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the credential file for accessing the base database");
        answer.setBaseDatabaseCredentialFile(baseCredentialFile);
        String baseFileFolder = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the path of the base folder where we have to create the file");
        answer.setBaseDatabaseFile(baseFileFolder);
        String baseOutputFile = answer.getBaseDatabaseFile() + File.separator + BASE_THREAD_NAME+"-"+ new Date().getTime() + ".csv";
        answer.setBaseFile(baseOutputFile);
        String baseSQLQuery = new DisplayQuestion(new NoValidator()).displayQuestion("Please provide the BASE SQL Query...");
        answer.setSqlQueryBase(baseSQLQuery);

        String targetDatabaseType = new DisplayQuestion(new DatabaseTypeValidator()).displayQuestion("What is the Target Database type (MYSQL, ORACLE, MS-SQL)");
        answer.setTargetDatabaseType(targetDatabaseType);
        String targetCredentialFile = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the credential file for accessing the target database");
        answer.setTargetDatabaseCredentialFile(targetCredentialFile);
        String targetFileFolder = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the path of the target folder where we have to create the file");
        answer.setTargetDatabaseFile(targetFileFolder);
        String targetOutputFile = answer.getTargetDatabaseFile() + File.separator + TARGET_THREAD_NAME+"-"+ new Date().getTime() + ".csv";
        answer.setTargetFile(targetOutputFile);
        String targetSQLQuery = new DisplayQuestion(new NoValidator()).displayQuestion("Please provide the TARGET SQL Query...");
        answer.setSqlQueryTarget(targetSQLQuery);

        return answer;
    }
}
