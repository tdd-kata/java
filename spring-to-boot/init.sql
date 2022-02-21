create table IF NOT EXISTS users
(
    username varchar(255) unique not null
);

insert into users (username)
values ('markruler');
