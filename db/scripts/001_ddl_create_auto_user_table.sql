create table auto_user
(
    id       SERIAL PRIMARY KEY,
    login     TEXT,
    password  varchar        not null
);