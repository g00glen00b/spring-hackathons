create table "user"
(
    id       uuid         not null,
    username varchar(128) not null,
    password varchar(128) not null,
    online   bool         not null,
    constraint pk_user primary key (id),
    constraint uq_user_username unique (username)
);