insert into route_schedule(train_code, train_no, depart_date)
select *, '2020-05-31'::date as depart_date
from route_sc_template;

select * from route_schedule
where depart_date = '2020-05-31'::date
