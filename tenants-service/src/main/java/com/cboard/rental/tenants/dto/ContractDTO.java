package com.cboard.rental.tenants.dto;

import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ContractDTO {

    private Long id;

    @NotNull(message = "Tenant ID is required")
    private Long tenantId;

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Monthly rent is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly rent must be greater than zero")
    private BigDecimal monthlyRent;

    @NotNull(message = "Deposit amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Deposit amount must be greater than zero")
    private BigDecimal depositAmount;

    @Size(max = 50, message = "Payment terms cannot exceed 50 characters")
    private String paymentTerms;

    @NotBlank(message = "Contract status is required")
    @Size(max = 20, message = "Contract status cannot exceed 20 characters")
    private String contractStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
