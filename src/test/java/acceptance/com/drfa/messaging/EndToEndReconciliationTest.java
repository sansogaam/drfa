package acceptance.com.drfa.messaging;


import com.drfa.cli.Answer;
import com.drfa.engine.Comparator;
import com.drfa.engine.ReconciliationContext;
import com.drfa.engine.file.CsvFileComparator;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.jms.ActiveMqListener;
import com.drfa.jms.ActiveMqRunner;
import com.drfa.report.ReportServer;
import com.drfa.util.DrfaProperties;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EndToEndReconciliationTest {
    public static final String TARGET_TEST_OUTPUT = "target/test-output/";
    private FileUtil fileUtil = new FileUtil();

    @Test()
    public void shouldBeAbleToSendAndReceiveMessages() throws Exception {
        
        ActiveMqRunner.startBroker();
        fileUtil.makeDirectoryIfNotExist(TARGET_TEST_OUTPUT);
                
        fileUtil.ensureNoReconciliationReportExists(TARGET_TEST_OUTPUT);


        ReportServer reportServer = new ReportServer();
        new ActiveMqListener(reportServer).startMsgListener("queue://BREAK_MESSAGE", DrfaProperties.BROKER_URL);

        Comparator comparator = new CsvFileComparator(context(), answer());
        comparator.compare();


        assertThat(fileUtil.getAllFiles(TARGET_TEST_OUTPUT).size(), is(1));

    }

    private Answer answer() {
        Answer answer = new Answer();
        answer.setKeyIndex(0);
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("FILE");
        answer.setBaseFile(new File("src/test/resources/test.csv").getAbsolutePath());
        answer.setFileDelimiter("|");
        answer.setTargetFile(new File("src/test/resources/test1.csv").getAbsolutePath());
        answer.setMetaDataFile(new File("src/test/resources/testing.fmt").getAbsolutePath());
        answer.setPluginPath(new File("src/main/resources/plugins").getAbsolutePath());
        answer.setReportOutputPath(new File("src/test/resources").getAbsolutePath());
        return answer;
    }

    private ReconciliationContext context() {
        ReconciliationContext context = new ReconciliationContext();
        context.setColumnAttributes(populateColumnNames());
        return context;
    }

    private List<ColumnAttribute> populateColumnNames() {
        List<ColumnAttribute> columnAttributes = new ArrayList<ColumnAttribute>();
        columnAttributes.add(new ColumnAttribute("C1", "String", "B-0|T-0", "SP-(B-NR|T-NR)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C2", "Date", "B-1|T-1", "DF-(B-dd-MM-yyyy|T-dd/MM/yyyy)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C3", "Integer", "B-2|T-2", "TA-(B-NR|T-NR)-(R-1)"));
        columnAttributes.add(new ColumnAttribute("C4", "Date", "B-3|T-3", "DF-(B-dd-MM-yyyy|T-dd-MM-yyyy)-(R-NA)"));
        columnAttributes.add(new ColumnAttribute("C5", "Integer", "B-4|T-4", "TP-(B-NR|T-NR)-(R-10)"));
        return columnAttributes;
    }

}
