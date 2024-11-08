package com.cboard.rental.tenants.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class DuePaymentEvent {
	private Long shdId; // Payment Schedule ID
	private Long tenantId; // Tenant ID
	private Long contractId; // Contract ID
	private BigDecimal amount; // Payment amount
	private LocalDate scheduledDate; // Scheduled date for payment
	private String tenantEmail; // Tenant's email address
	private String tenantPhone; // Tenant's phone number
	
	public DuePaymentEvent(Long shdId, Long tenantId, Long contractId, BigDecimal amount, LocalDate scheduledDate, String tenantPhone, String tenantEmail) {
	    this.shdId = shdId;
	    this.tenantId = tenantId;
	    this.contractId = contractId;
	    this.amount = amount;
	    this.scheduledDate = scheduledDate;
	    this.tenantPhone = tenantPhone;
	    this.tenantEmail = tenantEmail;
	}


	// Override toString() method for better logging output
	@Override
	public String toString() {
		return "DuePaymentEvent{" + "shdId=" + shdId + ", tenantId=" + tenantId + ", contractId=" + contractId
				+ ", amount=" + amount + ", scheduledDate=" + scheduledDate + ", tenantEmail='" + tenantEmail + '\''
				+ ", tenantPhone='" + tenantPhone + '\'' + '}';
	}
}

