package com.cboard.rental.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
//@EnableDiscoveryClient
public class MessagingServiceApplication {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private JwtDecoder jwtDecoder;

    public static void main(String[] args) {
        SpringApplication.run(MessagingServiceApplication.class, args);
    }
    
    // Send a message to Kafka with Authorization header for token validation
    public void sendMessage(Object message, String token) {
        Message<Object> kafkaMessage = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, "due-payment-topic")
                .setHeader("Authorization", "Bearer " + token)
                .build();
        kafkaTemplate.send(kafkaMessage);
    }



    
    // Listener to process incoming messages on the specified topic
    @KafkaListener(topics = "due-payment-topic", groupId = "payment-group")
    public void processMessage(ConsumerRecord<String, Object> record) {
        try {
            if (record != null && record.headers() != null) {
                Header authorizationHeader = record.headers().lastHeader("Authorization");
                if (authorizationHeader != null) {
                    String token = new String(authorizationHeader.value(), StandardCharsets.UTF_8).replace("Bearer ", "");

                    if (validateToken(token)) {
                        // Process the message with a valid token
                        Object message = record.value();
                        handleMessage(message);
                    } else {
                        System.out.println("Invalid JWT token");
                    }
                } else {
                    System.out.println("Authorization header missing");
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to handle the actual business logic for the incoming message
    private void handleMessage(Object message) {
        // Add message handling logic here
        System.out.println("Received message: " + message);
        // Additional processing based on message content
    }

    // Validate JWT token
    private boolean validateToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            // Optional: Additional checks like expiration, issuer, etc.
            return true;
        } catch (JwtException e) {
            System.err.println("Token validation error: " + e.getMessage());
            return false;
        }
    }
}
