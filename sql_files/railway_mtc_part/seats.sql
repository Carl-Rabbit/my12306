create table seats
(
    seat_id  bigserial primary key,
    train_no char(20) not null,
    carriage int      not null,
    -- 两位: 排
    location smallint  not null,
    -- 一位定位: A-G 座位型, 1-6 卧铺型
    code     char     not null,
    -- 'A': 商务, 'B': 一等， 'C': 二等
    class    char     not null,
    -- 'Z': 座式, W': 卧式
    type     char     not null
);

-- fk

alter table seats
    add foreign key (train_no) references trains (train_no)
        on update cascade;

drop index if exists seats_train_no_fk_idx;
create index seats_train_no_fk_idx on seats (train_no);

-- unique

create unique index seats_unique_seat_idx
    on seats (train_no, carriage, location, code);