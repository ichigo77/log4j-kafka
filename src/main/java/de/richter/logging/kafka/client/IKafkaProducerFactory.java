package de.richter.logging.kafka.client;

import de.richter.logging.kafka.configuration.KafkaProperties;

public interface IKafkaProducerFactory {

     LogKafkaProducer getProducer(KafkaProperties props);
}
