package de.richter.logging.kafka.client;

import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.kafka.clients.producer.Producer;

public abstract class LogKafkaProducer {

    protected KafkaProperties props;
    protected IKafkaSendMessage sendMessageInstanceImpl;

    public abstract void sendLogEvent(String message) throws Exception;
    public abstract SerializerTypes getSerializerType();
    public abstract void initialiseProducer(KafkaProperties properties) ;

    public void setSendMessageInstance(IKafkaSendMessage messageSendInstance) {
        this.sendMessageInstanceImpl = messageSendInstance;
    }

    public IKafkaSendMessage getSendMessageInstance(){
        return this.sendMessageInstanceImpl;
    }




}
