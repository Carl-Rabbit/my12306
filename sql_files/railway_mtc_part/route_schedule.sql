create table route_schedule
(
    route_id    bigserial primary key,
    train_code  varchar(20) not null,
    train_no    char(20),
    depart_date date        not null,
    status      char default 'V'        -- V: valid, S: stop running
);

-- fk

alter table route_schedule
    add foreign key (train_code) references trains (train_no)
        on update cascade;

drop index if exists seats_train_no_fk_idx;
create index seats_train_no_fk_idx on seats (train_no);