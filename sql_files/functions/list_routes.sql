select *
from cities as c
         join stations s on c.city_id = s.city_id
where name = '深圳';

create or replace function list_routes(from_str varchar,
                                       to_str varchar,
                                       dpt_date date)
    returns table
            (
                train_code_   varchar,
                from_station_ varchar,
                to_station_   varchar,
                from_time_    time,
                to_time_      time,
                runtime_      interval,
                mileage_      integer,
                depart_date_  date,
                status_       char,
                aw_rest       integer,
                az_rest       integer,
                bw_rest       integer,
                bz_rest       integer,
                cw_rest       integer,
                cz_rest       integer,
                aw_price      numeric(7, 2),
                az_price      numeric(7, 2),
                bw_price      numeric(7, 2),
                bz_price      numeric(7, 2),
                cw_price      numeric(7, 2),
                cz_price      numeric(7, 2)
            )
as
$$
declare
    tmp_r     record;
    rest_arr  integer[];
    price_arr numeric(7, 2)[];
begin
    -- find station code
    drop table if exists tmp_from_st;
    create local temp table tmp_from_st
    (
        id integer
    );
    drop table if exists tmp_to_st;
    create local temp table tmp_to_st
    (
        id integer
    );

--     insert into tmp_from_st
--     select station_id
--     from cities as c
--              join stations s on c.city_id = s.city_id
--     where c.name like '%' || from_str || '%'
--        or s.station_name like '%' || from_str || '%';
--
--     insert into tmp_to_st
--     select station_id
--     from cities as c
--              join stations s on c.city_id = s.city_id
--     where name like '%' || to_str || '%'
--        or s.station_name like '%' || to_str || '%';

    insert into tmp_from_st
    select station_id
    from stations as s
             join cities c on s.city_id = c.city_id
    where s.city_id in (select city_id
                        from stations as s
                        where s.station_name like '%' || from_str || '%')
       or c.name like '%' || from_str || '%';

    insert into tmp_to_st
    select station_id
    from stations as s
             join cities c on s.city_id = c.city_id
    where s.city_id in (select city_id
                        from stations as s
                        where s.station_name like '%' || to_str || '%')
       or c.name like '%' || to_str || '%';

    for tmp_r in (with q as (select td.*,     -- tmp_r: the record of train satisfied
                                    rs.status -- get all time_details satisfy station code and date
                             from time_details as td
                                      join route_schedule as rs
                                           on td.train_code = rs.train_code
                             where rs.depart_date = dpt_date
                               and (station_id in (select * from tmp_from_st)
                                 or station_id in (select * from tmp_to_st)))
                  select q1.train_code             as train_code,
                         s1.station_name           as from_station,
                         s2.station_name           as to_station,
                         q1.station_index          as from_index,
                         q2.station_index          as to_index,
                         q1.leave_time             as from_time,
                         q2.arrive_time            as to_time,
                         (q2.runtime - q1.runtime) as runtime,
                         (q2.mileage - q1.mileage) as mileage,
                         dpt_date                  as depart_date,
                         q1.status                 as status
                  from q as q1
                           join q as q2
                                on q1.train_code = q2.train_code -- same route
                                    and q1.station_id in (select * from tmp_from_st)
                                    and q2.station_id in (select * from tmp_to_st) -- contain two station
                                    and q1.station_index < q2.station_index -- correct direction)
                           join stations as s1 on q1.station_id = s1.station_id
                           join stations as s2 on q2.station_id = s2.station_id
                  order by runtime
    )
        loop
            train_code_ := tmp_r.train_code;
            from_station_ := tmp_r.from_station;
            to_station_ := tmp_r.to_station;
            from_time_ := tmp_r.from_time;
            to_time_ := tmp_r.to_time;
            runtime_ := tmp_r.runtime;
            mileage_ := tmp_r.mileage;
            depart_date_ := tmp_r.depart_date;
            status_ := tmp_r.status;

            -- fill rest ticket
            select array_agg(rest)
            from (with template as (
                select 'A' as class, 'W' as type
                union all
                select 'A', 'Z'
                union all
                select 'B', 'W'
                union all
                select 'B', 'Z'
                union all
                select 'C', 'W'
                union all
                select 'C', 'Z'
            )
                  select t.class,
                         t.type,
                         coalesce(x2.rest, 0) as rest -- left join all seat type
                  into rest_arr
                  from template as t
                           left join
                       (select class,
                               type,
                               count(*) as rest -- get partial statistics
                        from (with q as (select s.* -- all seat
                                         from seats as s
                                                  join trains t on s.train_no = t.train_no
                                                  join route_schedule rs on t.train_no = rs.train_no
                                             and depart_date = tmp_r.depart_date
                                         where rs.train_code = tmp_r.train_code)
                              select *
                              from q
                                  except

                              select q.*
                              from q
                                       join order_seat as os
                                            on q.seat_id = os.seat_id
                                                and route_id = (select route_id
                                                                from route_schedule as rs
                                                                where train_code = tmp_r.train_code
                                                                  and depart_date = tmp_r.depart_date)
                                       join time_details td
                                            on os.time_detail_id = td.time_detail_id
                                                and station_index between tmp_r.from_index
                                                   and tmp_r.to_index - 1) x        -- important !
                        group by class, type) x2
                       on t.class = x2.class and t.type = x2.type
                  order by class, type) rest_tickes;

            aw_rest := rest_arr[1];
            az_rest := rest_arr[2];
            bw_rest := rest_arr[3];
            bz_rest := rest_arr[4];
            cw_rest := rest_arr[5];
            cz_rest := rest_arr[6];

            -- fill prices

            price_arr := cal_prices(substr(tmp_r.train_code, 1, 1),
                                    tmp_r.mileage);
            aw_price := price_arr[1];
            az_price := price_arr[2];
            bw_price := price_arr[3];
            bz_price := price_arr[4];
            cw_price := price_arr[5];
            cz_price := price_arr[6];

            return next;
        end loop;

