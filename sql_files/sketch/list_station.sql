-- input route_code like 'G207'
-- return all station in order

select station_name
from time_details as td
    join stations as s on td.station_code = s.station_code
where route_code = 'G207'
order by station_index;