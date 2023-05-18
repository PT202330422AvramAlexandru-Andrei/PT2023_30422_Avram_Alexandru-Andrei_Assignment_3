create table product
(
    id       integer generated always as identity,
    name     varchar(30),
    price    double precision,
    quantity integer
);

alter table product
    owner to postgres;

