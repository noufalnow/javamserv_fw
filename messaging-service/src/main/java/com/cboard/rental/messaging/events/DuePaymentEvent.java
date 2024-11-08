package com.cboard.rental.messaging.events;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuePaymentEvent {
	private Long shdId; // Payment Schedule ID
	private Long tenantId; // Tenant ID
	private Long contractId; // Contract ID
	private BigDecimal amount; // Payment amount
	private LocalDate scheduledDate; // Scheduled date for payment
	private String tenantEmail; // Tenant's email address
	private String tenantPhone; // Tenant's phone number
    private String token;

	// Override toString() method for better logging output
    @Override
    public String toString() {
        return "DuePaymentEvent{" +
                "shdId=" + shdId +
                ", tenantId=" + tenantId +
                ", contractId=" + contractId +
                ", amount=" + amount +
                ", scheduledDate=" + scheduledDate +
                ", tenantEmail='" + tenantEmail + '\'' +
                ", tenantPhone='" + tenantPhone + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
