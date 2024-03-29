DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS user_phone_numbers CASCADE;
DROP TABLE IF EXISTS user_emails CASCADE;


CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name varchar(45)  NOT NULL,
    patronymic varchar(45)  NOT NULL,
    last_name  varchar(45)  NOT NULL,
    birth_date timestamp    NOT NULL,
    login      varchar(45)  NOT NULL UNIQUE,
    password   varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts
(
    id              SERIAL PRIMARY KEY,
    user_id         BIGINT      NOT NULL UNIQUE references users (id),
    number          varchar(45) NOT NULL,
    balance         numeric     NOT NULL,
    opened_at       TIMESTAMP   NOT NULL,
    initial_deposit NUMERIC     NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions
(
    id         SERIAL PRIMARY KEY,
    account_id BIGINT references accounts (id),
    date       timestamp   NOT NULL,
    type       varchar(45) NOT NULL,
    amount     numeric     NOT NULL
);

CREATE TABLE IF NOT EXISTS user_phone_numbers
(
    user_id bigint references users (id),
    phone   varchar(15) UNIQUE,
    PRIMARY KEY (user_id, phone)
);

CREATE TABLE IF NOT EXISTS user_emails
(
    user_id bigint references users (id),
    email   varchar(45) UNIQUE,
    PRIMARY KEY (user_id, email)
);

INSERT INTO users (first_name, patronymic, last_name, birth_date, login, password)
VALUES ('Petr', 'Andreevich', 'Smirnov', '1977-10-23', 'user1',
        '$2a$10$N2reNKAAoPb.KLLbR3LMRuF9uOFZuqa96cHcrERskk.PZOoF7S.2S');
-- password=123

INSERT INTO users (first_name, patronymic, last_name, birth_date, login, password)
VALUES ('Olga', 'Igorevna', 'Orehova', '2002-07-15', 'user2',
        '$2a$10$C5NbeCX6UQ9DK0jp3UUV8.et/UU9KXFvbfPJWiWfg/csfCt99WkIW');
-- password=456

INSERT INTO users (first_name, patronymic, last_name, birth_date, login, password)
VALUES ('Oleg', 'Petrovich', 'Zaharov', '1981-10-13', 'user3',
        '$2a$10$pThZjqVy05zhBwlIoJHg3eqIgZJUgn.ISIFl4aGtx8gV5PEkkRak6');
-- password=789

INSERT INTO accounts (user_id, number, balance, opened_at, initial_deposit)
VALUES (1, '1234567890', 50000, '2024-03-01 15:12:51', 4500);

INSERT INTO accounts (user_id, number, balance, opened_at, initial_deposit)
VALUES (2, '0987654321', 75000, '2024-03-01 18:34:23', 75000);

INSERT INTO accounts (user_id, number, balance, opened_at, initial_deposit)
VALUES (3, '2143658709', 30000, '2024-03-02 10:32:44', 25000);

INSERT INTO user_emails VALUES (1, 'smirnov@yandex.ru');
INSERT INTO user_emails VALUES (2, 'orehova@mail.ru');
INSERT INTO user_emails VALUES (2, 'orehova@yandex.ru');
INSERT INTO user_emails VALUES (3, 'zaharov@yandex.ru');

INSERT INTO user_phone_numbers VALUES (1, '+79162341234');
INSERT INTO user_phone_numbers VALUES (1, '+79132342934');
INSERT INTO user_phone_numbers VALUES (2, '+79233234234');
INSERT INTO user_phone_numbers VALUES (3, '+79292390834');
