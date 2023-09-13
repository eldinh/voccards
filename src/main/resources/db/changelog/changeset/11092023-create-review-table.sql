create table review(
    id bigserial primary key,
    user_id bigint not null,
    comment text not null,
    rate int not null,
    card_set_id bigint not null,
    foreign key (user_id) references users(id),
    foreign key (card_set_id) references card_set(id)
);