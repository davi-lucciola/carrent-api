package io.api.carrent.infra.repositories.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleQueryRepository;
import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.input.VehicleStatusQueryDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.dto.output.VehicleStatusDTO;
import io.api.carrent.infra.repositories.queries.mapper.AliasToDtoMapper;
import io.api.carrent.infra.repositories.queries.sql.VehicleSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.api.carrent.infra.repositories.queries.sql.SqlConstants.*;

@Repository
@RequiredArgsConstructor
public class VehicleQueryRepository implements IVehicleQueryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @SuppressWarnings({"unchecked"})
    public List<VehicleDTO> findAll(VehicleQueryDTO filter) {
        String sql = VehicleSQL.VEHICLE_QUERY;
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");

        if (filter.getStatus() != null) {
            where.append(" AND v.status = :status ");
        }

        if (filter.getFlActive() != null) {
            where.append(" AND v.fl_active = :flActive ");
        }

        if (filter.getVehicleTypeId() != null) {
            where.append(" AND v.vehicle_type_id = :vehicleTypeId ");
        }

        if (filter.getSearch() != null) {
            where.append(" AND ( ");
            where.append(" v.plate LIKE '%' + :search + '%' ");
            where.append(" OR v.brand LIKE '%' + :search + '%' ");
            where.append(" OR v.model LIKE '%' + :search + '%' ");
            where.append(" ) ");
        }

        sql = sql.replace(WHERE_VAR, where.toString()).replace(OFFSET_VAR, OFFSET_SQL);

        Query query = entityManager
                .createNativeQuery(sql)
                .setParameter("page", filter.getPage())
                .setParameter("perPage", filter.getPerPage());

        if (filter.getStatus() != null) {
            query.setParameter("status", filter.getStatus().name());
        }

        if (filter.getFlActive() != null) {
            query.setParameter("flActive", filter.getFlActive());
        }

        if (filter.getVehicleTypeId() != null) {
            query.setParameter("vehicleTypeId", filter.getVehicleTypeId());
        }

        if (filter.getSearch() != null) {
            query.setParameter("search", filter.getSearch());
        }

        return query.unwrap(org.hibernate.query.NativeQuery.class)
                .setTupleTransformer(new AliasToDtoMapper<>(VehicleDTO.class))
                .getResultList();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public VehicleDTO findById(Long vehicleId) {
        String sql = VehicleSQL.VEHICLE_QUERY
                .replace(OFFSET_VAR, "")
                .replace(WHERE_VAR, " AND v.id = :vehicleId ");

        return (VehicleDTO) entityManager
                .createNativeQuery(sql)
                .setParameter("vehicleId", vehicleId)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setTupleTransformer(new AliasToDtoMapper<>(VehicleDTO.class))
                .getSingleResult();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public List<VehicleStatusDTO> findAllStatusHistory(VehicleStatusQueryDTO filter) {
        String sql = VehicleSQL.VEHICLE_STATUS_HISTORY_QUERY.replace(OFFSET_VAR, OFFSET_SQL);

        return entityManager
                .createNativeQuery(sql)
                .setParameter("vehicleId", filter.getVehicleId())
                .setParameter("page", filter.getPage())
                .setParameter("perPage", filter.getPerPage())
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setTupleTransformer(new AliasToDtoMapper<>(VehicleStatusDTO.class))
                .getResultList();
    }
}
