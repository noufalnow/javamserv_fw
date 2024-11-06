package com.cboard.rental.messaging.listener;

import com.cboard.rental.messaging.entity.MessageRecord;
import com.cboard.rental.messaging.entity.MessageStatus;
import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.repository.MessageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DuePaymentEventListener {

    @Autowired
    private MessageRecordRepository messageRecordRepository;

    @KafkaListener(topics = "due-payment-topic", groupId = "payment-group")
    //@Transactional
    public void handleDuePaymentEvent(DuePaymentEvent event) {
    	
    	 System.out.println("Received DuePaymentEvent in listener: " + event);
    	
        // Step 1: Insert a new MessageRecord with status RECEIVED
        MessageRecord record = new MessageRecord();
        record.setContent("Due payment for tenant: " + event.getTenantId() + ", Amount: " + event.getAmount());
        record.setStatus(MessageStatus.RECEIVED);
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());
        
        System.out.println("To Save DuePaymentEvent: " + record);
        
        System.out.println("Attempting to save MessageRecord to DB: " + record);
        messageRecordRepository.save(record);
        System.out.println("MessageRecord saved to DB with ID: " + record.getId());


        try {
            // Step 2: Update status to PROCESSING
            record.setStatus(MessageStatus.PROCESSING);
            messageRecordRepository.save(record);

            // Step 3: Process the event (e.g., notify tenant)
            processEvent(event);

            // Step 4: Update status to COMPLETED
            record.setStatus(MessageStatus.COMPLETED);
            record.setUpdatedAt(LocalDateTime.now());
            messageRecordRepository.save(record);

        } catch (Exception e) {
            // If an error occurs, update status to FAILED
            record.setStatus(MessageStatus.FAILED);
            record.setUpdatedAt(LocalDateTime.now());
            messageRecordRepository.save(record);
        }
    }

    private void processEvent(DuePaymentEvent event) {
        // Simulate processing, e.g., by calling another service or sending a notification
    }
}

