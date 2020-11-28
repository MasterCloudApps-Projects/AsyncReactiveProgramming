CREATE TABLE IF NOT EXISTS astronauts
(
    id            bigserial not null
        constraint astronauts_pk primary key,
    name          varchar,
    status        varchar(1),
    birth_place   varchar(255),
    gender        varchar(1),
    space_flights int default 0,
    space_walks   int default 0,
    missions      text
);