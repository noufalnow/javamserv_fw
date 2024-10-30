package com.cboard.rental.tenants.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cboard.rental.tenants.dto.ContractDTO;
import com.cboard.rental.tenants.entity.Contract;

@Mapper
public interface ContractMapper {
    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);
    
    ContractDTO toDTO(Contract contract);
    Contract toEntity(ContractDTO contractDTO);
}