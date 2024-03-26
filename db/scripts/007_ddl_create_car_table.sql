create table car
(
   id    SERIAL PRIMARY KEY,
   brand varchar,
   model varchar,
   vinNumber varchar,
   colour varchar,
   bodyType varchar,
   engine_id  int not null unique references engine(id)
);