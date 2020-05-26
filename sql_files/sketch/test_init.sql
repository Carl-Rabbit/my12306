delete
from orders
where orders.order_id > 2000;

delete
from tickets
where ticket_id > 5000;

with q as (select *
           from tickets
           where ticket_id > 5000)
select distinct seat_info
from q