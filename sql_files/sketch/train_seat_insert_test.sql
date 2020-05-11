insert into trains (train_no, train_kind)
values ('1234567890G900123456', 'G');
insert into trains (train_no, train_kind)
values ('1234567890K901123456', 'K');
insert into trains (train_no, train_kind)
values ('12345678900902123456', '');

insert into route_schedule (train_code, train_no, depart_date)
values ('G900', '1234567890G900123456', to_date('9999-01-01', 'yyyy-mm-dd'));

insert into route_schedule (train_code, train_no, depart_date)
values ('K901', '1234567890K901123456', to_date('9999-01-02', 'yyyy-mm-dd'));

insert into route_schedule (train_code, train_no, depart_date)
values ('0902', '12345678900902123456', to_date('9999-01-03', 'yyyy-mm-dd'));

truncate route_schedule cascade;

select *
from route_schedule;


truncate seats cascade;

select *
from seats;

alter sequence seats_seat_id_seq restart with 1;

insert into seats (train_no, carriage, location, code, class, type)
values ('1234567890G900123456', 1, 1, 'A', 'A', 'W');

select *
from seats
where train_no = '1234567890G900123456'
  and class = 'A'
  and type = 'Z'