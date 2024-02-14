CREATE TABLE PRICE_HISTORY
(
   id SERIAL PRIMARY KEY,
   before BIGINT not null,
   after BIGINT not null,
   created TIMESTAMP,
   auto_post_id int references auto_post(id)
);