package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class ReportDetailValidator implements Validator {

    @Override
    public boolean validate(String inputString) {
        if(ReportDetailEnum.doReportDetailExist(inputString)){
            return true;
        }else{
            System.out.println("Please do enter the right report detail mentioned in the question");
            return false;
        }

    }
}
