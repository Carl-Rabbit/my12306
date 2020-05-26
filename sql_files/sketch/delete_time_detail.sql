select *
from time_details as td
where train_code = 'T999';

begin;
rollback;

delete from time_details as td
where train_code = 'T999'
  and station_index in (2);

update time_details as td
set station_index = q.rnk
from (select td.time_detail_id, rank() over (order by station_index) as rnk
      from time_details as td
      where train_code = 'T999'
        and station_index not in (2)) q
where td.train_code = 'T999'
  and td.time_detail_id = q.time_detail_id
