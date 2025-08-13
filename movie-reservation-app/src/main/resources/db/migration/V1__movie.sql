create table movie (
    id uuid primary key,
    title varchar(128) not null,
    description varchar(512) not null,
    duration varchar(16) not null
);

create table genre (
    id uuid primary key,
    name varchar(32) not null
);

create table movie_genre (
    movie_id uuid not null,
    genre_id uuid not null,
    primary key (movie_id, genre_id),
    constraint fk_movie_genre_movie foreign key (movie_id) references movie(id),
    constraint fk_movie_genre_genre foreign key (genre_id) references genre(id)
);