package com.drfa.report;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportEnricherTest{

    @Test
    public void shouldTestMainMessageParser() throws Exception{
        String mainMessage = "C3~70#70#MATCHED#$C4~31-08-2015#31-08-2015#MATCHED#$C5~175#175#MATCHED#$C1~T7#T7#MATCHED#$C2~03-08-2015#03/08/2015#MATCHED#$";
        Map<String, List<String>> mapOfBreaks = expectedHashMap();
        BreakReport breakReport = new BreakReport();
        ReportEnricher reportEnricher = new ReportEnricher(breakReport);
        reportEnricher.enrich(mainMessage);
        mainMessage = "C3~80#80#MATCHED#$C4~05-09-2015#05-09-2015#MATCHED#$C5~200#200#MATCHED#$C1~T8#T8#MATCHED#$C2~04-08-2015#04/08/2015#MATCHED#$";
        reportEnricher.enrich(mainMessage);
        Map<Integer,Map<String, List<String>>> mapOfRowBreaks = breakReport.getMapOfBreaks();
        System.out.println(mapOfRowBreaks);
        assertThat(mapOfBreaks.size(), is(2));
    }
    

    private Map<String, List<String>> expectedHashMap(){
        Map<String, List<String>> mapOfBreaks = new HashMap<String, List<String>>();
        //Column-1
        List<String> listOfColumn1 = new ArrayList<String>();
        listOfColumn1.add("T7");
        listOfColumn1.add("T7");
        listOfColumn1.add("MATCHED");
        mapOfBreaks.put("C1",listOfColumn1);
        //Column-2
        List<String> listOfColumn2 = new ArrayList<String>();
        listOfColumn2.add("03-08-2015");
        listOfColumn2.add("03/08/2015");
        listOfColumn2.add("MATCHED");
        mapOfBreaks.put("C2",listOfColumn2);
        return mapOfBreaks;
    }
}