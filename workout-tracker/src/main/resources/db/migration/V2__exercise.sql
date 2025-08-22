create type exercise_category as enum ('CARDIO', 'STRENGTH', 'FLEXIBILITY');
create type muscle_group as enum ('CHEST', 'BACK', 'LEGS', 'CORE', 'ARMS');

create table exercise (
    id uuid primary key,
    name varchar(64) not null,
    description varchar(128) not null,
    category varchar(16) not null,
    muscle_group varchar(16) not null
);

insert into exercise (id, name, description, category, muscle_group) values
-- CARDIO
('5a168eb6-166d-4444-8188-0020222d3f00', 'Running', 'Continuous jogging or sprinting for cardiovascular health', 'CARDIO', 'LEGS'),
('f8cd756c-97bf-43e8-b50a-d04b67fa4709', 'Cycling', 'Outdoor or stationary bike workout', 'CARDIO', 'LEGS'),
('fac2d1f6-6ee7-4df0-9000-4b285d38833a', 'Jump Rope', 'Rope skipping to improve stamina and coordination', 'CARDIO', 'CORE'),

-- STRENGTH
('0e34854d-2053-46db-86f3-a1867cd14bae', 'Bench Press', 'Pressing a barbell from chest level to strengthen chest and arms', 'STRENGTH', 'CHEST'),
('1b8ccc44-312f-4fdd-8a87-4b8c0a3d8b1c', 'Deadlift', 'Lifting a loaded barbell from the ground to standing position', 'STRENGTH', 'BACK'),
('9d73b7cc-d76c-41ac-85f8-5946b7325b07', 'Squat', 'Lowering and raising the body with weights to strengthen legs and core', 'STRENGTH', 'LEGS'),
('241da1d0-9df1-4cbd-85b6-d8069e32a3bd', 'Plank', 'Holding a push-up like position to strengthen core stability', 'STRENGTH', 'CORE'),
('514144d7-a58c-4f5c-b64c-f65368299ac7', 'Bicep Curl', 'Curling dumbbells to build arm strength', 'STRENGTH', 'ARMS'),

-- FLEXIBILITY
('319ad81d-18ea-4b3a-8d76-4fdc4983b864', 'Yoga Stretch', 'Full-body stretching sequence to improve flexibility', 'FLEXIBILITY', 'CORE'),
('75f779e7-38a4-4198-9f7f-4504f14525d3', 'Hamstring Stretch', 'Stretching the hamstrings to improve leg flexibility', 'FLEXIBILITY', 'LEGS'),
('8dfa4d53-aa54-4dbd-880f-f99d60115202', 'Shoulder Stretch', 'Arm and shoulder mobility exercise', 'FLEXIBILITY', 'ARMS'),
('22dccf8c-a004-43fe-8c20-82b7b5361276', 'Cat-Cow', 'Back flexibility exercise from yoga', 'FLEXIBILITY', 'BACK');