package de.richter.logging.kafka.test.client;

import de.richter.logging.kafka.client.ILogKafkaProducer;
import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.configuration.KafkaConfigurationException;
import de.richter.logging.kafka.configuration.KafkaProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

@Tag("TestOfKafkaProducerUsage")
class KafkaClientTest {

    @Test
    void NoRunningKafkaApplicationShallThrowExecutionException(){
        try {
            ILogKafkaProducer producer = new LogKafkaProducer(new KafkaProperties("127.0.0.1:9898","topic"));
            Assertions.assertThrows(ExecutionException.class,()-> producer.sendLogEvent(""));
        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.toString());
        }
    }

}
