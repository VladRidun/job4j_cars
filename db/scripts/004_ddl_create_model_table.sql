create table model
(
    id    SERIAL PRIMARY KEY,
    name   varchar not null,
    brand_id    int     NOT NULL    REFERENCES brand(id)
);