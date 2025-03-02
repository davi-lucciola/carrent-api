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
                v.created_at createdAt
            FROM
                vehicles v
                INNER JOIN vehicle_types vt
                    ON v.vehicle_type_id = vt.id
            WHERE 1 = 1
            """.trim();
}
