package com.example.kafka_springboot.service;


import com.example.kafka_springboot.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String TOPIC = "orders-topic";

    public String sendOrder(OrderEvent event) {
        log.info("RAW → {}", event);
        kafkaTemplate.send(TOPIC, event.getOrderId(), event);
        return "Order sent successfully: " + event.getOrderId();
    }
}