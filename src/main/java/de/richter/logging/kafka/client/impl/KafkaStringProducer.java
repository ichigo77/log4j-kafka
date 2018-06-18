package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaStringProducer  extends LogKafkaProducer{

    private Producer<String,String>client=null;

    @Override
    public void sendLogEvent(java.lang.String message) throws Exception {
        if(this.sendMessageInstanceImpl == null) throw new Exception("");
        ProducerRecord<String, String> record = new ProducerRecord<String,String>(props.getTopic(),message);
        this.sendMessageInstanceImpl.sendKafkaMessage(client, record);
    }

    @Override
    public SerializerTypes getSerializerType() {
        return SerializerTypes.STRING;
    }

    @Override
    public void initialiseProducer(KafkaProperties properties) {
        client = new KafkaProducer(properties.GetKafkaProperties());
        this.props = properties;
    }


}
