package com.drfa.engine.file.scan;

import com.drfa.engine.meta.ColumnAttribute;

import java.util.List;
import java.util.regex.Pattern;


public class ScanUtility {

    public String extractTheLineOfPrimaryKey(String primaryKeyIndex, String line, String fileDelimiter) {
        String splitLine[] = line.split(Pattern.quote(fileDelimiter));
        String splitPrimaryKey[] = primaryKeyIndex.split(Pattern.quote("-"));
        StringBuilder sb = new StringBuilder();
        for (String aSplitPrimaryKey : splitPrimaryKey) {
            sb.append(splitLine[new Integer(aSplitPrimaryKey)]).append(fileDelimiter);
        }
        String primaryKeyLine = sb.toString();
        return primaryKeyLine.substring(0, primaryKeyLine.length() - 1);
    }

    public String constructToBeComparedLineFromTheOriginalLine(String fileDelimiter, String threadName, String line, List<ColumnAttribute> columnAttributes) {
        StringBuilder toBeComparedLineBuffer = new StringBuilder();
        String splitLine[] = line.split(Pattern.quote(fileDelimiter));
        for (ColumnAttribute columnAttribute : columnAttributes) {
            String columnMatching = columnAttribute.getColumnMatching();
            String columnSplit[] = columnMatching.split(Pattern.quote(fileDelimiter));
            int columnIndex = 0;
            if (threadName.equalsIgnoreCase("BASE")) {
                String baseColumn = columnSplit[0];
                columnIndex = new Integer(baseColumn.substring(baseColumn.indexOf("-") + 1, baseColumn.length()));
            } else if (threadName.equalsIgnoreCase("TARGET")) {
                String targetColumn = columnSplit[1];
                columnIndex = new Integer(targetColumn.substring(targetColumn.indexOf("-") + 1, targetColumn.length()));
            }
            toBeComparedLineBuffer.append(splitLine[columnIndex]).append(fileDelimiter);
        }
        String toBeComparedLine = toBeComparedLineBuffer.toString();
        return toBeComparedLine.substring(0, toBeComparedLine.length() - 1);
    }

}
