insert into route_schedule (train_code, train_no, depart_date)
values ('G900', '1234567890G900123456', to_date('9999-01-01', 'yyyy-mm-dd'));

insert into route_schedule (train_code, train_no, depart_date)
values ('K901', '1234567890K901123456', to_date('9999-01-02', 'yyyy-mm-dd'));

insert into route_schedule (train_code, train_no, depart_date)
values ('0902', '12345678900902123456', to_date('9999-01-03', 'yyyy-mm-dd'));

truncate route_schedule;

select *
from route_schedule;