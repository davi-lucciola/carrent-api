package io.api.carrent.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_types")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public VehicleType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public VehicleType(String name) {
        this.name = name;
    }
}
