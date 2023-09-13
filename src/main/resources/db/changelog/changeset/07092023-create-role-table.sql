create table if not exists role(
    id bigserial primary key,
    name varchar(255) not null unique
);