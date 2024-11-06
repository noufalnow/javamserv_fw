package com.cboard.rental.messaging.events;

import lombok.Data;

@Data
public class NotificationEvent {
    private String tenantId;
    private double amountDue;
    private String dueDate;

    // Constructors, getters, and setters
}