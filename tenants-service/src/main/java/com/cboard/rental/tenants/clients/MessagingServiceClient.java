package com.cboard.rental.tenants.clients;

import com.cboard.rental.tenants.dto.DuePaymentEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "messaging-service", url = "http://localhost:8094", configuration = FeignConfig.class)
public interface MessagingServiceClient {
    @PostMapping("/api/v1/due-payments")
    void sendDuePaymentEvent(@RequestBody DuePaymentEvent event);
}
