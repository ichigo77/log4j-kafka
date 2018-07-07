package de.richter.logging.kafka.test.appender;


import de.richter.logging.kafka.appender.KafkaAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Tag("KafkaAppenderTest")
class KafkaAppenderTest {



@BeforeAll
public static void initialiseTest(){
    System.setProperty("log4j.configurationFile","kafkaAppender-log4j-config.xml");
}

@Test
public void NoKafkaServerInConfigShallReturnNull(){
    Logger logger = (Logger)LogManager.getLogger("NoKafkaServerInConfigShallReturnNull");
    //No appender has been found because expected kafka appender threw an exception and was not initialised.
    Assertions.assertEquals(0, logger.getAppenders().size());
}

    @Test
    public void NoKafkaTopicInConfigShallReturnNull(){
        Logger logger = (Logger)LogManager.getLogger("NoKafkaTopicInConfigShallReturnNull");
        //No appender has been found because expected kafka appender threw an exception and was not initialised.
        Assertions.assertEquals(0, logger.getAppenders().size());
    }


    @Test
    public void NoLayoutInConfigShallReturnNull(){
        Logger logger = (Logger)LogManager.getLogger("NoLayoutInConfigShallReturnNull");
        //No appender has been found because expected kafka appender threw an exception and was not initialised.
        Assertions.assertEquals(0, logger.getAppenders().size());
    }

    @Test
    public void MinRequiredFieldsExistsConfigShallHaveAppender(){
        Logger logger = (Logger)LogManager.getLogger("MinRequiredFieldsExistsConfigShallHaveAppender");
        //No appender has been found because expected kafka appender threw an exception and was not initialised.
        Map<String,Appender> foundAppenders = logger.getAppenders();
        Assertions.assertEquals(1, foundAppenders.size());
        Assertions.assertTrue(foundAppenders.get("MinRequiredFieldsExistsConfigShallHaveAppender") instanceof KafkaAppender);
    }



}