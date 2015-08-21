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
    
    @Test
    public void shouldTestTotalNumberOfMatchedRecords() throws Exception{
        String message ="MATCHED_RECORDS-34";
        BreakReport breakReport = new BreakReport();
        ReportEnricher reportEnricher = new ReportEnricher(breakReport);
        reportEnricher.enrich(message);
        assertThat(breakReport.getMatchedWithNumberOfKeys(), is(34));
    }

    @Test
    public void shouldTestTotalNumberOfBasedRecords() throws Exception{
        String message ="BASE_TOTAL_RECORDS-86";
        BreakReport breakReport = new BreakReport();
        ReportEnricher reportEnricher = new ReportEnricher(breakReport);
        reportEnricher.enrich(message);
        assertThat(breakReport.getBaseTotalRecords(), is(86));
    }

    @Test
    public void shouldTestTotalNumberOfTargetRecords() throws Exception{
        String message ="TARGET_TOTAL_RECORDS-967";
        BreakReport breakReport = new BreakReport();
        ReportEnricher reportEnricher = new ReportEnricher(breakReport);
        reportEnricher.enrich(message);
        assertThat(breakReport.getTargetTotalRecords(), is(967));
    }

    @Test
    public void shouldTestBaseOneSidedBreak() throws Exception{
        String message = "ONE-SIDED-BASE-C3~Exist3$C4~Exist4$C1~Exist1$C2~Exist2$";
        BreakReport breakReport = new BreakReport();
        ReportEnricher reportEnricher = new ReportEnricher(breakReport);
        reportEnricher.enrich(message);
        Map<Integer, Map<String, String>> mapOfBreaks = breakReport.getBaseOneSidedBreaksCollection();
        assertThat(mapOfBreaks.size(),is(1));
        Map<String, String> mapOfColumns = mapOfBreaks.get(new Integer(1));
        assertThat(mapOfColumns.size(),is(4));
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