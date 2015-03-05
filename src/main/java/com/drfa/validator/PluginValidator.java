package com.drfa.validator;

import java.io.File;

/**
 * Created by Sanjiv on 3/5/2015.
 */
public class PluginValidator implements Validator {
    
    @Override
    public boolean validate(String inputString){
        if("PLUGINS".equalsIgnoreCase(inputString)){
            return true;
        }else{
            return new File(inputString).exists();
        }
    }
}
