package com.cboard.rental.tenants.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Data
public class PaymentScheduleDTO {

    private Long id;

    @NotNull(message = "Tenant ID is required")
    private Long tenantId;

    @NotNull(message = "Contract ID is required")
    private Long contractId;

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    private Short bankId;

    @Size(max = 20, message = "Cheque number cannot exceed 20 characters")
    private String chequeNumber;

    @NotNull(message = "Scheduled date is required")
    private LocalDate scheduledDate;

    @NotNull(message = "Scheduled month is required")
    private LocalDate scheduledMonth;

    private LocalDate paymentDate;

    @NotNull(message = "Payment status is required")
    private Short paymentStatus;

    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
    private Long createdBy;
    private Long deletedBy;
    private Long modifiedBy;
}

