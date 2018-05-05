package acceptance.com.drfa.db;

import acceptance.com.drfa.helper.ActiveMqRunner;
import acceptance.com.drfa.helper.DBSeeder;
import acceptance.com.drfa.helper.DerbyServer;
import acceptance.com.drfa.helper.FileUtil;
import com.drfa.cli.Answer;
import com.drfa.cli.CommandConsole;
import com.drfa.engine.ReconciliationServer;
import com.drfa.jms.ResultListener;
import com.drfa.messaging.jms.ActiveMqListener;
import com.drfa.report.ResultMessageConstants;
import com.drfa.util.DrfaProperties;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;
import static com.drfa.util.DrfaProperties.BREAK_MESSAGE_QUEUE;
import static com.drfa.util.DrfaProperties.REC_ANSWER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Sanjiv on 9/20/2015.
 */
public class EndToEndDatabaseReconciliationTest {
    static final String BASE_DB = "baseDB";
    static final String TARGET_DB = "targetDB";
    static Connection baseConnection;
    static Connection targetConnection;
    private static final String DATABASE_TEST_OUTPUT = "target/test-db";
    private FileUtil fileUtil = new FileUtil();

    @BeforeClass
    public static void setUpThePreliminaryTaskForTest(){
        baseConnection = new DerbyServer().initialDerbyDatabase(BASE_DB);
        new DBSeeder(baseConnection).seedBaseDB();
        targetConnection = new DerbyServer().initialDerbyDatabase(TARGET_DB);
        new DBSeeder(targetConnection).seedTargetDB();
        ActiveMqRunner.startBroker();
    }

    @AfterClass
    public static void tearDownThePreliminaryTaskForTest(){
        new DBSeeder(baseConnection).tearBaseDB();
        new DBSeeder(targetConnection).tearTargetDB();
        new DerbyServer().shutDownDerbyDatabase(BASE_DB);
        new DerbyServer().shutDownDerbyDatabase(TARGET_DB);
        ActiveMqRunner.stopBroker();
    }

    @Test
    public void shouldTestEndToEndDatabaseReconciliationTest() throws Exception {
        
        fileUtil.ensureNoFileExistsInDirectory(DATABASE_TEST_OUTPUT);
        ReconciliationServer reconciliationServer = new ReconciliationServer();
        new ActiveMqListener(reconciliationServer).startMsgListener(REC_ANSWER);

        CountDownLatch latch = new CountDownLatch(104);
        ResultListener resultListener = new ResultListener(latch);
        new ActiveMqListener(resultListener).startMsgListener(BREAK_MESSAGE_QUEUE);

        CommandConsole commandConsole = new CommandConsole();
        commandConsole.publishMessage(answer());
        latch.await(10, TimeUnit.SECONDS);
        List<JSONObject> messages = resultListener.getMessages();
        assertThat(messages.size(), is(104));
        for (JSONObject jsonObject : messages) {
            assertThat(jsonObject.get(ResultMessageConstants.PROCESS_ID), Matchers.is(DrfaProperties.PROCESS_PREFIX+"1-"));
        }

    }


    private Answer answer() {
        Answer answer = new Answer();

        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("DATABASE");
        answer.setProcessId(1);
        answer.setFileDelimiter("|");

        answer.setBaseDatabaseCredentialFile("src/test/resources/derby-base.cfg");
        answer.setBaseDatabaseFile(DATABASE_TEST_OUTPUT);
        String baseOutputFile = answer.getBaseDatabaseFile() + File.separator + BASE_THREAD_NAME + "-" + new Date().getTime() + ".csv";
        answer.setBaseFile(baseOutputFile);


        answer.setBaseDatabaseType("DERBY");
        answer.setSqlQueryBase("SELECT ID, first_name, last_name, email_address,date_of_joining FROM EMPLOYEE");
        answer.setBaseDatabaseMetaDataFile("src/test/resources/rec-db-base.fmt");

        answer.setTargetDatabaseCredentialFile("src/test/resources/derby-target.cfg");

        answer.setTargetDatabaseFile(DATABASE_TEST_OUTPUT);
        String targetOutputFile = answer.getTargetDatabaseFile() + File.separator + TARGET_THREAD_NAME + "-" + new Date().getTime() + ".csv";
        answer.setTargetFile(targetOutputFile);

        answer.setTargetDatabaseType("DERBY");
        answer.setSqlQueryTarget("SELECT ID,name,address, email_detail, joining_date FROM PERSON");
        answer.setTargetDatabaseMetaDataFile("src/test/resources/rec-db-target.fmt");

        answer.setPluginPath("src/main/resources/plugins");

        answer.setMetaDataFile("src/test/resources/reconciliation-input.xml");
        return answer;
    }
}
