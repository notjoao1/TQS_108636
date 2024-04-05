CREATE TABLE Books (
  id BIGSERIAL PRIMARY KEY,
  title varchar(255) not null
);


INSERT INTO Books (title)
    VALUES ('Lord of the Rings');

INSERT INTO Books (title)
    VALUES ('Lol');