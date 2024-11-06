package com.cboard.rental.messaging.listener;

import com.cboard.rental.messaging.entity.MessageRecord;
import com.cboard.rental.messaging.entity.MessageStatus;
import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.repository.MessageRecordRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DuePaymentEventListener {

    @Autowired
    private MessageRecordRepository messageRecordRepository;

    @Autowired
    private RestTemplate restTemplate;

    @KafkaListener(topics = "due-payment-topic", groupId = "payment-group")
    public void handleDuePaymentEvent(DuePaymentEvent event) throws JsonProcessingException {
        System.out.println("Received DuePaymentEvent in listener: " + event);

        // Step 1: Insert a new MessageRecord with status RECEIVED
        MessageRecord record = new MessageRecord();
        record.setContent("Due payment for tenant: " + event.getTenantId() + ", Amount: " + event.getAmount());
        record.setId(event.getShdId());
        record.setStatus(MessageStatus.RECEIVED);
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());

        try {
            // Step 2: Update status to PROCESSING
            record.setStatus(MessageStatus.PROCESSING);
            messageRecordRepository.save(record);

            processEvent(event);

            // Step 4: Update status to COMPLETED
            record.setStatus(MessageStatus.COMPLETED);
            record.setUpdatedAt(LocalDateTime.now());
            messageRecordRepository.save(record);

            // Step 5: Send acknowledgment back to tenant-service
            sendAcknowledgment(record.getId(), "COMPLETED", event.getToken());

        } catch (Exception e) {
            // If an error occurs, update status to FAILED
            record.setStatus(MessageStatus.FAILED);
            record.setUpdatedAt(LocalDateTime.now());
            messageRecordRepository.save(record);

            // Send acknowledgment back with FAILED status if processing fails
            sendAcknowledgment(record.getId(), "FAILED", event.getToken());
        }
    }

    private void processEvent(DuePaymentEvent event) {
        // Simulate processing, e.g., by calling another service or sending a notification
    }

    public void sendAcknowledgment(Long messageId, String status, String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            System.err.println("JWT token is missing for acknowledgment");
            return;
        }

        // Set Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        System.out.println("Set Authorization header with JWT token.");

        // Construct the URL with query parameters
        String callbackUrl = "http://localhost:8093/api/v1/scheduler/acknowledgments?id=" + messageId + "&status=" + status;
        System.out.println("Sending acknowledgment to URL: " + callbackUrl);

        // Create the HttpEntity with headers only (no body needed)
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Send Request
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                callbackUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class
            );

            // Check Response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Acknowledgment sent successfully for message ID: " + messageId);
            } else {
                System.err.println("Failed to send acknowledgment for message ID: " + messageId);
                System.err.println("Response status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Exception while sending acknowledgment: " + e.getMessage());
        }
    }

}
