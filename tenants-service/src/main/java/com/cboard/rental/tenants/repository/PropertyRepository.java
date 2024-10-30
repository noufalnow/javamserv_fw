package com.cboard.rental.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cboard.rental.tenants.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
