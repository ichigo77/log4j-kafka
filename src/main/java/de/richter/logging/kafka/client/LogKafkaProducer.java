package de.richter.logging.kafka.client;

import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractManager;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public abstract class LogKafkaProducer{

    protected KafkaProperties props;
    protected IKafkaSendMessage sendMessageInstanceImpl;



    public abstract void sendLogEvent(LogEvent event, Layout <? extends Serializable> layout) throws NullPointerException;
    public abstract SerializerTypes getSerializerType();
    public abstract void initialiseProducer(KafkaProperties properties) ;
    public abstract void stopProducer(long timeout, TimeUnit timeUnit);

    public void setSendMessageInstance(IKafkaSendMessage messageSendInstance) {
        this.sendMessageInstanceImpl = messageSendInstance;
    }

    public IKafkaSendMessage getSendMessageInstance(){
        return this.sendMessageInstanceImpl;
    }
}
