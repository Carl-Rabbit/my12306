create table users
(
    user_id                 serial primary key,
    user_name               varchar(45) not null,
    password                varchar(45) not null,
    phone_no                char(11),
    kind                    char        not null default 'A', -- Adult, Child
    available               char        not null default 'Y',
    real_name_certification char        not null default 'N'  -- Y / N
);

drop index if exists users_user_name_idx;
create unique index users_user_name_idx on users (user_name);

alter table users
    add constraint users_kind_cons check (kind in ('A', 'C'));
alter table users
    add constraint users_available_cons check (available in ('Y', 'N'));
alter table users
    add constraint users_rnc_cons check (real_name_certification in ('Y', 'N'));

-- test

truncate users cascade;

alter sequence users_user_id_seq restart with 1;

insert into users (user_name, password, phone_no)
values ('Jack', '123456', '13511112222');

insert into users (user_name, password, phone_no)
values ('Jim', '123456', '13533334444');