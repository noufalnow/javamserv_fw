package com.cboard.rental.tenants.repository;

import com.cboard.rental.tenants.entity.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, Long> {
    // Custom query to find all payment schedules by tenant and contract
    List<PaymentSchedule> findByTenantIdAndContractId(Long tenantId, Long contractId);

    // Custom query to find payment schedules within a specific date range
    List<PaymentSchedule> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT p FROM PaymentSchedule p WHERE p.scheduledDate = :date AND p.status = 'DUE'")
    List<PaymentSchedule> findDuePayments(@Param("date") LocalDate date);

}