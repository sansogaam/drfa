package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class ReconciliationTypeValidator implements Validator {

    @Override
    public boolean validate(String inputString) {
        if(ReconciliationTypeEnum.doReconciliationTypeExist(inputString)){
            return true;
        }else{
            System.out.println("Please do enter the right reconciliation type");
            return false;
        }
    }
}
