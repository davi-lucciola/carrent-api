package io.api.carrent.infra.repositories.queries.sql;

public class VehicleTypeSQL {
    public static final String VEHICLE_TYPE_QUERY = """
            SELECT
                vt.id,
                vt.name
            FROM vehicle_types vt
            """;
}
