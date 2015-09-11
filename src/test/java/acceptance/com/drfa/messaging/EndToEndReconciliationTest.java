package acceptance.com.drfa.messaging;


import com.drfa.cli.Answer;
import com.drfa.cli.CommandConsole;
import com.drfa.engine.Comparator;
import com.drfa.engine.ReconciliationContext;
import com.drfa.engine.ReconciliationServer;
import com.drfa.engine.file.CsvFileComparator;
import com.drfa.engine.meta.ColumnAttribute;
import com.drfa.jms.ActiveMqListener;
import com.drfa.jms.ActiveMqRunner;
import com.drfa.jms.BasicMessageListener;
import com.drfa.report.ReportListener;
import com.drfa.util.DrfaProperties;
import org.junit.Test;

import javax.jms.TextMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

        CommandConsole commandConsole = new CommandConsole();
        commandConsole.publishMessage(answer());
        
        ReconciliationServer reconciliationServer = new ReconciliationServer();
        new ActiveMqListener(reconciliationServer).startMsgListener(DrfaProperties.REC_ANSWER, DrfaProperties.BROKER_URL);
        

        CountDownLatch latch = new CountDownLatch(14);

        BasicMessageListener listener = new BasicMessageListener(latch);
        new ActiveMqListener(listener).startMsgListener(DrfaProperties.BREAK_MESSAGE_QUEUE, DrfaProperties.BROKER_URL);

        latch.await();
        List<TextMessage> messages = listener.getMessages();
        assertThat(messages.size(), is(14));
    }

    private Answer answer() {
        Answer answer = new Answer();
        answer.setKeyIndex(0);
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("FILE");
        answer.setProcessId(1);
        answer.setBaseFile(new File("src/test/resources/test.csv").getAbsolutePath());
        answer.setFileDelimiter("|");
        answer.setTargetFile(new File("src/test/resources/test1.csv").getAbsolutePath());
        answer.setMetaDataFile(new File("src/test/resources/reconciliation-input.xml").getAbsolutePath());
        answer.setPluginPath(new File("src/main/resources/plugins").getAbsolutePath());
        answer.setTypeOfReport("HTML");
        answer.setReportCategory("BOTH");
        answer.setReportOutputPath(new File("src/test/resources").getAbsolutePath());
        return answer;
    }
}
