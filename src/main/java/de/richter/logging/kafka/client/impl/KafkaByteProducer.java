package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class KafkaByteProducer extends LogKafkaProducer {

    private Producer<byte[],byte[]> client;

    @Override
    public void sendLogEvent(LogEvent event, Layout<? extends Serializable> layout) throws NullPointerException {
        if(this.sendMessageInstanceImpl == null) throw new NullPointerException("The implementation of interface IKafkaSendMessage is null");
        ProducerRecord<byte[],byte[]> record = new ProducerRecord<byte[],byte[]>(props.getTopic(),layout.toByteArray(event));
        this.sendMessageInstanceImpl.sendKafkaMessage(client,record);
    }

    @Override
    public SerializerTypes getSerializerType() {
        return SerializerTypes.BYTE;
    }

    @Override
    public void initialiseProducer(KafkaProperties properties) {
        client = new KafkaProducer<byte[], byte[]>(properties.GetKafkaProperties());
        this.props = properties;
    }

    @Override
    public void stopProducer(long timeout, TimeUnit timeUnit) {
        if(client!=null)client.close(timeout, timeUnit);
    }
}
