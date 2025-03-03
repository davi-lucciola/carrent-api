package io.api.carrent.infra.repositories.queries.sql;

public class VehicleRentSQL {
    public static final String VEHICLE_RENT_QUERY = """
            SELECT
            	vr.id,
            	v.plate,
            	v.brand,
            	v.model,
            	vt.name vehicleType,
            	vr.rent_status status,
            	renter.name renter,
            	vr.withdraw_datetime withdrawDatetime,
            	vr.return_datetime returnDatetime,
            	w_operator.name operator,
            	r_operator.name returnOperator,
            	vr.withdraw_max_datetime expirationWithdrawDate,
            	vr.created_at createdAt,
            	COUNT(vr.id) OVER() total
            FROM
            	vehicle_rents vr
            	INNER JOIN vehicles v
            		ON vr.vehicle_id = v.id
            	INNER JOIN vehicle_types vt
            		ON v.vehicle_type_id = vt.id
            	INNER JOIN users renter
            		ON vr.renter_user_id = renter.id
            	LEFT JOIN users w_operator
            		ON vr.operator_user_id = w_operator.id
            	LEFT JOIN users r_operator
            		ON vr.return_operator_user_id = r_operator.id
            :where
            ORDER BY
                vr.created_at DESC
            :offset
            """;
}
