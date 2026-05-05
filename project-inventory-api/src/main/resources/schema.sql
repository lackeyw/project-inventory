CREATE TABLE IF NOT EXISTS pantry (
    id SERIAL PRIMARY KEY ,
    name varchar(255) NOT NULL UNIQUE,
    quantity int NULL,
    date_added date NOT NULL UNIQUE,
    expiration_date date NULL
);

CREATE TABLE IF NOT EXISTS fridge (
    id SERIAL PRIMARY KEY ,
    name varchar(255) NOT NULL UNIQUE,
    quantity int NULL,
    date_added date NOT NULL UNIQUE,
    expiration_date date NULL
);

CREATE TABLE IF NOT EXISTS freezer (
    id SERIAL PRIMARY KEY ,
    name varchar(255) NOT NULL UNIQUE,
    quantity int NULL,
    date_added date NOT NULL UNIQUE,
    expiration_date date NULL
);

CREATE TABLE IF NOT EXISTS shopping_list (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE,
    quantity int NULL
);