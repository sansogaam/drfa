package acceptance.com.drfa.messaging;


import com.drfa.cli.Answer;
import com.drfa.cli.CommandConsole;
import com.drfa.engine.ReconciliationServer;
import com.drfa.jms.ResultListener;
import com.drfa.messaging.jms.ActiveMqListener;
import com.drfa.messaging.jms.ActiveMqRunner;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.drfa.util.DrfaProperties.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

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

        latch.await(10, TimeUnit.SECONDS);
        List<String> messages = resultListener.getMessages();
        assertThat(messages.size(), is(14));

        assertThat(messages, hasItem("PROCESS_ID:1-TARGET_TOTAL_RECORDS-9"));
        assertThat(messages, hasItem("PROCESS_ID:1-BASE_TOTAL_RECORDS-9"));
        assertThat(messages, hasItem("PROCESS_ID:1-MATCHED_RECORDS-9"));
        assertThat(messages, hasItem("PROCESS_ID:1-BASE_ONE_SIDED_BREAK-0"));
        assertThat(messages, hasItem("PROCESS_ID:1-TARGET_ONE_SIDED_BREAK-0"));
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
