package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class KafkaAvroProducer extends LogKafkaProducer {


    @Override
    public void sendLogEvent(LogEvent event, Layout<? extends Serializable> layout) throws NullPointerException {
        if(this.sendMessageInstanceImpl == null) throw new NullPointerException("The implementation of interface IKafkaSendMessage is null");
        throw new NotImplementedException();
    }

    @Override
    public SerializerTypes getSerializerType() {
        return SerializerTypes.AVRO;
    }

    @Override
    public void initialiseProducer(KafkaProperties properties) {
        this.props = properties;
    }

    @Override
    public void stopProducer(long timeout, TimeUnit timeUnit) {
        //TODO: AVRO implementation is missing
        throw new NotImplementedException();
    }
}
