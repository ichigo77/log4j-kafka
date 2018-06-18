package de.richter.logging.kafka.test.client;

import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.client.impl.KafkaAvroProducer;
import de.richter.logging.kafka.client.impl.KafkaByteProducer;
import de.richter.logging.kafka.client.impl.KafkaStringProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerTest {

    @Test
    void sendStringLogEventNoSendMessageImplShouldThrowNullPointerException() {
        LogKafkaProducer stringProducer = new KafkaStringProducer();
        Assertions.assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                stringProducer.sendLogEvent("message");
            }
        });
    }

    @Test
    void sendByteLogEventNoSendMessageImplShouldThrowNullPointerException() {
        LogKafkaProducer stringProducer = new KafkaByteProducer();
        Assertions.assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                stringProducer.sendLogEvent("message");
            }
        });
    }

    @Test
    void sendAvroLogEventNoSendMessageImplShouldThrowNullPointerException() {
        LogKafkaProducer stringProducer = new KafkaAvroProducer();
        Assertions.assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                stringProducer.sendLogEvent("message");
            }
        });
    }
}