create table theatre (
    id uuid primary key,
    name varchar(128) not null
);

create table seat (
    id uuid primary key,
    theatre_id uuid not null,
    number varchar(16) not null,
    constraint fk_seat_theatre foreign key (theatre_id) references theatre(id)
);