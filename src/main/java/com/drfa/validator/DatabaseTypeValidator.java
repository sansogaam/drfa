package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class DatabaseTypeValidator implements Validator{

    @Override
    public boolean validate(String inputString) {
        if(DatabaseTypeEnum.doDatabaseTypeExist(inputString)){
            return true;
        }else{
            System.out.println("Please do enter the valid database type, specified in the questions");
            return false;
        }
    }
}
