package io.api.carrent.infra.repositories.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleTypeQueryRepository;
import io.api.carrent.domain.dto.output.VehicleTypeDTO;
import io.api.carrent.infra.repositories.queries.mapper.AliasToDtoMapper;
import io.api.carrent.infra.repositories.queries.sql.VehicleTypeSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VehicleTypeQueryRepository implements IVehicleTypeQueryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @SuppressWarnings({"unchecked"})
    public List<VehicleTypeDTO> findAll() {
        String sql = VehicleTypeSQL.VEHICLE_TYPE_QUERY;

        return entityManager
                .createNativeQuery(sql)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setTupleTransformer(new AliasToDtoMapper<>(VehicleTypeDTO.class))
                .getResultList();
    }
}
