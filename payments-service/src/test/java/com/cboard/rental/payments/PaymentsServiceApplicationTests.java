package com.cboard.rental.payments;

import com.cboard.rental.payments.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PaymentsServiceApplicationTests {

    @Autowired
    private PaymentService paymentService;

    @Test
    void contextLoads() {
        // Verify that the context loads
    }

    @Test
    void testPaymentProcessing() {
        String result = paymentService.processPayment();
        assertEquals("Payment processed successfully!", result);
    }
}
