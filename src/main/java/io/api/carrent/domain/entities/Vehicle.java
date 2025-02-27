package io.api.carrent.domain.entities;

import io.api.carrent.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Entity
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Vehicle(String plate, String brand, String model, Integer year, Float odometer, VehicleType vehicleType) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.vehicleType = vehicleType;
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
        this.flActive = false;
    }
}
