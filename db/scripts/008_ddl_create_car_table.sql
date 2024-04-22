create table car (
   id SERIAL PRIMARY KEY,
   brand_id int references brand(id),
   model_id int references model(id),
   vinNumber varchar not null,
   colour varchar not null,
   bodyType varchar not null,
   engine varchar not null,
   transmission varchar not null,
   yearProduction int not null,
   mileage bigint not null
);