begin;
rollback;

----------------------
-- fill table 'route_schedule'
----------------------

insert into route_schedule(train_code, train_no, depart_date)
select *, '2020-05-31'::date as depart_date
from route_sc_template;

with q as (select *
           from (select distinct td.train_code, train_no
                 from (select distinct train_code from time_details) as td
                          left join (select train_code, train_no
                                     from (select *, rank() over (partition by train_code order by train_no) as rnk
                                           from train_code_no) x
                                     where rnk = 1) tcn on td.train_code = tcn.train_code) x
           order by length(x.train_code), x.train_code)
select train_code, train_no
into route_sc_template
from q;

select *
from route_sc_template;

select *
from route_schedule
where depart_date = '2020-05-30'::date;

----------------------
-- fill table 'trains'
----------------------

select coalesce(type, '普通') as train_kind, count(*)
from (with q as (select train_no, substr(train_no, 2, 8) as str
                 from (select distinct train_no
                       from train_code_no) x)
      select train_no, substring(q.str from '_%#"[A-Z]#"%_' for '#') as type
      from q) x
group by type;

truncate trains;

insert into trains(train_no, train_kind)
select distinct train_no,
                case when train_kind ~ '[0-9]' then ' ' else train_kind end
from (select train_no, substr(train_code, 1, 1) as train_kind
      from route_sc_template) x





