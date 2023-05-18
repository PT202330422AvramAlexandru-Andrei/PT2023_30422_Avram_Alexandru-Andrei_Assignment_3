create table client
(
    id      serial,
    name    varchar(100),
    address varchar(100),
    email   varchar(100),
    age     integer
);

alter table client
    owner to postgres;

