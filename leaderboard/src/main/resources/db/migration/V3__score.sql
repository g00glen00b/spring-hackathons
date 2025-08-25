create table score (
    game_id uuid not null,
    user_id uuid not null,
    score int not null,
    last_modified_at timestamp not null,
    constraint fk_score_game foreign key (game_id) references game(id),
    constraint fk_score_user foreign key (user_id) references "user"(id),
    constraint pk_score primary key (game_id, user_id)
);

create table score_aud (
    game_id uuid not null,
    user_id uuid not null,
    rev int not null,
    revtype smallint not null,
    score int not null,
    last_modified_at timestamp not null
);