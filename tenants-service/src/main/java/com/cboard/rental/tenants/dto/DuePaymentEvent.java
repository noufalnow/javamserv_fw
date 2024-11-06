package com.cboard.rental.tenants.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DuePaymentEvent {
	private Long shdId;
    private Long tenantId;
    private Long contractId;
    private BigDecimal amount;
    private LocalDate scheduledDate;

    // No-argument constructor
    public DuePaymentEvent() {
    }

    // Parameterized constructor
    public DuePaymentEvent(Long shdId, Long tenantId, Long contractId, BigDecimal amount, LocalDate scheduledDate) {
    	this.shdId = shdId;
        this.tenantId = tenantId;
        this.contractId = contractId;
        this.amount = amount;
        this.scheduledDate = scheduledDate;
    }

    // Getters and Setters
    public Long getShdId() {
        return shdId;
    }
    
    public void setShdId(Long shdId) {
        this.shdId = shdId;
    }
    
    
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    // Override toString() method for better logging output
    @Override
    public String toString() {
        return "DuePaymentEvent{" +
               "shdId=" + shdId +
               ", tenantId=" + tenantId +
               ", contractId=" + contractId +
               ", amount=" + amount +
               ", scheduledDate=" + scheduledDate +
               '}';
    }
}
