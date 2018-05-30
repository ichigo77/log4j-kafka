package de.richter.logging.kafka.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProperties {

    private String serverBootstrap;
    private String topic;

    public KafkaProperties(String kafkaServer, String kafkaTopic) throws KafkaConfigurationException {
        this.setServerBootstrap(kafkaServer);
        this.setTopic(kafkaTopic);
    }

    public String getServerBootstrap() {
        return serverBootstrap;
    }

    private void setServerBootstrap(String serverBootstrap) throws KafkaConfigurationException {
        if(!ExistRequiredProperty(serverBootstrap))throw new KafkaConfigurationException("The kafka server mustn't be null or empty");
        this.serverBootstrap = serverBootstrap;
    }

    private boolean ExistRequiredProperty(String property) {
        return (property != null && !property.isEmpty());
    }

    public String getTopic() {
        return topic;
    }

    private void setTopic(String topic) throws KafkaConfigurationException {
        if(!ExistRequiredProperty(topic))throw new KafkaConfigurationException("The kafka topic mustn't be null or empty");
        this.topic = topic;
    }

    public Properties GetKafkaProperties(){
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,this.serverBootstrap);
        props.setProperty("Topic",this.topic);
        return props;
    }
}
