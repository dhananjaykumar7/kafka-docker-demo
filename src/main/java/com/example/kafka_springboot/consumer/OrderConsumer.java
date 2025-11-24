package com.example.kafka_springboot.consumer;


import com.example.kafka_springboot.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

   /* @KafkaListener(topics = "orders-topic", groupId = "demo-consumer-group")
    public void consume(OrderEvent event) {
        System.out.println("Received Order: {}"+ event);
        log.info("📩 Received Order: {}", event);
    }*/

    @KafkaListener(topics = "orders-topic", groupId = "demo-consumer-group",concurrency = "3" )
    public void consume(OrderEvent event) {
        System.out.println("RAW Message = " + event);
        log.info("RAW Message = {}", event);
    }
}