package com.drfa.validator;

import java.io.File;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class FileValidator implements Validator {

    @Override
    public boolean validate(String inputString) {
        if(new File(inputString).exists()){
            return true;
        }else{
            System.out.println(String.format("The file %s does not exist. Please specify the correct path.",inputString));
            return false;
        }
    }
}
