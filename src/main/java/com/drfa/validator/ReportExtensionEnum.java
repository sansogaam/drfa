package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public enum ReportExtensionEnum {
    XLS("XLS"), HTML("HTML");

    private String value;

    ReportExtensionEnum(String value){
        this.value = value;
    }

    private String getValue(){
        return value;
    }
    public static boolean doExtensionExist(String value){
        ReportExtensionEnum reportExtensionEnums[] = ReportExtensionEnum.values();
        for(ReportExtensionEnum reportExtensionEnum : reportExtensionEnums){
            String enumValue = reportExtensionEnum.getValue();
            if(enumValue.equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }
}
