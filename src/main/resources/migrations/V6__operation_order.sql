CREATE TABLE operation_order (
    id SERIAL PRIMARY KEY,
    order_status VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    vehicle_id INT,
    customer_id INT,
    CONSTRAINT fk_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle (id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE operation_order_employee (
    operation_order_id INT NOT NULL,
    employee_id INT NOT NULL,
    CONSTRAINT fk_operation_order FOREIGN KEY (operation_order_id) REFERENCES operation_order (id),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    PRIMARY KEY (operation_order_id, employee_id)
);

CREATE TABLE operation_order_operation (
    operation_order_id INT NOT NULL,
    operation_id INT NOT NULL,
    CONSTRAINT fk_operation_order FOREIGN KEY (operation_order_id) REFERENCES operation_order (id),
    CONSTRAINT fk_operation FOREIGN KEY (operation_id) REFERENCES operation (id),
    PRIMARY KEY (operation_order_id, operation_id)
);