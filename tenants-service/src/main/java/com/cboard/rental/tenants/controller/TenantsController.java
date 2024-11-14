package com.cboard.rental.tenants.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cboard.rental.tenants.dto.TenantsDTO;
import com.cboard.rental.tenants.service.TenantsService;

@RestController
@RequestMapping("/tenants")
public class TenantsController {
    
    private static final Logger logger = LoggerFactory.getLogger(TenantsController.class);

    @Autowired
    private TenantsService tenantsService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<TenantsDTO> createTenant(@RequestBody TenantsDTO tenantsDTO) {
        logger.info("Received TenantsDTO for creation: {}", tenantsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantsService.createTenant(tenantsDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<List<TenantsDTO>> getAllTenants() {
        List<TenantsDTO> tenants = tenantsService.getAllTenants();
        logger.info("Retrieved tenants: {}", tenants); // Log the tenants list before returning
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<TenantsDTO> getTenantById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantsService.getTenantById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<TenantsDTO> updateTenant(@PathVariable Long id, @RequestBody TenantsDTO tenantsDTO) {
        logger.info("Received TenantsDTO for update: {}", tenantsDTO);
        return ResponseEntity.ok(tenantsService.updateTenant(id, tenantsDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        tenantsService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}
