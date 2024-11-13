package com.cboard.rental.payments.controller;

import com.cboard.rental.payments.events.DuePaymentEvent;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    @PostMapping("/api/v1/add-due")
    public void receiveDuePayments(@RequestBody List<DuePaymentEvent> events) {
        System.out.println("Received due payment events:");

        for (DuePaymentEvent event : events) {
            System.out.println("Tenant ID: " + event.getTenantId());
            System.out.println("Amount: " + event.getAmount());
            System.out.println("Scheduled Date: " + event.getScheduledDate());
            System.out.println("Tenant Email: " + event.getTenantEmail());
            System.out.println("Tenant Phone: " + event.getTenantPhone());
            System.out.println("-----------");
        }

        // You can add further processing logic here if needed
    }
}
