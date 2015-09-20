package acceptance.com.drfa.rec;


import acceptance.com.drfa.helper.ActiveMqRunner;
import com.drfa.cli.Answer;
import com.drfa.cli.CommandConsole;
import com.drfa.engine.ReconciliationServer;
import com.drfa.jms.ResultListener;
import com.drfa.messaging.jms.ActiveMqListener;
import com.drfa.report.ResultMessageConstants;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.drfa.util.DrfaProperties.BREAK_MESSAGE_QUEUE;
import static com.drfa.util.DrfaProperties.REC_ANSWER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EndToEndReconciliationTest {

    @Test()
    public void shouldBeAbleToReconcileTwoFilesAndSendMessagesToProvidedListener() throws Exception {
        ActiveMqRunner.startBroker();

        ReconciliationServer reconciliationServer = new ReconciliationServer();
        new ActiveMqListener(reconciliationServer).startMsgListener(REC_ANSWER);

        CountDownLatch latch = new CountDownLatch(14);
        ResultListener resultListener = new ResultListener(latch);
        new ActiveMqListener(resultListener).startMsgListener(BREAK_MESSAGE_QUEUE);

        CommandConsole commandConsole = new CommandConsole();
        commandConsole.publishMessage(answer());

        latch.await(10, TimeUnit.SECONDS);
        List<JSONObject> messages = resultListener.getMessages();
        assertThat(messages.size(), is(14));


        for (JSONObject jsonObject : messages) {
            assertThat(jsonObject.get(ResultMessageConstants.PROCESS_ID), Matchers.is("PROCESS_ID:1-"));
        }

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
