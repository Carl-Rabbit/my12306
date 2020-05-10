create table tickets
(
    ticket_id    serial primary key,
    entrance     varchar(3) not null,
    ticket_price money      not null,
    passenger_id int        not null,
    order_id     int        not null,
    ticket_kind  char(2)
);

-- fk

alter table tickets
    add foreign key (passenger_id) references passengers (passenger_id)
        on update cascade;

drop index tickets_passenger_id_fk_idx;
create index tickets_passenger_id_fk_idx on tickets (passenger_id);

alter table tickets
    add foreign key (order_id) references orders (order_id)
        on update cascade;

drop index tickets_order_id_fk_idx;
create index tickets_order_id_fk_idx on tickets (order_id);

-- unique

alter table tickets
    add unique (passenger_id, order_id);

-- test

