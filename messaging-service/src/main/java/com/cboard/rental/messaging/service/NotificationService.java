package com.cboard.rental.messaging.service;

public interface NotificationService {
    void sendEmail(String to, String subject, String message);
    // Future methods: sendSms, sendPushNotification, etc.
}
