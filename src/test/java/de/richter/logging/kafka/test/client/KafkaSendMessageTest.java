package de.richter.logging.kafka.test.client;

import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.client.impl.KafkaSendMessageFireForget;
import de.richter.logging.kafka.client.impl.KafkaSendMessageSync;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;


class KafkaSendMessageTest {

    Producer<String,String> producer;
    long pausedTime = 6000;

    @BeforeEach
    void initialiseTest() throws ExecutionException, InterruptedException {
        producer = mock(Producer.class);
        FutureRecordMetadata futureRecord = mock(FutureRecordMetadata.class);
        when(futureRecord.get()).thenAnswer(new Answer<RecordMetadata>(){

            @Override
            public RecordMetadata answer(InvocationOnMock invocationOnMock) throws Throwable {
                Thread.sleep(pausedTime);
                return new RecordMetadata(null,12,12,12,new Long(2),12,12);
            }
        });
        when(producer.send(any())).thenAnswer(new Answer<FutureRecordMetadata>() {
            @Override
            public FutureRecordMetadata answer(InvocationOnMock invocationOnMock) throws Throwable {
                return futureRecord;
            }
        });
    }

    @Test
    void sendKafkaMessageSync() throws ExecutionException, InterruptedException {
        ProducerRecord<String,String> record = new ProducerRecord("topic","value");
        IKafkaSendMessage messageClient = new KafkaSendMessageSync();
        long start = System.currentTimeMillis();
        messageClient.sendKafkaMessage(producer,record);
        long end = System.currentTimeMillis();
        long spentTime = end-start;
        Assertions.assertTrue(spentTime > pausedTime);
    }

    @Test
    void sendKafkaMessageFireAndForget(){
        ProducerRecord<String,String> record = new ProducerRecord<>("topic"," value");
        IKafkaSendMessage messageClient = new KafkaSendMessageFireForget();
        long start = System.currentTimeMillis();
        messageClient.sendKafkaMessage(producer,record);
        long end = System.currentTimeMillis();
        long spentTime = end-start;
        Assertions.assertTrue(spentTime<pausedTime);
    }

    @Test
    void sendKafkaMessageAsync(){
        ProducerRecord<String,String> record = new ProducerRecord<>("topic"," value");
        IKafkaSendMessage messageClient = new KafkaSendMessageFireForget();
        long start = System.currentTimeMillis();
        messageClient.sendKafkaMessage(producer,record);
        long end = System.currentTimeMillis();
        long spentTime = end-start;
        Assertions.assertTrue(spentTime<pausedTime);
    }
}