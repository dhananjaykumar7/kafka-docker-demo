package com.example.kafka_springboot.config;

import com.example.kafka_springboot.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    // Topic name
    public static final String ORDERS_TOPIC = "orders-topic";
    public static final String ORDERS_TOPIC_DLQ = "orders-topic-dlq";

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder
                .name(ORDERS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderTopicsDLQ(){
        return TopicBuilder
                .name(ORDERS_TOPIC_DLQ)
                .partitions(3)
                .replicas(1)
                .build();
    }
}