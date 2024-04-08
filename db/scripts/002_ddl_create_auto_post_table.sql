create table auto_post
(
    id            serial primary key,
    title   varchar not null,
    description   varchar not null,
    created       TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    price BIGINT not null,
    active BOOLEAN,
    auto_user_id  int references auto_user(id),
    car_id int not null references car(id),
    auto_photo_id int references auto_photo(id)
);