package com.cboard.rental.tenants.mapper;

import com.cboard.rental.tenants.dto.ContractDTO;
import com.cboard.rental.tenants.entity.Contract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractMapper {

    public ContractDTO toDTO(Contract contract) {
        if (contract == null) {
            return null;
        }
        
        ContractDTO dto = new ContractDTO();
        dto.setId(contract.getId());
        dto.setTenantId(contract.getTenant().getId()); // Ensure tenant mapping
        dto.setPropertyId(contract.getPropertyId());
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());
        dto.setMonthlyRent(contract.getMonthlyRent());
        dto.setDepositAmount(contract.getDepositAmount());
        dto.setPaymentTerms(contract.getPaymentTerms());
        dto.setContractStatus(contract.getContractStatus());
        dto.setCreatedAt(contract.getCreatedAt());
        dto.setUpdatedAt(contract.getUpdatedAt());
        return dto;
    }

    public Contract toEntity(ContractDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Contract contract = new Contract();
        contract.setId(dto.getId());
        contract.setPropertyId(dto.getPropertyId());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());
        contract.setMonthlyRent(dto.getMonthlyRent());
        contract.setDepositAmount(dto.getDepositAmount());
        contract.setPaymentTerms(dto.getPaymentTerms());
        contract.setContractStatus(dto.getContractStatus());
        // Map tenant separately if needed
        return contract;
    }

    public List<ContractDTO> toDtoList(List<Contract> contracts) {
        return contracts.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
