package com.cboard.rental.tenants.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cboard.rental.tenants.dto.PaymentScheduleDTO;
import com.cboard.rental.tenants.entity.PaymentSchedule;

@Mapper
public interface PaymentScheduleMapper {
    PaymentScheduleMapper INSTANCE = Mappers.getMapper(PaymentScheduleMapper.class);

    PaymentScheduleDTO toDTO(PaymentSchedule paymentSchedule);
    PaymentSchedule toEntity(PaymentScheduleDTO paymentScheduleDTO);
}