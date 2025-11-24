package com.example.kafka_springboot.controller;


import com.example.kafka_springboot.dto.OrderEvent;
import com.example.kafka_springboot.service.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer producer;

    @PostMapping("/publish")
    public String publishOrder(@RequestBody OrderEvent orderEvent) {
        return producer.sendOrder(orderEvent);
    }
}
