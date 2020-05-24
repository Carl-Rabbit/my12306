truncate orders cascade;
alter sequence orders_order_id_seq restart with 1;
alter sequence tickets_ticket_id_seq restart with 1;

insert into orders (user_id)
values (1);

insert into tickets (passenger_id, order_id)
values (1, 1);

insert into order_seat (ticket_id, time_detail_id, route_id, seat_id)
values (1, 45934, 28710, 2473493);
insert into order_seat (ticket_id, time_detail_id, route_id, seat_id)
values (1, 45935, 28710, 2473493);

-- sketch

select *
from time_details as td
where train_code = 'G407'; -- 45934, 45935: 2 3 段

select *
from route_schedule as rs
where train_code = 'G407'; -- 28710

select s.* -- all seat
from seats as s
         join trains t on s.train_no = t.train_no
         join route_schedule rs on t.train_no = rs.train_no
    and depart_date = '2020-05-29'::date
where rs.train_code = 'G407'
  and class = 'A'
  and type = 'Z'