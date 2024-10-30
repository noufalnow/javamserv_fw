package com.cboard.rental.tenants.repository;

import com.cboard.rental.tenants.entity.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantsRepository extends JpaRepository<Tenants, Long> {
    // Custom query methods can be added here if needed, e.g., find by email or phone
    Tenants findByIdNo(String idNo);
}
