package com.drfa.cli;

import com.drfa.validator.FileValidator;
import com.drfa.validator.NoValidator;
import com.drfa.validator.NumberValidator;


public class FileQuestions implements Questions {

    @Override
    public Answer askQuestions() {
        Answer answer = new Answer();

        String baseFilePath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of the base file: ");
        answer.setBaseFile(baseFilePath);

        String targetFilePath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of the target file: ");
        answer.setTargetFile(targetFilePath);

        String fileDelimiter = new DisplayQuestion(new NoValidator()).displayQuestion("Please specify the file delimiter | or ,");
        answer.setFileDelimiter(fileDelimiter);

        String processId = new DisplayQuestion(new NumberValidator()).displayQuestion("Please enter the unique process id: " );
        answer.setProcessId(new Integer(processId));

        String metaDataFilePath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of the rule file: ");
        answer.setMetaDataFile(metaDataFilePath);

        String baseKeyIndex = new DisplayQuestion(new NumberValidator()).displayQuestion("Please Enter Base Key Index in the file. Combination of column can be represented as(0-1,1-2). Number represent the index of the column in the file");
        answer.setBaseKeyIndex(baseKeyIndex);

        String targetKeyIndex = new DisplayQuestion(new NumberValidator()).displayQuestion("Please Enter Target Key Index in the file. Combination of column can be represented as(0-1,1-2). Number represent the index of the column in the file");
        answer.setBaseKeyIndex(targetKeyIndex);

        return answer;
    }

}
