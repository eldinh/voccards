create table card_set(
    id bigserial primary key,
    name varchar(255),
    creator_id bigint not null,
    foreign key (creator_id) references users(id)
);