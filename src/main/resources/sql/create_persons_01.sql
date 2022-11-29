CREATE TABLE persons (
    id bigserial NOT NULL,
    nome varchar(255),
    documento varchar(255),
    telefone varchar(15)
);

alter table persons add constraint person_pk primary key(id);