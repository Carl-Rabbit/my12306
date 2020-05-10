create table trains
(
    train_no   char(20) primary key,
    -- C: 城际, D: 动车, G: 高铁, K: 快速, P: 出境
    -- S: 城郊, T: 特快, Y: 旅游, Z: 直达特快, '': 绿皮
    -- 统一处理: (CDGPSY) 高铁型, (KTZ'') 普通型
    train_kind char not null,
    status     char not null default 'V' -- V: valid, I: invalid
);

-- select type
-- from (select distinct substr(train_code, 1, 1) as type
--       from train_code_no) as x
-- where type !~ '[0-9]';