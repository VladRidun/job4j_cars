create table auto_user
(
    id       SERIAL PRIMARY KEY,
    login     TEXT not null unique,
    password  varchar        not null
);