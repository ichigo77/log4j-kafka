package de.richter.logging.kafka.test.client;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("TestOfKafkaProducerUsage")
class KafkaClientTest {

    @Test
    void NoRunningKafkaApplicationShallThrowExecutionException(){
       /* try {
            ILogKafkaProducer producer = new LogKafkaProducer(new KafkaProperties("127.0.0.1:9898","topic"));
            Assertions.assertThrows(ExecutionException.class,()-> producer.sendLogEvent(""));
        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.toString());
        }*/
    }

}
