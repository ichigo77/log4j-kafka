package de.richter.logging.kafka.test.client;

import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.client.impl.KafkaAvroProducer;
import de.richter.logging.kafka.client.impl.KafkaByteProducer;
import de.richter.logging.kafka.client.impl.KafkaStringProducer;
import de.richter.logging.kafka.configuration.KafkaConfigurationException;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.MessageSendType;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.Serializable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class KafkaProducerTest {

    private final static String messageText="This is a test";

    @Test
    void sendStringLogEventNoSendMessageImplShouldThrowNullPointerException() {
        LogKafkaProducer stringProducer = new KafkaStringProducer();
        Assertions.assertThrows(NullPointerException.class, () -> stringProducer.sendLogEvent(getLogEvent(),getLayout()));
    }

    @Test
    void sendByteLogEventNoSendMessageImplShouldThrowNullPointerException() {
        LogKafkaProducer stringProducer = new KafkaByteProducer();
        Assertions.assertThrows(NullPointerException.class, () -> stringProducer.sendLogEvent(getLogEvent(),getLayout()));
    }

    @Test
    void sendAvroLogEventNoSendMessageImplShouldThrowNullPointerException() {
        LogKafkaProducer stringProducer = new KafkaAvroProducer();
        Assertions.assertThrows(NullPointerException.class, () -> stringProducer.sendLogEvent(getLogEvent(),getLayout()));
    }

    @Test
    void sendStringLogEventShallHaveAStringRecordWithSameMessage(){
        String expectedMessage = messageText.concat("\n"); // new line is needed for comparing with log4J default layout pattern
        IKafkaSendMessage messageClient = mock(IKafkaSendMessage.class);
        ArgumentCaptor<Producer> producerArgCaptor = ArgumentCaptor.forClass(Producer.class);
        ArgumentCaptor<ProducerRecord> recordArgCaptor = ArgumentCaptor.forClass(ProducerRecord.class);
        doNothing().when(messageClient).sendKafkaMessage(producerArgCaptor.capture(),recordArgCaptor.capture());
        LogKafkaProducer stringProducer = new KafkaStringProducer();
        stringProducer.setSendMessageInstance(messageClient);
        stringProducer.initialiseProducer(initialiseProperties(MessageSendType.SYNC,SerializerTypes.STRING));
        stringProducer.sendLogEvent(getLogEvent(),getLayout());

        ProducerRecord<String,String> record = recordArgCaptor.getValue();
        Assertions.assertEquals(expectedMessage,record.value());
    }

    @Test
    void sendByteArrayLogEventShallHaveAByteArrayRecordWithSameMessage(){
        byte[] expectedMessage = messageText.concat("\n").getBytes();
        IKafkaSendMessage messageClient = mock(IKafkaSendMessage.class);
        ArgumentCaptor<Producer> producerArgCaptor = ArgumentCaptor.forClass(Producer.class);
        ArgumentCaptor<ProducerRecord> recordArgCaptor = ArgumentCaptor.forClass(ProducerRecord.class);
        doNothing().when(messageClient).sendKafkaMessage(producerArgCaptor.capture(),recordArgCaptor.capture());
        LogKafkaProducer stringProducer = new KafkaByteProducer();
        stringProducer.setSendMessageInstance(messageClient);
        stringProducer.initialiseProducer(initialiseProperties(MessageSendType.SYNC,SerializerTypes.STRING));
        stringProducer.sendLogEvent(getLogEvent(),getLayout());

        ProducerRecord<byte[],byte[]> record = recordArgCaptor.getValue();
        Assertions.assertArrayEquals(expectedMessage,record.value());

    }

    private KafkaProperties initialiseProperties(MessageSendType messageType, SerializerTypes serializer) {
        KafkaProperties props = null;
        try {
            props= new KafkaProperties("127.0.0.1:9876","topic");
            props.setSendType(messageType);
            props.setSerializer(serializer);

        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.getMessage());
        }
        return props;
    }

    private static LogEvent getLogEvent(){
        Message message = new SimpleMessage(messageText);
        LogEvent event = Log4jLogEvent.newBuilder()
                .setMessage(message)
                .build();
        return event;
    }

    private static Layout< ? extends Serializable> getLayout(){
        return PatternLayout.createDefaultLayout();
    }
}