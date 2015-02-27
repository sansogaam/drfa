package com.drfa.cli;

import com.drfa.validator.Validator;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class DisplayQuestion {
    Validator validator;

    public DisplayQuestion(Validator validator){
        this.validator = validator;
    }

    public String displayQuestion(String question){
        while(true){
            System.out.println( ansi().eraseScreen().fg(YELLOW).a(question));
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            if(validator.validate(inputString)){
                return inputString;
            }
        }
    }
}
