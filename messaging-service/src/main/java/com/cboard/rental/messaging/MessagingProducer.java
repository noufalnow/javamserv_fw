package com.cboard.rental.messaging;

import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.DTO.AcknowledgmentDTO;
import com.cboard.rental.messaging.events.ReminderEvent;
import com.cboard.rental.messaging.repository.MessageRecordRepository;
import com.cboard.rental.messaging.events.NotificationEvent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    private MessageRecordRepository messageRecordRepository;

    public MessagingProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishDuePaymentEvent(DuePaymentEvent event) {
    	    	
        System.out.println("Publishing DuePaymentEvent to Kafka: " + event);
        kafkaTemplate.send("due-payment-topic", event);
        kafkaTemplate.flush();
    }


    public void publishEmailReminderEvent(ReminderEvent event) {
        kafkaTemplate.send("email-reminder-topic", event);
    }

    public void publishNotificationEvent(NotificationEvent event) {
        kafkaTemplate.send("general-notification-topic", event);
    }
}
