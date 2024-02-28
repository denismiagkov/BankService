DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;

CREATE TABLE users (
                       id SERIAL PRIMARY KEY ,
                       first_name varchar(45) NOT NULL ,
                       patronymic varchar(45) NOT NULL ,
                       last_name varchar(45) NOT NULL ,
                       birth_date timestamp NOT NULL ,
                       phone varchar(15) NOT NULL UNIQUE ,
                       email varchar(45) NOT NULL UNIQUE ,
                       login varchar(45) NOT NULL UNIQUE ,
                       password varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
                                        id SERIAL PRIMARY KEY ,
                                        user_id BIGINT NOT NULL UNIQUE references users(id),
                                        number varchar(45) NOT NULL ,
                                        balance numeric NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
                                            id SERIAL PRIMARY KEY ,
                                            account_id BIGINT UNIQUE references accounts(id),
                                            date timestamp NOT NULL ,
                                            type varchar(45) NOT NULL ,
                                            amount numeric NOT NULL
);

INSERT INTO users (first_name, patronymic, last_name, birth_date, phone, email, login, password)
VALUES (
           'Petr', 'Andreevich', 'Smirnov', '1977-10-23', '+71234567890', 'petr@yandex.ru', 'user1', '123'
       );

INSERT INTO users (first_name, patronymic, last_name, birth_date, phone, email, login, password)
VALUES (
           'Elena', 'Olegovna', 'Orehova', '2002-07-15', '+773651947264', 'orehova@yandex.ru', 'user2', '456'
       );