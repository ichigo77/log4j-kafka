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
        client.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                //TODO: handle and log exception
            }
        });
    }

    @Override
    public MessageSendType getSendType() {
        return MessageSendType.ASYNC;
    }
}
