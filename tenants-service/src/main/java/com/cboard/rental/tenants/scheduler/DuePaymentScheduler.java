package com.cboard.rental.tenants.scheduler;

import com.cboard.rental.tenants.clients.MessagingServiceClient;
import com.cboard.rental.tenants.dto.DuePaymentEvent;
import com.cboard.rental.tenants.service.DuePaymentService;
import com.cboard.rental.tenants.entity.PaymentSchedule;
import com.cboard.rental.tenants.repository.PaymentScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DuePaymentScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DuePaymentScheduler.class);

    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    private MessagingServiceClient messagingServiceClient;

    @Scheduled(cron = "0 0 0 * * ?") // Example schedule: every day at midnight
    public void publishDuePayments() {
        try {
            LocalDate today = LocalDate.now(); // Get the current date
            List<PaymentSchedule> duePayments = paymentScheduleRepository.findDuePayments(today); // Pass the current date


            List<DuePaymentEvent> duePaymentEvents = new ArrayList<>();

            for (PaymentSchedule payment : duePayments) {
                if (payment != null && payment.getContractId() != null && payment.getTenant() != null) {
                    DuePaymentEvent event = createDuePaymentEvent(payment);

                    if (event.getContractId() != null && event.getTenantId() != null) {
                        duePaymentEvents.add(event);
                    } else {
                        logger.warn("Skipping event creation due to missing mandatory fields: {}", payment);
                    }
                } else {
                    logger.warn("Skipping payment due to null values: {}", payment);
                }
            }

            // Now send the entire list in one call
            try {
                System.out.println("Sending list of DuePaymentEvents: " + duePaymentEvents);
                messagingServiceClient.sendDuePaymentEvents(duePaymentEvents);
            } catch (Exception e) {
                logger.error("Error while sending list of DuePaymentEvents: {}", e.getMessage(), e);
            }
            
            
        } catch (Exception e) {
            logger.error("Error in publishDuePayments: {}", e.getMessage(), e);
        }
    }

    private DuePaymentEvent createDuePaymentEvent(PaymentSchedule payment) {
        DuePaymentEvent event = new DuePaymentEvent();
        event.setShdId(payment.getId());
        event.setContractId(payment.getContractId());
        event.setTenantId(payment.getTenant().getId());
        event.setAmount(payment.getAmount());
        event.setScheduledDate(payment.getScheduledDate());
        return event;
    }
}
