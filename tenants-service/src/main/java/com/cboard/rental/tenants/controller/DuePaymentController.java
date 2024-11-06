package com.cboard.rental.tenants.controller;

import com.cboard.rental.tenants.scheduler.DuePaymentScheduler;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scheduler")
public class DuePaymentController {

    private final DuePaymentScheduler duePaymentScheduler;

    public DuePaymentController(DuePaymentScheduler duePaymentScheduler) {
        this.duePaymentScheduler = duePaymentScheduler;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    @GetMapping("/publish-due-payments")
    public String triggerDuePayments() {
        duePaymentScheduler.publishDuePayments();
        return "Due payments published successfully";
    }
}
