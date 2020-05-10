create table cities
(
    city_id  serial primary key,
    name_ch  varchar(45) not null unique,
    name_en  varchar(45) not null unique,
    province varchar(10)
);