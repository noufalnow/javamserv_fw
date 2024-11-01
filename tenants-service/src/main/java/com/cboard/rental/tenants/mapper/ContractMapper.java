package com.cboard.rental.tenants.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cboard.rental.tenants.dto.ContractDTO;
import com.cboard.rental.tenants.entity.Contract;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);
    
    @Mapping(source = "tenant.id", target = "tenantId")
    ContractDTO toDTO(Contract contract);
    
    @Mapping(source = "tenantId", target = "tenant.id")
    Contract toEntity(ContractDTO contractDTO);
}
