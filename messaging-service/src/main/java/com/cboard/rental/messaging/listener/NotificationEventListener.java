package com.cboard.rental.messaging.listener;

import com.cboard.rental.messaging.entity.MessageRecord;
import com.cboard.rental.messaging.entity.MessageStatus;
import com.cboard.rental.messaging.events.NotificationEvent;
import com.cboard.rental.messaging.repository.MessageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationEventListener {

    @Autowired
    private MessageRecordRepository messageRecordRepository;

    @KafkaListener(topics = "general-notification-topic", groupId = "notification-group")
    //@Transactional
    public void handleNotificationEvent(NotificationEvent event) {
        MessageRecord record = new MessageRecord();
        record.setContent("Notification for tenant: " + event.getTenantId());
        record.setStatus(MessageStatus.RECEIVED);
        messageRecordRepository.save(record);

        try {
            record.setStatus(MessageStatus.PROCESSING);
            messageRecordRepository.save(record);

            // Process the event
            processNotification(event);

            record.setStatus(MessageStatus.COMPLETED);
            messageRecordRepository.save(record);

        } catch (Exception e) {
            record.setStatus(MessageStatus.FAILED);
            messageRecordRepository.save(record);
        }
    }

    private void processNotification(NotificationEvent event) {
        // Implement notification-specific processing
    }
}
