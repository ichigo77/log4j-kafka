package de.richter.logging.kafka.client.impl;

import de.richter.logging.kafka.client.IKafkaSendMessage;
import de.richter.logging.kafka.configuration.MessageSendType;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaSendMessageSync implements IKafkaSendMessage {

    @Override
    public void sendKafkaMessage(Producer client, ProducerRecord record) {
        Future<RecordMetadata> futureResponse =client.send(record);
        try {
            RecordMetadata response = futureResponse.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MessageSendType getSendType() {
        return MessageSendType.SYNC;
    }
}
