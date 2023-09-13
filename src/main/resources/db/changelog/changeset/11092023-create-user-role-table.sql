create table user_role(
    user_id bigint not null,
    role_id bigint not null,
    foreign key (user_id) references users(id),
    foreign key (role_id) references role(id)
);