create table orders
(
    order_id     serial primary key,
    user_id      int       not null,
    create_time  timestamp not null default now(),
    order_status char      not null default 'N', -- N: not payed, P: payed
    kind         char
);

alter table orders
    add foreign key (user_id) references users (user_id)
        on update cascade;

drop index if exists orders_user_id_fk_idx;
create index orders_user_id_fk_idx on orders (user_id);

-- unique

alter table orders
    add unique (user_id, create_time);