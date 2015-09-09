package acceptance.com.drfa.messaging;


import com.drfa.engine.ReconciliationServer;
import com.drfa.jms.ActiveMqListener;
import com.drfa.jms.ActiveMqPublisher;
import com.drfa.jms.ActiveMqRunner;
import com.drfa.util.DrfaProperties;

public class ReconciliationServerAcceptanceTest {

    //    @Test()
    public void shouldBeAbleToSendAndReceiveMessages() throws Exception {
        ActiveMqRunner.startBroker();
        String testQueue = "REC_ANSWER";
        new ActiveMqListener(new ReconciliationServer()).startMsgListener(testQueue, DrfaProperties.BROKER_URL);
        String msg = "Sample Text Message";
        new ActiveMqPublisher().sendMsg(msg, testQueue, DrfaProperties.BROKER_URL);
        while (true) {
            Thread.sleep(100);
        }

    }
}
