package de.richter.logging.kafka.test.client;

import de.richter.logging.kafka.client.IKafkaProducerFactory;
import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.client.impl.*;
import de.richter.logging.kafka.configuration.KafkaConfigurationException;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.MessageSendType;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("FactoryTests")
class KafkaProducerFactoryTest {

    @Test
    public void FactoryShallReturnStringProducer(){
        KafkaProperties props = initialiseProperties(MessageSendType.ASYNC,SerializerTypes.STRING);
        IKafkaProducerFactory factory = new KafkaProducerFactory();
        LogKafkaProducer producer = factory.getProducer(props);
        Assertions.assertTrue(producer instanceof KafkaStringProducer);
        }
    @Test
    public void FactoryShallReturnByteProducer(){
        KafkaProperties props = initialiseProperties(MessageSendType.ASYNC,SerializerTypes.BYTE);
        IKafkaProducerFactory factory = new KafkaProducerFactory();
        LogKafkaProducer producer = factory.getProducer(props);
        Assertions.assertTrue(producer instanceof KafkaByteProducer);
    }

    @Test
    public void FactoryShallReturnAvroProducer(){
        KafkaProperties props = initialiseProperties(MessageSendType.ASYNC,SerializerTypes.AVRO);
        IKafkaProducerFactory factory = new KafkaProducerFactory();
        LogKafkaProducer producer = factory.getProducer(props);
        Assertions.assertTrue(producer instanceof KafkaAvroProducer);
    }

    @Test
    public void FactoryShallReturnAsyncMessageClient(){
        KafkaProperties props = initialiseProperties(MessageSendType.ASYNC,SerializerTypes.STRING);
        IKafkaProducerFactory factory = new KafkaProducerFactory();
        LogKafkaProducer producer = factory.getProducer(props);
        Assertions.assertTrue(producer.getSendMessageInstance() instanceof KafkaSendMessageAsync);
    }

    @Test
    public void FactoryShallReturnSyncMessageClient(){
        KafkaProperties props = initialiseProperties(MessageSendType.SYNC,SerializerTypes.STRING);
        IKafkaProducerFactory factory = new KafkaProducerFactory();
        LogKafkaProducer producer = factory.getProducer(props);
        Assertions.assertTrue(producer.getSendMessageInstance() instanceof KafkaSendMessageSync);
    }

    @Test
    public void FactoryShallReturnFireAndForgetClient(){
        KafkaProperties props = initialiseProperties(MessageSendType.FIREANDFORGET,SerializerTypes.STRING);
        IKafkaProducerFactory factory = new KafkaProducerFactory();
        LogKafkaProducer producer = factory.getProducer(props);
        Assertions.assertTrue(producer.getSendMessageInstance() instanceof KafkaSendMessageFireForget);
    }

    private KafkaProperties initialiseProperties(MessageSendType messageType, SerializerTypes serializer) {
        KafkaProperties props = null;
        try {
            props= new KafkaProperties("127.0.0.1:9876","topic");
            props.setSendType(messageType);
            props.setSerializer(serializer);

        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.getMessage());
        }
        return props;
    }


}