package acceptance.com.drfa.messaging;


import com.drfa.util.ActiveMqRunner;
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
        MQListener listener = new MQListener(latch);
        listener.startMsgListener();

        String msg = "Sample Text Message";
        new MQProducer().sendMsg(msg);

        latch.await(10, TimeUnit.SECONDS);

        List<TextMessage> messages = listener.getMessages();

        assertThat(messages.size(), is(1));
        assertThat(messages.get(0).getText(), is(msg));


    }


}
