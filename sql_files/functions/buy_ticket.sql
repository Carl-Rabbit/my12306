-- buy tickets
-- giving:
--  user_id, psg_id[5], train_code[5], from_index[5], to_index[5], d_date[5]
--  price[5], seat_cls[5], seat_type[5], prefer[5](prefer seat code)
-- return:
--  ticket_id table

create or replace function buy_tickets(user_id_ integer,
                                       psg_id integer array,
                                       tr_code varchar array,
                                       from_index integer array,
                                       to_index integer array,
                                       d_date date array,
                                       seat_cls char array,
                                       seat_type char array,
                                       prefer char array)
    returns table
            (
                r_ticket_id integer
            )
as
$$
declare
    len         integer := array_length(psg_id, 1);
    route_id_   integer;
    order_id_   integer;
    ticket_id_  integer;
    seat_rc     record;
    td_id       integer;
    mile        integer;
    st_idx      integer;
    tot_mile    integer;
    prices      numeric(7, 2)[];
    tmp         integer;
begin
    -- create order
    insert into orders (user_id)
    values (user_id_)
    returning order_id into order_id_;

    for i in 1..len
        loop
            -- get route_id_
            select route_id
            into route_id_
            from route_schedule as rs
            where rs.train_code = tr_code[i]
              and depart_date = d_date[i];

            if route_id_ is null then
                r_ticket_id := -2;
                return next;
                continue;
            end if;

            for try in 1..20 -- try 10 times to lock seat
                loop

                    -- get valid seat
                    create local temp table if not exists tmp_seats
                    (
                        seat_id  bigint,
                        train_no char(12),
                        carriage integer,
                        location smallint,
                        code     char,
                        class    char,
                        type     char
                    );
                    truncate tmp_seats;

                    insert into tmp_seats(seat_id, train_no, carriage, location, code, class, type)
                    select *
                    from (with q as (select s.* -- all seat
                                     from seats as s
                                              join trains t on s.train_no = t.train_no
                                              join route_schedule rs on t.train_no = rs.train_no
                                         and depart_date = d_date[i]
                                     where rs.train_code = tr_code[i]
                                       and class = seat_cls[i]
                                       and type = seat_type[i])
                          select *
                          from q
                              except

                          select q.*
                          from q
                                   join order_seat as os
                                        on q.seat_id = os.seat_id
                                            and route_id = route_id_
                                   join time_details td
                                        on os.time_detail_id = td.time_detail_id
                                            and station_index between from_index[i] and to_index[i] - 1) x;

--                     select seat_id into tmp from tmp_seats limit 1;
                    select count(*) into tmp from tmp_seats;

                    if tmp != 0 then

                        -- get prefer seat
                        select *
                        into seat_rc
                        from tmp_seats
                        where code = prefer[i]
                        limit 1;

                        if seat_rc is null then
                            -- get other seat

                            select *
                            into seat_rc
                            from tmp_seats
                            offset floor(random() * tmp) limit 1;
--                             limit 1;

                        end if;

                        -- book this seat

                        begin
                            -- create ticket

                            insert into tickets (passenger_id, order_id)
                            values (psg_id[i], order_id_)
                            returning ticket_id into ticket_id_;

                            -- add to order_seat table
                            for td_id, mile, st_idx in (select time_detail_id, mileage, station_index
                                                        from time_details as td
                                                        where train_code = tr_code[i]
                                                          and station_index between from_index[i] and to_index[i])
                                loop
                                    if st_idx = from_index[i] then
                                        tot_mile := mile;
                                    elseif st_idx = to_index[i] then
                                        tot_mile := mile - tot_mile;
                                    end if;

                                    if st_idx != to_index[i] then -- not add last one
                                        insert into order_seat (ticket_id, time_detail_id, route_id, seat_id)
                                        values (ticket_id_, td_id, route_id_, seat_rc.seat_id);
                                    end if;

                                end loop;

                            -- update tickets

                            prices := cal_prices(substr(tr_code[i], 1, 1),
                                                 mile);

                            update tickets
                            set seat_info    = seat_rc,
                                entrance     = '01' || case
                                                           when seat_rc.carriage <= 8 then 'A'
                                                           else 'B' end,
                                ticket_price = case
                                                   when seat_rc.class = 'A' and seat_rc.type = 'W' then prices[1]
                                                   when seat_rc.class = 'A' and seat_rc.type = 'Z' then prices[2]
                                                   when seat_rc.class = 'B' and seat_rc.type = 'W' then prices[3]
                                                   when seat_rc.class = 'B' and seat_rc.type = 'Z' then prices[4]
                                                   when seat_rc.class = 'C' and seat_rc.type = 'W' then prices[5]
                                                   when seat_rc.class = 'C' and seat_rc.type = 'Z' then prices[6]
                                    end
--                                 ticket_price = 1
                            where ticket_id = ticket_id_;

                            r_ticket_id := ticket_id_;

                        exception
                            when others then
                                r_ticket_id := -1;
                        end;

                    else
                        r_ticket_id := -3;
                    end if;

                    exit when r_ticket_id > 0;

                end loop;

            return next;

        end loop;

exception
    when others then
        return query select -4;
end;
$$ language plpgsql;


-----------------------------
-- sketch
-----------------------------

select floor(random() * 4);

with q as (select s.* -- all seat
           from seats as s
                    join trains t on s.train_no = t.train_no
                    join route_schedule rs on t.train_no = rs.train_no
               and depart_date = '2020-05-29'::date
           where rs.train_code = 'G2924'
             and class = 'A'
             and type = 'Z')
select *
from q
    except

select q.*
from q
         join order_seat as os
              on q.seat_id = os.seat_id
                  and route_id = (select route_id
                                  from route_schedule as rs
                                  where train_code = 'G2924'
                                    and depart_date = '2020-05-29'::date)
         join time_details td
              on os.time_detail_id = td.time_detail_id
                  and station_index between 1 and 5;

select *
from seats
         tablesample system(0.1)
where code = 'A'
limit 1;

select time_detail_id, mileage, station_index
from time_details as td
where train_code = 'G407'
  and station_index between 1 and 3;

create or replace function test_func(arr varchar array) returns varchar
as
$$
declare
    seat_record record;
begin
    select * into seat_record from seats where class = 'z' limit 1;
    return seat_record.train_no;
end;
$$ language plpgsql;

select test_func(array ['1asd','2','3']);

drop function test_func(varchar array);


-----------------------------
-- test
-----------------------------
begin;

select *
from time_details as td
where train_code = 'G2924'

limit 1;

select *
from buy_tickets(1,
                 array [1],
                 array ['G407'],
                 array [1],
                 array [4],
                 array [date'2020-05-29'],
                 array ['B'],
                 array ['N'],
                 array ['F']);

select *
from order_seat
         join seats s on order_seat.seat_id = s.seat_id;

rollback;

create function generate_seat(train_kind char, train_no char(12))
    returns void
as
$$
begin
    -- todo: implement
end;
$$ language plpgsql;