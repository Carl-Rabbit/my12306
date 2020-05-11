create table loggers
(
    time timestamp default now(),
    event varchar not null,
    result varchar not null
);