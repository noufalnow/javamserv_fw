package com.cboard.rental.tenants.mapper;

import com.cboard.rental.tenants.dto.PaymentScheduleDTO;
import com.cboard.rental.tenants.entity.PaymentSchedule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentScheduleMapper {

    public PaymentScheduleDTO toDto(PaymentSchedule paymentSchedule) {
        if (paymentSchedule == null) {
            return null;
        }
        
        PaymentScheduleDTO dto = new PaymentScheduleDTO();
        dto.setId(paymentSchedule.getId());
        dto.setAmount(paymentSchedule.getAmount());
        dto.setBankId(paymentSchedule.getBankId());
        dto.setChequeNumber(paymentSchedule.getChequeNumber());
        dto.setScheduledDate(paymentSchedule.getScheduledDate());
        dto.setScheduledMonth(paymentSchedule.getScheduledMonth());
        dto.setPaymentDate(paymentSchedule.getPaymentDate());
        dto.setPaymentStatus(paymentSchedule.getPaymentStatus());
        // Map other fields as needed
        return dto;
    }

    public PaymentSchedule toEntity(PaymentScheduleDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PaymentSchedule paymentSchedule = new PaymentSchedule();
        paymentSchedule.setId(dto.getId());
        paymentSchedule.setAmount(dto.getAmount());
        paymentSchedule.setBankId(dto.getBankId());
        paymentSchedule.setChequeNumber(dto.getChequeNumber());
        paymentSchedule.setScheduledDate(dto.getScheduledDate());
        paymentSchedule.setScheduledMonth(dto.getScheduledMonth());
        paymentSchedule.setPaymentDate(dto.getPaymentDate());
        paymentSchedule.setPaymentStatus(dto.getPaymentStatus());
        // Map other fields as needed
        return paymentSchedule;
    }

    public List<PaymentScheduleDTO> toDtoList(List<PaymentSchedule> paymentSchedules) {
        return paymentSchedules.stream().map(this::toDto).collect(Collectors.toList());
    }
}
