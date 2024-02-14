create table car
(
    id    SERIAL PRIMARY KEY,
    name   TEXT,
    engine_id  int not null unique references engine(id)
);