insert into article (title, published_at, content)
values ('First post', '2025-07-21', '### Title' + char(13) + char(10) + 'This is some content'),
       ('Second post', '2025-07-22', '### Title' + char(13) + char(10) + 'Foo bar'),
       ('Third post', '2025-07-23', '### Title' + char(13) + char(10) + 'This is some content with **markup**'),
       ('Fourth post', '2025-07-24', '### Title' + char(13) + char(10) + '### Subtitle' + char(13) + char(10) + 'This is more content');