create table loggers
(
    time   timestamp default now(),
    event  varchar not null,
    result varchar not null
);

-- CREATE TABLE logging_event
-- (
--     event_id          BIGSERIAL PRIMARY KEY,
--     timestamp         BIGINT       NOT NULL,
--     formatted_message TEXT         NOT NULL,
--     logger_name       VARCHAR(254) NOT NULL,
--     level_string      VARCHAR(254) NOT NULL,
--     thread_name       VARCHAR(254),
--     reference_flag    SMALLINT,
--     caller_filename   VARCHAR(254) NOT NULL,
--     caller_class      VARCHAR(254) NOT NULL,
--     caller_method     VARCHAR(254) NOT NULL,
--     caller_line       CHAR(4)      NOT NULL,
--     arg0              VARCHAR(254),
--     arg1              VARCHAR(254),
--     arg2              VARCHAR(254),
--     arg3              VARCHAR(254)
--
-- );
--
-- CREATE TABLE logging_event_property
-- (
--     event_id     BIGINT       NOT NULL,
--     mapped_key   VARCHAR(254) NOT NULL,
--     mapped_value VARCHAR(1024),
--     PRIMARY KEY (event_id, mapped_key),
--     FOREIGN KEY (event_id) REFERENCES logging_event (event_id)
-- );
--
-- CREATE TABLE logging_event_exception
-- (
--     event_id   BIGINT       NOT NULL,
--     i          SMALLINT     NOT NULL,
--     trace_line VARCHAR(254) NOT NULL,
--     PRIMARY KEY (event_id, i),
--     FOREIGN KEY (event_id) REFERENCES logging_event (event_id)
-- );