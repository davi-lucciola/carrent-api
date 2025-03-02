package io.api.carrent.infra.repositories.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleQueryRepository;
import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.infra.repositories.queries.mapper.AliasToDtoMapper;
import io.api.carrent.infra.repositories.queries.sql.VehicleSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VehicleQueryRepository implements IVehicleQueryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @SuppressWarnings({"unchecked"})
    public List<VehicleDTO> findAll(VehicleQueryDTO filter) {
        StringBuilder sql = new StringBuilder(VehicleSQL.VEHICLE_QUERY);

        if (filter.status() != null) {
            sql.append(" AND v.status = :status ");
        }

        if (filter.flActive() != null) {
            sql.append(" AND v.fl_active = :flActive ");
        }

        if (filter.vehicleTypeId() != null) {
            sql.append(" AND v.vehicle_type_id = :vehicleTypeId ");
        }

        if (filter.search() != null) {
            sql.append(" AND ( ");
            sql.append(" v.plate LIKE '%' + :search + '%' ");
            sql.append(" OR v.brand LIKE '%' + :search + '%' ");
            sql.append(" OR v.model LIKE '%' + :search + '%' ");
            sql.append(" ) ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        if (filter.status() != null) {
            query.setParameter("status", filter.status().name());
        }

        if (filter.flActive() != null) {
            query.setParameter("flActive", filter.flActive());
        }

        if (filter.vehicleTypeId() != null) {
            query.setParameter("vehicleTypeId", filter.vehicleTypeId());
        }

        if (filter.search() != null) {
            query.setParameter("search", filter.search());
        }

        return query.unwrap(org.hibernate.query.NativeQuery.class)
                .setTupleTransformer(new AliasToDtoMapper<>(VehicleDTO.class))
                .getResultList();
    }

    @SuppressWarnings({"unchecked"})
    public VehicleDTO findById(Long vehicleId) {
        String sql = VehicleSQL.VEHICLE_QUERY + " AND v.id = :rentId ";

        return (VehicleDTO) entityManager
                .createNativeQuery(sql)
                .setParameter("rentId", vehicleId)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setTupleTransformer(new AliasToDtoMapper<>(VehicleDTO.class))
                .getSingleResult();
    }
}
