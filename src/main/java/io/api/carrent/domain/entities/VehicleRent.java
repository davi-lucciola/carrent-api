package io.api.carrent.domain.entities;

import io.api.carrent.domain.enums.RentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_rents")
public class VehicleRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_user_id", referencedColumnName = "id", nullable = false)
    private User renter;

    @Column(name = "rent_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_user_id", referencedColumnName = "id")
    private User operator;

    @Column(name = "withdraw_code", nullable = false)
    private String withdrawCode;

    @Column(name = "withdraw_datetime")
    private Instant withdrawDatetime;

    @Column(name = "withdraw_max_datetime", nullable = false)
    private Instant withdrawMaxDatetime;

    @Column(name = "return_code", nullable = false)
    private String returnCode;

    @Column(name = "return_datetime")
    private Instant returnDatetime;
}
