create table card(
    id bigserial primary key,
    word varchar(255) not null,
    translation varchar(255) not null,
    transcription varchar(255) not null,
    card_set_id bigint not null,
    foreign key (card_set_id) references card_set(id)
);