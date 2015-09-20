package acceptance.com.drfa.messaging;


import com.drfa.cli.Answer;
import com.drfa.cli.CommandConsole;
import com.drfa.engine.ReconciliationServer;
import com.drfa.messaging.jms.ActiveMqListener;
import com.drfa.report.ReportListener;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.drfa.util.DrfaProperties.BREAK_MESSAGE_QUEUE;
import static com.drfa.util.DrfaProperties.REC_ANSWER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EndToEndReportGenerationTest {
    private static final String TARGET_TEST_OUTPUT = "target/test-output/";
    private FileUtil fileUtil = new FileUtil();

    @Test()
    public void shouldBeAbleToSendAndReceiveMessages() throws Exception {
        ActiveMqRunner.startBroker();
        fileUtil.ensureNoReconciliationReportExists(TARGET_TEST_OUTPUT);

        ReconciliationServer reconciliationServer = new ReconciliationServer();
        new ActiveMqListener(reconciliationServer).startMsgListener(REC_ANSWER);

        ReportListener reportServer = new ReportListener();
        new ActiveMqListener(reportServer).startMsgListener(BREAK_MESSAGE_QUEUE);

        CommandConsole commandConsole = new CommandConsole();
        commandConsole.publishMessage(answer());

        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            if (fileUtil.fileExists("target/test-output/")) {
                latch.countDown();
            }
        }).start();

        latch.await(10, TimeUnit.SECONDS);


        assertThat(fileUtil.getAllFiles("target/test-output/").size(), is(1));

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
