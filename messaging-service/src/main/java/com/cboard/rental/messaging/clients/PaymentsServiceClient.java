package com.cboard.rental.messaging.clients;

import com.cboard.rental.messaging.events.DuePaymentEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "payments-service", url = "http://192.168.251.54:8095")
public interface PaymentsServiceClient {

    @PostMapping("/payments/api/v1/add-due")
    void sendDuePaymentEvents(@RequestHeader("Authorization") String authorizationToken, 
                              @RequestBody List<DuePaymentEvent> events);
}

