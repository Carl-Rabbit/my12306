create table order_seat
(
    ticket_id      int not null,
    time_detail_id int not null,
    route_id       int not null,
    seat_id        int not null
);

-- fk

alter table order_seat
    add foreign key (ticket_id) references tickets (ticket_id);

-- not run for now

alter table order_seat
    add foreign key (time_detail_id) references time_details (time_detail_id);

alter table order_seat
    add foreign key (route_id) references route_schedule (route_id);

alter table order_seat
    add foreign key (seat_id) references seats (seat_id);

-- /not run for now

-- union pk

alter table order_seat
 add primary key (ticket_id, time_detail_id, route_id, seat_id);
