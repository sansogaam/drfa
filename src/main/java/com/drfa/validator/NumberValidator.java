package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */

public class NumberValidator implements Validator {

    @Override
    public boolean validate(String inputString) {
        try {
            Integer inputValue = new Integer(inputString);
            if(inputValue >= 0) {
                return true;
            }else{
                System.out.println("Please do enter the +ve integer");
                return false;
            }
        }catch(NumberFormatException nfe){
            System.out.println("Please do enter the valid integer");
            return false;
        }
    }
}
