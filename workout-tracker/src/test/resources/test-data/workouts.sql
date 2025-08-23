insert into workout (id, user_id, name)
values ('7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3', '90bcce34-b4b5-418e-8c81-f095e638073c', 'Advanced workout'),
       ('a70c5266-ade3-4ac7-8e79-4c734aacfab1', '7edae0d7-8cc8-42e3-a826-bcb455e57c89', 'Basic workout');

insert into workout_exercise (id, exercise_id, workout_id, sets, reps, weight_in_kg)
values ('d40aa781-5b4c-4e6c-81a5-1e909664a5da', '1b8ccc44-312f-4fdd-8a87-4b8c0a3d8b1c', '7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3', 5, 5, 150),
       ('6d292623-d0f5-4b0e-af8f-12c3d83ded92', '0e34854d-2053-46db-86f3-a1867cd14bae', '7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3', 5, 7, 100),
       ('ec050e99-ccb6-4beb-862c-b3f5b4e67cfd', '1b8ccc44-312f-4fdd-8a87-4b8c0a3d8b1c', 'a70c5266-ade3-4ac7-8e79-4c734aacfab1', 3, 7, 140);