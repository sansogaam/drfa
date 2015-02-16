package com.drfa.engine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public class CreateCSVFile {

    public static void main(String args[]){
        Path file = Paths.get("D:/dev/test1.csv");
        Charset charset = Charset.forName("US-ASCII");
        String s = "T1|T2|T3|T4|T5";
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            for(int i=0; i<1000; i++){
                s = "T" + i + "|" + "T" + (i + 1) + "|" + "T" + (i + 2) + "|" + "T" + (i + 3) + "\n";
                writer.write(s, 0, s.length());
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
