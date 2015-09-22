package acceptance.com.drfa.report;


import acceptance.com.drfa.helper.ActiveMqRunner;
import acceptance.com.drfa.helper.FileUtil;
import com.drfa.cli.Answer;
import com.drfa.cli.CommandConsole;
import com.drfa.engine.ReconciliationServer;
import com.drfa.messaging.jms.ActiveMqListener;
import com.drfa.report.ReportListener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

    @BeforeClass
    public static void setUpThePreliminaryTaskForTest(){
        ActiveMqRunner.startBroker();
    }

    @AfterClass
    public static void tearDownThePreliminaryTaskForTest(){
        ActiveMqRunner.stopBroker();
    }

    @Test()
    public void shouldBeAbleToRecieveMessagesAndProduceTheDesiredReport() throws Exception {

        fileUtil.ensureNoFileExistsInDirectory(TARGET_TEST_OUTPUT);
        ReconciliationServer reconciliationServer = new ReconciliationServer();
        new ActiveMqListener(reconciliationServer).startMsgListener(REC_ANSWER);

        ReportListener reportServer = new ReportListener();
        new ActiveMqListener(reportServer).startMsgListener(BREAK_MESSAGE_QUEUE);

        CommandConsole commandConsole = new CommandConsole();
        commandConsole.publishMessage(answer());

        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            while (!fileUtil.fileExists("target/test-output/")) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
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
