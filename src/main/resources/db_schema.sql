DROP database IF EXISTS cash_register;

CREATE database cash_register;

USE cash_register;

CREATE TABLE users
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    login    VARCHAR(45) UNIQUE,
    passwd   VARCHAR(45),
    roles_id INT
);

create index fk_users_id_idx
    on users (id);

create index fk_users_login_idx
    on users (login);

CREATE TABLE roles
(
    id         INT         NOT NULL PRIMARY KEY,
    roles_name VARCHAR(45) NOT NULL
);

create index fk_roles_id_idx
    on roles (id);

CREATE TABLE goods
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    goods_name VARCHAR(150) NOT NULL UNIQUE,
    weight     BOOLEAN,
    scancode   VARCHAR(20)  NOT NULL UNIQUE
);

create index fk_goods_id_idx
    on goods (id);

create index fk_goods_goods_name_idx
    on goods (goods_name);

create index fk_goods_scancode_idx
    on goods (scancode);

CREATE TABLE warehouses
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    warehouses_name VARCHAR(45) NOT NULL UNIQUE
);

create index fk_warehouses_id_idx
    on warehouses (id);

CREATE TABLE goods_amount
(
    good_id      INT NOT NULL REFERENCES goods (id) on delete cascade,
    warehouse_id INT NOT NULL REFERENCES warehouses (id) on delete cascade,
    amount       DOUBLE(15, 3),
    constraint goods_count_pkey
        primary key (good_id, warehouse_id)
);

create index fk_goods_amount_good_id_idx
    on goods_amount (good_id);

create index fk_goods_amount_warehouse_id_idx
    on goods_amount (warehouse_id);

CREATE TABLE goods_price
(
    good_id INT NOT NULL PRIMARY KEY REFERENCES goods (id) on delete cascade,
    price   DOUBLE(15, 2)
);

create index fk_goods_price_good_id_idx
    on goods_price (good_id);

CREATE TABLE checkout_shift
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    checkout_date TIMESTAMP,
    closed              BOOLEAN,
    warehouse_id        INT,
    user_id             INT
);

CREATE TABLE orders
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    checkout_id INT NOT NULL,
    warehouses_id     INT NOT NULL,
    order_date        TIMESTAMP,
    closed            BOOLEAN,
    user_id           INT
);

create index fk_orders_id_idx
    on orders (id);

CREATE TABLE order_goods
(
    order_id INT           NOT NULL REFERENCES orders (id) on delete cascade,
    good_id  INT           NOT NULL REFERENCES goods (id) on delete cascade,
    amount   DOUBLE(15, 3) NOT NULL,
    price    DOUBLE(15, 2) NOT NULL,
    total    DOUBLE(15, 3) NOT NULL,
    constraint order_goods_pkey
        primary key (order_id, good_id)
);

create index fk_order_goods_orders_id_idx
    on order_goods (order_id);

create index fk_order_goods_good_id_idx
    on order_goods (good_id);

CREATE TABLE z_reports
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    check_amount int,
    check_sum    DOUBLE(15, 2),
    user_id      INT NOT NULL REFERENCES checkout_shift (id) on delete cascade,
    checkout_id  INT NOT NULL REFERENCES checkout_shift (id) on delete cascade
);

INSERT INTO roles (id, roles_name)
VALUES (1, 'cashier');
INSERT INTO roles (id, roles_name)
VALUES (2, 'senior_cashier');
INSERT INTO roles (id, roles_name)
VALUES (3, 'commodity_expert');
INSERT INTO roles (id, roles_name)
VALUES (4, 'admin');

INSERT INTO users (login, passwd, roles_id)
VALUES ('cashier', '1', 1);
INSERT INTO users (login, passwd, roles_id)
VALUES ('boss', '2', 2);
INSERT INTO users (login, passwd, roles_id)
VALUES ('warehouse', '2', 3);
INSERT INTO users (login, passwd, roles_id)
VALUES ('admin', 'admin', 4);

INSERT INTO warehouses (id, warehouses_name)
VALUES (1, 'main');