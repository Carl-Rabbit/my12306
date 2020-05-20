create table time_details_1
(
    time_detail_id serial primary key,
    train_code     varchar(20) not null,
    station_id     int         not null,
    station_index  int         not null,
    arrive_time    time,
    leave_time     time,
    runtime        interval    not null,
    mileage        int         not null
);

-- fk

alter table time_details
    add foreign key (station_id) references stations (station_id)
        on update cascade;

drop index if exists time_details_station_id_fk_idx;
create index time_details_station_id_fk_idx on time_details (station_id);

-- unique index

create unique index time_details_train_station_idx
    on time_details (train_code, station_index);
