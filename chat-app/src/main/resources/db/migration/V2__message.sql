create table room
(
    id   uuid        not null,
    name varchar(32) not null,
    constraint pk_room primary key (id),
    constraint uq_room_name unique (name)
);

create table message
(
    id      uuid         not null,
    room_id uuid         not null,
    user_id uuid         not null,
    message varchar(256) not null,
    created_at timestamp not null,
    constraint pk_message primary key (id),
    constraint fk_message_room foreign key (room_id) references room (id),
    constraint fk_message_user foreign key (user_id) references "user" (id)
);