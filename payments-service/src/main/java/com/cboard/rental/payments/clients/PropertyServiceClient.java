package com.cboard.rental.payments.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "property-service", configuration = FeignConfig.class)
public interface PropertyServiceClient {
    @GetMapping("/properties/{propertyId}/exists")
    Boolean doesPropertyExist(@PathVariable("propertyId") Long propertyId);
}

