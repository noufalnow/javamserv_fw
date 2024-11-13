package com.cboard.rental.messaging.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.clients.PaymentsServiceClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PaymentsRequestListener {

    @Autowired
    private PaymentsServiceClient paymentsServiceClient;

    @KafkaListener(topics = "payments-requests", groupId = "payments-requests-group")
    public void handlePaymentRequest(DuePaymentEvent event) {
        System.out.println("Received payment request for tenant ID: " + event.getTenantId());
        processPayment(event);
    }

    private void processPayment(DuePaymentEvent event) {
        System.out.println("Processing payment for amount: " + event.getAmount() +
                ", scheduled date: " + event.getScheduledDate());

        // Extract the token from the event and add "Bearer " prefix
        String token = "Bearer " + event.getToken();

        // Send the event to the payments-service using Feign
        try {
            paymentsServiceClient.sendDuePaymentEvents(token, List.of(event));  // Send as a list
            System.out.println("Payment event sent successfully to payments-service.");
        } catch (Exception e) {
            System.err.println("Failed to send payment event: " + e.getMessage());
        }
    }
}
