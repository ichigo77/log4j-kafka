package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.configuration.MessageSendType;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaSendMessageFireForget implements IKafkaSendMessage {
    @Override
    public void sendKafkaMessage(Producer client, ProducerRecord record) {
        client.send(record);
    }

    @Override
    public MessageSendType getSendType() {
        return MessageSendType.FIREANDFORGET;
    }
}
