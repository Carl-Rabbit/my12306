-- reset user part

truncate users cascade;
truncate passengers cascade;
truncate orders cascade;
truncate tickets cascade;

alter sequence users_user_id_seq restart with 1;
alter sequence passengers_passenger_id_seq restart with 1;
alter sequence orders_order_id_seq restart with 1;
alter sequence tickets_ticket_id_seq restart with 1;

insert into users (user_name, password, phone_no)
values ('CarlRabbit', '123456', '13511112222');
insert into users (user_name, password, phone_no)
values ('LittleWhiteCat', '654321', '13533334444');

insert into passengers (user_id, first_name, last_name, gender, id_no)
values (1, 'Carl', 'Rabbit', 'M', '52237220001122007X');
insert into passengers (user_id, first_name, last_name, gender, id_no)
values (1, '地', '御子', 'W', '522442200011220082');
insert into passengers (user_id, first_name, last_name, gender, id_no)
values (1, '明', '白', 'W', '522442200011230083');
insert into passengers (user_id, first_name, last_name, gender, id_no)
values (2, 'Jack', 'Steven', 'M', '522762200011240237');
insert into passengers (user_id, first_name, last_name, gender, id_no)
values (2, 'Jane', 'Steven', 'W', '522967200106220082');
