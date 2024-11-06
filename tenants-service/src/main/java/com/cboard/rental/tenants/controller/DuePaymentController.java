package com.cboard.rental.tenants.controller;

import com.cboard.rental.tenants.dto.PaymentScheduleDTO;
import com.cboard.rental.tenants.dto.PaymentScheduleStatusDTO;
import com.cboard.rental.tenants.scheduler.DuePaymentScheduler;
import com.cboard.rental.tenants.service.PaymentScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/scheduler")
public class DuePaymentController {

    private final DuePaymentScheduler duePaymentScheduler;
    private final PaymentScheduleService service;

    public DuePaymentController(DuePaymentScheduler duePaymentScheduler, PaymentScheduleService service) {
        this.duePaymentScheduler = duePaymentScheduler;
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    @GetMapping("/publish-due-payments")
    public String triggerDuePayments() {
        duePaymentScheduler.publishDuePayments();
        return "Due payments published successfully";
    }
    
    /*@PostMapping("/acknowledgments")
    public ResponseEntity<Void> receiveAcknowledgment(@RequestBody PaymentScheduleStatusDTO acknowledgmentEvent) throws JsonProcessingException {
    	
    	System.out.println("Incoming acknowledgmentEvent: " + acknowledgmentEvent.toString());

    	
    	ObjectMapper mapper = new ObjectMapper();
    	System.out.println("Raw JSON payload: " + mapper.writeValueAsString(acknowledgmentEvent));
    	
    	System.out.println("Received acknowledgment payload: " + acknowledgmentEvent);
    	
        // Process the acknowledgment by updating the payment schedule status
        //Long id = acknowledgmentEvent.getId();
        //service.updatePaymentScheduleStatus(id, acknowledgmentEvent);
        return ResponseEntity.ok().build();
    }*/
    
    @PostMapping("/acknowledgments")
    public ResponseEntity<Void> receiveAcknowledgment(@RequestParam Long id, @RequestParam String status) {
        System.out.println("Received acknowledgment with ID: " + id + " and Status: " + status);
                
        // Call the service to update the payment schedule status
        service.updatePaymentScheduleStatus(id, status);
        
        return ResponseEntity.ok().build();
    }
    
}
