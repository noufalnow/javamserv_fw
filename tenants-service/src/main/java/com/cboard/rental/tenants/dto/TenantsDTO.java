package com.cboard.rental.tenants.dto;

import java.time.LocalDateTime;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class TenantsDTO {

    private Long id;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String companyName;

    @NotBlank(message = "Phone is required")
    @Size(max = 15, message = "Phone cannot exceed 15 characters")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "ID number is required")
    @Size(max = 20, message = "ID number cannot exceed 20 characters")
    private String idNo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
