package com.drfa.cli;

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
        System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the absolute path of the base file: "));
        Scanner scanner = new Scanner(System.in);
        String baseFilePath = scanner.nextLine();
        answer.setBaseFile(baseFilePath);

        System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the absolute path of the target file: "));
        scanner = new Scanner(System.in);
        String targetFilePath = scanner.nextLine();
        answer.setTargetFile(targetFilePath);

        System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the absolute path of the meta data file: "));
        scanner = new Scanner(System.in);
        String metaDataFilePath = scanner.nextLine();
        answer.setMetaDataFile(metaDataFilePath);

        System.out.println( ansi().eraseScreen().fg(YELLOW).a("Key Index in the file(0,1,2,..., n"));
        scanner = new Scanner(System.in);
        String keyIndex = scanner.nextLine();
        answer.setKeyIndex(new Integer(keyIndex));

        System.out.println( ansi().eraseScreen().fg(YELLOW).a("Type of Report (XLS, HTML"));
        scanner = new Scanner(System.in);
        String typeOfReport = scanner.nextLine();
        answer.setTypeOfReport(typeOfReport);

        System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please select the category of the report SUMMARY, DETAILED, BOTH"));
        scanner = new Scanner(System.in);
        String reportCategory = scanner.nextLine();
        selectMoreQuestionOnBasisOfReportCategory(answer, reportCategory);

        return answer;
    }

    private void selectMoreQuestionOnBasisOfReportCategory(Answer answer, String reportCategory) {
        Scanner scanner;
        if("SUMMARY".equalsIgnoreCase(reportCategory)){
            System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the absolute path of SUMMARY Output"));
            scanner = new Scanner(System.in);
            String summaryOutPutPath = scanner.nextLine();
            answer.setSummaryOutputPath(summaryOutPutPath);
        }else if("DETAILED".equalsIgnoreCase(reportCategory)){
            System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the absolute path of DETAILED Output"));
            scanner = new Scanner(System.in);
            String detailedOutPutPath = scanner.nextLine();
            answer.setSummaryOutputPath(detailedOutPutPath);
        }else{
            System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the absolute path of SUMMARY Output"));
            scanner = new Scanner(System.in);
            String summaryOutPutPath = scanner.nextLine();
            answer.setSummaryOutputPath(summaryOutPutPath);
            System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the absolute path of DETAILED Output"));
            scanner = new Scanner(System.in);
            String detailedOutPutPath = scanner.nextLine();
            answer.setSummaryOutputPath(detailedOutPutPath);
        }
    }
}
