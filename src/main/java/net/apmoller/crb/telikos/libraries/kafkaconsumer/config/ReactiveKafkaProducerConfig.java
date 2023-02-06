package net.apmoller.crb.telikos.libraries.kafkaconsumer.config;


import com.demo.redis.patterns.dto.BookingAvro;
import com.demo.redis.patterns.util.CompressionConfig;
import com.demo.redis.patterns.util.CustomSerializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import reactor.kafka.sender.SenderOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class ReactiveKafkaProducerConfig {

    @Autowired
    private KafkaAdmin kafkaProducerAdmin;

    @Bean
    public AdminClient kafkaAdminProducerClient() {

        AdminClient adminClient =  AdminClient.create(kafkaProducerAdmin.getConfigurationProperties());

        // Get brokers' details
        DescribeClusterResult describeClusterResult = adminClient.describeCluster();


        return adminClient;
    }


    @Bean
    public ReactiveKafkaProducerTemplate<String, BookingDTO> reactiveKafkaProducerTemplate( KafkaProperties properties) {
       // Map<String, Object> props = properties.buildProducerProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);

//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                org.springframework.kafka.support.serializer.JsonSerializer.class);

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompressionConfig.class);

        props.put("schema.registry.url", "http://localhost:8081");

        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");


        return new ReactiveKafkaProducerTemplate<String, BookingDTO>(SenderOptions.create(props));
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, BookingAvro> reactiveKafkaProducerAvroTemplate(KafkaProperties properties) {
      //  Map<String, Object> props = properties.buildProducerProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
       // props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompressionConfig.class);
       // props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class);

        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");




        props.put("schema.registry.url", "http://localhost:8081");



        return new ReactiveKafkaProducerTemplate<String, BookingAvro>(SenderOptions.create(props));
    }


}

