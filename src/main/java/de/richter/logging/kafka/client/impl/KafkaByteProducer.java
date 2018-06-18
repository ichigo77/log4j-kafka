package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

public class KafkaByteProducer extends LogKafkaProducer {

    private Producer<Byte,Byte> client;

    @Override
    public void sendLogEvent(String message) throws NullPointerException {
        if(this.sendMessageInstanceImpl == null) throw new NullPointerException("The implementation of interface IKafkaSendMessage is null");
    }

    @Override
    public SerializerTypes getSerializerType() {
        return SerializerTypes.BYTE;
    }

    @Override
    public void initialiseProducer(KafkaProperties properties) {
        client = new KafkaProducer<Byte, Byte>(properties.GetKafkaProperties());
        this.props = properties;
    }
}
