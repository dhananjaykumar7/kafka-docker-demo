package com.example.kafka_springboot.config;

import com.example.kafka_springboot.dto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.function.BiFunction;

@Configuration
public class KafkaConfig {

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<String, OrderEvent> kafkaTemplate){
        BiFunction<ConsumerRecord<?,?>,Exception, TopicPartition> resolver =
                (record,ex) -> new TopicPartition("orders-topic-dlq",record.partition());
        return new DeadLetterPublishingRecoverer(kafkaTemplate,resolver);
    }

    @Bean
    public DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer) {
// Exponential backoff: initial 1s, multiplier 2, max 10s, maxAttempts will be 4 (3 retries + 1)
        ExponentialBackOff backOff = new ExponentialBackOff(1000, 2.0);
        backOff.setMaxInterval(10000);
        backOff.setMaxAttempts(4);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

// Example: don't retry for IllegalArgumentException
        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class);


// Optional: add retry listeners or logging
        errorHandler.setRetryListeners(((record, ex, deliveryAttempt) ->
                System.out.println("Retry attempt " + deliveryAttempt + " for record: " + record)));


        return errorHandler;
    }

}
