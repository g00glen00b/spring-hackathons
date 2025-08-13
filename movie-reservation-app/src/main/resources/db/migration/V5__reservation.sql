create table reservation (
    id uuid primary key,
    showtime_id uuid not null,
    owner_id uuid not null,
    constraint fk_reservation_reservable_showtime foreign key (showtime_id) references showtime(id),
    constraint fk_reservation_owner foreign key (owner_id) references "user"(id)
);

create table reservation_seat (
    reservation_id uuid not null,
    seat_id uuid not null,
    primary key (reservation_id, seat_id)
);