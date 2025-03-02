package io.api.carrent.domain.entities;

import io.api.carrent.domain.enums.RentStatus;
import io.api.carrent.domain.exceptions.DomainException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_rents")
public class VehicleRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_user_id", referencedColumnName = "id", nullable = false)
    private User renter;

    @Column(name = "rent_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_user_id", referencedColumnName = "id")
    private User operator;

    @Column(name = "withdraw_code", nullable = false)
    private String withdrawCode;

    @Column(name = "withdraw_datetime")
    private LocalDateTime withdrawDatetime;

    @Column(name = "withdraw_max_datetime", nullable = false)
    private LocalDateTime withdrawMaxDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_operator_user_id", referencedColumnName = "id")
    private User returnOperator;

    @Column(name = "return_code", nullable = false)
    private String returnCode;

    @Column(name = "return_datetime")
    private LocalDateTime returnDatetime;

    public VehicleRent(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.createdAt = LocalDateTime.now();
        this.returnCode = generateRandomCode();
        this.withdrawCode = generateRandomCode();
        this.withdrawMaxDatetime = this.createdAt.plusDays(1);
    }

    public void bookVehicle(User renter) {
        this.status = RentStatus.WATING;
        this.renter = renter;

        this.vehicle.book();
    }

    public void cancelRent(String cancellationReason, User operator) {
        if (!RentStatus.WATING.equals(this.status)) {
            String status = this.status.getDescription().toLowerCase();
            throw new DomainException("Essa reserva não pode ser mais cancelada pois está %s.".formatted(status));
        }

        this.status = RentStatus.CANCELED;
        this.operator = operator;
        this.cancellationReason = cancellationReason;

        this.vehicle.available();
    }

    public void startRent(User operator) {
        if (!RentStatus.WATING.equals(this.status)) {
            String status = this.status.getDescription().toLowerCase();
            throw new DomainException("Essa reserva não pode ser mais confirmada pois está %s.".formatted(status));
        }

        this.status = RentStatus.IN_PROGRESS;
        this.operator = operator;
        this.withdrawDatetime = LocalDateTime.now();

        this.vehicle.rent();
    }

    public void finishRent(User operator) {
        if (!RentStatus.IN_PROGRESS.equals(this.status)) {
            String status = RentStatus.IN_PROGRESS.getDescription().toLowerCase();
            throw new DomainException("Sua reserva não pode ser finalizada pois não está %s.".formatted(status));
        }

        this.status = RentStatus.FINISHED;
        this.returnOperator = operator;
        this.returnDatetime = LocalDateTime.now();

        this.vehicle.available();
    }

    private String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    public boolean verifyWithdrawCode(String withdrawCode) {
        return this.withdrawCode.equals(withdrawCode);
    }

    public boolean verifyReturnCode(String returnCode) {
        return this.returnCode.equals(returnCode);
    }
}
