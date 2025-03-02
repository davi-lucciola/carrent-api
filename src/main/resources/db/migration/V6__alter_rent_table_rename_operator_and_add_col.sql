EXEC sp_RENAME 'vehicle_rents.operator_user_id', 'withdraw_operator_user_id', 'COLUMN';

ALTER TABLE vehicle_rents
	ADD return_operator_user_id BIGINT NULL;

ALTER TABLE vehicle_rents
	ADD FOREIGN KEY (return_operator_user_id) REFERENCES users (id);

ALTER TABLE vehicle_status_historical
	ADD rent_id BIGINT NULL;

ALTER TABLE vehicle_status_historical
	ADD FOREIGN KEY (rent_id) REFERENCES vehicle_rents (id);
