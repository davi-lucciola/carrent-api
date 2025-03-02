ALTER TABLE vehicle_status_historical
    DROP CONSTRAINT FK__vehicle_s__rente__778AC167;

ALTER TABLE vehicle_status_historical
    DROP COLUMN renter_user_id;