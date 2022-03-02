create table city_user (
    id bigserial primary key,
    username varchar(64) not null,
    password varchar(255) not null,
    credentials_expired boolean not null default false,
    account_expired boolean not null default false,
    account_locked boolean not null default false,
    is_enabled boolean not null default false,
    created_on timestamp not null default now(),
    updated_on timestamp null default null
);

create table user_contact (
    user_id bigint not null primary key,
    first_name varchar(64) not null,
    last_name varchar(64) null default null,
    phone varchar(16) null default null,
    email varchar(128) null default null,
    foreign key (user_id) references city_user (id)
);

CREATE FUNCTION trigger_user_on_update() RETURNS trigger AS $$
    BEGIN
        update city_user set updated_on = now() where id = NEW.id;
    END;
$$ LANGUAGE plpgsql;

create trigger user_updated
    after update on city_user
    for each row
    execute procedure trigger_user_on_update();


create table user_authority (
    id bigserial primary key,
    authority varchar(32) not null,
    user_id bigint not null,
    created_on timestamp not null default now(),
    foreign key (user_id) references city_user (id)
);





