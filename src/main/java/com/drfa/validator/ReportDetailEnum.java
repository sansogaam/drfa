package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public enum ReportDetailEnum {

    SUMMARY("SUMMARY"), DETAILED("DETAILED"), BOTH("BOTH");

    private String value;

    ReportDetailEnum(String value){
        this.value = value;
    }

    private String getValue(){
        return value;
    }
    public static boolean doReportDetailExist(String value){
        ReportDetailEnum reportDetailEnums[] = ReportDetailEnum.values();
        for(ReportDetailEnum reportDetailEnum : reportDetailEnums){
            String enumValue = reportDetailEnum.getValue();
            if(enumValue.equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }

}
