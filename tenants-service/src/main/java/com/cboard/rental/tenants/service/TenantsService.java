package com.cboard.rental.tenants.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.cboard.rental.tenants.config.ResourceNotFoundException;
import com.cboard.rental.tenants.dto.TenantsDTO;
import com.cboard.rental.tenants.entity.Tenants;
import com.cboard.rental.tenants.mapper.TenantsMapper;
import com.cboard.rental.tenants.repository.TenantsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantsService {

    private static final Logger logger = LoggerFactory.getLogger(TenantsService.class);

    private final TenantsRepository tenantsRepository;
    private final TenantsMapper tenantsMapper;

    public TenantsService(TenantsRepository tenantsRepository, TenantsMapper tenantsMapper) {
        this.tenantsRepository = tenantsRepository;
        this.tenantsMapper = tenantsMapper;
    }

    public TenantsDTO createTenant(TenantsDTO tenantsDTO) {
        Tenants tenant = tenantsMapper.toEntity(tenantsDTO);
        tenant.setCreatedAt(LocalDateTime.now());
        Tenants savedTenant = tenantsRepository.save(tenant);
        return tenantsMapper.toDTO(savedTenant);
    }
    
    
    public TenantsDTO testMapping(Tenants tenant) {
        TenantsDTO dto = new TenantsDTO();
        dto.setId(tenant.getId());
        dto.setFullName(tenant.getFullName());
        dto.setCompanyName(tenant.getCompanyName());
        dto.setPhone(tenant.getPhone());
        dto.setEmail(tenant.getEmail());
        dto.setIdNo(tenant.getIdNo());
        dto.setCreatedAt(tenant.getCreatedAt());
        dto.setUpdatedAt(tenant.getUpdatedAt());
        logger.debug("Manual mapping result: {}", dto);
        return dto;
    }

    public List<TenantsDTO> getAllTenantsWithTestMapping() {
        List<Tenants> tenants = tenantsRepository.findAll();
        return tenants.stream()
            .map(tenant -> {
                TenantsDTO dto = testMapping(tenant); // Use the test mapping method here
                logger.info("Manual mapping tenant: {} to DTO: {}", tenant, dto);
                return dto;
            })
            .collect(Collectors.toList());
    }

    public List<TenantsDTO> getAllTenants() {
        List<Tenants> tenants = tenantsRepository.findAll();
        return tenants.stream()
            .map(tenant -> {
                TenantsDTO dto = tenantsMapper.toDTO(tenant);
                logger.info("Mapping tenant: {} to DTO: {}", tenant, dto);
                return dto;
            })
            .collect(Collectors.toList());
    }

    public TenantsDTO getTenantById(Long id) {
        Tenants tenant = tenantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", id));
        return tenantsMapper.toDTO(tenant);
    }

    public TenantsDTO updateTenant(Long id, TenantsDTO tenantsDTO) {
        Tenants tenant = tenantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", id));

        tenant.setFullName(tenantsDTO.getFullName());
        tenant.setCompanyName(tenantsDTO.getCompanyName());
        tenant.setPhone(tenantsDTO.getPhone());
        tenant.setEmail(tenantsDTO.getEmail());
        tenant.setIdNo(tenantsDTO.getIdNo());
        tenant.setUpdatedAt(LocalDateTime.now());

        Tenants updatedTenant = tenantsRepository.save(tenant);
        return tenantsMapper.toDTO(updatedTenant);
    }

    public void deleteTenant(Long id) {
        if (!tenantsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tenant", id);
        }
        tenantsRepository.deleteById(id);
    }
}
