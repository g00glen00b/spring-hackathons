insert into movie (id, title, description, duration) values
    ('e3c3126b-4ac2-4dab-b7d5-17cf625e796d', 'The Dark Knight', 'When a menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman, James Gordon and Harvey Dent must work together to put an end to the madness.', 'PT2H32M'),
    ('7c20a9c8-7e6b-4c20-bf2e-91fda65c2b1b', 'Interstellar', 'When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.', 'PT2H49M'),
    ('7923c0c6-a56d-440e-b198-5c6e3813c2a9', 'Avatar', 'A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.', 'PT2H42M'),
    ('48d1e4f9-adc8-4447-b7d6-5bc7968af7cb', 'Harry Potter And The Deathly Hallows - Part 2', 'As the battle between the forces of good and evil in the wizarding world escalates, Harry Potter draws ever closer to his final confrontation with Voldemort.', 'PT2H10M'),
    ('717ab8b4-359b-40d0-8ff0-03933d0b6975', 'The Dark Knight Rises', 'Bane, an imposing terrorist, attacks Gotham City and disrupts its eight-year-long period of peace. This forces Bruce Wayne to come out of hiding and don the cape and cowl of Batman again.', 'PT2H44M'),
    ('17f8638a-036e-46c3-b976-d764eb5347cb', 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'PT2H55M'),
    ('cb04de04-f7ff-4bf9-abf4-6590c1e81b8a', 'Titanic', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 'PT3H14M'),
    ('9da0c046-dbee-4c1d-9dda-4ec2485af90f', 'Harry Potter And The Sorcerer''s Stone', 'An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.', 'PT2H32M'),
    ('0ca63dc9-8282-47b1-8051-a860494507ca', 'Man Of Steel', 'An alien child is evacuated from his dying world and sent to Earth to live among humans. His peace is threatened when other survivors of his home planet invade Earth.', 'PT2H23M'),
    ('c86d0622-bd10-4c61-b735-ed9f0bd273de', 'Wonder Woman', 'When a pilot crashes and tells of conflict in the outside world, Diana, an Amazonian warrior in training, leaves home to fight a war, discovering her full powers and true destiny.', 'PT2H21M'),
    ('0fe6a806-250c-4bd3-a6e3-ad96d210a87e', 'Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'PT2H34M'),
    ('ab84acaf-0064-4cc3-9304-af99f6f905ef', 'The Shawshank Redemption', 'A banker convicted of uxoricide forms a friendship over a quarter century with a hardened convict, while maintaining his innocence and trying to remain hopeful through simple compassion.', 'PT2H22M'),
    ('df49942d-91c3-4ae9-9ba8-f9d969bc2ba4', 'Harry Potter And The Deathly Hallows - Part 1', 'Harry Potter is tasked with the dangerous and seemingly impossible task of locating and destroying Voldemort''s remaining Horcruxes. Harry must rely on Ron and Hermione more than ever, but dark forces threaten to tear them apart.', 'PT2H26M'),
    ('7c5283cf-3f64-45f3-af1d-040bb41e1987', 'Batman v Superman: Dawn of Justice', 'Batman is manipulated by Lex Luthor to fear Superman. Superman´s existence is meanwhile dividing the world and he is framed for murder during an international crisis. The heroes clash and force the neutral Wonder Woman to reemerge.', 'PT2H31M');

insert into movie_genre (movie_id, genre_id) values
    ('e3c3126b-4ac2-4dab-b7d5-17cf625e796d', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('e3c3126b-4ac2-4dab-b7d5-17cf625e796d', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('e3c3126b-4ac2-4dab-b7d5-17cf625e796d', '53388a85-1d77-40c8-9c18-ee21ed46d6aa'),
    ('e3c3126b-4ac2-4dab-b7d5-17cf625e796d', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),
    ('e3c3126b-4ac2-4dab-b7d5-17cf625e796d', 'c1167f46-b105-475d-9cb0-5ea4351a2090'),

    ('7c20a9c8-7e6b-4c20-bf2e-91fda65c2b1b', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('7c20a9c8-7e6b-4c20-bf2e-91fda65c2b1b', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('7c20a9c8-7e6b-4c20-bf2e-91fda65c2b1b', 'f7fd9ac8-3906-4ebb-97e2-ca23b40fede5'),
    ('7c20a9c8-7e6b-4c20-bf2e-91fda65c2b1b', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),
    ('7c20a9c8-7e6b-4c20-bf2e-91fda65c2b1b', 'c1167f46-b105-475d-9cb0-5ea4351a2090'),

    ('7923c0c6-a56d-440e-b198-5c6e3813c2a9', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('7923c0c6-a56d-440e-b198-5c6e3813c2a9', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('7923c0c6-a56d-440e-b198-5c6e3813c2a9', 'f7fd9ac8-3906-4ebb-97e2-ca23b40fede5'),
    ('7923c0c6-a56d-440e-b198-5c6e3813c2a9', '1e395d44-709f-4b05-b3c3-bbc45f3d142e'),

    ('48d1e4f9-adc8-4447-b7d6-5bc7968af7cb', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('48d1e4f9-adc8-4447-b7d6-5bc7968af7cb', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('48d1e4f9-adc8-4447-b7d6-5bc7968af7cb', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),
    ('48d1e4f9-adc8-4447-b7d6-5bc7968af7cb', 'b031867a-d015-4475-ab9b-f6cafdfd1c3f'),
    ('48d1e4f9-adc8-4447-b7d6-5bc7968af7cb', '1e395d44-709f-4b05-b3c3-bbc45f3d142e'),
    ('48d1e4f9-adc8-4447-b7d6-5bc7968af7cb', '886846e1-aef7-46d3-ac75-b9c27f98bffa'),

    ('717ab8b4-359b-40d0-8ff0-03933d0b6975', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('717ab8b4-359b-40d0-8ff0-03933d0b6975', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('717ab8b4-359b-40d0-8ff0-03933d0b6975', '53388a85-1d77-40c8-9c18-ee21ed46d6aa'),
    ('717ab8b4-359b-40d0-8ff0-03933d0b6975', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),
    ('717ab8b4-359b-40d0-8ff0-03933d0b6975', 'c1167f46-b105-475d-9cb0-5ea4351a2090'),

    ('17f8638a-036e-46c3-b976-d764eb5347cb', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('17f8638a-036e-46c3-b976-d764eb5347cb', '53388a85-1d77-40c8-9c18-ee21ed46d6aa'),
    ('17f8638a-036e-46c3-b976-d764eb5347cb', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),

    ('cb04de04-f7ff-4bf9-abf4-6590c1e81b8a', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('cb04de04-f7ff-4bf9-abf4-6590c1e81b8a', '3da5320b-5526-4a13-9edf-e86ecf587580'),
    ('cb04de04-f7ff-4bf9-abf4-6590c1e81b8a', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),

    ('9da0c046-dbee-4c1d-9dda-4ec2485af90f', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('9da0c046-dbee-4c1d-9dda-4ec2485af90f', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('9da0c046-dbee-4c1d-9dda-4ec2485af90f', 'b031867a-d015-4475-ab9b-f6cafdfd1c3f'),
    ('9da0c046-dbee-4c1d-9dda-4ec2485af90f', '1e395d44-709f-4b05-b3c3-bbc45f3d142e'),

    ('0ca63dc9-8282-47b1-8051-a860494507ca', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('0ca63dc9-8282-47b1-8051-a860494507ca', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('0ca63dc9-8282-47b1-8051-a860494507ca', '1e395d44-709f-4b05-b3c3-bbc45f3d142e'),
    ('0ca63dc9-8282-47b1-8051-a860494507ca', 'f7fd9ac8-3906-4ebb-97e2-ca23b40fede5'),

    ('c86d0622-bd10-4c61-b735-ed9f0bd273de', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('c86d0622-bd10-4c61-b735-ed9f0bd273de', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('c86d0622-bd10-4c61-b735-ed9f0bd273de', '1e395d44-709f-4b05-b3c3-bbc45f3d142e'),
    ('c86d0622-bd10-4c61-b735-ed9f0bd273de', 'f7fd9ac8-3906-4ebb-97e2-ca23b40fede5'),
    ('c86d0622-bd10-4c61-b735-ed9f0bd273de', '23926189-aed7-4af5-b029-01805113cb72'),

    ('0fe6a806-250c-4bd3-a6e3-ad96d210a87e', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('0fe6a806-250c-4bd3-a6e3-ad96d210a87e', '53388a85-1d77-40c8-9c18-ee21ed46d6aa'),
    ('0fe6a806-250c-4bd3-a6e3-ad96d210a87e', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),

    ('ab84acaf-0064-4cc3-9304-af99f6f905ef', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('ab84acaf-0064-4cc3-9304-af99f6f905ef', '53388a85-1d77-40c8-9c18-ee21ed46d6aa'),
    ('ab84acaf-0064-4cc3-9304-af99f6f905ef', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),

    ('df49942d-91c3-4ae9-9ba8-f9d969bc2ba4', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('df49942d-91c3-4ae9-9ba8-f9d969bc2ba4', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('df49942d-91c3-4ae9-9ba8-f9d969bc2ba4', 'd9ca02b5-c034-401c-bd77-0dac77a47c7e'),
    ('df49942d-91c3-4ae9-9ba8-f9d969bc2ba4', 'b031867a-d015-4475-ab9b-f6cafdfd1c3f'),
    ('df49942d-91c3-4ae9-9ba8-f9d969bc2ba4', '1e395d44-709f-4b05-b3c3-bbc45f3d142e'),
    ('df49942d-91c3-4ae9-9ba8-f9d969bc2ba4', '886846e1-aef7-46d3-ac75-b9c27f98bffa'),

    ('7c5283cf-3f64-45f3-af1d-040bb41e1987', '5bcadaa2-add3-47ab-bebd-503cd1ee5a14'),
    ('7c5283cf-3f64-45f3-af1d-040bb41e1987', '2a20133f-f6da-480a-b96c-ab5e9f5b2920'),
    ('7c5283cf-3f64-45f3-af1d-040bb41e1987', '1e395d44-709f-4b05-b3c3-bbc45f3d142e'),
    ('7c5283cf-3f64-45f3-af1d-040bb41e1987', 'f7fd9ac8-3906-4ebb-97e2-ca23b40fede5');