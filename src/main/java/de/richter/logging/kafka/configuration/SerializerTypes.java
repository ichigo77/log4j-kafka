package de.richter.logging.kafka.configuration;

public enum SerializerTypes {

    STRING("org.apache.kafka.common.serialization.StringSerializer"),
    BYTE("org.apache.kafka.common.serialization.BytesSerializer"),
    AVRO("io.confluent.kafka.serializers.KafkaAvroSerializer");

    SerializerTypes(String serializerNamespace) {
        this.serializerNamespace = serializerNamespace;
    }

    public String getSerializerNamespace() {
        return serializerNamespace;
    }

    private final String serializerNamespace;




}
