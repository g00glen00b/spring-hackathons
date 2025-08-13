create table "user" (
    id uuid primary key,
    username varchar(128) not null,
    password varchar(128) not null,
    admin boolean not null
);