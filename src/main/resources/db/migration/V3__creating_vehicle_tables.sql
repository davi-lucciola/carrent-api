CREATE TABLE vehicle_types (
    id INT PRIMARY KEY IDENTITY(1, 1),
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE vehicles (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    plate VARCHAR(7) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    odometer FLOAT NOT NULL,
    vehicle_type_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types (id)
);
