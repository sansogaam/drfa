package com.drfa.prop;

import java.io.File;

public class PropertyStarter {

    public static void main(String args[]){
        if(args.length == 0){
            System.out.println("Proper Usage is: pass the properties file argument");
            System.exit(0);
        }

        if(!new File(args[0]).exists()){
            System.out.println(String.format("Properties file does not exist in the specified location %s ", args[0]));
            System.exit(0);
        }
    }
}
