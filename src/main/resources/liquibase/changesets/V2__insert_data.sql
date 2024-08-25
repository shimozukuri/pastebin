insert into users (username, password, email)
values ('user', '$2y$10$enNm4tTSVWZ5NnK6r/FGi.J6ADQ9a4QO2iY2mAZFc0sL.ho6SXNLK', 'user@email.com'),
       ('admin', '$2y$10$enNm4tTSVWZ5NnK6r/FGi.J6ADQ9a4QO2iY2mAZFc0sL.ho6SXNLK', 'admin@email.com');

insert into notes (title)
values ('User note'),
       ('Admin note');

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users_notes (user_id, note_id)
values (1, 1),
       (2, 2);

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);