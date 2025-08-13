create table showtime (
    id uuid primary key,
    movie_id uuid not null,
    theatre_id uuid not null,
    starting_at timestamp not null,
    ending_at timestamp not null,
    price decimal not null,
    constraint fk_showtime_movie foreign key (movie_id) references movie(id),
    constraint fk_showtime_theatre foreign key (theatre_id) references theatre(id)
);