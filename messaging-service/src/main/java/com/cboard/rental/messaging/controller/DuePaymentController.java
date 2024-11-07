package com.cboard.rental.messaging.controller;

import com.cboard.rental.messaging.events.DuePaymentEvent;

import jakarta.servlet.http.HttpServletRequest;

import com.cboard.rental.messaging.MessagingProducer;

import java.util.List;

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
    public void handleDuePayment(@RequestBody List<DuePaymentEvent> events, HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // Extract the token
        }

        // Loop through the list and set the token for each event, then publish it
        for (DuePaymentEvent event : events) {
            event.setToken(token);
            messagingProducer.publishDuePaymentEvent(event);
        }
    }


}
