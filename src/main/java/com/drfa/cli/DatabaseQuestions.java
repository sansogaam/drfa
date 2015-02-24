package com.drfa.cli;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by Sanjiv on 2/24/2015.
 */
public class DatabaseQuestions implements Questions {
    @Override
    public Answer askQuestions() {
        Answer answer = new Answer();
        System.out.println( ansi().eraseScreen().fg(YELLOW).a("What is the Base Database type (MYSQL, ORACLE, MSSQL)"));
        Scanner scanner = new Scanner(System.in);
        String baseDatabaseType = scanner.nextLine();
        System.out.println( ansi().eraseScreen().fg(YELLOW).a("Please provide the credential file for accessing the database"));
        scanner = new Scanner(System.in);
        String baseCredentialFile = scanner.nextLine();
        answer.setBaseDatabaseCredentialFile(baseCredentialFile);
        return answer;
    }
}
