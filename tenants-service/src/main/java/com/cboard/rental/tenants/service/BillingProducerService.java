package com.cboard.rental.tenants.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BillingProducerService {

    private static final String DUE_PAYMENT_TOPIC = "due-payment-topic";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BillingProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Object message) {
        kafkaTemplate.send(DUE_PAYMENT_TOPIC, message);
    }
}
