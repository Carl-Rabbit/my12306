-- input start station code (s_st), target station code (t_st), depart date (d_date)
-- return all possible route_code
-- when use, get start station code first by city (replace two station code by subselect)

with q as (select * -- get all time_details satisfy station code and date
           from time_details as td
                    join route_schedule as rs
                         on td.train_code = rs.train_code
           where depart_date = '2020-5-29'::date
             and (station_code = s_st or station_code = t_st))
select *
from q as q1
         join q as q2
              on q1.route_code = q2.route_code -- same route
                  and q1.station_code = s_st
                  and q2.station_code = t_st -- contain two station
                  and q1.station_index < q2.station_index -- correct direction
