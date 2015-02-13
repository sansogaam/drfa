package com.drfa.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Sanjiv on 2/13/2015.
 */
public class MessageSplitter {

    String message;
    public MessageSplitter(String message){
        this.message = message;
    }

    public List<String> splitMessage(){
        List<String> splittedList = new ArrayList<String>();
        String splitTheMessage[] = message.split(Pattern.quote("$"));
        String firstLine = splitTheMessage[0];
        String secondLine = splitTheMessage[1];
        boolean isFromBaseFile = isItFromBaseFile(firstLine);
        if(isFromBaseFile){
            firstLine = firstLine.substring(5, firstLine.length());
            splittedList.add(firstLine);
            splittedList.add(secondLine);
        }else{
            firstLine = firstLine.substring(7, firstLine.length());
            splittedList.add(secondLine);
            splittedList.add(firstLine);
        }
        return splittedList;
    }

    private boolean isItFromBaseFile(String firstLine){
        return firstLine.startsWith("BASE:");
    }

}
