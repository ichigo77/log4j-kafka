package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.SerializerTypes;

public class KafkaAvroProducer extends LogKafkaProducer {


    @Override
    public void sendLogEvent(String message) throws Exception {

    }

    @Override
    public SerializerTypes getSerializerType() {
        return SerializerTypes.AVRO;
    }

    @Override
    public void initialiseProducer(KafkaProperties properties) {
        this.props = properties;
    }
}
