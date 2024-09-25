CREATE TABLE vehicle (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year_of_release INT,
    engine_type VARCHAR(50) NOT NULL,
    displacement NUMERIC,
    transmission_type VARCHAR(50),
    wheel_radius INT,
    registration_number VARCHAR(255) NOT NULL,
    owner_id INT,
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES customer (id)
);