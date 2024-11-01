package com.cboard.rental.tenants.controller;

import com.cboard.rental.tenants.dto.PaymentScheduleDTO;
import com.cboard.rental.tenants.service.PaymentScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment-schedules")
public class PaymentScheduleController {

    private final PaymentScheduleService service;

    @Autowired
    public PaymentScheduleController(PaymentScheduleService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    /*public ResponseEntity<PaymentScheduleDTO> createPaymentSchedule(@Valid @RequestBody PaymentScheduleDTO dto) {
        PaymentScheduleDTO createdPaymentSchedule = service.createPaymentSchedule(dto);
        return ResponseEntity.ok(createdPaymentSchedule);
    }
    */
    
    public ResponseEntity<List<PaymentScheduleDTO>> createPaymentSchedules(@Valid @RequestBody List<PaymentScheduleDTO> dtos) {
        List<PaymentScheduleDTO> createdSchedules = dtos.stream()
            .map(service::createPaymentSchedule)
            .collect(Collectors.toList());
        return ResponseEntity.ok(createdSchedules);
    }
     
    

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<PaymentScheduleDTO> getPaymentScheduleById(@PathVariable Long id) {
        PaymentScheduleDTO paymentSchedule = service.getPaymentScheduleById(id);
        return ResponseEntity.ok(paymentSchedule);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<List<PaymentScheduleDTO>> getAllPaymentSchedules() {
        List<PaymentScheduleDTO> paymentSchedules = service.getAllPaymentSchedules();
        return ResponseEntity.ok(paymentSchedules);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<PaymentScheduleDTO> updatePaymentSchedule(@PathVariable Long id, @Valid @RequestBody PaymentScheduleDTO dto) {
        PaymentScheduleDTO updatedPaymentSchedule = service.updatePaymentSchedule(id, dto);
        return ResponseEntity.ok(updatedPaymentSchedule);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<Void> deletePaymentSchedule(@PathVariable Long id) {
        service.deletePaymentSchedule(id);
        return ResponseEntity.noContent().build();
    }
}

