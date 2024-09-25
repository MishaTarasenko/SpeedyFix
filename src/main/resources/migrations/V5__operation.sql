CREATE TABLE operation (
    id SERIAL PRIMARY KEY,
    operation_name VARCHAR(255) NOT NULL,
    operation_description TEXT NOT NULL,
    price NUMERIC
);

CREATE TABLE operation_employee (
    operation_id INT NOT NULL,
    employee_id INT NOT NULL,
    CONSTRAINT fk_operation FOREIGN KEY (operation_id) REFERENCES operation (id),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    PRIMARY KEY (operation_id, employee_id)
);