end;
$$ language plpgsql;

-------------------
-- sketch
-------------------

-- get rest ticket
select array_agg(rest)
from (with template as (
    select 'A' as class, 'W' as type
    union all
    select 'A', 'Z'
    union all
    select 'B', 'W'
    union all
    select 'B', 'Z'
    union all
    select 'C', 'W'
    union all
    select 'C', 'Z'
)
      select t.class,
             t.type,
             coalesce(x2.rest, -1) as rest -- left join all seat type
      from template as t
               left join
           (select class,
                   type,
                   count(*) as rest -- get partial statistics
            from (with q as (select s.* -- all seat
                             from seats as s
                                      join trains t on s.train_no = t.train_no
                                      join route_schedule rs on t.train_no = rs.train_no
                                 and depart_date = '2020-05-29'::date
                             where rs.train_code = 'G410')
                  select *
                  from q
                      except

                  select q.*
                  from q
                           join order_seat as os
                                on q.seat_id = os.seat_id
                                    and route_id = (select route_id
                                                    from route_schedule as rs
                                                    where train_code = 'G410'
                                                      and depart_date = '2020-05-29'::date)
                           join time_details td
                                on os.time_detail_id = td.time_detail_id
                                    and station_index between 1 and 4 - 1) x
            group by class, type) x2
           on t.class = x2.class and t.type = x2.type
      order by class, type) rest_tickes;

select station_id
from cities as c
         join stations s on c.city_id = s.city_id
where c.name like '%' || '福田' || '%'
   or s.station_name like '%' || '福田' || '%';

select *
from stations as s
where s.city_id in (select city_id
                    from stations as s
                    where s.station_name like '%贵阳%');

-------------------
-- test
-------------------

select *
from list_routes('白云北', '福田', '2020-05-29'::date);
select *
from list_routes('福田', '贵阳', '2020-05-29'::date);
