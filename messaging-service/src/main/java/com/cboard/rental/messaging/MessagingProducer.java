package com.cboard.rental.messaging;

import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.events.ReminderEvent;
import com.cboard.rental.messaging.events.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

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
