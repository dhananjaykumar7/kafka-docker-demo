package com.example.kafka_springboot.consumer;

import com.example.kafka_springboot.dto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class DlqConsumer {


@KafkaListener(topics = "orders-topic-dlq", groupId = "dlq-consumer-group")
public void listenDlq(ConsumerRecord<String, OrderEvent> record,
                      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
System.err.println("DLQ received on topic=" + topic + " partition=" + record.partition() + " offset=" + record.offset());
System.err.println("DLQ payload: " + record.value());


// Store to DB or notify ops; build replay UI or service
}
}