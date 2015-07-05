package com.drfa.engine.file;

import com.drfa.engine.meta.ColumnAttribute;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Sanjiv on 7/2/2015.
 */
public class ScanUtility {

    public String extractTheLineOfPrimaryKey(String primaryKeyIndex, String line, String fileDelimiter){
        String splitLine[] = line.split(Pattern.quote(fileDelimiter));
        String splitPrimaryKey[] = primaryKeyIndex.split(Pattern.quote("-"));
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<splitPrimaryKey.length;i++) {
            sb.append(splitLine[new Integer(splitPrimaryKey[i])]).append(fileDelimiter);
        }
        String primaryKeyLine = sb.toString();
        return primaryKeyLine.substring(0,primaryKeyLine.length()-1);
    }
    
    public String construtToBeComparedLineFromTheOriginalLine(String fileDelimiter, String threadName, String line, List<ColumnAttribute> columnAttributes){
        StringBuffer toBeComparedLine = new StringBuffer();
        String splitLine[] = line.split(Pattern.quote(fileDelimiter));
        for(int i=0; i<columnAttributes.size();i++){
            ColumnAttribute columnAttribute = columnAttributes.get(i);
        }
        return null;
    }

}
