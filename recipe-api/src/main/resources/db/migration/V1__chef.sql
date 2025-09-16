create table chef (
    id uuid not null,
    username varchar(128) not null,
    displayname varchar(128) not null,
    password varchar(128) not null,
    constraint pk_chef primary key (id),
    constraint uq_chef_username unique (username)
);