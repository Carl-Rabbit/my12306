create or replace function cal_prices(train_kind char, mileage integer)
    returns numeric(7, 2)[]
as
$$
declare
    price1   double precision;
    price2   double precision;
    price3   double precision;
    price4   double precision;
    price5   double precision;
    price6   double precision;
    p        double precision;
    plus     double precision;
    mileage1 double precision;
begin
    if train_kind in ('C', 'D', 'G', 'P', 'S', 'Y') then
        if mileage <= 200 then
            p := 0.05681;
            price6 := (mileage * p) * (2 + 0.25 + 0.4) * 1.5;
            price4 := (mileage * p) * (2 + 0.25 + 0.4) * 1.5 * 1.5;
            price2 := (mileage * p) * (2 + 0.25 + 0.4) * 1.5 * 3;
            price5 := (mileage * p) * (2 + 0.25 + 0.4 + 1.3) * 1.5 * 1.5;
            price3 := price5 * 1.5;
            price1 := price3 * 1.5;
        elseif mileage <= 500 then
            p := 0.052749;
            mileage1 := mileage - 200;
            plus := 11.722;
            price6 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 1.5;
            price2 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 3;
            price5 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.3) * 1.5 * 1.5;
            price3 := price5 * 1.5;
            price1 := price3 * 1.5;
        elseif mileage <= 1000 then
            p := 0.046888;
            mileage1 := mileage - 500;
            plus := 27.5467;
            price6 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 1.5;
            price2 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 3;
            price5 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.3) * 1.5 * 1.5;
            price3 := price5 * 1.5;
            price1 := price3 * 1.5;
        elseif mileage <= 1500 then
            p := 0.041027;
            mileage1 := mileage - 1000;
            plus := 50.9907;
            price6 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 1.5;
            price2 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 3;
            price5 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.3) * 1.5 * 1.5;
            price3 := price5 * 1.5;
            price1 := price3 * 1.5;
        elseif mileage <= 2500 then
            p := 0.035166;
            mileage1 := mileage - 1500;
            plus := 71.5042;
            price6 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 1.5;
            price2 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 3;
            price5 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.3) * 1.5 * 1.5;
            price3 := price5 * 1.5;
            price1 := price3 * 1.5;
        else
            p := 0.029305;
            mileage1 := mileage - 2500;
            plus = 106.6702;
            price6 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 1.5;
            price2 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5 * 3;
            price5 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.3) * 1.5 * 1.5;
            price3 := price5 * 1.5;
            price1 := price3 * 1.5;
        end if;
    elseif train_kind in ('K', 'T', 'Z') then
        if mileage <= 200 then
            p := 0.05681;
            mileage1 := mileage;
            price6 := (mileage1 * p) * (1 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p) * (2 + 0.25 + 0.4) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p) * (2 + 0.25 + 0.4 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p) * (1 + 0.25 + 0.4 + 1.2) * 1.5 + 10;
        elseif mileage <= 500 then
            p := 0.052749;
            mileage1 := mileage - 200;
            plus := 11.722;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.4 + 1.2) * 1.5 + 10;
        elseif mileage <= 1000 then
            p := 0.046888;
            mileage1 := mileage - 500;
            plus := 27.5467;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.4 + 1.2) * 1.5 + 10;
        elseif mileage <= 1500 then
            p := 0.041027;
            mileage1 := mileage - 1000;
            plus := 50.9907;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.4 + 1.2) * 1.5 + 10;
        elseif mileage <= 2500 then
            p := 0.035166;
            mileage1 := mileage - 1500;
            plus := 71.5042;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.4 + 1.2) * 1.5 + 10;
        else
            p := 0.029305;
            mileage1 := mileage - 2500;
            plus := 106.6702;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.4 + 1.2) * 1.5 + 10;
        end if;
    else
        if mileage <= 200 then
            p := 0.05681;
            mileage1 := mileage;
            price6 := (mileage1 * p) * (1 + 0.25 + 0.2) * 1.5;
            price4 := (mileage1 * p) * (2 + 0.25 + 0.2) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p) * (2 + 0.25 + 0.2 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p) * (1 + 0.25 + 0.2 + 1.2) * 1.5 + 10;
        elseif mileage <= 500 then
            p := 0.052749;
            mileage1 := mileage - 200;
            plus := 11.722;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.4) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.4) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.4 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.4 + 1.2) * 1.5 + 10;
        elseif mileage <= 1000 then
            p := 0.046888;
            mileage1 := mileage - 500;
            plus := 27.5467;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.2) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.2) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.2 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.2 + 1.2) * 1.5 + 10;
        elseif mileage <= 1500 then
            p := 0.041027;
            mileage1 := mileage - 1000;
            plus := 50.9907;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.2) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.2) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.2 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.2 + 1.2) * 1.5 + 10;
        elseif mileage <= 2500 then
            p := 0.035166;
            mileage1 := mileage - 1500;
            plus := 71.5042;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.2) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.2) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.2 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.2 + 1.2) * 1.5 + 10;
        else
            p := 0.029305;
            mileage1 := mileage - 2500;
            plus := 106.6702;
            price6 := (mileage1 * p + plus) * (1 + 0.25 + 0.2) * 1.5;
            price4 := (mileage1 * p + plus) * (2 + 0.25 + 0.2) * 1.5;
            price2 := price4 * 1.5;
            price3 := (mileage1 * p + plus) * (2 + 0.25 + 0.2 + 1.8) * 1.5 + 10;
            price1 := price3 * 1.5;
            price5 := (mileage1 * p + plus) * (1 + 0.25 + 0.2 + 1.2) * 1.5 + 10;
        end if;
    end if;
    return array [(price1 * 4)::numeric(10, 2),
        (price2 * 3)::numeric(7, 2),
        (price3 * 4)::numeric(7, 2),
        (price4 * 3)::numeric(7, 2),
        (price5 * 2)::numeric(7, 2),
        (price6 * 2)::numeric(7, 2)];
end;
$$ language plpgsql;

-- update to database

create or replace function tmp_prices_fix() returns void
as
$$
declare
    rc     record;
    prices money[];
begin
    for rc in (select * from time_details)
        loop
            prices := cal_prices(substr(rc.train_code, 1, 1),
                                 rc.mileage);

            update time_details
            set aw_price = prices[1],
                az_price = prices[2],
                bw_price = prices[3],
                bz_price = prices[4],
                cw_price = prices[5],
                cz_price = prices[6]
            where time_detail_id = rc.time_detail_id;
        end loop;
end;
$$ language plpgsql;

select tmp_prices_fix()