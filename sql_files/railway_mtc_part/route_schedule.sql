create table route_schedule
(
    route_id    bigserial primary key,
    train_code  varchar(20) not null,
    train_no    char(20),
    depart_date date        not null,
    status      char default 'V'        -- V: valid, S: stop running
);

-- fk

-- alter table route_schedule
--     add foreign key (train_code) references time_details (train_code)
--         on update cascade;
--
-- drop index if exists route_schedule_train_code_fk_idx;
-- create index route_schedule_train_code_fk_idx on route_schedule (train_code);


alter table route_schedule
    add foreign key (train_no) references trains (train_no)
        on update cascade;

drop index if exists route_schedule_train_no_fk_idx;
create index route_schedule_train_no_fk_idx on route_schedule (train_no);

-- unique index

create unique index route_schedule_train_date_idx
    on route_schedule (train_code, depart_date);


