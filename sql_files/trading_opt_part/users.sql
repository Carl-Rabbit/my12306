create table users
(
    user_id                 serial primary key,
    user_name               varchar(45) not null,
    password                varchar(45) not null,
    phone_no                char(11),
    real_name_certification char        not null default 'N' -- Y / N
);

drop index if exists users_user_name_idx;
create unique index users_user_name_idx on users (user_name);

-- test

truncate users cascade;

insert into users (user_name, password, phone_no)
values ('Jack', '123456', '13511112222');

insert into users (user_name, password, phone_no)
values ('Jim', '123456', '13533334444');