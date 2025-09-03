create table message_read_user (
    message_id uuid not null,
    user_id uuid not null,
    constraint pk_message_read_user primary key (message_id, user_id)
)