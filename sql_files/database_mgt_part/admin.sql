create table admins
(
    admin_id   serial primary key,
    admin_name varchar(45) not null unique,
    password   varchar(45) not null,
    kind       char        not null default 'A', -- Adult, Robot
    available  char        not null default 'Y'
);

truncate admins;

insert into admins (admin_name, password)
values ('admin', 'admin');