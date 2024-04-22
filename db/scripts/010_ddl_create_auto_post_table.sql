create table auto_post
(
    id            serial primary key,
    title   varchar not null,
    description   varchar not null,
    created       TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    price BIGINT not null,
    sold BOOLEAN DEFAULT FALSE,
    auto_user_id  int references auto_user(id),
    car_id int references car(id),
    auto_photo_id int references auto_photo(id)
);