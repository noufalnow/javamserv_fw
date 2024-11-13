package com.cboard.rental.payments.clients;

import com.cboard.rental.payments.events.DuePaymentEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "messaging-service", url = "http://localhost:8094", configuration = FeignConfig.class)
public interface MessagingServiceClient {

    @PostMapping("/api/v1/due-payments")
    void sendDuePaymentEvents(@RequestBody List<DuePaymentEvent> events);
}