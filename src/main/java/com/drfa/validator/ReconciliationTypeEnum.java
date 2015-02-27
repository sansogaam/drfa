package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public enum ReconciliationTypeEnum {
    FILE("FILE"), DATABASE("DATABASE");

    private String value;

    ReconciliationTypeEnum(String value){
        this.value = value;
    }
    private String getValue(){
        return value;
    }

    public static boolean doReconciliationTypeExist(String value){
        ReconciliationTypeEnum reconciliationTypeEnums[] = ReconciliationTypeEnum.values();
        for(ReconciliationTypeEnum reconciliationTypeEnum : reconciliationTypeEnums){
            String enumValue = reconciliationTypeEnum.getValue();
            if(enumValue.equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }

}
