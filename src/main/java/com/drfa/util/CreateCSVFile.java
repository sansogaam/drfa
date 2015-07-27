package com.drfa.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public class CreateCSVFile {

    public static void main(String args[]){
        createDifferentTypeCSVFile();
    }

    private static void createDifferentTypeCSVFile() {
        Path file = Paths.get("D:/dev/test.csv");
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            for(int i=1; i<10; i++){
                String s = "T" + i + "|" + addDays(i, "dd-MM-yyyy")+ "|" + i*10 + "|" + addDays(i*5,"dd-MM-yyyy") + "|" + i*25 + "\n";
                writer.write(s, 0, s.length());
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
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
    
    private static void createStringCSVFile() {
        Path file = Paths.get("D:/dev/test.csv");
        Charset charset = Charset.forName("US-ASCII");
        String s = "T1|T2|T3|T4|T5";
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            for(int i=1; i<1000; i++){
                s = "T" + i + "|" + "T" + (i + 1) + "|" + "T" + (i + 2) + "|" + "T" + (i + 3) + "|" + "T" + (i + 4) + "|" + "T" + (i + 5)+ "|" + "T" + (i + 6)+  "\n";
                writer.write(s, 0, s.length());
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
