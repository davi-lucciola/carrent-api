package io.api.carrent.infra.repositories.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleRentQueryRepository;
import io.api.carrent.domain.dto.input.VehicleRentQueryDTO;
import io.api.carrent.domain.dto.output.VehicleRentDTO;
import io.api.carrent.infra.repositories.queries.mapper.AliasToDtoMapper;
import io.api.carrent.infra.repositories.queries.sql.VehicleRentSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.api.carrent.infra.repositories.queries.sql.SqlConstants.*;

@Repository
@RequiredArgsConstructor
public class VehicleRentQueryRepository implements IVehicleRentQueryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @SuppressWarnings({"unchecked"})
    public List<VehicleRentDTO> findAll(VehicleRentQueryDTO filter) {
        String sql = VehicleRentSQL.VEHICLE_RENT_QUERY;
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");

        if (filter.getStatus() != null) {
            where.append(" AND vr.rent_status = :status ");
        }

        if (filter.getRenterUserId() != null) {
            where.append(" AND vr.renter_user_id = :renterUserId ");
        }

        if (filter.getSearch() != null) {
            where.append(" AND ( ");
            where.append(" v.plate LIKE '%' + :search + '%' ");
            where.append(" OR v.brand LIKE '%' + :search + '%' ");
            where.append(" OR v.model LIKE '%' + :search + '%' ");
            where.append(" OR vt.name LIKE '%' + :search + '%' ");
            where.append(" OR renter.name LIKE '%' + :search + '%' ");
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

        if (filter.getRenterUserId() != null) {
            query.setParameter("renterUserId", filter.getRenterUserId());
        }

        if (filter.getSearch() != null) {
            query.setParameter("search", filter.getSearch());
        }

        return query.unwrap(org.hibernate.query.NativeQuery.class)
                .setTupleTransformer(new AliasToDtoMapper<>(VehicleRentDTO.class))
                .getResultList();
    }
}
