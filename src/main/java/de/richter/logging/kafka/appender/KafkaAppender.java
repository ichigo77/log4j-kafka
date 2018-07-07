package de.richter.logging.kafka.appender;

import de.richter.logging.kafka.client.IKafkaProducerFactory;
import de.richter.logging.kafka.client.LogKafkaProducer;
import de.richter.logging.kafka.client.impl.KafkaProducerFactory;
import de.richter.logging.kafka.configuration.KafkaConfigurationException;
import de.richter.logging.kafka.configuration.KafkaProperties;
import de.richter.logging.kafka.configuration.MessageSendType;
import de.richter.logging.kafka.configuration.SerializerTypes;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Plugin(name="Kafka", category = Node.CATEGORY,elementType = Appender.ELEMENT_TYPE,printObject = true)
public class KafkaAppender extends AbstractAppender {

    public static class KafkaAppenderBuilder
            implements org.apache.logging.log4j.core.util.Builder<KafkaAppender>{


        @PluginAttribute("name")
        @Required(message="The attribute name is required")
        private String name;

        @PluginAttribute("topic")
        @Required(message="The attribute topic and it's value is required ")
        private String topic;

        @PluginAttribute("serverBootstrap")
        @Required(message="The attribute serverBootstrap and its value is required")
        private String serverBootstrap;

        @PluginAttribute("key")
        private String key;

        @PluginAttribute("serializer")
        private SerializerTypes serializer;

        @PluginAttribute("sendType")
        private MessageSendType messageSendType;

        @PluginElement("Layout")
        private Layout<? extends Serializable> layout;

        @PluginElement("Filter")
        private Filter filter;

        @PluginAttribute("ignoreExceptions")
        private boolean ignoreExceptions = true;


        @Override
        public KafkaAppender build() {

            if(layout==null){
                LOGGER.error("No layout provided for KafkaAppender.");
                return null;
            }

            try {
                KafkaProperties props = getKafkaProperties();
                LogKafkaProducer client = getKafkaClient(props);
                return new KafkaAppender(name,filter,layout,ignoreExceptions,client);
            } catch (KafkaConfigurationException e) {
                AbstractLifeCycle.LOGGER.fatal(e.getMessage());
                return null;
            }catch(Exception ex){
                AbstractLifeCycle.LOGGER.fatal(ex.getMessage());
                return null;
            }
        }

        private LogKafkaProducer getKafkaClient(KafkaProperties props) {
            IKafkaProducerFactory kafkaProducerFactory = new KafkaProducerFactory();
            return kafkaProducerFactory.getProducer(props);
        }

        private KafkaProperties getKafkaProperties() throws KafkaConfigurationException {
            KafkaProperties props = new KafkaProperties(this.serverBootstrap,this.topic);
            props.setSerializer(serializer);
            props.setSendType(messageSendType);
            //TODO: set key and other properties
            return props;
        }

        public KafkaAppenderBuilder setName(String name) {
            this.name = name;
            return this;

        }

        public KafkaAppenderBuilder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public KafkaAppenderBuilder setServerBootstrap(String serverBootstrap) {
            this.serverBootstrap = serverBootstrap;
            return this;
        }
    }


    private LogKafkaProducer kafkaLogClient;

    protected KafkaAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean isIgnoreExceptions, LogKafkaProducer kafkaClient) {
        super(name, filter, layout, true);
        kafkaLogClient = kafkaClient;
    }


    @PluginBuilderFactory
    @PluginFactory
    public static KafkaAppenderBuilder newBuilder() {
        return new KafkaAppenderBuilder();
    }

    @Override
    public void append(LogEvent logEvent) {
        kafkaLogClient.sendLogEvent(logEvent,this.getLayout());
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        setStopping();
        boolean stopped = super.stop(timeout, timeUnit, false);
        kafkaLogClient.stopProducer(timeout,timeUnit);
        setStopped();
        return stopped;
    }





}
