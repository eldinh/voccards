create table user_card_set(
    user_id bigint not null,
    card_set_id bigint not null,
    foreign key (user_id) references users(id),
    foreign key (card_set_id) references card_set(id)
);