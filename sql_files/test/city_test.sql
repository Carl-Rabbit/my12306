select *
from cities
join stations s on cities.city_id = s.city_id
where cities = '深圳市';

with q as (select distinct s.station_id
from cities
join stations s on cities.city_id = s.city_id
join time_details td on s.station_id = td.station_id
where s.station_code is null)
select *
from q
join time_details as td
where td.station_index = 1 or

with q as (select distinct train_code, station_index
from time_details as td
where arrive_time = leave_time)
select train_code, count(*)
from q
group by train_code
having count(*) != 2;

select *
from time_details
where train_code = '4245';

select distinct s.station_name
from stations as s
left join time_details td on s.station_id = td.station_id;



