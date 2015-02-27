package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class ReportExtensionValidator implements Validator {

    @Override
    public boolean validate(String inputString) {
        if(ReportExtensionEnum.doExtensionExist(inputString)){
            return true;
        }else{
            System.out.println("Please do enter the right report extension type");
            return false;
        }
    }
}
