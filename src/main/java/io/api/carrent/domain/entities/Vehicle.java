package io.api.carrent.domain.entities;

import io.api.carrent.domain.enums.VehicleStatus;
import io.api.carrent.domain.exceptions.DomainException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String plate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Float odometer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @Column(name = "fl_active", nullable = false)
    private Boolean flActive = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_type_id", referencedColumnName = "id", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<VehicleStatusHistory> statusHistories;

    public Vehicle(String plate, String brand, String model, Integer year, Float odometer, VehicleType vehicleType) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.vehicleType = vehicleType;
        this.statusHistories = List.of(new VehicleStatusHistory(this));
    }

    public void book() {
        if (!this.status.equals(VehicleStatus.AVAILABLE)) {
            throw new DomainException("Você não pode reservar veículos que não estão disponiveis.");
        }

        this.status = VehicleStatus.BOOKED;
    }

    public void rent() {
        if (!this.status.equals(VehicleStatus.BOOKED)) {
            throw new DomainException("Você não pode alugar veículos que não estão reservados.");
        }

        this.status = VehicleStatus.RENTED;
    }

    public void available() {
        this.status = VehicleStatus.AVAILABLE;
    }

    public void update(String plate, String brand, String model, Integer year, Float odometer, VehicleType vehicleType) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.vehicleType = vehicleType;
    }

    public void activate() {
        this.flActive = true;
    }

    public void deactivate() {
        if (!VehicleStatus.AVAILABLE.equals(this.status)) {
            throw new DomainException("Não é possivel desativar um veículo que não está disponivel.");
        }

        this.flActive = false;
    }
}
