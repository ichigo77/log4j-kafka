package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.configuration.MessageSendType;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaSendMessageAsync implements IKafkaSendMessage {
    @Override
    public void sendKafkaMessage(Producer client, ProducerRecord record) {
        client.send(record, (recordMetadata, e) -> {
        //    System.out.println(e);
        });
    }

    @Override
    public MessageSendType getSendType() {
        return MessageSendType.ASYNC;
    }
}
