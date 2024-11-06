package com.cboard.rental.messaging.controller;

import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.MessagingProducer;
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
    public void handleDuePayment(@RequestBody DuePaymentEvent event) {
    	
    	//System.out.println("Received in controller DuePaymentEvent: " + event);
    	
        messagingProducer.publishDuePaymentEvent(event);
    }
}
