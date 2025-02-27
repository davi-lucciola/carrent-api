package io.api.carrent.domain.entities;

import io.api.carrent.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_status_historical")
public class VehicleStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_user_id", referencedColumnName = "id", nullable = false)
    private User renter;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
}
