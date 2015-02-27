package com.drfa.validator;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public enum DatabaseTypeEnum {

    MYSQL("MYSQL"), ORACLE("ORACLE"), MSSQL("MS-SQL");

    private String value;
    DatabaseTypeEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static boolean doDatabaseTypeExist(String value){
        DatabaseTypeEnum databaseTypeEnums[] = DatabaseTypeEnum.values();
        for(DatabaseTypeEnum databaseTypeEnum : databaseTypeEnums){
            String enumValue = databaseTypeEnum.getValue();
            if(enumValue.equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }
}
