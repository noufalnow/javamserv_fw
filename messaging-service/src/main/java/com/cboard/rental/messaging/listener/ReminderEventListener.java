package com.cboard.rental.messaging.listener;

import com.cboard.rental.messaging.entity.MessageRecord;
import com.cboard.rental.messaging.entity.MessageStatus;
import com.cboard.rental.messaging.events.ReminderEvent;
import com.cboard.rental.messaging.repository.MessageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReminderEventListener {

    @Autowired
    private MessageRecordRepository messageRecordRepository;

    @KafkaListener(topics = "email-reminder-topic", groupId = "reminder-group")
    //@Transactional
    public void handleReminderEvent(ReminderEvent event) {
        MessageRecord record = new MessageRecord();
        record.setContent("Reminder for tenant: " + event.getTenantId());
        record.setStatus(MessageStatus.RECEIVED);
        messageRecordRepository.save(record);

        try {
            record.setStatus(MessageStatus.PROCESSING);
            messageRecordRepository.save(record);

            // Process the event
            processReminder(event);

            record.setStatus(MessageStatus.COMPLETED);
            messageRecordRepository.save(record);

        } catch (Exception e) {
            record.setStatus(MessageStatus.FAILED);
            messageRecordRepository.save(record);
        }
    }

    private void processReminder(ReminderEvent event) {
        // Implement reminder-specific processing
    }
}
