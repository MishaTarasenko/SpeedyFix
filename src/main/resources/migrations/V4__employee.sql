CREATE TABLE employee
(
    id       SERIAL PRIMARY KEY,
    position VARCHAR(1000) NOT NULL,
    type     VARCHAR(50)   NOT NULL,
    user_id  INT           NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_info
);