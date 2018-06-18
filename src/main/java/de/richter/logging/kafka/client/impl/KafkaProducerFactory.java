package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.IKafkaProducerFactory;
import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.MessageSendType;
import de.richter.logging.kafka.configuration.SerializerTypes;

import java.util.HashMap;

public class KafkaProducerFactory implements IKafkaProducerFactory {

    private HashMap<SerializerTypes,LogKafkaProducer> producers;
    private HashMap<MessageSendType,IKafkaSendMessage> messageClients;

    public KafkaProducerFactory() {

        this.initialiseProducers();
        this.initialiseMessageClients();

    }

    private void initialiseProducers() {
        producers = new HashMap<>(3);
        producers.put(SerializerTypes.STRING, new KafkaStringProducer());
        producers.put(SerializerTypes.AVRO, new KafkaAvroProducer());
        producers.put(SerializerTypes.BYTE, new KafkaByteProducer());
    }

    private void initialiseMessageClients(){
        messageClients = new HashMap<>(3);
        messageClients.put(MessageSendType.ASYNC, new KafkaSendMessageAsync());
        messageClients.put(MessageSendType.FIREANDFORGET, new KafkaSendMessageFireForget());
        messageClients.put(MessageSendType.SYNC, new KafkaSendMessageSync());
    }


    @Override
    public LogKafkaProducer getProducer(KafkaProperties props) {
        IKafkaSendMessage sendMessage = this.messageClients.get(props.getSendType());
        LogKafkaProducer producer = this.producers.get(props.getSerializer());
        producer.initialiseProducer(props);
        producer.setSendMessageInstance(sendMessage);
        return producer;
    }



}
