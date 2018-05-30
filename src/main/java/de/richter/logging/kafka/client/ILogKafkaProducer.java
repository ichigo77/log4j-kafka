package de.richter.logging.kafka.client;

import java.util.concurrent.ExecutionException;

public interface ILogKafkaProducer {

    void sendLogEvent(String message) throws ExecutionException, InterruptedException;
}
