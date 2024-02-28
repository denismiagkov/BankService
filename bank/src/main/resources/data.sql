DROP TABLE IF EXISTS users CASCADE;
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

INSERT INTO users (first_name, patronymic, last_name, birth_date, phone, email, login, password)
VALUES (
           'Petr', 'Andreevich', 'Smirnov', '1977-10-23', '+71234567890', 'petr@yandex.ru', 'user1', '123'
       );

INSERT INTO users (first_name, patronymic, last_name, birth_date, phone, email, login, password)
VALUES (
           'Elena', 'Olegovna', 'Orehova', '2002-07-15', '+773651947264', 'orehova@yandex.ru', 'user2', '456'
       );