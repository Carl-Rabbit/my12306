create or replace function del_time_details(tr_code varchar, arr integer[])
    returns integer
as
$$
declare
    before_cnt integer;
    after_cnt integer;
begin
    select count(*)
    into before_cnt
    from time_details
    where train_code = tr_code;

    delete
    from time_details
    where train_code = tr_code
      and station_index in (select unnest(arr));

    update time_details as td
    set station_index = q.rnk
    from (select td.time_detail_id, rank() over (order by station_index) as rnk
          from time_details as td
          where train_code = tr_code) q
    where td.time_detail_id = q.time_detail_id;

    select count(*)
    into after_cnt
    from time_details
    where train_code = tr_code;

    return before_cnt - after_cnt;
end;
$$ language plpgsql;

begin;

select *
from time_details as td
where train_code = 'T999';

select del_time_details('T999', array [1, 3]);

rollback;
