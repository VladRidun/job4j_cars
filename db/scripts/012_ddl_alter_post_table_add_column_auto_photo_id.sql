ALTER TABLE auto_post ADD COLUMN auto_photo_id int not null references auto_photo(id);