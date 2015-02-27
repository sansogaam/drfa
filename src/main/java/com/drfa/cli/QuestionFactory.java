package com.drfa.cli;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class QuestionFactory {

    public Questions getQuestion(String questionType) {
        if (questionType.equalsIgnoreCase("FILE")) {
            return new FileQuestions();
        }else if(questionType.equalsIgnoreCase("DATABASE")) {
            return new DatabaseQuestions();
        }
        return null;
    }

}
