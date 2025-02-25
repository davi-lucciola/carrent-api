package io.api.carrent.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Float odometer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_type_id", referencedColumnName = "id", nullable = false)
    private VehicleType vehicleType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

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
}
