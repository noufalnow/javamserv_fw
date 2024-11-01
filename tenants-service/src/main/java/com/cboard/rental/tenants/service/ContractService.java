package com.cboard.rental.tenants.service;

import com.cboard.rental.tenants.clients.PropertyServiceClient;
import com.cboard.rental.tenants.config.ResourceNotFoundException;
import com.cboard.rental.tenants.dto.ContractDTO;
import com.cboard.rental.tenants.entity.Contract;
import com.cboard.rental.tenants.entity.Tenants;
import com.cboard.rental.tenants.mapper.ContractMapper;
import com.cboard.rental.tenants.repository.ContractRepository;
import com.cboard.rental.tenants.repository.TenantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private TenantsRepository tenantsRepository;

    @Autowired
    private PropertyServiceClient propertyServiceClient;

    private final ContractMapper mapper = ContractMapper.INSTANCE;

    public ContractDTO createContract(ContractDTO contractDTO) {
        // Validate tenant
        Tenants tenant = tenantsRepository.findById(contractDTO.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", contractDTO.getTenantId()));

        // Validate property from property-service using Feign client
        Boolean propertyExists = propertyServiceClient.doesPropertyExist(contractDTO.getPropertyId());
        if (Boolean.FALSE.equals(propertyExists)) {
            throw new ResourceNotFoundException("Property", contractDTO.getPropertyId());
        }

        Contract contract = mapper.toEntity(contractDTO);
        contract.setTenant(tenant);
        contract.setCreatedAt(LocalDateTime.now());

        Contract savedContract = contractRepository.save(contract);
        return mapper.toDTO(savedContract);
    }

    public List<ContractDTO> getAllContracts() {
        return contractRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContractDTO getContractById(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", id));
        return mapper.toDTO(contract);
    }

    public List<ContractDTO> getContractsByTenantId(Long tenantId) {
        return contractRepository.findByTenantId(tenantId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContractDTO> getContractsByPropertyId(Long propertyId) {
        return contractRepository.findByPropertyId(propertyId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContractDTO updateContract(Long id, ContractDTO contractDTO) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", id));

        // Update fields
        contract.setPropertyId(contractDTO.getPropertyId());
        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());
        contract.setMonthlyRent(contractDTO.getMonthlyRent());
        contract.setDepositAmount(contractDTO.getDepositAmount());
        contract.setPaymentTerms(contractDTO.getPaymentTerms());
        contract.setContractStatus(contractDTO.getContractStatus());
        contract.setUpdatedAt(LocalDateTime.now());

        Contract updatedContract = contractRepository.save(contract);
        return mapper.toDTO(updatedContract);
    }

    public void deleteContract(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contract", id);
        }
        contractRepository.deleteById(id);
    }
}
