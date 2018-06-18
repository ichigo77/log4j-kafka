package de.richter.logging.kafka.client;

import de.richter.logging.kafka.configuration.MessageSendType;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public interface IKafkaSendMessage {

     void sendKafkaMessage(Producer client, ProducerRecord record);
     MessageSendType getSendType();

}
