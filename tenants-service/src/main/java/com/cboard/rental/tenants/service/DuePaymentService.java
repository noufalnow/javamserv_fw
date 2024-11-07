package com.cboard.rental.tenants.service;

import com.cboard.rental.tenants.clients.MessagingServiceClient;
import com.cboard.rental.tenants.dto.DuePaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DuePaymentService {

    private final MessagingServiceClient messagingServiceClient;

    @Autowired
    public DuePaymentService(MessagingServiceClient messagingServiceClient) {
        this.messagingServiceClient = messagingServiceClient;
    }

    public void sendDuePayments(List<DuePaymentEvent> events) {
        try {
            messagingServiceClient.sendDuePaymentEvents(events);
            System.out.println("List of DuePaymentEvents sent successfully");
        } catch (Exception e) {
            System.err.println("Exception while sending list of DuePaymentEvents: " + e.getMessage());
        }
    }
}
