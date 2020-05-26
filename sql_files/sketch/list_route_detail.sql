-- input route_code like 'G407'
-- return all station in order

with q as (select station_index,
                  station_name,
                  arrive_time,
                  leave_time,
                  date_part('min', leave_time - arrive_time) as stay_time
           from time_details as td
                    join stations as s on td.station_id = s.station_id
           where train_code = '1148'
           order by station_index)
select station_index,
       station_name,
       arrive_time,
       leave_time,
       case when stay_time = 0 then '---'
           when stay_time < 0 then (stay_time + 60) || ' min'
           else stay_time || ' min'
           end as stay_time
from q;


select date_part('min', '00:10:00'::time - '00:11:00'::time);

select *
from time_details as td
where (date_part('min', leave_time - arrive_time) < 0)