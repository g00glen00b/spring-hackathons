create table game (
    id uuid not null,
    name varchar(128) not null,
    constraint pk_game primary key (id),
    constraint uq_game_name unique (name)
);

insert into game (id, name)
values ('03eb1cc9-fa24-4441-8951-ff3c51abf568', 'Bowling'),
       ('aad29d62-2a18-4e43-a71b-59c14c19abca', 'Darts'),
       ('f6e880bf-12fa-4cf5-94d4-48126bffc299', 'Golf'),
       ('601fd813-86c4-41a6-a25c-6ff356cd5297', 'Scrabble'),
       ('06f4c1dc-6f26-4a0e-9f86-c49c6cbb560d', 'Yahtzee'),
       ('c84b81ca-cf2b-4e0f-823a-a377084aad45', 'Pinball'),
       ('c5719e8c-05b8-428a-ac4f-85975a3180fd', 'Tetris'),
       ('286d65db-d775-4e20-9c76-ba31cfac5995', 'Cookie clicker'),
       ('2d00db89-a2a5-4c28-ab4c-4be9a66c80d4', 'Flappy bird'),
       ('23972cae-c483-41ad-9a28-cbab3c9f87b5', '2048'),
       ('008db774-3182-4aa4-90e9-f068b1de7a24', 'Snake'),
       ('c9bae555-5e1e-45c3-abf1-b1fc45cbab89', 'Temple Run');