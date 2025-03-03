package io.api.carrent.infra.repositories.queries.sql;

public class VehicleSQL {
    public static String VEHICLE_QUERY = """
            SELECT
               v.id,
               v.plate,
               v.brand,
               v.model,
               v.year,
               v.odometer,
               (SELECT TOP 1
                   vt.id,
                   vt.name
               FOR JSON PATH, WITHOUT_ARRAY_WRAPPER
               ) vehicleType,
               v.status,
               v.fl_active flActive,
               v.created_at createdAt,
               COUNT(v.id) OVER() total
           FROM
               vehicles v
               INNER JOIN vehicle_types vt
                   ON v.vehicle_type_id = vt.id
           :where
           ORDER BY
               v.created_at
           :offset
           """.trim();

    public static String VEHICLE_STATUS_HISTORY_QUERY = """
            SELECT
            	vsh.id,
            	vsh.status,
            	renter.name renter,
            	vsh.rent_status rentStatus,
            	vsh.updated_at updatedAt,
            	COUNT(vsh.id) OVER() total
            FROM
            	vehicle_status_historical vsh
            	LEFT JOIN vehicle_rents vr
            		ON vsh.rent_id = vr.id
            	LEFT JOIN users renter
            		ON vr.renter_user_id = renter.id
            WHERE
            	vsh.vehicle_id = :vehicleId
            ORDER BY
            	vsh.updated_at DESC
            :offset
            """;
}
