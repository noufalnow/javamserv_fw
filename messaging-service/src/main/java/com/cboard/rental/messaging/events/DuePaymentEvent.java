package com.cboard.rental.messaging.events;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class DuePaymentEvent {
    private Long tenantId;         // Use Long instead of String for ID
    private BigDecimal amount;     // Use BigDecimal instead of double for amount
    private LocalDate scheduledDate; // Use LocalDate instead of String for dates
}
