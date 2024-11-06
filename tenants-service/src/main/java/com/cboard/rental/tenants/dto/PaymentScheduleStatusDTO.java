package com.cboard.rental.tenants.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;


@Data
public class PaymentScheduleStatusDTO {
    private Long id;
    
    @NotNull(message = "Payment status is required")
    private String status;
}

