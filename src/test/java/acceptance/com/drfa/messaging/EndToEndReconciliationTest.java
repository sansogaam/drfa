package acceptance.com.drfa.messaging;


import com.drfa.cli.Answer;
import com.drfa.cli.CommandConsole;
import com.drfa.engine.ReconciliationServer;
import com.drfa.jms.ActiveMqListener;
import com.drfa.jms.ActiveMqRunner;
import com.drfa.jms.ResultListener;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.drfa.util.DrfaProperties.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EndToEndReconciliationTest {
    public static final String TARGET_TEST_OUTPUT = "target/test-output/";
    private FileUtil fileUtil = new FileUtil();

    @Test()
    public void shouldBeAbleToSendAndReceiveMessages() throws Exception {
        ActiveMqRunner.startBroker();
                
        fileUtil.ensureNoReconciliationReportExists(TARGET_TEST_OUTPUT);

        CommandConsole commandConsole = new CommandConsole();
        commandConsole.publishMessage(answer());
        
        ReconciliationServer reconciliationServer = new ReconciliationServer();
        new ActiveMqListener(reconciliationServer).startMsgListener(REC_ANSWER, BROKER_URL);
        

        CountDownLatch latch = new CountDownLatch(14);

        ResultListener resultListener = new ResultListener(latch);
        new ActiveMqListener(resultListener).startMsgListener(BREAK_MESSAGE_QUEUE, BROKER_URL);

        latch.await();
        List<String> messages = resultListener.getMessages();
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
