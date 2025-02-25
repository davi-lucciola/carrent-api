ALTER TABLE vehicles
    ADD status VARCHAR(20) NOT NULL, fl_active BIT NOT NULL DEFAULT 1;

ALTER TABLE vehicles
    ADD CONSTRAINT unq_plate UNIQUE (plate);
