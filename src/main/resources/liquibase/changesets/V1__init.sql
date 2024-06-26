create table if not exists users
(
    id bigserial primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    email varchar(255) not null unique
);

create table if not exists roles
(
    id bigserial primary key,
    name varchar(255) not null unique
);

create table if not exists notes
(
    id bigserial primary key,
    title varchar(255) not null unique
);

create table if not exists users_roles
(
    user_id bigserial not null,
    role_id bigserial not null,
    primary key (user_id, role_id),
    constraint fk_users_roles_users foreign key (user_id) references users (id),
    constraint fk_users_roles_roles foreign key (role_id) references roles (id)
);

create table if not exists users_notes
(
    user_id bigserial not null,
    note_id bigserial not null,
    primary key (user_id, note_id),
    constraint fk_users_notes_users foreign key (user_id) references users (id),
    constraint fk_users_notes_notes foreign key (note_id) references notes (id)
);
