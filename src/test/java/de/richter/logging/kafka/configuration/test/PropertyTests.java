package de.richter.logging.kafka.configuration.test;

import de.richter.logging.kafka.configuration.KafkaConfigurationException;
import de.richter.logging.kafka.configuration.KafkaProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

@Tag("PropertyTests")
class PropertyTests {

    @Test
    public void NoBootstrapServerInfoShallThrowKafkaConfigurationException(){
        try {
            KafkaProperties props = new KafkaProperties(null,"topic");
            Assertions.fail("Expected KafkaConfigurationException was not thrown.");
        } catch (KafkaConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EmptyBootstrapServerInfoShallThrowKafkaConfigurationException(){
        try {
            KafkaProperties props = new KafkaProperties("","topic");
            Assertions.fail("Expected KafkaConfigurationException was not thrown");
        } catch (KafkaConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void BootstrapServerInfoWasAssigned(){
        try {
            String expectedServerValue = "server";
            KafkaProperties props = new KafkaProperties(expectedServerValue,"topic");
            Assertions.assertEquals(expectedServerValue,props.getServerBootstrap());
        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.toString());
        }
    }

    @Test
    public void NoTopicInfoShallThrowKafkaConfigurationException(){
        try {
            KafkaProperties props = new KafkaProperties("sever",null);
            Assertions.fail("Expected KafkaConfigurationException was not thrown.");
        } catch (KafkaConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EmptyTopicInfoShallThrowKafkaConfigurationException(){
        try {
            KafkaProperties props = new KafkaProperties("server","");
            Assertions.fail("Expected KafkaConfigurationException was not thrown");
        } catch (KafkaConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TopicInfoWasAssigned(){
        try {
            String expectedTopicValue = "topic";
            KafkaProperties props = new KafkaProperties("server",expectedTopicValue);
            Assertions.assertEquals(expectedTopicValue, props.getTopic());
        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.toString());
        }
    }

    @Test
    public void PropertyInstanceContainsBootstrapServerInfo(){
        try {
            String expectedBootstrapServer ="server";
            KafkaProperties kafkaProps = new KafkaProperties(expectedBootstrapServer, "topic");
            Properties props = kafkaProps.GetKafkaProperties();
            Assertions.assertEquals(expectedBootstrapServer,props.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.toString());
        }
    }


    @Test
    public void PropertyInstanceContainsTopicInfo(){
        try {
            String expectedTopic ="topic";
            KafkaProperties kafkaProps = new KafkaProperties("server", expectedTopic);
            Properties props = kafkaProps.GetKafkaProperties();
            Assertions.assertEquals(expectedTopic,props.getProperty("Topic"));
        } catch (KafkaConfigurationException e) {
            Assertions.fail(e.toString());
        }
    }


}
