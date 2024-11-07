package com.cboard.rental.messaging.controller;

import com.cboard.rental.messaging.events.DuePaymentEvent;
import com.cboard.rental.messaging.listener.DuePaymentEventListener;

import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.servlet.http.HttpServletRequest;

import com.cboard.rental.messaging.MessagingProducer;
import com.cboard.rental.messaging.DTO.AcknowledgmentDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cboard.rental.messaging.repository.MessageRecordRepository;

@RestController
public class DuePaymentController {

	private final MessagingProducer messagingProducer;

	@Autowired
	private MessageRecordRepository messageRecordRepository;
	
	@Autowired
	private DuePaymentEventListener duePaymentEventListener;

	public DuePaymentController(MessagingProducer messagingProducer) {
		this.messagingProducer = messagingProducer;
	}

	@PostMapping("/api/v1/due-payments")
	public void handleDuePayment(@RequestBody List<DuePaymentEvent> events, HttpServletRequest request) {
	    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	    String token = null;
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        token = authHeader.substring(7); // Extract the token
	    }

	    List<AcknowledgmentDTO> skippedAcknowledgments = new ArrayList<>();

	    for (DuePaymentEvent event : events) {
	        event.setToken(token);
	        if (messageRecordRepository.existsByShdIdAndTopics(event.getShdId(), "due-payment-topic")) {
	            System.out.println("Duplicate event detected. Skipping publishing for shd_id: " + event.getShdId());
	            skippedAcknowledgments.add(new AcknowledgmentDTO(event.getShdId(), "COMPLETED"));
	        } else {
	            messagingProducer.publishDuePaymentEvent(event); // Only publish non-duplicates
	        }
	    }
	    
	    // Send acknowledgment for skipped events
	    if (!skippedAcknowledgments.isEmpty()) {
	        duePaymentEventListener.sendBulkAcknowledgment(skippedAcknowledgments, token);
	    }
	}

}
