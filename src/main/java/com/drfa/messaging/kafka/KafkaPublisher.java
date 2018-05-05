package com.drfa.messaging.kafka;

import com.drfa.messaging.Publisher;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaPublisher implements Publisher{


    private final static String BOOTSTRAP_SERVERS ="localhost:9092,localhost:9093,localhost:9094";
    long index=0;
    Producer<Long, String> producer;
    private String topicName;

    public KafkaPublisher(String topicName){
        this.topicName = topicName;
        producer = createProducer();
    }
    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaReconciliationProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    @Override
    public void publish(String message){
        try{
            publishMessage(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void publishMessage(String message) throws Exception {
        long time = System.currentTimeMillis();
        ProducerRecord<Long, String> record =new ProducerRecord<>(this.topicName , index,message);
        RecordMetadata metadata = producer.send(record).get();
        long elapsedTime = System.currentTimeMillis() - time;
        System.out.printf("sent record(key=%s value=%s) " +"meta(partition=%d, offset=%d) time=%d\n",
                record.key(), record.value(), metadata.partition(),
                metadata.offset(), elapsedTime);
        index++;
    }

}
