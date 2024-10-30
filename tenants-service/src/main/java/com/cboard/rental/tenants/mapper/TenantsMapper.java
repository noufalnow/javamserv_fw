package com.cboard.rental.tenants.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.cboard.rental.tenants.dto.TenantsDTO;
import com.cboard.rental.tenants.entity.Tenants;

@Mapper
public interface TenantsMapper {
    TenantsMapper INSTANCE = Mappers.getMapper(TenantsMapper.class);
    
    TenantsDTO toDTO(Tenants tenants);
    Tenants toEntity(TenantsDTO tenantsDTO);
}
