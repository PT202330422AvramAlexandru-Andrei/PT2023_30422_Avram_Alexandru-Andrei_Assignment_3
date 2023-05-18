create table orders
(
    id         integer generated always as identity,
    client_id  integer,
    product_id integer,
    total      double precision,
    quantity   integer
);

alter table orders
    owner to postgres;

