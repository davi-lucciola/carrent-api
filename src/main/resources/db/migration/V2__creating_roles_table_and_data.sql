CREATE TABLE roles (
    id INT PRIMARY KEY IDENTITY(1, 1),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

INSERT INTO roles (name, description)
VALUES ('ADMIN', 'Permissão de administrador do sistema.');

CREATE TABLE users_roles (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    role_id INT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);