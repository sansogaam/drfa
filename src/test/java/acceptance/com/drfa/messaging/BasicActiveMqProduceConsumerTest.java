package acceptance.com.drfa.messaging;


import com.drfa.jms.BasicMessageListener;
import com.drfa.messaging.jms.ActiveMqListener;
import com.drfa.messaging.jms.ActiveMqPublisher;
import com.drfa.messaging.jms.ActiveMqRunner;
import com.drfa.util.DrfaProperties;
import org.junit.Test;

import javax.jms.TextMessage;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicActiveMqProduceConsumerTest {


    @Test
    public void shouldBeAbleToSendAndReceiveMessages() throws Exception {
        ActiveMqRunner.startBroker();

        CountDownLatch latch = new CountDownLatch(1);
        BasicMessageListener listener = new BasicMessageListener(latch);

        String testQueue = "testQueue";
        new ActiveMqListener(listener).startMsgListener(testQueue, DrfaProperties.BROKER_URL);

        String msg = "Sample Text Message";
        new ActiveMqPublisher().sendMsg(msg, testQueue, DrfaProperties.BROKER_URL);

        latch.await(10, TimeUnit.SECONDS);

        List<TextMessage> messages = listener.getMessages();

        assertThat(messages.size(), is(1));
        assertThat(messages.get(0).getText(), is(msg));


    }


}
