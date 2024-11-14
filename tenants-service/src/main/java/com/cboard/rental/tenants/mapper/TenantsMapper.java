package com.cboard.rental.tenants.mapper;

import com.cboard.rental.tenants.dto.TenantsDTO;
import com.cboard.rental.tenants.entity.Tenants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component  // Make it a Spring bean
public class TenantsMapper {

    public TenantsDTO toDTO(Tenants tenant) {
        if (tenant == null) {
            return null;
        }
        TenantsDTO dto = new TenantsDTO();
        dto.setId(tenant.getId());
        dto.setFullName(tenant.getFullName());
        dto.setCompanyName(tenant.getCompanyName());
        dto.setPhone(tenant.getPhone());
        dto.setEmail(tenant.getEmail());
        dto.setIdNo(tenant.getIdNo());
        dto.setCreatedAt(tenant.getCreatedAt());
        dto.setUpdatedAt(tenant.getUpdatedAt());
        return dto;
    }

    public Tenants toEntity(TenantsDTO dto) {
        if (dto == null) {
            return null;
        }
        Tenants tenant = new Tenants();
        tenant.setId(dto.getId());
        tenant.setFullName(dto.getFullName());
        tenant.setCompanyName(dto.getCompanyName());
        tenant.setPhone(dto.getPhone());
        tenant.setEmail(dto.getEmail());
        tenant.setIdNo(dto.getIdNo());
        tenant.setCreatedAt(dto.getCreatedAt());
        tenant.setUpdatedAt(dto.getUpdatedAt());
        return tenant;
    }

    public List<TenantsDTO> toDTOList(List<Tenants> tenants) {
        return tenants.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Tenants> toEntityList(List<TenantsDTO> tenantsDTOs) {
        return tenantsDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
