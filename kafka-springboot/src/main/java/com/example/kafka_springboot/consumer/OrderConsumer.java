package com.example.kafka_springboot.consumer;


import com.example.kafka_springboot.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

   /* @KafkaListener(topics = "orders-topic", groupId = "demo-consumer-group")
    public void consume(OrderEvent event) {
        System.out.println("Received Order: {}"+ event);
        log.info("📩 Received Order: {}", event);
    }*/

    @KafkaListener(topics = "orders-topic", groupId = "demo-consumer-group" )
    public void consume(OrderEvent event) {

        // Simulate business exception for testing
        if (event.getItemName() == null || event.getItemName().isEmpty()) {
            throw new IllegalArgumentException("Product is missing");
        }


        if ("ABC".equalsIgnoreCase(event.getItemName())) {
            throw new RuntimeException("Simulated processing failure");
        }
        System.out.println("RAW Message = " + event);
        log.info("RAW Message = {}", event);
    }

    @KafkaListener(topics = "orders-topic", groupId = "orders-group")
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000L, multiplier = 2),
            dltTopicSuffix = "-dlq",
            autoCreateTopics = "true",
            exclude = IllegalArgumentException.class
    )
    public void consumeOrders(OrderEvent order) {
        System.out.println("Received: " + order);

        if (order.getQuantity() > 100) {
            throw new RuntimeException("Quantity too large!");
        }
    }
}