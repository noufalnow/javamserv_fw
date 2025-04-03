package com.cboard.rental.messaging.listener;
import com.cboard.rental.messaging.DTO.AcknowledgmentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.entity.MessageRecord;
import com.cboard.rental.messaging.entity.MessageStatus;
import com.cboard.rental.messaging.repository.MessageRecordRepository;
import com.cboard.rental.messaging.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DuePaymentEventListener {

    @Autowired
    private MessageRecordRepository messageRecordRepository;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private KafkaTemplate kafkaTemplate;
    
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "due-payment-topic", groupId = "payment-group")
    public void handleDuePaymentEvents(List<DuePaymentEvent> events) {
        System.out.println("Received list of DuePaymentEvents in listener: " + events);
        List<AcknowledgmentDTO> acknowledgments = new ArrayList<>();

        for (DuePaymentEvent event : events) {
            MessageRecord record = new MessageRecord();
            record.setContent("Due payment for tenant: " + event.getTenantId() + ", Amount: " + event.getAmount());
            record.setShdId(event.getShdId());
            record.setStatus(MessageStatus.RECEIVED);
            record.setCreatedAt(LocalDateTime.now());
            record.setUpdatedAt(LocalDateTime.now());
            record.setTopics("due-payment-topic");
            record.setGroupId("payment-group");

            try {
                record.setStatus(MessageStatus.PROCESSING);
                messageRecordRepository.save(record);

                processEvent(event);

                record.setStatus(MessageStatus.COMPLETED);
                acknowledgments.add(new AcknowledgmentDTO(record.getShdId(), "COMPLETED"));

            } catch (Exception e) {
                record.setStatus(MessageStatus.FAILED);
                acknowledgments.add(new AcknowledgmentDTO(record.getShdId(), "FAILED"));
            } finally {
                record.setUpdatedAt(LocalDateTime.now());
                messageRecordRepository.save(record);
            }
        }

        // Send bulk acknowledgment
        sendBulkAcknowledgment(acknowledgments, events.get(0).getToken());
    }

    private void processEvent(DuePaymentEvent event) {
        // Build the message
        String subject = "Payment Due Reminder";
        String message = "Dear tenant, your payment is due. Please make arrangements by the due date.";

        // Send email
        if (event.getTenantEmail() != null) {
            notificationService.sendEmail(event.getTenantEmail(), subject, message);
        }
        
        kafkaTemplate.send("payments-requests", event);
        
        // You can expand to SMS in future phases
    }


    public void sendBulkAcknowledgment(List<AcknowledgmentDTO> acknowledgments, String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            System.err.println("JWT token is missing for acknowledgment");
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Logging callback URL
        String callbackUrl = "http://192.168.251.54:8093/api/v1/scheduler/acknowledgments";
        System.out.println("Sending bulk acknowledgment to URL: " + callbackUrl);

        // Convert acknowledgment list to JSON for logging
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonAcknowledgments = mapper.writeValueAsString(acknowledgments);
            System.out.println("Bulk acknowledgment payload: " + jsonAcknowledgments);
        } catch (Exception e) {
            System.err.println("Error converting acknowledgment list to JSON: " + e.getMessage());
        }

        // Prepare the request entity
        HttpEntity<List<AcknowledgmentDTO>> requestEntity = new HttpEntity<>(acknowledgments, headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                callbackUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class
            );

            // Check response status
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Bulk acknowledgment sent successfully.");
            } else {
                System.err.println("Failed to send bulk acknowledgment. Response status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Exception while sending bulk acknowledgment: " + e.getMessage());
        }
    }
}
