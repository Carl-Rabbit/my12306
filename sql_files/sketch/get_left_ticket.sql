-- input route_code (r_code), start station code (s_st), target station code (t_st), depart date (d_date)
-- return left ticket for each seat type

select s.seat_id -- get all seats on route of that day
from seats as s
         join trains as t
              on s.train_code = t.train_code
         join route_schedule as rs
              on rs.train_id = t.train_id
                  and rs.date = d_date

where route_code = r_code

except

select os.seat_id
from order_seat as os
    join route_schedule as rs
        on rs.



with q as ()
select *
from q
         join order_seat as os
              on q.seat_id = os.seat_id;

select *
from order_seat as os
         join time_details as td
              on os.time_detail_id = td.time_details_id
                  and td.route_code = r_code
where

