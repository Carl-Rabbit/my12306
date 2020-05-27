create or replace function generate_seat(train_no_0 varchar(12), train_kind_0 char)
    returns integer
as
$$
begin
    if train_kind_0 in ('C', 'D', 'G', 'P', 'S', 'Y') then
        --level 1
        -- sleeper: 一节车厢共有6个房间。一个房间2个铺位
        for i in 1..6
            loop
                insert into seats (train_no, carriage, location, code, class, type)
                values (train_no_0, 1, i, '1', 'A', 'W');

                insert into seats (train_no, carriage, location, code, class, type)
                values (train_no_0, 1, i, '2', 'A', 'W');
            end loop;


        --seat: 一节车厢共有8排。一排3个座位

        for i in 1..8
            loop
                insert into seats (train_no, carriage, location, code, class, type)
                values (train_no_0, 2, i, 'A', 'A', 'Z');
                insert into seats (train_no, carriage, location, code, class, type)
                values (train_no_0, 2, i, 'C', 'A', 'Z');
                insert into seats (train_no, carriage, location, code, class, type)
                values (train_no_0, 2, i, 'F', 'A', 'Z');
            end loop;


        -- level 2
        -- sleeper: 3-4车。一节车厢共有8个房间。一个房间4个铺位
        for i in 3..4
            loop
                for j in 1..8
                    loop
                        for k in 1..4
                            loop
                                insert into seats (train_no, carriage, location, code, class, type)
                                values (train_no_0, i, j, cast(k as varchar(1)), 'B', 'W');
                            end loop;
                    end loop;
            end loop;


        -- seat: 5-6车。一节车厢共有16排。一排4个座位
        for i in 5..6
            loop
                for j in 1..16
                    loop
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'A', 'B', 'Z');
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'C', 'B', 'Z');
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'D', 'B', 'Z');
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'F', 'B', 'Z');
                    end loop;
            end loop;


        -- level 3
        -- sleeper: 7-11车。一节车厢共有8个房间。一个房间6个铺
        for i in 7..11
            loop
                for j in 1..8
                    loop
                        for k in 1..6
                            loop
                                insert into seats (train_no, carriage, location, code, class, type)
                                values (train_no_0, i, j, cast(k as varchar(1)), 'C', 'W');
                            end loop;
                    end loop;
            end loop;


        -- seat: 12-16车。一节车厢共有16排。一排5个座位
        for i in 12..16
            loop
                for j in 1..16
                    loop
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'A', 'B', 'Z');
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'C', 'B', 'Z');
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'D', 'B', 'Z');
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, 'F', 'B', 'Z');
                    end loop;
            end loop;
    else
        -- level 2
        -- sleeper: 1-3车。一节车厢共有11个房间。一个房间4个铺位
        for i in 1..3
            loop
                for j in 1..11
                    loop
                        for k in 1..4
                            loop
                                insert into seats (train_no, carriage, location, code, class, type)
                                values (train_no_0, i, j, cast(k as varchar(1)), 'B', 'W');
                            end loop;
                    end loop;
            end loop;

        -- seat: 4-6车。一节车厢共有80个座位, 2 + 2
        for i in 4..6
            loop
                for j in 1..80
                    loop
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, '', 'B', 'Z');
                    end loop;
            end loop;
        -- level 3
        -- sleeper: 7-11车。一节车厢共有11个房间。一个房间6个铺位
        for i in 7..11
            loop
                for j in 1..11
                    loop
                        for k in 1..6
                            loop
                                insert into seats (train_no, carriage, location, code, class, type)
                                values (train_no_0, i, j, cast(k as varchar(1)), 'C', 'W');
                            end loop;
                    end loop;
            end loop;
        -- seat: 12-16车。一节车厢共有118个座位, 3 + 2
        for i in 12..16
            loop
                for j in 1..118
                    loop
                        insert into seats (train_no, carriage, location, code, class, type)
                        values (train_no_0, i, j, '', 'C', 'Z');
                    end loop;
            end loop;

    end if;

    return (select count(*) from seats where train_no = train_no_0);
exception
    when others then
        return 0;
end;
$$ language plpgsql;

-- test

select generate_seat('12000T999000', 'T');

select *
from seats
where train_no = '12000T999000';

delete from seats
where train_no = '12000T999000';