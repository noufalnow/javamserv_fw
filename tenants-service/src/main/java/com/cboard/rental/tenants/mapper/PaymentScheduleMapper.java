package com.cboard.rental.tenants.mapper;

import com.cboard.rental.tenants.dto.PaymentScheduleDTO;
import com.cboard.rental.tenants.entity.PaymentSchedule;
import com.cboard.rental.tenants.entity.Tenants;
import com.cboard.rental.tenants.repository.TenantsRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TenantIdMapper.class})
public interface PaymentScheduleMapper {

    @Mapping(source = "tenant.id", target = "tenantId")
    PaymentScheduleDTO toDto(PaymentSchedule paymentSchedule);

    @Mapping(source = "tenantId", target = "tenant")
    PaymentSchedule toEntity(PaymentScheduleDTO paymentScheduleDTO);

    List<PaymentScheduleDTO> toDtoList(List<PaymentSchedule> paymentSchedules);
}

// Custom Mapper to resolve tenant by ID
@Component
class TenantIdMapper {
    
    @Autowired
    private TenantsRepository tenantsRepository;

    public Tenants mapTenantIdToTenant(Long tenantId) {
        return tenantId != null ? tenantsRepository.findById(tenantId).orElse(null) : null;
    }

    public Long mapTenantToTenantId(Tenants tenant) {
        return tenant != null ? tenant.getId() : null;
    }
}
