--- We want clean data to test
DROP TABLE APPLE;
CREATE TABLE APPLE (
    id serial primary key,
    name varchar(50) not null
);

--- We want clean data to test
DROP TABLE MANGO;
CREATE TABLE MANGO (
    id serial primary key,
    name varchar(50) not null
);

--- We want clean data to test
DROP TABLE BANANA;
CREATE TABLE BANANA (
    id serial primary key,
    name varchar(50) not null
);
