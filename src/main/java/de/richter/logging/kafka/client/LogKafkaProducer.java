package de.richter.logging.kafka.client;

import de.richter.logging.kafka.configuration.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ExecutionException;

public class LogKafkaProducer implements ILogKafkaProducer {
    private final KafkaProperties props;
    private final KafkaProducer<String,String> producer;

    public LogKafkaProducer(KafkaProperties configuration){
       props = configuration;
       producer = new KafkaProducer<String, String>(configuration.GetKafkaProperties());
    }

    @Override
    public void sendLogEvent(String message) throws ExecutionException, InterruptedException {
        ProducerRecord record = new ProducerRecord<>(props.getTopic(),message);
        producer.send(record).get();
    }
}
