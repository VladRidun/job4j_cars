create table owner
(
    id    SERIAL PRIMARY KEY,
    name   TEXT,
    user_id  int not null unique references auto_user(id)
);