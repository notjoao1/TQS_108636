CREATE TABLE cars (
    id BIGSERIAL PRIMARY KEY,
    maker varchar(255) not null,
    model varchar(255) not null
);


INSERT INTO cars (maker, model)
    VALUES ('Renault', 'Megane');
INSERT INTO cars (maker, model)
    VALUES ('Nissan', '350Z');
INSERT INTO cars (maker, model)
    VALUES ('Renault', 'Clio');