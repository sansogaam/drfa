package com.drfa.cli;

import com.drfa.engine.ReconciliationEngine;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class CommandConsole {

    public static void main(String args[]) {
        System.out.println( ansi().eraseScreen().fg(RED).a("Welcome to reconciliation tool"));
        System.out.println( ansi().eraseScreen().fg(GREEN).a("Enter the reconciliation type (FILE, DATABASES)"));
        Scanner scanner = new Scanner(System.in);
        String typeOfReconciliation = scanner.nextLine();
        System.out.println("Type of reconciliation: " + typeOfReconciliation);
        QuestionFactory questionFactory = new QuestionFactory();
        Questions questions = questionFactory.getQuestion(typeOfReconciliation);
        Answer answer = questions.askQuestions();
        ReconciliationEngine reconciliationEngine = new ReconciliationEngine(answer);
        try {
            reconciliationEngine.reconcile();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
