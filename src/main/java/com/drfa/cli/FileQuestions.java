package com.drfa.cli;

import com.drfa.validator.*;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by Sanjiv on 2/18/2015.
 */
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

        String pluginPath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of the plugin: ");
        answer.setPluginPath(pluginPath);

        String metaDataFilePath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of the meta data file: ");
        answer.setMetaDataFile(metaDataFilePath);

        String keyIndex = new DisplayQuestion(new NumberValidator()).displayQuestion("Please Enter Key Index in the file(0,1,2,..., n)");
        answer.setKeyIndex(new Integer(keyIndex));

        String typeOfReport = new DisplayQuestion(new ReportExtensionValidator()).displayQuestion("Type of Report (XLS, HTML)");
        answer.setTypeOfReport(typeOfReport);

        String reportCategory = new DisplayQuestion(new ReportDetailValidator()).displayQuestion("Please select the category of the report SUMMARY, DETAILED, BOTH");
        selectMoreQuestionOnBasisOfReportCategory(answer, reportCategory);

        return answer;
    }

    private void selectMoreQuestionOnBasisOfReportCategory(Answer answer, String reportCategory) {
        if("SUMMARY".equalsIgnoreCase(reportCategory)){
            String summaryOutPutPath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of SUMMARY Output");
            answer.setSummaryOutputPath(summaryOutPutPath);
        }else if("DETAILED".equalsIgnoreCase(reportCategory)){
            String detailedOutPutPath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of DETAILED Output");
            answer.setSummaryOutputPath(detailedOutPutPath);
        }else{
            String summaryOutPutPath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of SUMMARY Output");
            answer.setSummaryOutputPath(summaryOutPutPath);
            String detailedOutPutPath = new DisplayQuestion(new FileValidator()).displayQuestion("Please provide the absolute path of DETAILED Output");
            answer.setSummaryOutputPath(detailedOutPutPath);
        }
    }
}
