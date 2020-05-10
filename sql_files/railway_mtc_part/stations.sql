create table stations
(
    station_id serial primary key,
    name_ch    varchar(45) not null,
    name_en    varchar(45) not null,
    logo       char(3)     not null,
    city_id    int         not null
);

-- unique index

create unique index stations_name_ch_idx on stations (name_ch);
create unique index stations_name_zn_idx on stations (name_en);

-- index

create index stations_logo_idx on stations (logo);

-- fk

alter table stations
    add foreign key (city_id) references cities (city_id)
        on update cascade;

drop index if exists stations_city_id_fk_idx;
create index stations_city_id_fk_idx on stations (city_id);