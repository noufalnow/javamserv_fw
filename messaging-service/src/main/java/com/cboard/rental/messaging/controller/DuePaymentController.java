package com.cboard.rental.messaging.controller;

import com.cboard.rental.messaging.events.DuePaymentEvent;

import jakarta.servlet.http.HttpServletRequest;

import com.cboard.rental.messaging.MessagingProducer;

import org.apache.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DuePaymentController {

    private final MessagingProducer messagingProducer;

    public DuePaymentController(MessagingProducer messagingProducer) {
        this.messagingProducer = messagingProducer;
    }

    @PostMapping("/api/v1/due-payments")
    public void handleDuePayment(@RequestBody DuePaymentEvent event, HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            event.setToken(token);  // assuming DuePaymentEvent has a token field
        }
        messagingProducer.publishDuePaymentEvent(event);
    }

}
