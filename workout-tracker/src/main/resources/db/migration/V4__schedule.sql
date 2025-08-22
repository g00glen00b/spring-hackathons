create table workout_schedule (
    id uuid primary key,
    workout_id uuid not null,
    planned_start timestamp not null,
    actual_start timestamp null,
    actual_end timestamp null,
    constraint fk_workout_schedule_workout foreign key (workout_id) references workout(id) on delete cascade
);