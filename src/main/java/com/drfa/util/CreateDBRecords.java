package com.drfa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sanjiv on 9/2/2015.
 */
public class CreateDBRecords {
    public static void main(String args[]){
        //printCreateBaseDBRecords();
        printCreateTargetDBRecords();
    }
    public static void printCreateBaseDBRecords() {
        for (int i = 1; i < 100; i++) {
            StringBuffer sb = new StringBuffer("INSERT INTO EMPLOYEE VALUES(");
            sb.append("'").append(i).append("'").append(",");
            sb.append("'").append("FIRST-").append(i).append("'").append(",");
            sb.append("'").append("LAST-").append(i).append("'").append(",");
            sb.append("'").append("test.drfa-").append(i).append("@drfa.com").append("'").append(",");
            sb.append("'").append(addDays(i,"yyyy-MM-dd")).append("'").append(");");
            System.out.println(sb.toString());
        }
    }

    public static void printCreateTargetDBRecords() {
        for (int i = 1; i < 100; i++) {
            StringBuffer sb = new StringBuffer("INSERT INTO PERSON VALUES(");
            sb.append("'").append(i).append("'").append(",");
            sb.append("'").append("FIRST-").append(i).append("'").append(",");
            sb.append("'").append("ADDRESS-").append(i).append("'").append(",");
            sb.append("'").append("test.drfa-").append(i).append("@drfa.com").append("'").append(",");
            sb.append("'").append(addDays(i,"yyyy-MM-dd")).append("'").append(");");
            System.out.println(sb.toString());
        }
    }
    public static String addDays(int numberOfDays, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, numberOfDays); // Adding 5 days
        String output = sdf.format(c.getTime());
        return output;
    }
}
