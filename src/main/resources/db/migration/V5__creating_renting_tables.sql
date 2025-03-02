CREATE TABLE vehicle_rents (
	id BIGINT PRIMARY KEY IDENTITY(1, 1),
	vehicle_id BIGINT NOT NULL,
	renter_user_id BIGINT NOT NULL,
	rent_status VARCHAR(20) NOT NULL,
	created_at DATETIME NOT NULL DEFAULT GETUTCDATE(),
	operator_user_id BIGINT NULL,
	withdraw_code VARCHAR(5) NOT NULL,
	withdraw_datetime DATETIME NULL,
	withdraw_max_datetime DATETIME NOT NULL,
	return_code VARCHAR(5) NOT NULL,
	return_datetime DATETIME NULL,
	FOREIGN KEY (vehicle_id) REFERENCES vehicles (id),
	FOREIGN KEY (renter_user_id) REFERENCES users (id),
	FOREIGN KEY (operator_user_id) REFERENCES users (id)
);

CREATE TABLE vehicle_status_historical (
	id BIGINT PRIMARY KEY IDENTITY(1, 1),
	vehicle_id BIGINT NOT NULL,
	renter_user_id BIGINT NULL,
	status VARCHAR(20) NOT NULL,
	updated_at DATETIME NOT NULL DEFAULT GETUTCDATE(),
	FOREIGN KEY (vehicle_id) REFERENCES vehicles (id),
	FOREIGN KEY (renter_user_id) REFERENCES users (id)
);