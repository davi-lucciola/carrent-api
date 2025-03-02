EXEC sp_RENAME 'vehicle_rents.withdraw_operator_user_id', 'operator_user_id', 'COLUMN';

ALTER TABLE vehicle_rents
    ADD cancellation_reason VARCHAR(255) NULL;