package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class NoValidator implements Validator {

    @Override
    public boolean validate(String inputString) {
        return true;
    }
}
