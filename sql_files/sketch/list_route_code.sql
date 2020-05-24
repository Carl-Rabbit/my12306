-- input start station code (s_st), target station code (t_st), depart date (d_date)
-- return all possible route_code
-- when use, get start station code first by city (replace two station code by subselect)

create local temp table if not exists tmp_from_st
(
    id integer
);
create local temp table if not exists tmp_to_st
(
    id integer
);

truncate tmp_from_st;

insert into tmp_from_st
select station_id
from cities
         join stations s on cities.city_id = s.city_id
where name like '%' || '贵阳' || '%';

truncate tmp_to_st;

insert into tmp_to_st
select station_id
from cities
         join stations s on cities.city_id = s.city_id
where name like '%' || '深圳' || '%';

select *
from tmp_from_st;
select *
from tmp_to_st;

with q as (select td.*,
                  rs.status -- get all time_details satisfy station code and date
           from time_details as td
                    join route_schedule as rs
                         on td.train_code = rs.train_code
           where rs.depart_date = '2020-05-29'::date
             and (station_id in (select * from tmp_from_st)
               or station_id in (select * from tmp_to_st)))
select q1.train_code,
       s1.station_name,
       s2.station_name,
       q1.leave_time,
       q2.arrive_time,
       (q2.runtime - q1.runtime)    as runtime,
       abs(q2.mileage - q1.mileage) as mileage,
       '2020-05-29'::date,
       q1.status
from q as q1
         join q as q2
              on q1.train_code = q2.train_code -- same route
                  and q1.station_id in (select * from tmp_from_st)
                  and q2.station_id in (select * from tmp_to_st) -- contain two station
                  and q1.station_index < q2.station_index -- correct direction)
         join stations as s1 on q1.station_id = s1.station_id
         join stations as s2 on q2.station_id = s2.station_id

select time_details.*, s.station_name
from time_details
         join stations s on time_details.station_id = s.station_id
where train_code = '5645'
order by station_index;

select *
from stations as s
where station_id in (4022, 4223);

select train_code,
       station_index,
       runtime,
       case
           when substr(train_code, 1, 1) in ('C', 'D', 'G', 'P', 'S', 'Y') then
               round((EXTRACT(epoch FROM (runtime)) / 24))
           else round((EXTRACT(epoch FROM (runtime)) / 60))
           end as mileage
from time_details;

begin;
update time_details
set mileage = case
                  when substr(train_code, 1, 1) in ('C', 'D', 'G', 'P', 'S', 'Y') then
                      round((EXTRACT(epoch FROM (runtime)) / 28))
                  else round((EXTRACT(epoch FROM (runtime)) / 65))
    end;
rollback;
select * from time_details
where train_code like 'G407';
