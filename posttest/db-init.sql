-- Drop tables if they exist
DROP TABLE IF EXISTS Users CASCADE;

CREATE TABLE Users
(
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(20),
    create_at timestamp ,
    create_by Integer,
    update_at timestamp ,
    update_by Integer,
    role_id Integer
);

-- -- Initial data
INSERT INTO Users(user_id, username,password,name,role_id)
VALUES (1, 'admin','$2a$10$9ctAdsOY/YvxYkUJPUQKce3c/3yL9uP4LpzzcHgb.Kw.IxRCdrmnC','jirayus',1);

DROP TABLE IF EXISTS Role CASCADE;

CREATE TABLE Role
(
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL
);

-- -- Initial data
INSERT INTO Role(role_id, role_name)
VALUES (1, 'ADMIN'),(2, 'MEMBER');

DROP TABLE IF EXISTS user_tickets CASCADE;

CREATE TABLE user_tickets
(
    order_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL
);

DROP TABLE IF EXISTS ticket CASCADE;

CREATE TABLE ticket
(
    ticket_id SERIAL PRIMARY KEY,
    ticket Integer NOT NULL,
    price Integer NOT NULL,
    amount Integer NOT NULL,
    create_at timestamp,
    create_by Integer
);

DROP TABLE IF EXISTS user_tickets CASCADE;

CREATE TABLE user_tickets
(
    order_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    create_at timestamp
);

DROP TABLE IF EXISTS order_history_mapping CASCADE;

CREATE TABLE order_history_mapping
(
    order_id Integer,
    user_id BIGINT,
    ticket_id Integer,
    create_at timestamp,
    update_at timestamp,
    status_id Integer,
    PRIMARY KEY(order_id, user_id, ticket_id)
);

comment on column order_history_mapping.status_id is 'value 1 = Active , value 2 = Delete';

