package com.drfa.engine.file;

import com.drfa.engine.meta.ColumnAttribute;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Sanjiv on 7/2/2015.
 */
public class ScanUtility {

    static Logger LOG = Logger.getLogger(ScanUtility.class);

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
        StringBuffer toBeComparedLineBuffer = new StringBuffer();
        LOG.debug(String.format("Line to find primary key %s with thread Name %s and delimeter %s", line, threadName, fileDelimiter));
        String splitLine[] = line.split(Pattern.quote(fileDelimiter));
        LOG.debug(String.format("Length of the split line %s and column attributes size %s",splitLine.length, columnAttributes.size()));
        for(int i=0; i<columnAttributes.size();i++){
            ColumnAttribute columnAttribute = columnAttributes.get(i);
            String columnMatching = columnAttribute.getColumnMatching();
            String columnSplit[] = columnMatching.split(Pattern.quote("|"));
            int columnIndex=0;
            if(threadName.equalsIgnoreCase("BASE")){
                String baseColumn = columnSplit[0];
                columnIndex = new Integer(baseColumn.substring(baseColumn.indexOf("-")+1, baseColumn.length()));
            }else if(threadName.equalsIgnoreCase("TARGET")) {
                String targetColumn = columnSplit[1];
                columnIndex = new Integer(targetColumn.substring(targetColumn.indexOf("-")+1, targetColumn.length()));
            }
            LOG.debug("Column Index:"+columnIndex+"Split Line: "+ splitLine.length);
            toBeComparedLineBuffer.append(splitLine[columnIndex]).append(fileDelimiter);
        }
        String toBeComparedLine = toBeComparedLineBuffer.toString();
        return toBeComparedLine.substring(0, toBeComparedLine.length()-1);
    }

}
