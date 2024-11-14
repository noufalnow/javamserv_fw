package com.cboard.rental.tenants.service;

import com.cboard.rental.tenants.dto.PaymentScheduleDTO;
import com.cboard.rental.tenants.dto.PaymentScheduleStatusDTO;
import com.cboard.rental.tenants.entity.PaymentSchedule;
import com.cboard.rental.tenants.mapper.PaymentScheduleMapper;
import com.cboard.rental.tenants.repository.PaymentScheduleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentScheduleService {

    private final PaymentScheduleRepository repository;
    private final PaymentScheduleMapper mapper;

    public PaymentScheduleService(PaymentScheduleRepository repository, PaymentScheduleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PaymentScheduleDTO createPaymentSchedule(@Valid PaymentScheduleDTO dto) {
        PaymentSchedule paymentSchedule = mapper.toEntity(dto);
        PaymentSchedule savedPaymentSchedule = repository.save(paymentSchedule);
        return mapper.toDto(savedPaymentSchedule);
    }

    public PaymentScheduleDTO getPaymentScheduleById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Payment Schedule not found with id: " + id));
    }

    public List<PaymentScheduleDTO> getAllPaymentSchedules() {
        return mapper.toDtoList(repository.findAll());
    }

    public PaymentScheduleDTO updatePaymentSchedule(Long id, @Valid PaymentScheduleDTO dto) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Payment Schedule not found with id: " + id);
        }
        PaymentSchedule paymentSchedule = mapper.toEntity(dto);
        paymentSchedule.setId(id); // Ensure updating the correct entity
        PaymentSchedule updatedPaymentSchedule = repository.save(paymentSchedule);
        return mapper.toDto(updatedPaymentSchedule);
    }
    
    public PaymentScheduleDTO updatePaymentScheduleStatus(Long id, String status) {
        // Check if PaymentSchedule exists
        PaymentSchedule paymentSchedule = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment Schedule not found with id: " + id));

        // Update only the payment status
        paymentSchedule.setStatus(status);

        // Save and return the updated entity
        PaymentSchedule updatedPaymentSchedule = repository.save(paymentSchedule);
        return mapper.toDto(updatedPaymentSchedule);
    }

    

    public void deletePaymentSchedule(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Payment Schedule not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
