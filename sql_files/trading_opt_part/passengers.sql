create table passengers
(
    passenger_id serial primary key,
    user_id      int         not null,
    first_name   varchar(45) not null,
    last_name    varchar(45) not null,
    kind         char        not null default 'A', -- A: adult, S: student
    gender       char        not null,             -- M: man, W: woman
    available    char        not null default 'Y',
    id_no        char(18)    not null
--     is_account_owner char        not null default 'N'  -- Y / N
);

-- index on fk

alter table passengers
    add foreign key (user_id) references users (user_id)
        on update cascade;

drop index if exists passengers_user_id_fk_idx;
create index passengers_user_id_fk_idx on passengers (user_id);

-- unique

alter table passengers
    add unique (user_id, kind, id_no);

-- particular index on owner

-- drop index if exists passengers_one_owner_idx;
-- create unique index passengers_one_owner_idx
--     on passengers (user_id, is_account_owner)
--     where is_account_owner = 'Y';

-- test

truncate passengers cascade;

alter sequence passengers_passenger_id_seq restart with 1;

insert into passengers (user_id, first_name, last_name, gender, id_no)
values (1, 'Jack', 'Steven', 'M', '52213120001122007X');

insert into passengers (user_id, first_name, last_name, gender, id_no)
values (1, 'Jane', 'Steven', 'W', '522131200011220082');

insert into passengers (user_id, first_name, last_name, gender, id_no)
values (1, 'Jane', 'Steven', 'W', '522131200011220082');
