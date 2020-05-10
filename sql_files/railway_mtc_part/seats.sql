create table seats
(
    seat_id  bigserial primary key,
    train_no char(20) not null,
    carriage int      not null,
        -- 前两位: 排; 后一位定位: A-G 座位型, 1-6 卧铺型
    locate   char(3)      not null,
        -- 'A*': 一等（商务）, 'B*': 二等， 'C*': 三等
        -- '*A': 硬座, '*B': 软座
    type     char(2)  not null
);

-- fk

alter table seats
    add foreign key (train_no) references trains (train_no)
        on update cascade;

drop index if exists seats_train_no_fk_idx;
create index seats_train_no_fk_idx on seats (train_no);