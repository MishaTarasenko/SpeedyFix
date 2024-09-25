CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_info (id)
);