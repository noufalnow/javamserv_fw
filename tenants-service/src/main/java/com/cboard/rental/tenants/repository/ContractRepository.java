package com.cboard.rental.tenants.repository;

import com.cboard.rental.tenants.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    // Custom query to retrieve contracts by tenant ID
    List<Contract> findByTenantId(Long tenantId);
    
    List<Contract> findByPropertyId(Long propertyId);
    
}
