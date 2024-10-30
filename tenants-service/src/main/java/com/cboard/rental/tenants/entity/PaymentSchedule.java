package com.cboard.rental.tenants.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment_schedule")
public class PaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shd_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenants tenant;

    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @Column(name = "amount", precision = 13, scale = 3, nullable = false)
    private BigDecimal amount;

    @Column(name = "bank_id")
    private Short bankId;

    @Column(name = "cheque_number", length = 20)
    private String chequeNumber;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @Column(name = "scheduled_month", nullable = false)
    private LocalDate scheduledMonth;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_status", nullable = false)
    private Short paymentStatus;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "modified_by")
    private Long modifiedBy;
}
