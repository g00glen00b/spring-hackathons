create table "user" (
    id uuid primary key,
    username varchar(32) not null,
    password varchar(128) not null
);