package com.drfa.cli;

import com.drfa.validator.FileValidator;
import com.drfa.validator.NoValidator;

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

        String pluginPath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the plugin path");
        answer.setPluginPath(pluginPath);

        String metaDataFile = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the metadata file path");
        answer.setMetaDataFile(metaDataFile);

        String keyIndex = new DisplayQuestion(new NoValidator()).displayQuestion("Please provide the key index(0,1,2,3,..., n)");
        answer.setKeyIndex(new Integer(keyIndex));

        String baseDatabaseType = new DisplayQuestion(new NoValidator()).displayQuestion("What is the Base Database type (MYSQL, ORACLE, MS-SQL)");
        //TODO: Need to send this to validator

        String baseCredentialFile = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the credential file for accessing the base database");
        answer.setBaseDatabaseCredentialFile(baseCredentialFile);

        String baseFileFolder = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the path of the base folder where we have to create the file");
        answer.setBaseDatabaseFile(baseFileFolder);

        String baseOutputFile = answer.getBaseDatabaseFile() + File.separator + BASE_THREAD_NAME+"-"+ new Date().getTime() + ".csv";
        answer.setBaseFile(baseOutputFile);

        String baseSQLQuery = new DisplayQuestion(new NoValidator()).displayQuestion("Please provide the BASE SQL Query...");
        answer.setSqlQueryBase(baseSQLQuery);

        String targetDatabaseType = new DisplayQuestion(new NoValidator()).displayQuestion("What is the Target Database type (MYSQL, ORACLE, MS-SQL)");
        //TODO: Need to send this to validator

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
