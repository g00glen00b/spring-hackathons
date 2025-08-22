create table workout (
    id uuid primary key,
    user_id uuid not null,
    name varchar(64) not null,
    constraint fk_workout_user foreign key (user_id) references "user"(id) on delete cascade
);

create table workout_exercise (
    id uuid primary key,
    exercise_id uuid not null,
    workout_id uuid not null,
    sets integer not null,
    reps integer not null,
    weight_in_kg decimal null,
    constraint fk_workout_exercise_workout foreign key (workout_id) references workout(id) on delete cascade,
    constraint fk_workout_exercise_exercise foreign key (exercise_id) references exercise(id) on delete cascade
);