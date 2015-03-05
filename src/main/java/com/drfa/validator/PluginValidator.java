package com.drfa.validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
