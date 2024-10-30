package com.cboard.rental.tenants.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TenantsRepository tenantsRepository;

    private final TenantsMapper mapper = TenantsMapper.INSTANCE;

    public TenantsDTO createTenant(TenantsDTO tenantsDTO) {
        Tenants tenant = mapper.toEntity(tenantsDTO);
        tenant.setCreatedAt(LocalDateTime.now());
        Tenants savedTenant = tenantsRepository.save(tenant);
        return mapper.toDTO(savedTenant);
    }

    public List<TenantsDTO> getAllTenants() {
        return tenantsRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public TenantsDTO getTenantById(Long id) {
        Tenants tenant = tenantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", id));
        return mapper.toDTO(tenant);
    }

    public TenantsDTO updateTenant(Long id, TenantsDTO tenantsDTO) {
        Tenants tenant = tenantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", id));

        // Update tenant fields
        tenant.setFullName(tenantsDTO.getFullName());
        tenant.setCompanyName(tenantsDTO.getCompanyName());
        tenant.setPhone(tenantsDTO.getPhone());
        tenant.setEmail(tenantsDTO.getEmail());
        tenant.setIdNo(tenantsDTO.getIdNo());
        tenant.setUpdatedAt(LocalDateTime.now());

        Tenants updatedTenant = tenantsRepository.save(tenant);
        return mapper.toDTO(updatedTenant);
    }

    public void deleteTenant(Long id) {
        if (!tenantsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tenant", id);
        }
        tenantsRepository.deleteById(id);
    }
}
