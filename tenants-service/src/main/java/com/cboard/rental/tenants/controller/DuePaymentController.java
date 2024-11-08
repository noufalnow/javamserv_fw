package com.cboard.rental.tenants.controller;

import com.cboard.rental.tenants.dto.PaymentScheduleStatusDTO;
import com.cboard.rental.tenants.scheduler.DuePaymentScheduler;
import com.cboard.rental.tenants.service.PaymentScheduleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
	@PostMapping("/acknowledgments")
	public ResponseEntity<Void> receiveAcknowledgments(HttpServletRequest request) {
		try {
			// Read the raw body
			String rawJson = new BufferedReader(new InputStreamReader(request.getInputStream())).lines()
					.collect(Collectors.joining("\n"));

			// Log the raw JSON payload
			System.out.println("Received raw JSON payload: " + rawJson);

			// Parse JSON
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(rawJson);

			List<PaymentScheduleStatusDTO> acknowledgmentEvents = new ArrayList<>();
			if (rootNode.isArray()) { // Confirm it's an array
				for (JsonNode node : rootNode) {
					PaymentScheduleStatusDTO dto = mapper.treeToValue(node, PaymentScheduleStatusDTO.class);
					acknowledgmentEvents.add(dto);
				}
			} else {
				System.err.println("Expected JSON array but received a different structure.");
				return ResponseEntity.badRequest().build();
			}

			// Process each DTO
			for (PaymentScheduleStatusDTO acknowledgment : acknowledgmentEvents) {
				service.updatePaymentScheduleStatus(acknowledgment.getId(), acknowledgment.getStatus());
			}

		} catch (Exception e) {
			System.err.println("Error processing JSON payload: " + e.getMessage());
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}

}
